package com.xiaodou.st.dataclean.vo.mq;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.st.dataclean.model.domain.dashboard.alarm.AlarmRecordModel;

/**
 * @name @see com.xiaodou.st.dataclean.vo.mq.AlarmMessage.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月24日
 * @description 报警消息
 * @version 1.0
 */
public class AlarmMessage extends AbstractMessagePojo {

  private static String MESSAGE_NAME = "2_sendAlarmWebMessage";
  
  public AlarmMessage(AlarmRecordModel alarmModel) {
    super(MESSAGE_NAME);
    setTransferObject(alarmModel);
  }

}
