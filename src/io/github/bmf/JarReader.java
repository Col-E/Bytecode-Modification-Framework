package io.github.bmf;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.github.bmf.type.Type;
import io.github.bmf.util.ConstUtil;
import io.github.bmf.util.descriptors.MethodDescriptor;
import io.github.bmf.util.io.JarUtil;
import io.github.bmf.util.mapping.ClassMapping;
import io.github.bmf.util.mapping.Mapping;
import io.github.bmf.util.mapping.MemberMapping;

public class JarReader {
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
        if (file == null || !file.exists())
            throw new IllegalArgumentException("Invalid file given: " + file.getAbsolutePath());
        this.file = file;
        this.mapping = new Mapping();
        if (read) read();
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
        for (ClassNode node : classEntries.values()) {
            ClassMapping map = new ClassMapping(ConstUtil.getName(node));
            for (MethodNode method : node.methods) {
                String name = ConstUtil.getUTF8String(node, method.name);
                String descStr = ConstUtil.getUTF8String(node, method.desc);
                MethodDescriptor desc = Type.method(mapping, descStr);
                MemberMapping member = new MemberMapping(name, desc);
                map.members.add(member);
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
