package com.xiaodou.st.dataclean.model.domain.dashboard.score;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;

/**
 * 
 * @name LearnRecordDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月31日
 * @description 由成绩列表页进入， 统计课程维度下面的学习时长
 * @version 1.0
 */

@Data
public class LearnRecordDO {
	@Column(isMajor = true)
	private Long id;
	/* 学生id */
	private Integer studentId;
	/* 产品id */
	private Long productId;
	/* 学习行为 */
	private Short learnType;
	/* 学习内容 */
	private String learnContent;
	/* 记录时间 */
	private Date recordTime;
	/* 学习时长 */
	private Integer learnTime;
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						LearnRecordDO.class,
						"xd_dashboard_learn_record",
						"D:/work/workspace_xd/xd_2b_dashboard_clean/src/main/resources/conf/mybatis/dashboard/score/")
				.buildXml();
	}

	public static LearnRecordDO getInstance(RawDataLearnRecordModel rdl)
			throws ParseException {
		if (null == rdl)
			return null;
		LearnRecordDO ardo = new LearnRecordDO();
		if (StringUtils.isNotBlank(rdl.getProductId()))
			ardo.setProductId(Long.valueOf(rdl.getProductId()));
		ardo.setStudentId(rdl.getStudentId());
		ardo.setLearnContent(rdl.getLearnContent());
		ardo.setLearnTime(rdl.getLearnTime());
		ardo.setLearnType(rdl.getLearnType());
		if (StringUtils.isNotBlank(rdl.getRecordTime())) {
			ardo.setRecordTime(DateUtil.SDF_YMD.parse(rdl.getRecordTime()));
		}
		ardo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		return ardo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Short getLearnType() {
		return learnType;
	}

	public void setLearnType(Short learnType) {
		this.learnType = learnType;
	}

	public String getLearnContent() {
		return learnContent;
	}

	public void setLearnContent(String learnContent) {
		this.learnContent = learnContent;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Integer getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(Integer learnTime) {
		this.learnTime = learnTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
