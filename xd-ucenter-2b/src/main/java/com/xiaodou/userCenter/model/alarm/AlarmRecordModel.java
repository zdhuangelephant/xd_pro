package com.xiaodou.userCenter.model.alarm;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.userCenter.common.enums.AlarmLevelEnum;
import com.xiaodou.userCenter.common.enums.AlarmTypeEnum;
import com.xiaodou.userCenter.common.enums.PretreatmentEnum;
import com.xiaodou.userCenter.common.enums.StatusEnum;

import lombok.Data;

@Data
public class AlarmRecordModel {

  /* id 主键id */
  @Column(isMajor = true)
  private Long id;
  /* 学生id */
  @Column(canUpdate = false)
  private Long userId;
  /* 触发报警时的设备号 */
  @Column(canUpdate = false)
  private String deviceId;
  /* 触发报警的id 登录记录id */
  private Long loginInfoId;
  /* alarmLevel 报警级别 初级，中级，高级 */
  @Column(canUpdate = false)
  private AlarmLevelEnum alarmLevel;
  /* alarmType 报警类型 */
  @Column(canUpdate = false)
  private AlarmTypeEnum alarmType;
  /* status 状态 */
  @Column(canUpdate = true)
  private StatusEnum status;
  /* pretreatment 预处理 */
  @Column(canUpdate = false)
  private PretreatmentEnum pretreatment;
  /* alarmTime 报警时间 */
  @Column(canUpdate = false)
  private Timestamp alarmTime;
  /* createTime 创建时间 */
  @Column(canUpdate = false)
  private Timestamp createTime;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(AlarmRecordModel.class, "xd_user_login_alarm_record",
        "D:/work/workspace_xd/xd-ucenter-2b/src/main/resources/conf/mybatis/alarm/")
        .buildXml();
  }

}
