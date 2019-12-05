package com.xiaodou.rdonline.domain.majorCategory;

import java.sql.Timestamp;
import java.util.List;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;

import lombok.Data;

@Data
public class MajorCategoryModel {
	@Column(isMajor = true, betweenScope = true, persistent = true)
	private Long id;
	
	private String majorCategory;
	
	private String image;
	
	private String remark;
	/* 创建时间 */
	@Column(canUpdate = false)
	private Timestamp createTime;
	
	private List<TutorMajorModel> listTutorMajor;
	
	private String htmlContent;
	
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(MajorCategoryModel.class, "xd_rd_major_category",
	        "F:/snippet/eclipseWorks/xd-rd-online/src/main/resources/conf/mybatis/majorCategory")
	        .buildXml();
	  }
	
}
