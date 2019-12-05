package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.validation.Errors;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.mission.vo.request.EventRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.summer.web.BaseController;

/**
 * @name @see com.xiaodou.mission.engine.event.BaseEvent.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月17日
 * @description 基础事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEvent extends BaseValidatorPojo {

  private final static String MESSAGE_NAME_TEMP = "mission_event";

  /** module 所属地域 */
  @NotEmpty
  private String module;
  @NotEmpty
  /** userId 操作用户ID */
  private String userId;
  /** majorId 专业ID */
  private String majorId;
  /** targetUserId 目标用户ID */
  private String targetUserId;
  /** courseId 课程ID */
  private String courseId;

  /**
   * 获取关键阀值
   * 
   * @return 关键阀值
   */
  public String getThreshold() {
    return null;
  }

  public void send() {
    Errors errors = this.validate();
    if (errors.hasErrors()) {
      String errMsg = BaseController.getErrMsg(errors);
      RuntimeException e =
          new RuntimeException(String.format("发送事件失败:%s:%s", this.getClass().getSimpleName(),
              errMsg));
      LoggerUtil.error(errMsg, e);
      throw e;
    }
    EventRequest req = EventBuilder.buildRequest(this);
    AbstractMessagePojo pojo = new AbstractMessagePojo(MESSAGE_NAME_TEMP);
    pojo.setCustomTag(req.getEventTag());
    pojo.setTransferObject(req);
    RabbitMQSender.getInstance().send(pojo);
  }

}
