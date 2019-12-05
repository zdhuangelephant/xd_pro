package com.xiaodou.docplugin.util;

import java.util.List;

import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;

import com.xiaodou.docplugin.entity.SubFieldEntity;

/**
 * 参数类型工具
 * @author bin.song
 *
 */
public class TypeHelper {

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static String getType(Type type){
		String ret = "";
		if(type.getClass().equals(ParameterizedType.class)){
			ParameterizedType realType = (ParameterizedType)type;
			String complexType = realType.getType().toString();
			String realSubType = "";
			if(realType.typeArguments().size() > 0){
				ret = realType.typeArguments().get(0).toString();
			}
		}else if(type.getClass().equals(SimpleType.class)){
			SimpleType realType = (SimpleType)type;
			ret = realType.getName().toString(); 
		}
		return ret;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static Object getFieldEntity(Type type, List<String> imports){
		Object ret = new Object();
		if(type.getClass().equals(ParameterizedType.class)){
			ParameterizedType realType = (ParameterizedType)type;
			String complexType = realType.getType().toString();
			String realSubType = "";
			if(realType.typeArguments().size() > 0){
				ret = realType.typeArguments().get(0).toString();
			}
		}else if(type.getClass().equals(SimpleType.class)){
			SimpleType realType = (SimpleType)type;
			ret = realType.getName().toString(); 
		}
		return ret;
	}
}
