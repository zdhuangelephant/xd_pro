package com.xiaodou.docplugin.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author bin.song
 *
 */
public class TestClassEntity {

	private String name;
	
	private int id;
	
	private float score;
	
	private List<SubFieldEntity> fieldList;
	
	private Set<SubFieldEntity> fieldSet;
	
	private HashMap<SubFieldEntity, ParamEntity> myMap;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public List<SubFieldEntity> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<SubFieldEntity> fieldList) {
		this.fieldList = fieldList;
	}

	public Set<SubFieldEntity> getFieldSet() {
		return fieldSet;
	}

	public void setFieldSet(Set<SubFieldEntity> fieldSet) {
		this.fieldSet = fieldSet;
	}

	public HashMap<SubFieldEntity, ParamEntity> getMyMap() {
		return myMap;
	}

	public void setMyMap(HashMap<SubFieldEntity, ParamEntity> myMap) {
		this.myMap = myMap;
	}
}
