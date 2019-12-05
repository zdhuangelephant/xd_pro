package com.xiaodou.docplugin.util;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author junpeng.chen
 */
public class JarUtils {
    public static void unzip(String jarFileName, String toDir) throws IOException {
        JarFile jarFile = new JarFile(jarFileName);
        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
        while (jarEntryEnumeration.hasMoreElements()) {
            JarEntry jarEntry = jarEntryEnumeration.nextElement();
            if (jarEntry.getName().contains("refer/") && !jarEntry.isDirectory()) {
                String jarName = jarEntry.getName();
                int lastSlash = jarName.lastIndexOf("/");
                String outputDir = toDir + jarName.substring(0, lastSlash);
                File outputDirFile = new File(outputDir);
                if (!outputDirFile.exists()) {
                    outputDirFile.mkdirs();
                }
                String outputFileName = toDir + jarName;
                writeFile(jarFile.getInputStream(jarEntry), outputFileName);
            }
        }
    }

    public static void writeFile(InputStream inputStream, String fileName) throws IOException {
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileName));
        try {
            byte[] buffer = new byte[1024];
            int nBytes;
            while ((nBytes = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, nBytes);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            outputStream.flush();
            outputStream.close();
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
