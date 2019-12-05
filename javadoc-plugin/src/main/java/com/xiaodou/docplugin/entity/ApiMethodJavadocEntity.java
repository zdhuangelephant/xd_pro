package com.xiaodou.docplugin.entity;

import java.util.HashMap;

/**
 * 方法javadoc实体
 * @author bin.song
 *
 */
public class ApiMethodJavadocEntity {

	//方法名称
	private String methodName;
	//url mapping
	private String requestMapping;
	//描述
	private String description;
	//http方法类别
	private String httpMethod;
	//请求map,当类型为复合类型时(List<T>),List<T>存储在key中，value中存储真正的类
	private HashMap<SubFieldEntity, Object> requestDic;
	//返回值map
	private HashMap<SubFieldEntity, Object> responseDic;
	//请求JSON
	private String requestJSON;
	//返回值JSON
	private String responseJSON;
	
	public String getRequestJSON() {
		return requestJSON;
	}
	public void setRequestJSON(String requestJSON) {
		this.requestJSON = requestJSON;
	}
	public String getResponseJSON() {
		return responseJSON;
	}
	public void setResponseJSON(String responseJSON) {
		this.responseJSON = responseJSON;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getRequestMapping() {
		return requestMapping;
	}
	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public HashMap<SubFieldEntity, Object> getRequestDic() {
		return requestDic;
	}
	public void setRequestDic(HashMap<SubFieldEntity, Object> requestDic) {
		this.requestDic = requestDic;
	}
	public HashMap<SubFieldEntity, Object> getResponseDic() {
		return responseDic;
	}
	public void setResponseDic(HashMap<SubFieldEntity, Object> responseDic) {
		this.responseDic = responseDic;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
