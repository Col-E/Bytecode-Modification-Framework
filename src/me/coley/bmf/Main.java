package me.coley.bmf;

import java.io.File;
import java.util.Map;

import me.coley.bmf.consts.Constant;
import me.coley.bmf.consts.mapping.ConstName;
import me.coley.bmf.mapping.ClassMapping;
import me.coley.bmf.mapping.InnerClassMapping;
import me.coley.bmf.mapping.MemberMapping;
import me.coley.bmf.util.ConstUtil;
import me.coley.bmf.util.ImmutableBox;
import me.coley.bmf.util.io.JarUtil;

@SuppressWarnings("unused")
public class Main {
    private static final String IN_FILE = "tests/Test.jar", OUT_FILE = "tests/OUT.jar";

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        all(IN_FILE);
        System.out.println("Time Elapsed: " + (System.currentTimeMillis() - l));
    }

    private static void all(String file) {
        try {
            JarReader read = new JarReader(new File(file), true, true);
            // read.getMapping().getClassName("com/example/test/Edible").setValue("dank/memes/WeedJokeHere");
            // read.getMapping().getClassName("com/example/test/Apple").setValue("keep/the/doctors/Away");
            // read.getMapping().getClassName("com/example/test/Fruit").setValue("not/a/Vegetable");
            int classIndex = 1;
            for (String name : read.getClassEntries().keySet()) {
                ClassMapping cm = read.getMapping().getMapping(name);
                String obName = getCapName(classIndex);
                if (!(cm instanceof InnerClassMapping)){
                    obName = "AAA/" + obName;
                }
                cm.name.setValue(obName);
                System.out.println(name + " -> " + cm.name.getValue());
                int memberIndex = 1;
                for (MemberMapping mm : cm.getMembers()) {
                    if (mm.name.getValue().equals(mm.name.original) && !mm.name.original.equals("main") && !mm.name.original.contains("<")) {
                        mm.name.setValue(getLowName(memberIndex));
                        memberIndex++;
                    }
                }
                classIndex++;
            }
            read.saveJarTo(new File(OUT_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLowName(int i) {
        return getString("abcdefghijklmnopqrstuvwxyz", i, 24);
    }

    public static String getCapName(int i) {
        return getString("ABCDEFGHIJKLMNOPQRSTUVWXYZ", i, 24);
    }

    public static String getString(String alpha, int i, int n) {
        char[] charz = alpha.toCharArray();
        if (n < 2) {
            n = 2;
        } else if (n > alpha.length()) {
            n = alpha.length();
        }
        final char[] array = new char[33];
        final boolean b = i < 0;
        int n2 = 32;
        if (!b) {
            i = -i;
        }
        while (i <= -n) {
            array[n2--] = charz[-(i % n)];
            i /= n;
        }
        array[n2] = charz[-i];
        if (b) {
            array[--n2] = '_';
        }
        return new String(array, n2, 33 - n2);
    }
}
