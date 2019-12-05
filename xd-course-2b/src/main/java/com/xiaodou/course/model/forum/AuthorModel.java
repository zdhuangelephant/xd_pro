package com.xiaodou.course.model.forum;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

public class AuthorModel {
	@GeneralField
	@Column(isMajor = true)
	private String id;
	@GeneralField
	private String name;
	@GeneralField
	private String portrait;
	@GeneralField
	private String cover;
	@GeneralField
	private String gender;
	@GeneralField
	private String info;	
	@GeneralField
	private Timestamp createTime;
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(AuthorModel.class, "xd_resouces_author",
	            "E:/work3/xd-course-branch/src/main/resources/conf/mybatis/forum/")
	            .buildXml();
	}

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getPortrait() {
		return portrait;
	}




	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}




	public String getCover() {
		return cover;
	}




	public void setCover(String cover) {
		this.cover = cover;
	}




	public String getGender() {
		return gender;
	}




	public void setGender(String gender) {
		this.gender = gender;
	}




	public String getInfo() {
		return info;
	}




	public void setInfo(String info) {
		this.info = info;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}





}
