package com.xiaodou.mooccrawler.domain.course;

import java.sql.Timestamp;

/**
 * 
 * @ClassName: KeywordModel
 * @Description: 章节的关键点(可以认为是知识点，标签)
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午9:55:38
 */
public class CourseKeywordModel {

	// 关键知识点的ID
	private Integer id;

	// 关键知识点所属的章节ID
	private Integer chapterId;

  // 描述
  private String detail;

	// 关键知识点名称
	private String name;

	// 创建时间
	private Timestamp createTime;

  // 章节名称
  private String chapterName;

  // 重要程度
  private Integer importanceLevel;

  // 是否被选择
  private Integer isSelected = 0;

  public Integer getIsSelected() {
    return isSelected;
  }

  public void setIsSelected(Integer isSelected) {
    this.isSelected = isSelected;
  }

  public Integer getImportanceLevel() {
    return importanceLevel;
  }

  public void setImportanceLevel(Integer importanceLevel) {
    this.importanceLevel = importanceLevel;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getChapterId() {
    return chapterId;
  }

  public void setChapterId(Integer chapterId) {
    this.chapterId = chapterId;
  }

  public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
}
