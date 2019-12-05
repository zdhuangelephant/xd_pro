package com.xiaodou.mooccrawler.web.request;

import com.xiaodou.summer.vo.in.BaseRequest;

/**
 * Created by zyp on 15/7/5.
 */
public class ResourceVideoEditRequest extends BaseRequest {

  // 文档ID
  private Integer id;

  // 文档所属的大纲章节ID
  private Integer chapterId;

  // 文档名称
  private String name;

  // 文档所在的URL地址
  private String url;

  // 下载地址
  private String fileUrl;

  // 文档详细描述
  private String detail;

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }
}
