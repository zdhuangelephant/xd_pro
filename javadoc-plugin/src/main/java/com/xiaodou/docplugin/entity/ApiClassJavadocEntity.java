package com.xiaodou.docplugin.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * javadoc实体，针对于httpapi
 * @author bin.song
 *
 */
public class ApiClassJavadocEntity {
	
	public ApiClassJavadocEntity(){
		isController = false;
		isApi = true;
	}
	
	private boolean isController;
	
	private String apiName;
	
	private String requestMapping;
	
	private String description;
	
	private String author;
	
	private boolean isApi;
	
	/**
	 * 类全名称，如com.elong.hotel.java.entity.TestClassEntity，用于load class时使用
	 */
	private String fullName;
	
	private HashMap<SubFieldEntity, Object> fieldList;

	private List<ApiMethodJavadocEntity> methodList;
	
	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getRequestMapping() {
		return requestMapping;
	}

	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}

	public List<ApiMethodJavadocEntity> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<ApiMethodJavadocEntity> methodList) {
		this.methodList = methodList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public HashMap<SubFieldEntity, Object> getFieldList() {
		return fieldList;
	}

	public void setFieldList(HashMap<SubFieldEntity, Object> fieldList) {
		this.fieldList = fieldList;
	}

	public boolean isController() {
		return isController;
	}

	public void setController(boolean isController) {
		this.isController = isController;
	}
	
	/**
	 * 
	 */
	public void printSelf(){
		System.out.println("==============================================");
		System.out.println("");
		
		System.out.println("|--Controller=" + this.apiName);
		System.out.println("|----Author=" + this.author);
		System.out.println("|----Description=" + this.description);
		System.out.println("|----RequestMapping=" + this.requestMapping);
		
		for (ApiMethodJavadocEntity method : methodList) {
			System.out.println("|------Method=" + method.getMethodName());
			System.out.println("|--------Description=" + method.getDescription());
			System.out.println("|--------RequestMapping=" + this.requestMapping + "/" + method.getRequestMapping());
			System.out.println("|--------HttpMethod=" + method.getHttpMethod());
			System.out.println("|--------Request Param List:");
			printField(method.getRequestDic());
			System.out.println("|---------------------------------");
			System.out.println("|--------Response Type:");
			printField(method.getResponseDic());	
			System.out.println("|---------------------------------");
		}

		System.out.println("==============================================");
		System.out.println("");
	}
	
	/**
	 * 
	 * @param fieldDic
	 */
	public void printField(HashMap<SubFieldEntity, Object> fieldDic){
		if(fieldDic != null){
			Set<SubFieldEntity> keySet = fieldDic.keySet();
			if(keySet != null){
				for (SubFieldEntity field : keySet) {
					Object fieldType = fieldDic.get(field);
					if(fieldType instanceof ApiClassJavadocEntity){
						ApiClassJavadocEntity realFieldType = (ApiClassJavadocEntity)fieldType;
						System.out.println("++FieldName=" + field.getName());
						System.out.println("++Description=" + field.getDescription());
						System.out.println("++Type=" + field.getType());
						System.out.println("####" + field.getType() + "为复合类，如下所示：");
						printClassField(realFieldType);
					}else {
						System.out.println("++FieldName=" + field.getName());
						System.out.println("++Description=" + field.getDescription());
						System.out.println("++Type=" + field.getType());
					}
				}
				if(keySet.size() <= 0){
					System.out.println("|--------无任何field...");
				}
			}
		}
		
	}
	
	
	public void printClassField(ApiClassJavadocEntity classField){
		System.out.println("####Name=" + classField.getApiName());
		System.out.println("####Author=" + classField.getAuthor());
		System.out.println("####Description=" + classField.getDescription());
		HashMap<SubFieldEntity, Object> fieldDic = classField.getFieldList();
		Set<SubFieldEntity> keySet = fieldDic.keySet();
		if(keySet != null){
			for (SubFieldEntity subfield : keySet) {
				Object fieldType = fieldDic.get(subfield);
				if(fieldType instanceof ApiClassJavadocEntity){
					ApiClassJavadocEntity realFieldType = (ApiClassJavadocEntity)fieldType;
					System.out.println("++FieldName=" + subfield.getName());
					System.out.println("++Description=" + subfield.getDescription());
					System.out.println("++Type=" + subfield.getType());
					System.out.println("####" + subfield.getType() + "为复合类，如下所示：");
					printClassField(realFieldType);
				}else {
					System.out.println("++FieldName=" + subfield.getName());
					System.out.println("++Description=" + subfield.getDescription());
					System.out.println("++Type=" + subfield.getType());
				}
			}
			if(keySet.size() <= 0){
				System.out.println("|--------无任何field...");
			}
		}
		
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isApi() {
		return isApi;
	}

	public void setApi(boolean isApi) {
		this.isApi = isApi;
	}
}
