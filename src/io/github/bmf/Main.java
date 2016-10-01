package io.github.bmf;

import io.github.bmf.io.JarUtil;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

@SuppressWarnings("unused")
public class Main {
    private static final String IN_FILE = "tests/Jar.jar", OUT_FILE = "tests/Out.jar";

    public static void main(String[] args) {
        all(IN_FILE);
        // one(OUT_FILE, "IExample");
    }

    private static void all(String file) {
        try {
            Map<String, byte[]> entries = JarUtil.readJarClasses(new File(file));
            Map<String, ClassNode> nodes = Maps.newHashMap();
            for (String s : entries.keySet()) {
                ClassNode cn = ClassReader.getNode(entries.get(s));
                nodes.put(s, cn);
            }
            JarUtil.writeJar(new File(OUT_FILE), nodes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void one(String file, String entry) {
        try {
            Map<String, byte[]> entries = JarUtil.readJarClasses(new File(file));
            ClassNode cn = ClassReader.getNode(entries.get(entry));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
