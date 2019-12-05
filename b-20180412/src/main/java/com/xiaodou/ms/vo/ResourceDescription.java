package com.xiaodou.ms.vo;

/**
 * Created by zyp on 15/8/20.
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
  
  private String name;
  private String id;
  private String cover;

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }
  

}
