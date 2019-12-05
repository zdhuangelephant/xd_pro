package com.xiaodou.moofficial.entity;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * @name AuthorModel CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月6日
 * @description 作者模型
 * @version 1.0
 */
@Data
public class AuthorModel {

	/** id 主键ID */
	@Column(isMajor = true, canUpdate = false)
	private Long id;
	/** name 姓名 */
	private String name;
	/** gender 性别 */
	private Short gender;
	/** portrait 头像 */
	private String portrait;
	/** cover 封面 */
	private String cover;
	/** info 描述 */
	private String info;
	/** createTime 创建时间 */
	@Column(betweenScope = true)
	private Timestamp createTime;
	/** worksNum 发布作品数 */
	private Integer worksNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getGender() {
		return gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
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

	public Integer getWorksNum() {
		return worksNum;
	}

	public void setWorksNum(Integer worksNum) {
		this.worksNum = worksNum;
	}

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(AuthorModel.class, "xd_resource_author", "src/main/resources/conf/mybatis")
				.buildXml();
	}
}
