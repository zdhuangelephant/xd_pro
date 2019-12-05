package com.xiaodou.st.dashboard.domain.dashboard;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

/**
 * @name EverydaySummaryVO CopyRright (c) 2018 by Corp.XiaodouTech
 *
 * @author <a href="mailto:hzd82274@gmail.com">zdhuang</a>
 * @date 2018年4月11日
 * @description TODO
 * @version 1.0
 */
@Data
public class EverydaySummaryVO {
  @Column(isMajor = true)
  @GeneralField
  private Long id;
  /** totalStudents 总的学生人数 */
  @Column(canUpdate = true, sortBy = false)
  @GeneralField
  private Long totalStudents;
  /** totalSubjectsAndStus 总科次 */
  @Column(canUpdate = true, sortBy = false)
  @GeneralField
  private Long totalSubjectsAndStus;
  /** learnZeroCount 无学习记录科次 */
  // @GeneralField
  // @Column(canUpdate = true, sortBy = false)
  // private Integer learnNoneCounts;
  /** learnPercent 学习使用率 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Double learnPercent;
  /** passPercent 及格率 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Double passPercent;
  /** passPercent 0分科次占比 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Double zeroPercent;
  /** roleType 角色类型 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer roleType;
  /** unitId 执行单元ID */
  @GeneralField
  @Column(canUpdate = true, sortBy = true, listValue = true)
  private Integer unitId;
  /** updateTime 学习记录更新时间 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false, betweenScope = true)
  private Timestamp updateTime;
  /** createTime 创建时间 */
  @GeneralField
  @Column(canUpdate = true, sortBy = true, betweenScope = true)
  private Timestamp createTime;



  public static void main(String[] args) {
    MybatisXmlTool.getInstance(EverydaySummaryVO.class, "xd_dashboard_everyday_summary_statistics",
        "src/main/resources/conf/mybatis/dashboard/").buildXml();
  }
  public EverydaySummaryVO() {};
  public EverydaySummaryVO(Long totalStudents, Long totalSubjectsAndStus, Double learnPercent,
      Double passPercent, Double zeroPercent, Timestamp updateTime) {
    this.totalStudents = totalStudents;
    this.totalSubjectsAndStus = totalSubjectsAndStus;
    this.learnPercent = learnPercent;
    this.passPercent = passPercent;
    this.zeroPercent = zeroPercent;
    this.updateTime = updateTime;
  }
}
