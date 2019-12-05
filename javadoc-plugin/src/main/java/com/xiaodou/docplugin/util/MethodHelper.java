package com.xiaodou.docplugin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.Type;

import com.xiaodou.docplugin.entity.ParamEntity;

/**
 * 
 * @author bin.song
 *
 */
public class MethodHelper {
	
	public static final String[] IgnoredRequestArray = 
			new String[]{"HttpServletResponse", "HttpServletRequest"};

	/**
	 * 
	 * @param descriptions
	 * @return
	 */
	public static String getMethodDesc(List<Object> descriptions){
		String desc = "";
		if(descriptions != null){
			for (Object description : descriptions) {
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
	 * @param type
	 * @param descriptions
	 * @return
	 */
	public static String getMethodReturnTypeDesc(Type type, List<Object> descriptions){
		String retType = "";
		if(descriptions != null){
			if(type.toString().equals("void")){
				return retType;
			}
			//如果描述size小于2，说明书写错误，直接返回空
			if(descriptions.size() < 2){
				return retType;
			}
			int descSize = descriptions.size() - 1;
			if(descriptions.get(descSize).getClass().equals(TagElement.class)){
				TagElement realDesc = (TagElement)descriptions.get(descSize);
				if(realDesc.fragments().size() > 0){
					retType = realDesc.fragments().get(0).toString();
				}
			}
		}
		return retType;
	}
	
	/**
	 * 获取变量描述
	 * @param params
	 * @param descriptions
	 * @return
	 */
	public static HashMap<String, ParamEntity> getMethodParamTypeDesc(List<Object> params, List<Object> descriptions){
		HashMap<String, ParamEntity> paramDescMap = new HashMap<String, ParamEntity>();
		if(descriptions != null){
			for (Object param : params) {
				if(param.getClass().equals(SingleVariableDeclaration.class)){
					ParamEntity paramEntity = new ParamEntity();
					//获取变量类型与名称
					SingleVariableDeclaration realParam = (SingleVariableDeclaration)param;
					String variableName = realParam.getName().toString();
					Type variableType = realParam.getType();
					paramEntity.setName(variableName);
					paramEntity.setType(variableType);
					
					//判断是否为可忽略的request
					boolean ignore = false;
					for (String ignoredReq : IgnoredRequestArray) {
						if(ignoredReq.endsWith(variableType.toString())){
							ignore = true;
							break;
						}
					}
					if(ignore){
						break;
					}
					
					//获取变量描述与名称字典
					for (Object description : descriptions) {
						if(description.getClass().equals(TagElement.class)){
							TagElement realDescription = (TagElement)description;
							//获取变量描述
							String cmpVariableName = realDescription.fragments().size() > 0 ? 
									realDescription.fragments().get(0).toString() : "";
							String variableDesc = realDescription.fragments().size() > 1 ? 
									realDescription.fragments().get(1).toString() : "";
							//判断变量名称是否相等
							if(variableName.equals(cmpVariableName)){
								paramEntity.setDesc(variableDesc);
								break;
							}							
						}
					}

					paramDescMap.put(variableName, paramEntity);
				}
			}
		}
		return paramDescMap;
	}
}
