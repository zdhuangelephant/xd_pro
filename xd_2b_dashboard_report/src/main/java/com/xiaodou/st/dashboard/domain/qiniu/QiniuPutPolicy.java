package com.xiaodou.st.dashboard.domain.qiniu;

import lombok.Data;

@Data
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
}
