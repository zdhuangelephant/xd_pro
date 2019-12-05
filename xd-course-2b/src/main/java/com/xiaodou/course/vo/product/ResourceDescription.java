package com.xiaodou.course.vo.product;

/**
 * Created by zyp on 15/8/25.
 */
public class ResourceDescription {
  // 文件下载地址
  private String fileUrl;

  // 访问url
  private String url;

  // 时长展示分钟段
  private Integer timeLengthMinute;

  // 时长展示秒数段
  private Integer timeLengthSecond;

  // 音频文件大小M
  private Double size;
  

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

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

  
}
