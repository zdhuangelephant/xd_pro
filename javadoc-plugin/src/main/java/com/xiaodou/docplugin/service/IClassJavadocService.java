package com.xiaodou.docplugin.service;

import java.io.File;

import com.xiaodou.docplugin.entity.ApiClassJavadocEntity;

/**
 * 
 * @author bin.song
 *
 */
public interface IClassJavadocService {

	/**
	 * 获取class javadoc 实体
	 * @param javaFile
	 * java文件
	 * @param fullName
	 * 类全路径
	 * @return
	 * classjavadoc实体
	 */
	ApiClassJavadocEntity getClassJavadoc(File javaFile, String fullName);
}
