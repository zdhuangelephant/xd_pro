package com.xiaodou.docplugin.entity;

import org.eclipse.jdt.core.dom.Type;

/**
 * 
 * @author bin.song
 *
 */
public class ParamEntity {

	private String name;
	
	private Type type;
	
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	public ParamEntity(){
		this.name = "";
		this.type = null;
		this.desc = "";
	}
}
