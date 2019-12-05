package com.xiaodou.userCenter.model.alarm;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;
@Data
public class LoginInfoModel {
  /* id 主键id */
  @Column(isMajor = true,betweenScope=true)
  private Long id;
  /*学生id*/
  @Column(canUpdate = false)
  private Long userId;
  /* systemType 系统类型 */
  @Column(canUpdate = false)
  private String systemType;
  /* deviceId 设备号 */
  @Column(canUpdate = false)
  private String deviceId;
  /* loginTime 登录时间 */
  @Column(canUpdate = false,betweenScope = true)
  private Timestamp loginTime;
  private String area;
  /* createTime 创建时间 */
  @Column(canUpdate = false)
  private Timestamp createTime;
  /* 经度（不为空）*/
  @Column(canUpdate = false)
  private float lat;
  /*纬度（不为空）*/
  @Column(canUpdate = false)
  private float lng;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(LoginInfoModel.class, "xd_user_login_info",
        "D:/work/workspace_xd/xd-ucenter-2b/src/main/resources/conf/mybatis/alarm/").buildXml();
  }
}
