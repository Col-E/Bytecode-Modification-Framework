package io.github.bmf.io;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;

/**
 * A utility class for reading information from jar files.
 * 
 * @author Matt
 *
 */
public class JarUtil {
	/**
	 * Reads all classes in a jar file and stores them in a map of byte arrays.
	 * 
	 * @param file
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	public static Map<String, byte[]> readJarClasses(File file) throws ZipException, IOException {
		return readJar(file, new NameFilter() {
			@Override
			public boolean matches(String name) {
				return name.endsWith(".class");
			}

			@Override
			public String filterName(String name) {
				return name.replace(".class", "");
			}
		});
	}

	/**
	 * Reads all entries in a jar file and stores them in a map of byte arrays.
	 * Certain entries may be excluded bases on the NameFilter parameter.
	 * 
	 * @param file
	 * @param filer
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	public static Map<String, byte[]> readJar(File file, NameFilter filer) throws ZipException, IOException {
		Map<String, byte[]> entries = Maps.newHashMap();
		ZipFile zip = new ZipFile(file);
		zip.stream().forEach(new Consumer<ZipEntry>() {
			@Override
			public void accept(ZipEntry entry) {
				String name = entry.getName();
				if (entry.isDirectory() || !filer.matches(name)) {
					return;
				}
				try {
					entries.put(filer.filterName(name), IOUtils.toByteArray(zip.getInputStream(entry)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		zip.close();
		return entries;
	}
}