package com.xiaodou.docplugin.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.xiaodou.docplugin.entity.ApiClassJavadocEntity;
import com.xiaodou.docplugin.entity.ApiMethodJavadocEntity;
import com.xiaodou.docplugin.entity.ParamEntity;
import com.xiaodou.docplugin.entity.SubFieldEntity;
import com.xiaodou.docplugin.service.IMethodJavadocService;
import com.xiaodou.docplugin.service.IParamJavadocService;
import com.xiaodou.docplugin.util.AnnotationHelper;
import com.xiaodou.docplugin.util.ClassHelper;
import com.xiaodou.docplugin.util.MethodHelper;
import com.xiaodou.docplugin.util.TypeHelper;

public class MethodJavadocService implements IMethodJavadocService {

	private List<String> imports = new ArrayList<String>();
	
	/**
	 * 
	 */
	public List<ApiMethodJavadocEntity> getMethodJavadoc(
			TypeDeclaration type, CompilationUnit cUnit) {
		// TODO Auto-generated method stub
		List<ApiMethodJavadocEntity> methodJavadocEntities = new ArrayList<ApiMethodJavadocEntity>();
		//获取import的package列表
		imports = ClassHelper.getImports(cUnit);
		//获取方法集合
		MethodDeclaration[] methodDeclarationArray = type.getMethods();
		for (MethodDeclaration method : methodDeclarationArray) {
			//判断方法是否为HttpAPI方法：@RequestMapping，@ResponseBody，Public
			List<Object> annotations = new ArrayList<Object>();
			List<Object> descriptions = new ArrayList<Object>();
			if(!isHttpApiMethod(method)){
				continue;
			}
			System.out.println(method.getName().toString());
			if(method.getName().toString().contains("saveProductManagerToCRM")){
				System.out.println(method.getName().toString());
			}
			//定义实体
			ApiMethodJavadocEntity methodJavadocEntity = new ApiMethodJavadocEntity();
			//获取方法名称
			methodJavadocEntity.setMethodName(method.getName().toString());
			//获取RequestMapping value
			annotations = this.getAnnotations(method);
			methodJavadocEntity.setRequestMapping(getRequestMapping(method, annotations));
			//获取HttpAPI调用方式
			methodJavadocEntity.setHttpMethod(getHttpMethod(method, annotations));
			//获取方法描述
			descriptions = getDescription(method);
			methodJavadocEntity.setDescription(getDescription(descriptions));
			//获取返回值类型
			HashMap<SubFieldEntity, Object> retMap = getReturnMap(method.getReturnType2(), descriptions);
			methodJavadocEntity.setResponseDic(retMap);	
			//获取入参类型列表
			HashMap<SubFieldEntity, Object> paramMap = getParamMap(method, descriptions);
			methodJavadocEntity.setRequestDic(paramMap);
			if(paramMap != null){
				Collection<Object> values = paramMap.values();
				for (Object value : values) {
					if(value instanceof ApiClassJavadocEntity){
						methodJavadocEntity.setHttpMethod("POST");
						break;
					}
				}
			}
			methodJavadocEntities.add(methodJavadocEntity);
		}
		
		return methodJavadocEntities;
	}
	
	/**
	 * 
	 * @param method
	 * @return
	 */
	private HashMap<SubFieldEntity, Object> getReturnMap(Type type, List<Object> descriptions){
		HashMap<SubFieldEntity, Object> returnMap = new HashMap<SubFieldEntity, Object>();
		//Type returnType = method.getReturnType2();
		SubFieldEntity fieldEntity = new SubFieldEntity();
		fieldEntity.setName("");
		fieldEntity.setDescription(MethodHelper.getMethodReturnTypeDesc(type, descriptions));
		fieldEntity.setType(type.toString());
		String realRetType = TypeHelper.getType(type);
		IParamJavadocService paramJavadocService = new ParamJavadocService();
		Object realReturnType = paramJavadocService.getFieldJavadoc(realRetType, imports);
		returnMap.put(fieldEntity, realReturnType);
		return returnMap;
	}
	
	/**
	 * 
	 * @param method
	 * @return
	 */
	private HashMap<SubFieldEntity, Object> getParamMap(MethodDeclaration method, List<Object> descriptions){
		HashMap<SubFieldEntity, Object> paramMap = new HashMap<SubFieldEntity, Object>();
		HashMap<String, ParamEntity> paramDic = 
				MethodHelper.getMethodParamTypeDesc(method.parameters(), descriptions);
		for (String variable : paramDic.keySet()) {
			ParamEntity paramEntity = paramDic.get(variable);
			SubFieldEntity fieldEntity = new SubFieldEntity();
			fieldEntity.setName(paramEntity.getName());
			fieldEntity.setDescription(paramEntity.getDesc());
			fieldEntity.setType(paramEntity.getType().toString());
			String realRetType = TypeHelper.getType(paramEntity.getType());
			IParamJavadocService paramJavadocService = new ParamJavadocService();
			Object realReturnType = paramJavadocService.getFieldJavadoc(realRetType, imports);
			paramMap.put(fieldEntity, realReturnType);
		}
		return paramMap;
	}
	

