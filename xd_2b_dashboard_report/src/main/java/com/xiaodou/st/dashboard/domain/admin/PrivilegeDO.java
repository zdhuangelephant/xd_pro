package com.xiaodou.st.dashboard.domain.admin;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class PrivilegeDO {

  /**
   * 权限id
   */
  @Column(isMajor = true,listValue=true,autoIncrement=true,persistent=true)
  private Integer id;

  /**
   * 权限名称
   */
  private String name;

  /**
   * 父权限id
   */
  private Integer parentId;

  /**
   * url
   */
  private String url;

  /**
   * 排序
   */
  private Integer sortOrder;

  /**
   * 是否显示
   */
  private Integer display;

  /**
   * 参数
   */
  private String params;

  /**
   * 图标
   */
  private String icon;

  /**
   * 子Id
   */
  private String childId;

  /**
   * 所有父节点
   */
  private String allParentId;

  /**
   * 所有子节点
   */
  private String allChildId;

  /**
   * 等级
   */
  private Integer level;

  /**
   * 是否为叶节点
   */
  private Integer isLeaf;

  private Timestamp createTime;
  
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(PrivilegeDO.class, "xd_dashboard_privilege",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/admin/")
        .buildXml();
  }
}
