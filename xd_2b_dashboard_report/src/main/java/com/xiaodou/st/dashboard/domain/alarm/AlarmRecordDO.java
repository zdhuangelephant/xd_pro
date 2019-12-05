package com.xiaodou.st.dashboard.domain.alarm;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.st.dashboard.constants.enums.AlarmLevelEnum;
import com.xiaodou.st.dashboard.constants.enums.AlarmTypeEnum;
import com.xiaodou.st.dashboard.constants.enums.PretreatmentEnum;
import com.xiaodou.st.dashboard.constants.enums.StatusEnum;
@Data
public class AlarmRecordDO {

  /* id 主键id */
  @Column(isMajor = true)
  private Integer id;
  /* 学生id */
  @Column(canUpdate = false)
  private Integer studentId;
  /* 触发报警时的设备号 */
  @Column(canUpdate = false)
  private String deviceId;
  /* 触发报警的id */
  private String triggerId;
  /* 触发报警的类型 0：登录 1：人脸识别 */
  private Short triggerType;
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
  /* 登录管理员所在单位角色类型 */
  private Short roleType;
  /* 登录管理员所在单位id */
  private Long unitId;
  /* 读取状态(0:未读，1:已读) */
  private Short readStatus;
  /* createTime 创建时间 */
  @Column(canUpdate = false)
  private Timestamp createTime;


  /* 第三级单位id */
  private Long pilotUnitId;
  /* 第三级单位名称 */
  private String pilotUnitName;
  /* classId 所在班级id */
  private Long classId;
  /* 班级名称 */
  private String className;

  /* admissionCardCode 准考证号 */
  private String admissionCardCode;

  private String studentName;


  @Data
  public static class AlarmRecordVO {
    private Integer id;
    private String alarmLevel = StringUtils.EMPTY;
    private String alarmTime = StringUtils.EMPTY;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(AlarmRecordDO.class, "xd_dashboard_alarm_record",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/alarm/")
        .buildXml();
  }

  @Data
  public static class ARVO{
    private List<AlarmRecordVO> listVO;
    private Long totalCount;
  }
  
  
}
