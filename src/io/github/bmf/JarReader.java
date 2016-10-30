package io.github.bmf;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.github.bmf.consts.ConstUTF8;
import io.github.bmf.consts.Constant;
import io.github.bmf.consts.mapping.ConstMemberDesc;
import io.github.bmf.consts.mapping.ConstName;
import io.github.bmf.type.Type;
import io.github.bmf.util.ConstUtil;
import io.github.bmf.util.descriptors.MemberDescriptor;
import io.github.bmf.util.descriptors.MethodDescriptor;
import io.github.bmf.util.io.JarUtil;
import io.github.bmf.util.mapping.ClassMapping;
import io.github.bmf.util.mapping.Mapping;
import io.github.bmf.util.mapping.MemberMapping;

public class JarReader {
    public static final int PASS_MAKE_CLASS = 0;
    public static final int PASS_MAKE_MEMBER_DATA = 1;
    public static final int PASS_UPDATE_CONSTANTS = 2;
    public static final int PASS_LINK_HIERARCHY = 3;

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
        genMappings(PASS_MAKE_CLASS);
        genMappings(PASS_MAKE_MEMBER_DATA);
        genMappings(PASS_UPDATE_CONSTANTS);
        genMappings(PASS_LINK_HIERARCHY);
    }

    /**
     * Sets up part of the mapping based on the pass used.
     */
    public void genMappings(int pass) {
        for (ClassNode node : classEntries.values()) {
            if (pass == PASS_MAKE_CLASS) {
                ClassMapping map = new ClassMapping(ConstUtil.getName(node));
                mapping.addMapping(map);
            } else if (pass == PASS_MAKE_MEMBER_DATA) {
                ClassMapping map = mapping.getMapping(ConstUtil.getName(node));
                for (MethodNode method : node.methods) {
                    String name = ConstUtil.getUTF8String(node, method.name);
                    String descStr = ConstUtil.getUTF8String(node, method.desc);
                    MethodDescriptor desc = Type.method(mapping, descStr);
                    MemberMapping member = new MemberMapping(name, desc);
                    map.members.add(member);
                }
            } else if (pass == PASS_UPDATE_CONSTANTS) {
                for (int i = 0; i < node.constants.size(); i++) {
                    Constant<?> cnst = node.constants.get(i);
                    if ((cnst == null) || !(cnst instanceof ConstUTF8)) {
                        continue;
                    }

                    ConstUTF8 utf = (ConstUTF8) cnst;
                    String v = utf.getValue();
                    if (mapping.hasMapping(v)) {
                        node.constants.set(i, new ConstName(mapping.getClassName(v)));
                    } else {
                        MemberDescriptor md = mapping.getDesc(ConstUtil.getName(node), v);
                        if (md != null) {
                            node.constants.set(i, new ConstMemberDesc(mapping.getDesc(ConstUtil.getName(node), v)));
                        }
                    }
                }
            } else if (pass == PASS_LINK_HIERARCHY) {

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
