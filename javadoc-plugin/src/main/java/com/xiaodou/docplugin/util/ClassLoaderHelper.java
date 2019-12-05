package com.xiaodou.docplugin.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaodou.docplugin.log.LogHelper;

/**
 * 
 * @author bin.song
 *
 */
public class ClassLoaderHelper {

	/**
	 * 获取JSON初始化
	 * @param jarPath
	 * jar包路径
	 * @param classPath
	 * class路径
	 * @return
	 * 对象初始化
	 */
	public static Object getInitJSONObject(String jarPath, String classPath, String libPath){
		Object initJson = null;
		try {
			/*String basePath = 
					"D:/code/Java/javadoc/target/javadoc-1.0.0-SNAPSHOT.jar";*/
			
			URL targetJarUrl = new URL("file:" + jarPath);
			List<File> libJars = FileHelper.getFiles(libPath, ".jar");
			int length = libJars != null ? libJars.size() : 0;
			URL[] jarUrls = new URL[length + 1];
			for (int i = 0; i < length; i++) {
				jarUrls[i] = new URL("file:" + libJars.get(i));
			}
			jarUrls[length] = targetJarUrl;
	        URLClassLoader urlClassLoader = new URLClassLoader(jarUrls, Thread.currentThread().getContextClassLoader());  
	        Class<?> targetClass = urlClassLoader.loadClass(classPath);
			
	        ResourseResoler resourseResoler = new ResourseResoler();
	        JSONObject jsonObject = new JSONObject();
	        initJson = resourseResoler.getInitJsonObject(targetClass, jsonObject, true);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getInitJSONObject-->excepMsg:" + e.getMessage() + "StackInfo:" + ExceptionHelper.getStackTrace(e));
			LogHelper.Log("getInitJSONObject-->excepMsg:" + e.getMessage() + "StackInfo:" + ExceptionHelper.getStackTrace(e));
		}
		return initJson;
	}
}
