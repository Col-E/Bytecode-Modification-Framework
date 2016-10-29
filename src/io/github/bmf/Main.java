package io.github.bmf;

import com.google.common.collect.Maps;
import io.github.bmf.util.io.JarUtil;
import io.github.bmf.util.mapping.ClassMapping;
import io.github.bmf.util.mapping.Mapping;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Main {
    private static final String IN_FILE = "tests/Jar.jar", OUT_FILE = "tests/Out.jar";

    public static void main(String[] args) {
        all(IN_FILE);
    }

    private static void all(String file) {
        try {
            JarReader read = new JarReader(new File(file), true);
            read.genMappings(JarReader.PASS_MAKE_CLASS);
            read.genMappings(JarReader.PASS_MAKE_MEMBER_DATA);
            read.genMappings(JarReader.PASS_UPDATE_CONSTANTS);
            read.getMapping().getMapping("io/github/bmf/attribute/Attribute").name.value = "dank/meme/NewAttribute";
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
