package com.xiaodou.st.dashboard.domain.staticinfo;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

@Data
public class SyncApplyDO {
  /* id 主键id */
  @Column(isMajor = true)
  private Integer id;
  /* tellPhone 手机号 */
   @Column(canUpdate = true)
   private String telephone;
  /* 学生id */
  @Column(canUpdate = true)
  private Integer studentId;
  /* 操作结果 记录id */
  @Column(canUpdate = false)
  private String syncId;
  /*导入报错描述*/
  @Column(canUpdate = false,sortBy=false)
  private String msg;
  @Column(canUpdate = false)
  private Timestamp createTime;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(SyncApplyDO.class, "xd_dashboard_sync_apply",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/staticinfo/")
        .buildXml();
  }
}
