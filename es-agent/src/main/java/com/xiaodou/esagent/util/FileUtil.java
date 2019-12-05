package com.xiaodou.esagent.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.esagent.model.DumpType;

public class FileUtil<T> {
	/**
	 * 配置文件
	 */
	private String filePath = "";
    public FileUtil(String name){
    	this.filePath=name.toLowerCase()+".json";
    }

	public  List<T> findFile() {
		String sets = ReadFile();// 获得json文件的内容
		List<T> list = getResources(sets);
		return list;
	}

	// 设置属性，并保存
	public  boolean saveFile(String sets) {
		try {
			writeFile(sets);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// 读文件，返回字符串
	public  String ReadFile() {
		File file = new File(filePath);
		BufferedReader reader = null;
		String laststr = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr = laststr + tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		file.delete();
		return "["+laststr+"]";
	}

	// 把json格式的字符串写到文件
	public  void writeFile(String sets)
			throws IOException {
		FileWriter fw = new FileWriter(filePath, true);
		PrintWriter out = new PrintWriter(fw);
		out.write(sets);
		out.println();
		fw.close();
		out.close();
	}

	/**
	 * 通用方法
	 * 
	 * @author zhouhuan
	 */
	public  List<T> getResources(String sets) {
		List<T> reList = new ArrayList<T>();
		if (!StringUtils.isBlank(sets)) {
			reList = FastJsonUtil.fromJsons(sets,
					new TypeReference<List<T>>() {
					});
		}
		return reList;
	}

}
