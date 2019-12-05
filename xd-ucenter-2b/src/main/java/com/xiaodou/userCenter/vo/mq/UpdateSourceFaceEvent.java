package com.xiaodou.userCenter.vo.mq;

import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.userCenter.vo.mq.UpdateSourceFaceEvent.TransferStudentData;

/**
 * @name @see com.xiaodou.userCenter.vo.mq.UpdateSourceFaceEvent.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月11日
 * @description 更新基准面容消息
 * @version 1.0
 */
public class UpdateSourceFaceEvent extends DCCoreEvent<TransferStudentData> {

  private static final String EVENT_NAME = "updateStudentFace";

  public UpdateSourceFaceEvent() {
    setEventName(EVENT_NAME);
  }

  public static class TransferStudentData {

    /** module 所属地域 */
    @NotEmpty
    private String module;
    /** userId 用户ID */
    @NotEmpty
    private String userId;
    /** sourcePortrait 用户上传头像 */
    @NotEmpty
    private String sourcePortrait;
    /** clientType 设备类型 */
    @NotEmpty
    private String clientType;

    public String getModule() {
      return module;
    }

    public void setModule(String module) {
      this.module = module;
    }

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
}
