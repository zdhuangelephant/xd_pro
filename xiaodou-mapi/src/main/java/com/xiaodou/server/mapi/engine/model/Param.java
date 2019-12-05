package com.xiaodou.server.mapi.engine.model;

/**
 * @name @see com.xiaodou.server.mapi.engine.model.Param.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description 参数模型
 * @version 1.0
 */
public class Param {

	/** constructor **/
	public Param(String name) {
		this.name = name;
	}
	public Param(String name,Object req) {
		this.name = name;
		this.req = req;
	}

	/** name 参数名 */
	private String name;
	/** req 参数*/
	private Object req;

	public String getname() {
		return name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getReq() {
		return req;
	}
	public void setReq(Object req) {
		this.req = req;
	}

}
