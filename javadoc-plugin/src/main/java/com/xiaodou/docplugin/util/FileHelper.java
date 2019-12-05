package com.xiaodou.docplugin.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author bin.song
 *
 */
public class FileHelper {

	/**
	 * 获取指定路径下的java文件
	 * @param path
	 * 指定路径，如src/com/elong/hotel/giftcardms/web
	 * @return
	 * 符合条件的java文件集合
	 */
	public static List<File> getFiles(String path, String pattern){
		List<File> fileList = new ArrayList<File>();
		if(path == null || path.equals("")){
			return fileList;
		}
		File file = new File(path);
		File[] subFiles = file.listFiles();
		if(subFiles != null){
			for (File subFile : subFiles) {
				if(subFile.isDirectory()){
					fileList.addAll(getFiles(subFile.getAbsolutePath(), pattern));
				}else if(subFile.isFile() && subFile.getName().endsWith(pattern)) {
					fileList.add(subFile);
				}
			}
		}
		return fileList;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static boolean exists(String path){
		File file = new File(path);
		if(file == null){
			return false;
		}else {
			if(file.isDirectory() || !file.exists()){
				return false;
			}else {
				return true;
			}
		}
	}
	
	/**
	 * 创建文件
	 * @param fileName
	 * 文件全路径
	 * @param content
	 * 内容
	 */
	public static void createFile(String dirPath, String fileName, String content){
		try {
			File dir = new File(dirPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(fileName);
			if(!file.exists()){
				file.createNewFile();
			}
			OutputStreamWriter outputWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			outputWriter.write(content);
			outputWriter.close();
			/*FileWriter fileWriter = new FileWriter(file, false);
			fileWriter.write(content);
			fileWriter.close();*/
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("generate html file error...");
		}
	}
}
