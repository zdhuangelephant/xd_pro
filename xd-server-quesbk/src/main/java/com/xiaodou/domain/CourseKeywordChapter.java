package com.xiaodou.domain;

import java.util.Date;

public class CourseKeywordChapter extends BaseEntity {
  private Long id;

  private Long chapterId;
  
  private Long keywordId;

  private Date createTime;

  public Long getKeywordId() {
	return keywordId;
}

public void setKeywordId(Long keywordId) {
	this.keywordId = keywordId;
}

public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getChapterId() {
    return chapterId;
  }

  public void setChapterId(Long chapterId) {
    this.chapterId = chapterId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}