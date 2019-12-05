package com.xiaodou.st.dataclean.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.st.dataclean.model.transport.TransferStudentData.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月7日
 * @description 用户数据
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferStudentData extends BaseTransferModel {

  /** userId 用户ID */
  @NotEmpty
  private String userId;
  /** sourcePortrait 用户上传头像 */
  @NotEmpty
  private String sourcePortrait;
  /** clientType 设备类型 */
  @NotEmpty
  private String clientType;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getSourcePortrait() {
    return sourcePortrait;
  }

  public void setSourcePortrait(String sourcePortrait) {
    this.sourcePortrait = sourcePortrait;
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

}
