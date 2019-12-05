package com.xiaodou.st.dashboard.domain.dashboard;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

/**
 * @name EverydaySummaryDetailVO 
 * CopyRright (c) 2018 by Corp.XiaodouTech
 *
 * @author <a href="mailto:hzd82274@gmail.com">zdhuang</a>
 * @date 2018年4月11日
 * @description TODO
 * @version 1.0
 */
@Data
public class EverydaySummaryDetailVO {
  @Column(isMajor = true)
  @GeneralField
  private Long id;
  /** totalStudents 总的学生人数*/
  @Column(canUpdate = true, sortBy = false)
  @GeneralField
  private Long totalStudents;
  /** totalSubjectsAndStus 总科次*/
  @Column(canUpdate = true, sortBy = false)
  @GeneralField
  private Long totalSubjectsAndStus;
  /** learnZeroCount 无学习记录科次*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer learnNoneCounts;
  /** learnPercent 学习使用率*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Double learnPercent;
  /** passPercent 及格率*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Double passPercent;
  /** levelFullCreditApplyCounts 100分科次*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer levelFullCreditApplyCounts;
  /** levelExcellentApplyCounts (100-80]分*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer levelExcellentApplyCounts;
  /** levelGoodApplyCounts (80-60]分*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer levelGoodApplyCounts;
  /** levelGeneralApplyCounts (60-0)分*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer levelGeneralApplyCounts;
  /** levelPoorApplyCounts 0分*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer levelPoorApplyCounts;
  /** roleType 角色类型*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer roleType;
  /** unitId 执行单元ID*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false,listValue = true)
  private Integer unitId;
  /** unitId 第三级单位ID*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false,listValue = true)
  private Integer pilotUnitId;
  /** updateTime 学习记录更新时间*/
  @GeneralField
  @Column(canUpdate = true, sortBy = false,betweenScope = true)
  private Timestamp updateTime;
  /** createTime 创建时间*/
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp createTime;
  
  private String pilotUnitName;
 
  public static void main(String[] args) {
      MybatisXmlTool.getInstance(EverydaySummaryDetailVO.class,
              "xd_dashboard_everyday_summary_statistics_details",
              "src/main/resources/conf/mybatis/dashboard/").buildXml();
  }
}
