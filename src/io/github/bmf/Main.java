package io.github.bmf;

import io.github.bmf.io.JarUtil;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) {
        all("tests/Jar.jar");
    }

    private static void all(String file) {
        try {
            Map<String, byte[]> entries = JarUtil.readJarClasses(new File(file));
            Map<String, ClassNode> nodes = Maps.newHashMap();
            for (String s : entries.keySet()) {
                ClassNode cn = ClassReader.getNode(entries.get(s));
                nodes.put(s, cn);
            }
           JarUtil.writeJar(new File("tests/Out.jar"), nodes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void one(String file, String entry) {
        try {
            Map<String, byte[]> entries = JarUtil.readJarClasses(new File(file));
            // String test = "me/lpk/util/Reference";
            ClassNode cn = ClassReader.getNode(entries.get(entry));
            // System.out.println(cn.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
