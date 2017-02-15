package io.github.bmf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.bmf.attribute.clazz.InnerClass;
import io.github.bmf.attribute.method.Local;
import io.github.bmf.attribute.method.LocalVariableType;
import io.github.bmf.consts.ConstClass;
import io.github.bmf.consts.ConstField;
import io.github.bmf.consts.ConstInterfaceMethod;
import io.github.bmf.consts.ConstInvokeDynamic;
import io.github.bmf.consts.ConstMethod;
import io.github.bmf.consts.ConstNameType;
import io.github.bmf.consts.ConstUTF8;
import io.github.bmf.consts.Constant;
import io.github.bmf.consts.ConstantType;
import io.github.bmf.consts.mapping.ConstMemberDesc;
import io.github.bmf.consts.mapping.ConstName;
import io.github.bmf.exception.InvalidClassException;
import io.github.bmf.mapping.ClassMapping;
import io.github.bmf.mapping.Mapping;
import io.github.bmf.mapping.MemberMapping;
import io.github.bmf.mapping.MethodMapping;
import io.github.bmf.type.PrimitiveType;
import io.github.bmf.type.Type;
import io.github.bmf.util.Access;
import io.github.bmf.util.ConstUtil;
import io.github.bmf.util.ImmutableBox;
import io.github.bmf.util.io.JarUtil;

/**
 * Helper for loaded programs packaged in JAR files.
 */
public class JarReader {
    public static final int PASS_MAKE_CLASSES = 0;
    public static final int PASS_LINK_HIERARCHY = 1;
    public static final int PASS_FINISH_CLASSES = 2;
    public static final int PASS_SPLIT_NAME_DESC = 3;
    public static final int PASS_UPDATE_CONSTANTS = 4;

