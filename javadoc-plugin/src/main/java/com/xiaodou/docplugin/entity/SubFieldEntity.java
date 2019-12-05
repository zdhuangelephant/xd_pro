package com.xiaodou.docplugin.entity;

/**
 * 字段实体，包括字段名称与描述
 * @author bin.song
 *
 */
public class SubFieldEntity {

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 类型，如：List<T>或者T
	 */
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.replace("\n", "").replace("\r", "");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description.replace("\n", "").replace("\r", "");;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
