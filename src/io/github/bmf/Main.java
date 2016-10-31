package io.github.bmf;

import io.github.bmf.consts.Constant;
import io.github.bmf.consts.mapping.ConstName;
import io.github.bmf.util.ConstUtil;
import io.github.bmf.util.io.JarUtil;
import io.github.bmf.util.mapping.MemberMapping;

import java.io.File;
import java.util.Map;

@SuppressWarnings("unused")
public class Main {
    private static final String IN_FILE = "tests/MethodNames.jar", OUT_FILE = "tests/Out.jar";

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        all(IN_FILE);
        System.out.println(System.currentTimeMillis() - l);
    }

    private static void all(String file) {
        try {
            JarReader read = new JarReader(new File(file), true);
            read.genMappings();
           // read.getMapping().getClassName("com/example/test/Edible").setValue("dank/memes/WeedJokeHere");
           // read.getMapping().getClassName("com/example/test/Apple").setValue("keep/the/doctors/Away");
           // read.getMapping().getClassName("com/example/test/Fruit").setValue("not/a/Vegetable");
           
            MemberMapping mm = read.getMapping().getMapping("com/example/test/Edible").getMemberMapping("isRotten", "()Z");
            mm.name.setValue("renamedRotten");

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
