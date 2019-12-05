package com.xiaodou.common.util.log.model;

import org.apache.commons.httpclient.NameValuePair;

/**
 * @name @see com.xiaodou.common.util.log.model.AlarmEntityImpl.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月12日
 * @description 报警类基础实现
 * @version 1.0
 */
public abstract class AlarmEntityImpl extends BaseEntity implements IAlarmEntity {

  public NameValuePair[] getParams() {
    NameValuePair[] params = new NameValuePair[4];
    params[0] = new NameValuePair("eventModule", getEventModule());
    params[1] = new NameValuePair("eventName", getEventName());
    params[2] = new NameValuePair("messageInfo", getMessageInfo());
    params[3] = new NameValuePair("mailInfo", getMailInfo());
    return params;
  }

}
