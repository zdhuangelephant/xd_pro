package com.xiaodou.mooccrawler.web.request;

import com.xiaodou.summer.vo.in.BaseRequest;

/**
 * @name @see com.xiaodou.ms.web.request.course.ResourceAudioEditRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月21日
 * @description 音频资源修改请求
 * @version 1.0
 */
public class ResourceAudioEditRequest extends BaseRequest {

  // 音频ID
  private Integer id;

  // 音频所属的大纲章节ID
  private Integer chapterId;

  // 音频名称
  private String name;

  // 音频所在的URL地址
  private String url;

  // 时长展示分钟段
  private Integer timeLengthMinute;

  // 时长展示秒数段
  private Integer timeLengthSecond;

  // 音频文件大小M
  private Double size;

  // 音频详细描述
  private String detail;

  public Integer getTimeLengthMinute() {
    return timeLengthMinute;
  }

  public void setTimeLengthMinute(Integer timeLengthMinute) {
    this.timeLengthMinute = timeLengthMinute;
  }

  public Integer getTimeLengthSecond() {
    return timeLengthSecond;
  }

  public void setTimeLengthSecond(Integer timeLengthSecond) {
    this.timeLengthSecond = timeLengthSecond;
  }

  public Double getSize() {
    return size;
  }

  public void setSize(Double size) {
    this.size = size;
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
