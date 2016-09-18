package io.github.bmf;


import java.io.File;
import java.util.Map;

import io.github.bmf.io.JarUtil;

@SuppressWarnings("unused")
public class Main {
	public static void main(String[] args) {
		all("tests/Jar.jar");
	}

	private static void all(String file) {
		try {
			Map<String, byte[]> entries = JarUtil.readJarClasses(new File(file));
			for (String s : entries.keySet()) {
				//System.out.println(">>>>>>>>>>" + s);
				ClassNode cn = ClassInterpreter.getNode(entries.get(s));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static void one(String file, String entry) {
		try {
			Map<String, byte[]> entries = JarUtil.readJarClasses(new File(file));
			// String test = "me/lpk/util/Reference";
			ClassNode cn = ClassInterpreter.getNode(entries.get(entry));
			// System.out.println(cn.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
