package com.xiaodou.ms.vo;

/**
 * Created by zyp on 15/7/18.
 */
public class QiniuPutPolicy {

  /**
   * 空间
   */
  private String scope;

  /**
   * 限定为“新增”语意
   */
  private Integer insertOnly = 1;

  /**
   * 上传成功后，自定义七牛云最终返回給上传端的数据
   */
  private String returnBody;

  /**
   * 限定用户上传的文件类型
   */
  private String mimeLimit;

  /**
   * 限定上传文件的大小，单位：字节（Byte）
   */
  private Integer fsizeLimit;

  public Integer getInsertOnly() {
    return insertOnly;
  }

  public void setInsertOnly(Integer insertOnly) {
    this.insertOnly = insertOnly;
  }

  public String getReturnBody() {
    return returnBody;
  }

  public void setReturnBody(String returnBody) {
    this.returnBody = returnBody;
  }

  public String getMimeLimit() {
    return mimeLimit;
  }

  public void setMimeLimit(String mimeLimit) {
    this.mimeLimit = mimeLimit;
  }

  public Integer getFsizeLimit() {
    return fsizeLimit;
  }

  public void setFsizeLimit(Integer fsizeLimit) {
    this.fsizeLimit = fsizeLimit;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }
}