    private final Mapping mapping;
    private final File file;
    private List<File> libraries = new ArrayList<File>();
    private Map<String, ClassNode> classEntries;
    private Map<String, ClassNode> libClassEntries;
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
            // Library classes
            libClassEntries = new HashMap<String, ClassNode>();
            for (File lib : libraries) {
                Map<String, byte[]> libClassEntryBytes = JarUtil.readJarClasses(lib);
                for (String className : libClassEntryBytes.keySet()) {
                    try {
                        ClassNode cn = ClassReader.getNode(libClassEntryBytes.get(className));
                        libClassEntries.put(className, cn);
                    } catch (InvalidClassException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Main classes
            Map<String, byte[]> classEntryBytes = JarUtil.readJarClasses(file);
            fileEntries = JarUtil.readJarNonClasses(file);
            classEntries = new HashMap<String, ClassNode>();
            for (String className : classEntryBytes.keySet()) {
                try {
                    ClassNode cn = ClassReader.getNode(classEntryBytes.get(className));
                    classEntries.put(className, cn);
                } catch (InvalidClassException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up the mapping.
     */
    public void genMappings() {
        genMappings(PASS_MAKE_CLASSES);
        genMappings(PASS_LINK_HIERARCHY);
        genMappings(PASS_FINISH_CLASSES);
        genMappings(PASS_SPLIT_NAME_DESC);
        genMappings(PASS_UPDATE_CONSTANTS);
    }

    /**
     * Sets up part of the mapping based on the pass used.
     */
    public void genMappings(int pass) {
        List<String> names = new ArrayList<String>();
        names.addAll(classEntries.keySet());
        if (pass == PASS_MAKE_CLASSES || pass == PASS_LINK_HIERARCHY || pass == PASS_FINISH_CLASSES) {
            // The libs are not needed for the other steps
            names.addAll(libClassEntries.keySet());
        }
        for (String nodeName : names) {
            boolean isLibrary = libClassEntries.containsKey(nodeName);
            ClassNode node = isLibrary ? libClassEntries.get(nodeName) : classEntries.get(nodeName);
            //
            if (pass == PASS_MAKE_CLASSES) {
                // Create and add ClassMapping values from the loaded nodes.
                mapping.addMapping(mapping.makeMappingFromNode(node, isLibrary));
            } else if (pass == PASS_LINK_HIERARCHY) {
                // Mapping hierarchy. Useful for searching for members in
                // parent/interface classes later on.
                String className = ConstUtil.getName(node);
                String superName = ConstUtil.getSuperName(node);
                // Ensure the classes exist.
                // Attempt to load them if they do not.
                if (!mapping.hasClass(className)) {
                    ClassMapping temp = mapping.makeMappingFromRuntime(className);
                    if (temp != null) mapping.addMapping(temp);
                }
                if (!mapping.hasClass(superName)) {
                    ClassMapping temp = mapping.makeMappingFromRuntime(superName);
                    if (temp != null) mapping.addMapping(temp);
                }
                ClassMapping cm = mapping.getMapping(className);
                ClassMapping sm = mapping.getMapping(superName);
                if (cm != null) {
                    if (sm != null) {
                        mapping.addParent(cm, sm);
                        mapping.addChild(sm, cm);
                    }
                    for (int i : node.interfaceIndices) {
                        String interfaceName = ConstUtil.getClassName(node, i);
                        // Ensure interface exists
                        if (!mapping.hasClass(interfaceName)) {
                            ClassMapping temp = mapping.makeMappingFromRuntime(interfaceName);
                            if (temp != null) mapping.addMapping(temp);
                        }
                        ClassMapping im = mapping.getMapping(interfaceName);
                        if (im != null) {
                            mapping.addInterface(cm, im);
                            mapping.addChild(im, cm);
                        }
                    }
                }
            } else if (pass == PASS_FINISH_CLASSES) {
                // Add the members to the MappedClass associated with the
                // classnode.
                // Fun fact: This is done in a separate step because if members
                // references classes not in the mapping it would die. Lazily
                // loading them would be too much work since in the end they all
                // need to be loaded anyways.
                mapping.addMemberMappings(classEntries, node, isLibrary);
            } else if (pass == PASS_SPLIT_NAME_DESC) {
                // This step makes sure a UTF isn't used in too many different
                // contexts.
                // For example: UTF "I".
                // It can be a class name, method name, descriptor, etc.
                // This makes extra copy UTFs to be used in different contexts
                // allowing for class renaming without breaking the other
                // usages.

                // Map of constant pool indices to the type.
                Map<Integer, PrimitiveType> prims = new HashMap<Integer, PrimitiveType>();
                for (int i = 1; i < node.constants.size(); i++) {
                    Constant<?> constant = node.getConst(i);
                    if (constant != null && constant.type == ConstantType.UTF8) {
                        String utfVal = ConstUtil.getUTF8String(node, i);
                        if (utfVal.length() != 1) continue;
                        char ch = utfVal.charAt(0);
                        if (ch == 'B' || ch == 'D' || ch == 'F' || ch == 'I' || ch == 'J' || ch == 'C' || ch == 'S'
                                || ch == 'V') {
                            prims.put(i, PrimitiveType.getFromChar(ch));
                        }
                    }
                }
                // Creates new UTF copies for primitives for name usage.
                Map<PrimitiveType, Integer> primToNameIndex = new HashMap<PrimitiveType, Integer>();
                for (PrimitiveType pt : prims.values()) {
                    node.addConst(new ConstUTF8(pt.desc));
                    primToNameIndex.put(pt, node.constants.size());
                }
                // Another issue is if a UTF is used as a class and member name.
                // Same concept of the primitive name issue.
                String className = ConstUtil.getName(node);
                int altClassName = -1;
                // Iterate constants and serch for NameTypes
                for (int i = 0; i < node.constants.size(); i++) {
                    Constant<?> constant = node.constants.get(i);
                    if (constant == null) continue;
                    if (constant.type != ConstantType.NAME_TYPE) continue;
                    // NameType found, update the name index if it conflicts
                    // with a primitive or the class name.
                    ConstNameType cnt = (ConstNameType) constant;
                    if (prims.keySet().contains(cnt.getNameIndex())) {
                        int newNameIndex = primToNameIndex.get(prims.get(cnt.getNameIndex()));
                        node.constants.set(i, new ConstNameType(newNameIndex, cnt.getDescIndex()));
                    } else if (ConstUtil.getUTF8String(node, cnt.getNameIndex()).equals(className)) {
                        node.addConst(new ConstUTF8(className));
                        altClassName = node.constants.size();
                        node.constants.set(i, new ConstNameType(altClassName, cnt.getDescIndex()));
                    }
                    // TODO: May end up having to check for interface/parent
                    // name conflicts too.
                }
                // Updating name indices in methods if they conflict with
                // primitives or the class name.
                for (MethodNode mn : node.methods) {
                    if (prims.keySet().contains(mn.name)) {
                        mn.name = primToNameIndex.get(prims.get(mn.name));
                    } else if (ConstUtil.getUTF8String(node, mn.name).equals(className)) {
                        mn.name = altClassName;
                    }
                }
                // Updating name indices in fields if they conflict with
                // primitives or the class name.
                for (FieldNode fn : node.fields) {
                    if (prims.keySet().contains(fn.name)) {
                        fn.name = primToNameIndex.get(prims.get(fn.name));
                    } else if (ConstUtil.getUTF8String(node, fn.name).equals(className)) {
                        fn.name = altClassName;
                    }
                }
            } else if (pass == PASS_UPDATE_CONSTANTS) {
                // This step is what really lets "rename once, applied
                // everywhere" occur.
                // UTF constants are updated with wrappers with boxed names /
                // descriptors all pointing to the same string.

                // Replacing class name & super name.
                ConstClass cc = (ConstClass) node.getConst(node.classIndex);
                ConstClass ccs = (ConstClass) node.getConst(node.superIndex);
                String className = ConstUtil.getName(node);
                String superName = ConstUtil.getUTF8String(node, ccs.getValue());
                ClassMapping cm = mapping.getMapping(className);
                node.setConst(cc.getValue(), new ConstName(cm.name));
                node.setConst(ccs.getValue(), new ConstName(mapping.getClassName(superName)));
                if (node.innerClasses != null) {
                    /*
                     * TODO: Inner classes are not updated correctly
                     * 
                     * for (InnerClass i : node.innerClasses.classes) {
                     * 
                     * }
                     */
                }
                // Methods are iterated and their names/descriptor UTF constnats
                // are replaced.
                // Local variable data is also replaced.
                for (MethodNode mn : node.methods) {
                    String name = ConstUtil.getUTF8String(node, mn.name);
                    String desc = ConstUtil.getUTF8String(node, mn.desc);
                    MethodMapping mm = (MethodMapping) cm.getMemberMapping(name, desc);
                    // This should never happen
                    if (mm == null) continue;
                    // Check if the member points to a value already mapped to a
                    // name.
                    // If so, add a constant and update the member to point to
                    // the new constant.
                    ConstUTF8 memberUTF8 = (ConstUTF8) node.getConst(mn.name);
                    if (memberUTF8 instanceof ConstName) {
                        node.addConst(new ConstName(mm.name));
                        mn.name = node.constants.size();
                    } else {
                        node.setConst(mn.name, new ConstName(mm.name));
                    }
                    node.setConst(mn.desc, new ConstMemberDesc(mm.desc));
                    // TODO: Re-enable this later. Totally pointless for now.
                    if (mn.code != null && false) {
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
                                    String ldesc = ((ConstUTF8) node.getConst(type.signature)).getValue();
                                    // Resolve case:
                                    // Requested unmapped class:
                                    // java/util/Map$Entry<Ljava/lang/String
                                    MemberMapping var = new MemberMapping(lname, Type.variable(mapping, ldesc));
                                    node.setConst(type.name, new ConstName(var.name));
                                    node.setConst(type.signature, new ConstMemberDesc(var.desc));
                                }
                            }
                        }
                    }
                }
                // Fields are iterated and their names/descriptor UTF constnats
                // are replaced.
                for (FieldNode fn : node.fields) {
                    String name = ConstUtil.getUTF8String(node, fn.name);
                    String desc = ConstUtil.getUTF8String(node, fn.desc);
                    MemberMapping mf = cm.getMemberMapping(name, desc);
                    // Check if the member points to a value already mapped to a
                    // name.
                    // If so, add a constant and update the member to point to
                    // the new constant.
                    ConstUTF8 memberUTF8 = (ConstUTF8) node.getConst(fn.name);
                    if (memberUTF8 instanceof ConstName) {
                        node.addConst(new ConstName(mf.name));
                        fn.name = node.constants.size();
                    } else {
                        node.setConst(fn.name, new ConstName(mf.name));
                    }
                    node.setConst(fn.desc, new ConstMemberDesc(mapping.getDesc(className, name, desc)));
                }

                // Temporary, will be removed when method opcode and
                // variable_type reading is
                // complete
                for (int i = 0; i < node.constants.size(); i++) {
                    Constant<?> constant = node.constants.get(i);
                    if (constant == null) continue;
                    boolean dynamic = false;
                    int classIndex = -1, nameTypeIndex = -1;
                    String owner = null, name = null, desc = null;
                    switch (constant.type) {
                    case INTERFACE_METHOD:
                        ConstInterfaceMethod cim = (ConstInterfaceMethod) constant;
                        classIndex = cim.getClassIndex();
                        nameTypeIndex = cim.getNameTypeIndex();
                        break;
                    case INVOKEDYNAMIC:
                        dynamic = true;
                        ConstInvokeDynamic cid = (ConstInvokeDynamic) constant;
                        nameTypeIndex = cid.getNameTypeIndex();
                        break;
                    case METHOD:
                        ConstMethod cm2 = (ConstMethod) constant;
                        classIndex = cm2.getClassIndex();
                        nameTypeIndex = cm2.getNameTypeIndex();
                        break;
                    case FIELD:
                        ConstField cf = (ConstField) constant;
                        classIndex = cf.getClassIndex();
                        nameTypeIndex = cf.getNameTypeIndex();
                        break;
                    case CLASS:
                        // Sure this is all member based except for here,
                        // but it works when I have class constants I can't
                        // yet access for things like opcodes.
                        int nameI = ((ConstClass) constant).getValue();
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
                            //
                            // Please somebody figure out a better way.
                            if (name.startsWith("[")) {
                                name = name.replace("[", "");
                                if (name.endsWith(";")) {
                                    name = name.substring(1, name.length() - 1);
                                }
                            } else {
                                node.setConst(nameI, new ConstName(mapping.getClassName(name)));
                            }
                        }
                        break;
                    default:
                        break;
                    }
                    if (nameTypeIndex == -1) continue;
                    owner = dynamic ? className : ConstUtil.getClassName(node, classIndex);
                    ConstNameType cnt = (ConstNameType) node.getConst(nameTypeIndex);
                    name = ConstUtil.getUTF8String(node, cnt.getNameIndex());
                    desc = ConstUtil.getUTF8String(node, cnt.getDescIndex());
                    ClassMapping ownerMap = mapping.getMapping(owner);
                    if (ownerMap == null) {
                        // If the owner can't be located it's probably a library
                        // call.
                        continue;
                    }
                    MemberMapping member = mapping.getMemberMapping(ownerMap, name, desc);
                    ConstUTF8 memberUTF8 = (ConstUTF8) node.getConst(cnt.getNameIndex());
                    if (member == null) {
                        // The exact metod can't be found but the owner is in
                        // the mappings. It's most likely extending a library
                        // method that does have a mapping (Like the java/lang
                        // package)
                        if (memberUTF8 instanceof ConstName) {
                            node.addConst(new ConstName(new ImmutableBox<String>(name)));
                            cnt.setNameIndex(node.constants.size());
                        } else {
                            node.setConst(cnt.getNameIndex(), new ConstName(new ImmutableBox<String>(name)));
                        }
                        node.setConst(cnt.getDescIndex(), new ConstMemberDesc(
                                desc.contains("(") ? Type.method(mapping, desc) : Type.variable(mapping, desc)));
                    } else {
                        if (memberUTF8 instanceof ConstName) {
                            node.addConst(new ConstName(member.name));
                            cnt.setNameIndex(node.constants.size());
                        } else {
                            node.setConst(cnt.getNameIndex(), new ConstName(member.name));
                        }
                        node.setConst(cnt.getDescIndex(), new ConstMemberDesc(member.desc));
                    }
                }
            }
        }

    }

    /**
     * Saves the ClassNode objects to a given jar destination.
     * 
     * @param fileOut
     */
    public void saveJarTo(File fileOut) {
        JarUtil.writeJar(file, fileOut, classEntries, fileEntries);
    }

    /**
     * TODO: Make an automated system to do this automatically.
     */
    @Deprecated
    public void updateInners() {
        for (String className : classEntries.keySet()) {
            ClassNode cn = classEntries.get(className);
            if (cn.innerClasses == null) {
                continue;
            }
            String outerName = ConstUtil.getName(cn);
            for (InnerClass ic : cn.innerClasses.classes) {
                ConstClass cc2 = (ConstClass) cn.getConst(ic.innerClassIndex);
                ConstName cname = (ConstName) cn.getConst(cc2.getValue());
                ClassNode cnInner = classEntries.get(cname.name.original);
                if (cnInner == null) {
                    continue;
                }
                ConstClass cc = (ConstClass) cnInner.getConst(cnInner.classIndex);
                ConstUTF8 utf = (ConstUTF8) cnInner.getConst(cc.getValue());
                if (utf instanceof ConstName) {
                    ConstName name = (ConstName) utf;
                    String mappedName = name.name.getValue();
                    if (mappedName.contains("/")) {
                        mappedName = mappedName.substring(mappedName.lastIndexOf("/") + 1);
                    }
                    String newInnerName = outerName + "$" + mappedName;
                    name.name.setValue(newInnerName);
                }
            }
        }
    }

    /**
     * Saves the Mapping objects to a given file destination.
     * 
     * @param fileOut
     * @throws IOException
     */
    public void saveMappingsTo(File fileOut) throws IOException {
        saveMappingsTo(fileOut, false);
    }

    /**
     * Loads mappings from the given file.
     * 
     * @param fileIn
     */
    public void loadMappingsFrom(File fileIn) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileIn))) {
            ClassMapping cm=null;
            String in = null;
            while ((in = br.readLine()) != null) {
                if (in.startsWith("CLASS ")) {
                    String[] args = in.split(" ");
                    String orig = args[1];
                    String rename = args.length > 2 ? args[2] : null;
                    cm = this.mapping.getMapping(this.classEntries.get(orig));
                    if (cm != null && rename != null){
                        cm.name.setValue(rename);
                    }
                } else if (cm != null){
                    String tr = in.trim();
                    String[] args = tr.split(" ");
                    if (tr.startsWith("FIELD ") || tr.startsWith("METHOD ")) {
                        String orig = args[1];
                        String rename = args[2];
                        String desc = args[3];
                        MemberMapping mm = cm.getMemberMapping(orig, desc);
                        if (mm != null){
                            mm.name.setValue(rename);
                        }
                    }
                }
            }
        }
    }

    /**
     * Saves the Mapping objects to a given file destination.
     * 
     * @param fileOut
     * @param exclude
     *            Exclude classes/members not renamed
     * @throws IOException
     */
    public void saveMappingsTo(File fileOut, boolean exclude) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut));
        for (String name : classEntries.keySet()) {
            ClassMapping cm = mapping.getMappings().get(name);
            boolean include = !cm.name.getValue().equals(cm.name.original);
            String c = "CLASS " + cm.name.original + " " + cm.name.getValue() + "\n";
            List<MemberMapping> s = new ArrayList<MemberMapping>(cm.getMembers());
            s.sort(new Comparator<MemberMapping>() {
                @Override
                public int compare(MemberMapping m1, MemberMapping m2) {
                    boolean b1 = m1 instanceof MethodMapping;
                    boolean b2 = m2 instanceof MethodMapping;
                    if (b1 != b2) {
                        if (b1) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                    return m1.name.original.compareTo(m2.name.original);
                }

            });
            List<String> m = new ArrayList<String>(s.size());
            for (MemberMapping mm : s) {
                boolean method = mm instanceof MethodMapping;
                if (!exclude || (exclude && !mm.name.getValue().equals(mm.name.original))) {
                    m.add("\t" + (method ? "METHOD" : "FIELD") + " " + mm.name.original + " " + mm.name.getValue() + " "
                            + mm.desc.original + "\n");
                    include = true;
                }
            }
            if (!exclude || (exclude && include)) {
                bw.write(c);
                for (String sm : m) {
                    bw.write(sm);
                }
            }
        }
        bw.close();
    }

    /**
     * Adds a given file to the dependencies.
     * 
     * @param file
     */
    public void addLibrary(File file) {
        libraries.add(file);
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
