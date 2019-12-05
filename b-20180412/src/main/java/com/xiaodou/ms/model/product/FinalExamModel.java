package com.xiaodou.ms.model.product;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

@Data
public class FinalExamModel {
	/** id 主键ID */
	@Column(isMajor = true)
	private Long id;
	/** examName 试卷 */
	private String examName;
	/** questionNums 试题个数 */
	private Integer questionNums;
	/** sort 排序角标 */
	private Integer sort;
	/** courseId 关联课程id */
	private Long courseId;
	/** createTime 创建时间 */
	@Column(betweenScope = true, sortBy = true)
	private Timestamp createTime;
	// 闯关任务ID
	private String missionId;
	/** missionOrder 任务排序字段 */
	private Integer missionOrder;
	// 闯关任务状态
	private String missionState;
	// 地域
	private String module;

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(FinalExamModel.class,
						"xd_course_product_final_exam",
						"D:/snippets/eclipseWorks/xiaodou-ms-2b/src/main/resources/conf/mybatis/")
				.buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public Integer getQuestionNums() {
		return questionNums;
	}

	public void setQuestionNums(Integer questionNums) {
		this.questionNums = questionNums;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public Integer getMissionOrder() {
		return missionOrder;
	}

	public void setMissionOrder(Integer missionOrder) {
		this.missionOrder = missionOrder;
	}

	public String getMissionState() {
		return missionState;
	}

	public void setMissionState(String missionState) {
		this.missionState = missionState;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	

}
