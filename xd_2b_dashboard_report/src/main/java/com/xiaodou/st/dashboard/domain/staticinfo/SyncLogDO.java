package com.xiaodou.st.dashboard.domain.staticinfo;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

@Data
public class SyncLogDO {

  @Column(isMajor = true, sortBy = false, autoIncrement = true)
  private Long id;
  /* 同步类型 1、同步学生2、同步课程3、同步准考证号 */
  @Column(canUpdate = false)
  private Short syncType;
  /* 同步时间 */
  @Column(canUpdate = false)
  private Timestamp syncTime;
  /* 执行人 */
  @Column(canUpdate = false)
  private Integer syncAdminId;
  @Column(canUpdate = false)
  private String syncAdminName;
  /* 操作结果 同步结果 */
  @Column(canUpdate = true)
  private String syncResult;
  /* 操作结果 同步结果描述 */
  @Column(canUpdate = true)
  private String syncResultMsg;
  /* 操作结果 记录id */
  @Column(canUpdate = false)
  private String syncId;
  @Column(canUpdate = false)
  private Timestamp createTime;

  /** totalCount 任务总数 */
  @Column(persistent = false)
  private String totalCount;
  /** completeCount 任务完成数 */
  @Column(persistent = false)
  private String completeCount;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(SyncLogDO.class, "xd_dashboard_sync_log",
        "src/main/resources/conf/mybatis/staticinfo/").buildXml();
  }
}
