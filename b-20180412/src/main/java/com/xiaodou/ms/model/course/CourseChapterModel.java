package com.xiaodou.ms.model.course;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * @author zhaoxu.yang
 * @ClassName: ChapterModel
 * @Description: 大纲章节Model
 * @date 2015年4月11日 下午5:30:59
 */
@Data
public class CourseChapterModel {

	/** 章节ID */
	@Column(isMajor = true)
	private Long id;

	/** 章节对应的科目ID */
	private Long subjectId;

	/** 章节父ID */
	private Long parentId;

	/** 所有的第一级子ID */
	private String childId;

	/** 所有的子孙ID */
	private String allChildId;

	/** 所有父ID */
	private String allParentId;

	/** 当前章节的名称 */
	private String name;

	/** sindex 当前章节序号 */
	private String sindex;

	/** 所在层级(从1级开始，最好不要超过3级) */
	private Integer level;

	/** 章节的描述 */
	private String detail;

	/** 创建时间 */
	private Timestamp createTime;

	/** 是否为叶节点 */
	private Integer isLeaf;

	/** 排序 */
	private Integer listOrder;
	/** 任务ID */
	private String missionId;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(CourseChapterModel.class,
				"xd_course_chapter").buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public String getAllChildId() {
		return allChildId;
	}

	public void setAllChildId(String allChildId) {
		this.allChildId = allChildId;
	}

	public String getAllParentId() {
		return allParentId;
	}

	public void setAllParentId(String allParentId) {
		this.allParentId = allParentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSindex() {
		return sindex;
	}

	public void setSindex(String sindex) {
		this.sindex = sindex;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

}
