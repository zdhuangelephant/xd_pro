package com.xiaodou.userCenter.vo.alarm;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;

/**
 * @name RemoteInvokeExceptionAlarm 
 * CopyRright (c) 2018 by Corp.XiaodouTech
 *
 * @author <a href="mailto:hzd82274@gmail.com">zdhuang</a>
 * @date 2018年3月15日
 * @description 异步消息消费异常报警类 
 * @version 1.0
 */
public class UCenterExecuteExceptionAlarm extends AlarmEntityImpl {

  private String eventModule = "xd-ucenter";
  private String messageInfo = "异步消息消费异常. %s";
  private String mailInfo = "异步消息消费异常. %s";
  private String eventName;

  public enum UcenterAlarmEnum{
    REGIST_USER_FAIL("regist-user-fail","[用户注册失败]. %s"),
    CHECK_TOKEN_FAIL("check-token-fail","[校验token失败]. %s"),
    FIND_MODULE_FAIL("find-module-fail","[沒有找到任何地域信息(module)]. %s"),
    LOGIN_USER_FAIL("login-user-fail","[用户登录失败]. %s"),
    IMPORT_USER_FAIL("import-user-fail","[导入用户失败]. %s");
    private String code;
    private String msg;
    UcenterAlarmEnum(String code,String msg) {
      this.code = code;
      this.msg = msg;
    }
    public String getCode() {
      return code;
    }
    public void setCode(String code) {
      this.code = code;
    }
    public String getMsg() {
      return msg;
    }
    public void setMsg(String msg) {
      this.msg = msg;
    }
  }
  
  public UCenterExecuteExceptionAlarm(UcenterAlarmEnum alarmEnum,String message) {
    this.messageInfo = String.format(alarmEnum.getMsg(), message);
    this.mailInfo = String.format(alarmEnum.getMsg(), message);
    this.eventName = alarmEnum.getCode();
  }

  @Override
  public String getEventModule() {
    return eventModule;
  }

  @Override
  public String getEventName() {
    return eventName;
  }

  @Override
  public String getMessageInfo() {
    return messageInfo;
  }

  @Override
  public String getMailInfo() {
    return mailInfo;
  }

  @Override
  public IAlarmEntity getLoggerEntity() {
    return this;
  }
  
}