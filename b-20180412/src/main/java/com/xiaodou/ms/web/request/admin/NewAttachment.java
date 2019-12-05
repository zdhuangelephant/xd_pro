package com.xiaodou.ms.web.request.admin;


import com.xiaodou.ms.web.request.BaseRequest;

/**
 * Created by zyp on 14-9-23.
 */
public class NewAttachment extends BaseRequest {

  /**
   * 附件模块名称
   */
  private String module;

  /**
   * 栏目id
   */
  private String catId;

  /**
   * 是否加载水印
   */
  private Integer watermark = 0;

  /**
   * 是否为加密文件
   */
  private Integer isSecurity = 0;

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getCatId() {
    return catId;
  }

  public void setCatId(String catId) {
    this.catId = catId;
  }

  public Integer getWatermark() {
    return watermark;
  }

  public void setWatermark(Integer watermark) {
    this.watermark = watermark;
  }

  public Integer getIsSecurity() {
    return isSecurity;
  }

  public void setIsSecurity(Integer isSecurity) {
    this.isSecurity = isSecurity;
  }
}
