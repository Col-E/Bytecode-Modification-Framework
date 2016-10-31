package io.github.bmf;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.bmf.attribute.method.Local;
import io.github.bmf.consts.ConstClass;
import io.github.bmf.consts.ConstInterfaceMethod;
import io.github.bmf.consts.ConstInvokeDynamic;
import io.github.bmf.consts.ConstMethod;
import io.github.bmf.consts.ConstMethodHandle;
import io.github.bmf.consts.ConstMethodType;
import io.github.bmf.consts.ConstNameType;
import io.github.bmf.consts.ConstUTF8;
import io.github.bmf.consts.Constant;
import io.github.bmf.consts.ConstantType;
import io.github.bmf.consts.mapping.ConstMemberDesc;
import io.github.bmf.consts.mapping.ConstName;
import io.github.bmf.type.Type;
import io.github.bmf.util.ConstUtil;
import io.github.bmf.util.io.JarUtil;
import io.github.bmf.util.mapping.ClassMapping;
import io.github.bmf.util.mapping.Mapping;
import io.github.bmf.util.mapping.MemberMapping;
import io.github.bmf.util.mapping.MethodMapping;

public class JarReader {
    public static final int PASS_MAKE_CLASSES = 0;
    public static final int PASS_UPDATE_CONSTANTS = 1;
    public static final int PASS_LINK_HIERARCHY = 2;

    private final Mapping mapping;
    private final File file;
    private Map<String, ClassNode> classEntries;
    private Map<String, byte[]> fileEntries;

    /**
     * Creates a JarReader without reading from the file initially.
     * 
     * @param file
     */
    public JarReader(File file) {
        this(file, false);
    }

    /**
     * Creates a JarReader. If <i>read</i> is true the file will be read from
     * initially.
     * 
     * @param file
     * @param read
     */
    public JarReader(File file, boolean read) {
        if ((file == null) || !file
                .exists()) { throw new IllegalArgumentException("Invalid file given: " + file.getAbsolutePath()); }
        this.file = file;
        this.mapping = new Mapping();
        if (read) {
            read();
        }
    }

