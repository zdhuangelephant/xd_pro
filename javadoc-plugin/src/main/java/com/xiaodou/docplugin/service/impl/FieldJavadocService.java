package com.xiaodou.docplugin.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FalseLiteral;

import com.xiaodou.docplugin.entity.ApiClassJavadocEntity;
import com.xiaodou.docplugin.entity.SubFieldEntity;
import com.xiaodou.docplugin.service.IFieldJavadocService;
import com.xiaodou.docplugin.service.IParamJavadocService;
import com.xiaodou.docplugin.util.FileHelper;

/**
 * 
 * @author bin.song
 *
 */
public class FieldJavadocService implements IFieldJavadocService {

	public HashMap<SubFieldEntity, Object> getFieldJavadoc(
			TypeDeclaration type, List<String> imports, String fullName) {
		// TODO Auto-generated method stub
		HashMap<SubFieldEntity, Object> ret = new HashMap<SubFieldEntity, Object>();
		if(type == null || imports == null){
			return ret;
		}
		//获取字段集合
		FieldDeclaration[] fields = type.getFields();
		for (FieldDeclaration field : fields) {
			//是否为public
			/*if(!isPublic(field)){
				continue;
			}*/
			SubFieldEntity fieldEntity = new SubFieldEntity();
			//获取字段描述
			fieldEntity.setDescription(getDescription(field));
			//获取字段名称
			fieldEntity.setName(getName(field));
			//获取字段类型
			fieldEntity.setType(field.getType().toString());
			//获取subtype
			IParamJavadocService paramJavadocService = new ParamJavadocService();
			imports.add(fullName + getType(field));
			Object fieldType = paramJavadocService.getFieldJavadoc(getType(field), imports);
			ret.put(fieldEntity, fieldType);
		}
		
		return ret;
	}
	
	
	private String getType(FieldDeclaration field){
		String type = "";
		Object _type = field.getType();
		if(_type.getClass().equals(ParameterizedType.class)){
			ParameterizedType realType = (ParameterizedType)_type;
			if(realType.typeArguments().size() > 0){
				type = ((ParameterizedType)_type).typeArguments().get(0).toString();
			}
		}else if(_type.getClass().equals(SimpleType.class)) {
			type = ((SimpleType)_type).getName().toString();
		}else {
			type = _type.toString();
		}
		return type;
	}
	
	/**
	 * 
	 * @param field
	 * @return
	 */
	private String getName(FieldDeclaration field){
		String name = "";
		String fieldStr = field.toString();
		String[] fieldArray = fieldStr.split(" ");
		if(fieldArray.length > 0){
			name = fieldArray[fieldArray.length - 1].replace(";", "");
		}
		return name;
	}
	
	/**
	 * 获取描述
	 * @return
	 */
	private String getDescription(FieldDeclaration field){
		String desc = "";
		Javadoc javadoc =  field.getJavadoc();
		if(javadoc == null){
			return desc;
		}
		List<Object> desciptions = javadoc.tags();
		if(desciptions != null){
			for (Object description : desciptions) {
				if(description.getClass().equals(TagElement.class)){
					TagElement realDesc = (TagElement)description;
					if(realDesc.fragments().size() > 0){
						desc = realDesc.fragments().get(0).toString();
						break;
					}
				}
			}
		}
		return desc;
	}
	
	/**
	 * 
	 * @param field
	 * @return
	 */
	private boolean isPublic(FieldDeclaration field){
		boolean isPublic = false;
		List<Object> modifers = field.modifiers();
		for (Object modifer : modifers) {
			if(modifer.getClass().equals(Modifier.class)){
				Modifier realModifier = (Modifier)modifer;
				return realModifier.isPublic();
			}
		}
		return isPublic;
	}

}
