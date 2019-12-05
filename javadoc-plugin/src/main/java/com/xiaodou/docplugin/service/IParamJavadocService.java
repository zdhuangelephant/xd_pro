package com.xiaodou.docplugin.service;

import java.util.List;

/**
 * 
 * @author bin.song
 *
 */
public interface IParamJavadocService {

	/**
	 * 获取参数javadoc
	 * @param type
	 * class type
	 * @param imports
	 * 导入的packages
	 * @return
	 * class名称或者Map
	 */
	Object getFieldJavadoc(String type, List<String> imports);
}
