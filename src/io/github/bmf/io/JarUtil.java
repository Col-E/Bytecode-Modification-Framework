package io.github.bmf.io;

import com.google.common.collect.Maps;
import io.github.bmf.ClassNode;
import io.github.bmf.ClassWriter;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * A utility class for reading information from jar files.
 *
 * @author Matt
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
                if (entry.isDirectory() || !filer.matches(name)) { return; }
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

    public static void writeJar(File in, File outf, Map<String, ClassNode> nodes) {
        try {
            FileOutputStream fos = new FileOutputStream(outf);
            JarOutputStream jos = new JarOutputStream(fos, getManifest(in));
            for (String className : nodes.keySet()) {
                JarEntry entry = new JarEntry(className + ".class");
                entry.setTime(System.currentTimeMillis());
                jos.putNextEntry(entry);
                jos.write(ClassWriter.write(nodes.get(className)));
            }
            jos.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Manifest getManifest(File in) throws IOException {
        JarFile jar = new JarFile(in);
        Manifest manifest = jar.getManifest();
        jar.close();
        return manifest;
    }
}