	/**
	 * 
	 * @param type
	 * @return
	 */
	private List<Object> getAnnotations(MethodDeclaration method){
		List<Object> annotations = new ArrayList<Object>();
		List<Object> bodyDeclarations = method.modifiers();
		if(bodyDeclarations != null){
			for (Object bodyDeclaration : bodyDeclarations) {
				annotations.add(bodyDeclaration);
			}
		}
		return annotations;
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	private String getRequestMapping(MethodDeclaration method, List<Object> annotations){
		String requestMapping = "";
		for (Object annotation : annotations) {
			//遍历所有注解
			if(AnnotationHelper.isRequestMapping(annotation.toString())){
				//判断是否为SingleMemberAnnotation
				if(annotation.getClass().equals(SingleMemberAnnotation.class)){
					SingleMemberAnnotation realAnnotation =(SingleMemberAnnotation)annotation;
					requestMapping = realAnnotation.getValue().toString().trim().replace("\"", "");
				}else if(annotation.getClass().equals(NormalAnnotation.class)){
					//如果是NormalAnnotation，则需要遍历values
					NormalAnnotation realAnnotation = (NormalAnnotation)annotation;
					for (Object anno : realAnnotation.values()) {
						String realAnno = anno.toString().trim();
						if(realAnno.startsWith("value=\"")){
							requestMapping = realAnno.replace("value=", "").replace("\"", "");
							break;
						}
					}
				}
			}
		}
		return requestMapping;
	}
	
	/**
	 * 
	 * @param method
	 * @return
	 */
	private String getHttpMethod(MethodDeclaration method, List<Object> annotations){
		//样例：method=RequestMethod.POST
		String httpMethod = "GET";
		for (Object annotation : annotations) {
			
			//遍历所有注解
			if(AnnotationHelper.isRequestMapping(annotation.toString())){
				
				//判断是否为SingleMemberAnnotation
				if(annotation.getClass().equals(SingleMemberAnnotation.class)){
					SingleMemberAnnotation realAnnotation =(SingleMemberAnnotation)annotation;
					String tmpHttpMethod = realAnnotation.getValue().toString().trim();
					if(tmpHttpMethod.startsWith("RequestMethod.")){
						httpMethod = tmpHttpMethod.replace("RequestMethod.", "").toUpperCase();
					}
				}else if(annotation.getClass().equals(NormalAnnotation.class)){
					//如果是NormalAnnotation，则需要遍历values
					NormalAnnotation realAnnotation = (NormalAnnotation)annotation;
					for (Object anno : realAnnotation.values()) {
						String realAnno = anno.toString().trim();
						if(realAnno.startsWith("method=RequestMethod.")){
							httpMethod = realAnno.replace("method=RequestMethod.", "").toUpperCase();
							break;
						}
					}
				}
				
			}
		}
		return httpMethod;
	}


	/**
	 * 
	 * @param type
	 * @return
	 */
	private List<Object> getDescription(MethodDeclaration method){
		List<Object> descriptions = new ArrayList<Object>();
		Javadoc javadoc = method.getJavadoc();
		if(javadoc != null){
			descriptions = javadoc.tags();
		}
		return descriptions;
	}
	
	/**
	 * 获取描述
	 * @return
	 */
	private String getDescription(List<Object> descriptions){
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
	 * 判断是否为httpmethod
	 * @param method
	 * @return
	 */
	private boolean isHttpApiMethod(MethodDeclaration method){
		boolean isHttpApiMethod =false;
		boolean isPublic = false;
		boolean isResquestMapping = false;
		boolean isResponseBody = false;
		List<Object> bodyDeclarations = method.modifiers();
		if(bodyDeclarations != null){
			for (Object bodyDeclaration : bodyDeclarations) {
				if(bodyDeclaration.getClass().equals(Modifier.class)){
					Modifier body = (Modifier)bodyDeclaration;
					if(body.toString().equals("public")){
						isPublic = true;
					}
				}
				if(bodyDeclaration.getClass().equals(NormalAnnotation.class)){
					NormalAnnotation body = (NormalAnnotation)bodyDeclaration;
					if(body.getTypeName().toString().equals("RequestMapping")){
						isResquestMapping = true;
					}
				}
				if(bodyDeclaration.getClass().equals(SingleMemberAnnotation.class)){
					SingleMemberAnnotation body = (SingleMemberAnnotation)bodyDeclaration;
					if(body.getTypeName().toString().equals("RequestMapping")){
						isResquestMapping = true;
					}
				}
				if(bodyDeclaration.getClass().equals(MarkerAnnotation.class)){
					MarkerAnnotation body = (MarkerAnnotation)bodyDeclaration;					
					if(body.getTypeName().toString().equals("ResponseBody")){
						isResponseBody = true;
					}
				}
			}
		}
		isHttpApiMethod = isPublic && isResquestMapping && isResponseBody;
		return isHttpApiMethod;
	}
}
