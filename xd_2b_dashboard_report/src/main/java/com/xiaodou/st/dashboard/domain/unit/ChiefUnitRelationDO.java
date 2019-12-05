package com.xiaodou.st.dashboard.domain.unit;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

public class ChiefUnitRelationDO {
  @Column(isMajor = true)
  @GeneralField
  private Integer id;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long unitId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer catId;
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp createTime;

  public Integer getId() {
    return id;
  }

  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  public Integer getCatId() {
    return catId;
  }

  public void setCatId(Integer catId) {
    this.catId = catId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(ChiefUnitRelationDO.class,
        "xd_dashboard_chief_unit_cat_relation",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/unit/").buildXml();
  }
}
