package com.xiaodou.docplugin.util;

import com.xiaodou.docplugin.service.impl.dc.DcJavadocService;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * 自定义ClassLoader
 *
 * @author junpeng.chen
 */
public enum DcClassLoader {
    INSTANCE;
    private URLClassLoader urlClassLoader;

    private DcClassLoader() {
        String jarPath = DcJavadocService.basePath + "/target/" + DcJavadocService.projectName + ".jar";
        String libPath = DcJavadocService.basePath + "/target/" + DcJavadocService.projectName + "/WEB-INF/lib";
        URL targetJarUrl;
        try {
            targetJarUrl = new URL("file:" + jarPath);
            List<File> libJars = FileHelper.getFiles(libPath, ".jar");
            int length = libJars != null ? libJars.size() : 0;
            URL[] jarUrls = new URL[length + 1];
            for (int i = 0; i < length; i++) {
                jarUrls[i] = new URL("file:" + libJars.get(i));
            }
            jarUrls[length] = targetJarUrl;
            this.urlClassLoader = new URLClassLoader(jarUrls, Thread.currentThread().getContextClassLoader());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return this.urlClassLoader.loadClass(name);
    }
}
