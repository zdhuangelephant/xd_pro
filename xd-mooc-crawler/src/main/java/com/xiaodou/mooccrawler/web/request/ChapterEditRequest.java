package com.xiaodou.mooccrawler.web.request;

import com.xiaodou.summer.vo.in.BaseRequest;

/**
 * Created by zyp on 15/4/19.
 */
public class ChapterEditRequest extends BaseRequest {

  /** 章节ID */
  private Integer id;

  /** 章节父ID */
  private Integer parentId;

  /** 当前章节的名称 */
  private String name;

  /** sIndex 中文序号 */
  private String sIndex;

  /** 章节的描述 */
  private String detail;

  private Integer subjectId;

  public String getsIndex() {
    return sIndex;
  }

  public void setsIndex(String sIndex) {
    this.sIndex = sIndex;
  }

  public Integer getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(Integer subjectId) {
    this.subjectId = subjectId;
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
}