    /**
     * Reads from the jar file.
     */
    public void read() {
        try {
            Map<String, byte[]> classEntryBytes = JarUtil.readJarClasses(file);
            fileEntries = JarUtil.readJarNonClasses(file);
            classEntries = new HashMap<String, ClassNode>();
            for (String className : classEntryBytes.keySet()) {
                ClassNode cn = ClassReader.getNode(classEntryBytes.get(className));
                classEntries.put(className, cn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up the mapping.
     */
    public void genMappings() {
        genMappings(PASS_MAKE_CLASSES);
        genMappings(PASS_UPDATE_CONSTANTS);
        genMappings(PASS_LINK_HIERARCHY);
    }

    /**
     * Sets up part of the mapping based on the pass used.
     */
    public void genMappings(int pass) {
        for (ClassNode node : classEntries.values()) {
            if (pass == PASS_MAKE_CLASSES) {
                mapping.addMapping(mapping.makeMappingFromNode(node));
            } else if (pass == PASS_UPDATE_CONSTANTS) {
                String className = ConstUtil.getName(node);
                ConstClass cc = (ConstClass) node.getConst(node.classIndex);
                ConstClass ccs = (ConstClass) node.getConst(node.superIndex);
                node.setConst(cc.getValue(),
                        new ConstName(mapping.getClassName(ConstUtil.getUTF8String(node, cc.getValue()))));
                node.setConst(ccs.getValue(),
                        new ConstName(mapping.getClassName(ConstUtil.getUTF8String(node, ccs.getValue()))));
                ClassMapping cm = mapping.getMapping(className);
                for (MethodNode mn : node.methods) {
                    String name = ConstUtil.getUTF8String(node, mn.name);
                    String desc = ConstUtil.getUTF8String(node, mn.desc);
                    MethodMapping mm = (MethodMapping) cm.getMemberMapping(name, desc);
                    node.setConst(mn.name, new ConstName(mm.name));
                    node.setConst(mn.desc, new ConstMemberDesc(mm.desc));
                    if (mn.code != null) {
                        if (mn.code.variables != null) {
                            // Lists of local data
                            List<Local> locals = mn.code.variables.variableTable.locals;
                            // TODO: Generics with LocalVariableType attribute
                            //
                            // List<LocalVariableType> types =
                            // mn.code.variableTypes.localTypes;
                            for (int i = 0; i < locals.size(); i++) {
                                // Get locals and their type
                                // Indices should line up
                                Local local = locals.get(i);
                                // Values
                                String lname = ConstUtil.getUTF8String(node, local.name);
                                String ldesc = ConstUtil.getUTF8String(node, local.desc);
                                // Replacing constants
                                MemberMapping var = new MemberMapping(lname, Type.variable(mapping, ldesc));
                                mm.addVariable(mapping, var);
                                node.setConst(local.name, new ConstName(var.name));
                                node.setConst(local.desc, new ConstMemberDesc(var.desc));
                            }
                        }
                    }
                }
                for (FieldNode fn : node.fields) {
                    String name = ConstUtil.getUTF8String(node, fn.name);
                    String desc = ConstUtil.getUTF8String(node, fn.desc);
                    node.setConst(fn.name, new ConstName(name));
                    node.setConst(fn.desc, new ConstMemberDesc(mapping.getDesc(className, name, desc)));
                }
                for (Constant<?> c : node.constants) {
                    if (c != null) {
                        switch (c.type) {
                        case INTERFACE_METHOD: {
                            ConstInterfaceMethod cim = (ConstInterfaceMethod) c;
                            String methodOwner = ConstUtil.getClassName(node, cim.getClassIndex());
                            ConstNameType cnt = (ConstNameType) node.getConst(cim.getNameTypeIndex());
                            String name = ConstUtil.getUTF8String(node, cnt.getNameIndex());
                            String desc = ConstUtil.getUTF8String(node, cnt.getDescIndex());
                            ClassMapping owner = mapping.getMapping(methodOwner);
                            MemberMapping method = owner.getMemberMapping(name, desc);
                            node.setConst(cnt.getNameIndex(), new ConstName(method.name));
                            node.setConst(cnt.getDescIndex(), new ConstMemberDesc(method.desc));
                            break;
                        }
                        case INVOKEDYNAMIC: {
                            ConstInvokeDynamic cid = (ConstInvokeDynamic) c;
                            ConstNameType cnt = (ConstNameType) node.getConst(cid.getNameTypeIndex());
                            String name = ConstUtil.getUTF8String(node, cnt.getNameIndex());
                            String desc = ConstUtil.getUTF8String(node, cnt.getDescIndex());
                            MemberMapping method = cm.getMemberMapping(name, desc);
                            node.setConst(cnt.getNameIndex(), new ConstName(method.name));
                            node.setConst(cnt.getDescIndex(), new ConstMemberDesc(method.desc));
                            break;
                        }
                        case METHOD: {
                            ConstMethod cm2 = (ConstMethod) c;
                            String methodOwner = ConstUtil.getClassName(node, cm2.getClassIndex());
                            ConstNameType cnt = (ConstNameType) node.getConst(cm2.getNameTypeIndex());
                            String name = ConstUtil.getUTF8String(node, cnt.getNameIndex());
                            String desc = ConstUtil.getUTF8String(node, cnt.getDescIndex());
                            ClassMapping owner = mapping.getMapping(methodOwner);
                            MemberMapping method = owner.getMemberMapping(name, desc);
                            if (method == null) {
                                // TODO: This backup case is used if the mapping
                                // for the methodOwner doesn't have the method
                                // described by name/desc.
                                //
                                // Example:
                                // java/lang/Object.<init>()V
                                //
                                // A proper fix should be used instead since I
                                // can see this causing problems elsewhere.
                                method = cm.getMemberMapping(name, desc);
                            }
                            node.setConst(cnt.getNameIndex(), new ConstName(method.name));
                            node.setConst(cnt.getDescIndex(), new ConstMemberDesc(method.desc));
                            break;
                        }
                        default:
                            break;
                        }
                    }
                }
            } else if (pass == PASS_LINK_HIERARCHY) {
                String className = ConstUtil.getName(node);
                String superName = ConstUtil.getSuperName(node);
                ClassMapping cm = mapping.getMapping(className);
                ClassMapping sm = mapping.getMapping(superName);
                mapping.setParent(cm, sm);
                mapping.addChild(sm, cm);
                for (int i : node.interfaceIndices) {
                    String interfaceName = ConstUtil.getClassName(node, i);
                    ClassMapping im = mapping.getMapping(interfaceName);
                    mapping.addInterface(cm, im);
                    mapping.addChild(im, cm);

                }
            }
        }
    }

    /**
     * Saves the currently stored information to a given file.
     * 
     * @param fileOut
     */
    public void saveTo(File fileOut) {
        JarUtil.writeJar(file, fileOut, classEntries, fileEntries);

    }

    public File getFile() {
        return file;
    }

    public Map<String, ClassNode> getClassEntries() {
        return classEntries;
    }

    public Map<String, byte[]> getFileEntries() {
        return fileEntries;
    }

    public Mapping getMapping() {
        return mapping;
    }
}
