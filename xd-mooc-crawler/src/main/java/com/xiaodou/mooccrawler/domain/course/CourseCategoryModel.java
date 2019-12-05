package com.xiaodou.mooccrawler.domain.course;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: CategoryModel
 * @Description: 课程分类Model
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午1:46:20
 */
public class CourseCategoryModel {

	// 课程分类ID
	private Integer id;
	// 课程分类父ID
	private Integer parentId;
	// 课程分类名称
	private String name;
	// 课程分类详细介绍
	private String detail;
	// 课程分类创建时间
	private Timestamp createTime;
	// 子ID
	private String childId;
	// 所有的子ID
	private String allChildId;
	// 所有的父ID
	private String allParentId;
	// level
	private Integer level;
	// 是否为叶节点
	private Integer isLeaf;
	// 所有父节点
	private List<CourseCategoryModel> allParentList;
	// 所有子节点
	private List<CourseCategoryModel> allChildList;
	// 子节点
	private List<CourseCategoryModel> childList;

	public List<CourseCategoryModel> getAllParentList() {
		return allParentList;
	}

	public void setAllParentList(List<CourseCategoryModel> allParentList) {
		this.allParentList = allParentList;
	}

	public List<CourseCategoryModel> getAllChildList() {
		return allChildList;
	}

	public void setAllChildList(List<CourseCategoryModel> allChildList) {
		this.allChildList = allChildList;
	}

	public List<CourseCategoryModel> getChildList() {
		return childList;
	}

	public void setChildList(List<CourseCategoryModel> childList) {
		this.childList = childList;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
