package com.xiaodou.st.dashboard.domain.unit;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
@Data
public class UnitDO {
  @Column(isMajor = true)
  private Long id;
  /* 单位名称 */
  private String unitName;
  /* 头像 */
  private String unitPortrait;
  /* 角色 */
  private Short role;
  /*折扣率*/
  private  Double priceRate;
  /*创建时间*/
  private Timestamp createTime;
  
  private String roleName;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(UnitDO.class, "xd_dashboard_unit",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/unit/")
        .buildXml();
  }
}
