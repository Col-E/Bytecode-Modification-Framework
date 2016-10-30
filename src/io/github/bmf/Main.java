package io.github.bmf;

import io.github.bmf.util.io.JarUtil;
import java.io.File;
import java.util.Map;

@SuppressWarnings("unused")
public class Main {
    private static final String IN_FILE = "tests/MethodNames.jar", OUT_FILE = "tests/Out.jar";

    public static void main(String[] args) {
        all(IN_FILE);
    }

    private static void all(String file) {
        try {
            JarReader read = new JarReader(new File(file), true);
            read.genMappings();
            read.saveTo(new File(OUT_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ClassNode one(String file, String entry) {
        try {
            Map<String, byte[]> entries = JarUtil.readJarClasses(new File(file));
            ClassNode cn = ClassReader.getNode(entries.get(entry));
            return cn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
