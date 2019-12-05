package com.xiaodou.docplugin.service;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.xiaodou.docplugin.entity.SubFieldEntity;

/**
 * 
 * @author bin.song
 *
 */
public interface IFieldJavadocService {

	/**
	 * 获取class中的字段
	 * @param type
	 * class文件描述
	 * @param imports
	 * 导入的packages
	 * @return
	 * 字段map
	 */
	HashMap<SubFieldEntity, Object> getFieldJavadoc(TypeDeclaration type, List<String> imports, String fullName);
}
