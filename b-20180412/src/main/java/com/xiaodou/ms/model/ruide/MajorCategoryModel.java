package com.xiaodou.ms.model.ruide;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class MajorCategoryModel {
	@Column(isMajor = true, persistent = true)
	private Long id;
	
	private String majorCategory;
	
	private String image;
	
	private String remark;
	
	/* 创建时间 */
	@Column(canUpdate = false)
	private Timestamp createTime;
	
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(MajorCategoryModel.class, "xd_rd_major_category",
	        "F:/snippet/eclipseWorks/xiaodou-ms-2b/src/main/resources/conf/mybatis/ruide/")
	        .buildXml();
	  }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMajorCategory() {
		return majorCategory;
	}

	public void setMajorCategory(String majorCategory) {
		this.majorCategory = majorCategory;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
