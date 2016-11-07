package io.github.bmf;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.bmf.attribute.clazz.InnerClass;
import io.github.bmf.attribute.method.Local;
import io.github.bmf.attribute.method.LocalVariableType;
import io.github.bmf.consts.ConstClass;
import io.github.bmf.consts.ConstField;
import io.github.bmf.consts.ConstInterfaceMethod;
import io.github.bmf.consts.ConstInvokeDynamic;
import io.github.bmf.consts.ConstMethod;
import io.github.bmf.consts.ConstNameType;
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
    public static final int PASS_FINISH_CLASSES = 1;
    public static final int PASS_LINK_HIERARCHY = 2;
    public static final int PASS_UPDATE_CONSTANTS = 3;

    private final Mapping mapping;
    private final File file;
    private Map<String, ClassNode> classEntries;
    private Map<String, byte[]> fileEntries;

    /**
     * Creates a JarReader without reading from the file initially. This also
     * prevents mappings from being generated initially.
     * 
     * @param file
     */
    public JarReader(File file) {
        this(file, false);
    }

    /**
     * Creates a JarReader that can read the file initially. No mappings are
     * generated. initially.
     * 
     * @param file
     *            Read and create ClassNodes on init.
     * @param read
     */
    public JarReader(File file, boolean read) {
        this(file, read, false);
    }

    /**
     * Creates a JarReader that can read the file and creates mappings
     * initially.
     * 
     * @param file
     * @param read
     *            Read and create ClassNodes on init.
     * @param genMappings
     *            Also create mappings on init.
     */
    public JarReader(File file, boolean read, boolean genMappings) {
        if ((file == null) || !file
                .exists()) { throw new IllegalArgumentException("Invalid file given: " + file.getAbsolutePath()); }
        this.file = file;
        this.mapping = new Mapping();
        if (read) {
            read();
            if (genMappings) {
                genMappings();
            }
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
        genMappings(PASS_FINISH_CLASSES);
        genMappings(PASS_LINK_HIERARCHY);
        genMappings(PASS_UPDATE_CONSTANTS);
    }

    /**
     * Sets up part of the mapping based on the pass used.
     */
    public void genMappings(int pass) {
        for (ClassNode node : classEntries.values()) {
            if (pass == PASS_MAKE_CLASSES) {
                mapping.addMapping(mapping.makeMappingFromNode(node));
            } else if (pass == PASS_FINISH_CLASSES) {
                mapping.addMemberMappings(node);
            } else if (pass == PASS_LINK_HIERARCHY) {
                String className = ConstUtil.getName(node);
                String superName = ConstUtil.getSuperName(node);
                ClassMapping cm = mapping.getMapping(className);
                ClassMapping sm = mapping.getMapping(superName);
                mapping.addParent(cm, sm);
                mapping.addChild(sm, cm);
                for (int i : node.interfaceIndices) {
                    String interfaceName = ConstUtil.getClassName(node, i);
                    ClassMapping im = mapping.getMapping(interfaceName);
                    mapping.addInterface(cm, im);
                    mapping.addChild(im, cm);
                }
            } else if (pass == PASS_UPDATE_CONSTANTS) {
                ConstClass cc = (ConstClass) node.getConst(node.classIndex);
                ConstClass ccs = (ConstClass) node.getConst(node.superIndex);
                String className = ConstUtil.getName(node);
                String superName = ConstUtil.getUTF8String(node, ccs.getValue());
                ClassMapping cm = mapping.getMapping(className);
                node.setConst(cc.getValue(), new ConstName(cm.name));
                node.setConst(ccs.getValue(), new ConstName(mapping.getClassName(superName)));
                if (node.innerClasses != null) {
                    for (InnerClass i : node.innerClasses.classes) {
                        // TODO: Inner classes are not updated correctly
                    }
                }
                for (MethodNode mn : node.methods) {
                    String name = ConstUtil.getUTF8String(node, mn.name);
                    String desc = ConstUtil.getUTF8String(node, mn.desc);
                    MethodMapping mm = (MethodMapping) cm.getMemberMapping(name, desc);
                    // In RARE cases usually due to obfuscation the class name
                    // and method name can point to the same value.
                    if (mn.name == cc.getValue()) {
                        node.addConst(new ConstName(cm.name));
                        cc.setValue(node.constants.size());
                    }
                    node.setConst(mn.name, new ConstName(mm.name));
                    node.setConst(mn.desc, new ConstMemberDesc(mm.desc));
                    if (mn.code != null) {
                        if (mn.code.variables != null) {
                            List<Local> locals = mn.code.variables.variableTable.locals;
                            for (Local local : locals) {
                                // Values
                                String lname = ConstUtil.getUTF8String(node, local.name);
                                String ldesc = ConstUtil.getUTF8String(node, local.desc);
                                // Replacing constants
                                MemberMapping var = new MemberMapping(lname, Type.variable(mapping, ldesc));
                                mm.addVariable(mapping, var);
                                node.setConst(local.name, new ConstName(var.name));
                                node.setConst(local.desc, new ConstMemberDesc(var.desc));
                            }
                            // TODO: Extra generics information.
                            if (mn.code.variableTypes != null) {
                                List<LocalVariableType> types = mn.code.variableTypes.localTypes;
                                for (LocalVariableType type : types) {
                                    String lname = ConstUtil.getUTF8String(node, type.name);
                                    String ldesc = ConstUtil.getUTF8String(node, type.signature);
                                    // System.out.println(lname + ":" + ldesc);
                                }
                            }
                        }
                    }
                }
                for (FieldNode fn : node.fields) {
                    String name = ConstUtil.getUTF8String(node, fn.name);
                    String desc = ConstUtil.getUTF8String(node, fn.desc);
                    // In RARE cases usually due to obfuscation the class name
                    // and field name can point to the same value.
                    if (fn.name == cc.getValue()) {
                        node.addConst(new ConstName(cm.name));
                        cc.setValue(node.constants.size());
                    }
                    node.setConst(fn.name, new ConstName(name));
                    node.setConst(fn.desc, new ConstMemberDesc(mapping.getDesc(className, name, desc)));
                }
                // Temporary, will be removed when method opcode reading is
                // complete
                for (int i = 0; i < node.constants.size(); i++) {
                    Constant<?> c = node.constants.get(i);
                    if (c != null) {
                        boolean dynamic = false;
                        int classI = -1, nt = -1;
                        String methodOwner = null, name = null, desc = null;
                        switch (c.type) {
                        case INTERFACE_METHOD:
                            ConstInterfaceMethod cim = (ConstInterfaceMethod) c;
                            classI = cim.getClassIndex();
                            nt = cim.getNameTypeIndex();
                            break;
                        case INVOKEDYNAMIC:
                            dynamic = true;
                            ConstInvokeDynamic cid = (ConstInvokeDynamic) c;
                            nt = cid.getNameTypeIndex();
                            break;
                        case METHOD:
                            ConstMethod cm2 = (ConstMethod) c;
                            classI = cm2.getClassIndex();
                            nt = cm2.getNameTypeIndex();
                            break;
                        case FIELD:
                            ConstField cf = (ConstField) c;
                            classI = cf.getClassIndex();
                            nt = cf.getNameTypeIndex();
                            break;
                        case CLASS:
                            // Sure this is all member based except for here,
                            // but it works when I have class constants I can't
                            // yet access for things like opcodes.
                            int nameI = ((ConstClass) c).getValue();
                            if (!(node.getConst(nameI) instanceof ConstName)) {
                                name = ConstUtil.getUTF8String(node, nameI);
                                // For some brilliant reason the following is
                                // considered a "legitimate" class name:
                                //
                                // [Lcom/example/Name;
                                // Happens mostly with enums, encountered in
                                // minecraft.jar testing.
                                // This is a poor fix but it's the only way
                                // logically that I can think of getting around
                                // it,
                                if (name.startsWith("[")) {
                                    name = name.replace("[", "");
                                    if (name.endsWith(";")) {
                                        name = name.substring(1, name.length() - 1);
                                    }
                                }
                                node.setConst(nameI, new ConstName(mapping.getClassName(name)));
                            }
                            break;
                        default:
                            break;
                        }
                        if (nt == -1) continue;
                        methodOwner = dynamic ? className : ConstUtil.getClassName(node, classI);
                        ConstNameType cnt = (ConstNameType) node.getConst(nt);
                        name = ConstUtil.getUTF8String(node, cnt.getNameIndex());
                        desc = ConstUtil.getUTF8String(node, cnt.getDescIndex());
                        ClassMapping owner = mapping.getMapping(methodOwner);
                        if (owner == null) {
                            continue;
                        }
                        MemberMapping method = mapping.getMemberMapping(owner, name, desc);
                        if (method == null) {
                            // if (methodOwner.length() < 16)
                            // System.out.println("2: " + className + " " +
                            // methodOwner + " " + name + " " + desc);
                            throw new RuntimeException(className + "   -> " + owner.name.original + ":" + name + desc);
                        }
                        // In RARE cases usually due to obfuscation the class
                        // name and method name can point to the same value.
                        if (cnt.getNameIndex() == cc.getValue()) {
                            node.addConst(new ConstName(cm.name));
                            cc.setValue(node.constants.size());
                        }
                        node.setConst(cnt.getNameIndex(), new ConstName(method.name));
                        node.setConst(cnt.getDescIndex(), new ConstMemberDesc(method.desc));
                    }
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
