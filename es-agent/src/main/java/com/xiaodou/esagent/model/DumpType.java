package com.xiaodou.esagent.model;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.esagent.enums.CommonEnum.DumpMethod;

public class DumpType {
	private DumpMethod method;
	private Object object;
	private String id;
	public DumpType(){}
	public DumpType(DumpMethod method, String id, Object object) {
		this.id = id;
		this.method = method;
		this.object = object;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DumpMethod getMethod() {
		return method;
	}

	public void setMethod(DumpMethod method) {
		this.method = method;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public void setMethod(String method) {
		this.method = Enum.valueOf(DumpMethod.class, method);
	}

	@Override
	public String toString() {
		return FastJsonUtil.toJson(this);
	}
}
