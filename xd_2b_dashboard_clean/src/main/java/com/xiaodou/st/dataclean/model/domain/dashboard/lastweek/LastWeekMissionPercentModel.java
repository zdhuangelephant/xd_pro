package com.xiaodou.st.dataclean.model.domain.dashboard.lastweek;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see
 *       com.xiaodou.st.dataclean.model.domain.dashboard.last_week.LastWeekMissionPercentModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月6日
 * @description 上周每日任务完成度数据模型
 * @version 1.0
 */
@Data
public class LastWeekMissionPercentModel {

  /** id 主键 */
  @Column(isMajor = true)
  private Integer id;
  /** rank 排名 */
  private Integer rank;
  /** tendency 趋势 */
  private Short tendency;
  /** pilotUnitId 试点单位ID */
  private Integer pilotUnitId;
  /** missionPercent 任务完成度 */
  private Double missionPercent;
  /** roleType 角色类型 */
  private Short roleType;
  /** unitId 单位ID */
  private Integer unitId;
  /** createTime 创建时间 */
  private Timestamp createTime;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(LastWeekMissionPercentModel.class,
        "xd_dashboard_last_week_mission_percent",
        "src/main/resources/conf/mybatis/dashboard/last_week/").buildXml();
  }

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public Integer getRank() {
	return rank;
}

public void setRank(Integer rank) {
	this.rank = rank;
}

public Short getTendency() {
	return tendency;
}

public void setTendency(Short tendency) {
	this.tendency = tendency;
}

public Integer getPilotUnitId() {
	return pilotUnitId;
}

public void setPilotUnitId(Integer pilotUnitId) {
	this.pilotUnitId = pilotUnitId;
}

public Double getMissionPercent() {
	return missionPercent;
}

public void setMissionPercent(Double missionPercent) {
	this.missionPercent = missionPercent;
}

public Short getRoleType() {
	return roleType;
}

public void setRoleType(Short roleType) {
	this.roleType = roleType;
}

public Integer getUnitId() {
	return unitId;
}

public void setUnitId(Integer unitId) {
	this.unitId = unitId;
}

public Timestamp getCreateTime() {
	return createTime;
}

public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}

}
