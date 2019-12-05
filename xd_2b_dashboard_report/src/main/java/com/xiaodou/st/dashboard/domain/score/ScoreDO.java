package com.xiaodou.st.dashboard.domain.score;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * 
 * @name ScoreDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月31日
 * @description 不同维度下的。所有学生成绩统计
 * @version 1.0
 */
@Data
public class ScoreDO {
  /* id 主键id */
  @Column(isMajor = true)
  @GeneralField
  private Long id;
  /* 第三级单位id */
  @GeneralField
  private Long pilotUnitId;
  /* 第三级单位名称 */
  @GeneralField
  private String pilotUnitName;
  /* 专业id */
  @GeneralField
  private Integer catId;
  /* 专业代码 */
  private String catCode;
  /* 专业名称 */
  @GeneralField
  private String catName;
  /* 所在班级id */
  @GeneralField
  private Long classId;
  /* 班级名称 */
  @GeneralField
  private String className;
  /* 课程id */
  @GeneralField
  private Integer productId;
  /* 课程代码 */
  @GeneralField
  private String productCode;
  /* 课程名称 */
  @GeneralField
  private String productName;
  /* 课程开始时间 */
  @GeneralField
  private String beginTime;
  /* 课程结束时间 */
  @GeneralField
  private String endTime;
  /* 学生id */
  @GeneralField
  private Integer studentId;
  /* 姓名 */
  @GeneralField
  private String studentName;
  /* 准考证号 */
  @GeneralField
  private String admissionCardCode;
  /* 手机号 */
  @GeneralField
  private String telephone;
  /* 头像 */
  @GeneralField
  private String studentPortrait;
  /* 成绩 */
  @GeneralField
  private Double score;
  /* 考期 */
  @GeneralField
  private String examDate;
  /* 登录管理员所在单位角色类型 */
  @GeneralField
  private Short roleType;
  /* 登录管理员所在单位id */
  @GeneralField
  private Long unitId;
  @GeneralField
  private Timestamp createTime;
  /* 章 */
  @GeneralField
  private String chapterNodeList;
  /* 卷子 */
  @GeneralField
  private String finalExamNodeList;
  /* 章平均分 */
  @GeneralField
  private Double avgChapterScore;
  /* 期末成绩平均分 */
  @GeneralField
  private Double finalExamScore;
  /* 阶段测评成绩 */
  @GeneralField
  private Double stageEvaluationScore;
  /* 任务分数 */
  @GeneralField
  private Double missionFinishScore;
  /* 查漏补缺分数 */
  @GeneralField
  private Double supplementScore;
  
  /****2017/10/19 新增批量导入成绩***/
  /* 导入的平时成绩 */
  @GeneralField
  private Double dailyScore;
  /* 批量导入的操作人 */
  @GeneralField
  private Integer dailyScoreOperator;
  /* 导入的时间 */
  @GeneralField
  private Timestamp dailyScoreOperateTime;
  
  // 
  private Double discountScore;
  private Double reportFinalScore;
  private Double coefficient;
  
  private Integer pageNo;
  private Integer pageSize;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(ScoreDO.class, "xd_dashboard_score",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/score/")
        .buildXml();
  }

  @Data
  public static class ScoreNode {
    // 名称内容
    private String indexName;
    // 權重
    private String weight;
    // 排序
    private String order;
    // 得分
    private String score;
  }
  
}
