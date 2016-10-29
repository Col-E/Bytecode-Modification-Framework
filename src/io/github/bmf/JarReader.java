package io.github.bmf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.bmf.util.io.JarUtil;
import io.github.bmf.util.mapping.ClassMapping;

public class JarReader {
    private final Map<String, ClassMapping> infoMap;
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
        this.infoMap = new HashMap<String, ClassMapping>();
        if (read) read();
    }

    /**
     * Reads from the jar file.
     */
    public void read() {
        try {
            Map<String, byte[]> classEntryBytes = JarUtil.readJarClasses(file);
            fileEntries = JarUtil.readJarClasses(file);
            classEntries = new HashMap<String, ClassNode>();
            for (String className : classEntries.keySet()) {
                ClassNode cn = ClassReader.getNode(classEntryBytes.get(className));
                classEntries.put(className, cn);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public Map<String, ClassMapping> getClassInfos() {
        return infoMap;
    }
}
