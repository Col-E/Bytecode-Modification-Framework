package io.github.bmf;

import com.google.common.collect.Maps;
import io.github.bmf.util.io.JarUtil;

import java.io.File;
import java.util.Map;

@SuppressWarnings("unused")
public class Main {
    private static final String IN_FILE = "tests/Jar.jar", OUT_FILE = "tests/Out.jar";

    public static void main(String[] args) {
        all(IN_FILE);
    }

    private static void all(String file) {
        try {
            
            JarReader read = new JarReader(new File(file));
            read.read();
            System.out.println(read.getClassEntries().size());
            System.out.println(read.getFileEntries().size());
            for (String s : read.getFileEntries().keySet()){
                System.out.println(s);
            }
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
