package com.xiaodou.dashboard.vo.alarm.request;

import com.xiaodou.dashboard.model.alarm.domain.AlarmPolicyDo;


/**
 * @name @see com.xiaodou.dashboard.vo.alarm.request.PolicyRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月9日
 * @description 报警请求
 * @version 1.0
 */
public class PolicyRequest {
  /** alarmPolicyId 报警策略ID */
  private String alarmPolicyId;
  /** alarmPolicyName 报警策略名 */
  private String alarmPolicyName;
  /** starttime 开始时间 */
  private Integer starttime;
  /** endtime 结束时间 */
  private Integer endtime;
  /** message 短信联系人 */
  private String message;
  /** mail 邮件联系人 */
  private String mail;
  /** dingURL 钉钉机器人 */
  private String dingURL;
  /** group 所属分组 */
  private String group;

  public String getAlarmPolicyId() {
    return alarmPolicyId;
  }

  public void setAlarmPolicyId(String alarmPolicyId) {
    this.alarmPolicyId = alarmPolicyId;
  }

  public String getAlarmPolicyName() {
    return alarmPolicyName;
  }

  public void setAlarmPolicyName(String alarmPolicyName) {
    this.alarmPolicyName = alarmPolicyName;
  }

  public Integer getStarttime() {
    return starttime;
  }

  public void setStarttime(Integer starttime) {
    this.starttime = starttime;
  }

  public Integer getEndtime() {
    return endtime;
  }

  public void setEndtime(Integer endtime) {
    this.endtime = endtime;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getDingURL() {
    return dingURL;
  }

  public void setDingURL(String dingURL) {
    this.dingURL = dingURL;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public AlarmPolicyDo getPolicyDo() {
    AlarmPolicyDo policyDo = new AlarmPolicyDo();
    policyDo.setAlarmPolicyId(alarmPolicyId);
    policyDo.setAlarmPolicyName(alarmPolicyName);
    policyDo.setStarttime(starttime);
    policyDo.setEndtime(endtime);
    policyDo.setMessage(message);
    policyDo.setMail(mail);
    policyDo.setDingURL(dingURL);
    policyDo.setGroup(group);
    return policyDo;
  }
}
