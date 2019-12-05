package com.xiaodou.st.dashboard.domain.score;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 
 * @name LearnRecordDO 
 * CopyRright (c) 2017 by 李德洪
 *
 * @author 李德洪
 * @date 2017年3月31日
 * @description 由成绩列表页进入， 统计课程维度下面的学习时长
 * @version 1.0
 */
@Data
public class LearnRecordDO {

  @Column(isMajor=true)
  private Long id;
  /* 学生id */
  private Long studentId;
  /*成绩id*/
  private Long productId;
  /*学习行为*/
  private Short learnType;
  /*学习内容*/
  private String learnContent;
  /*记录时间*/
  private Date recordTime;
  /*学习时长*/
  private Integer learnTime;
  private Timestamp createTime;
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(LearnRecordDO.class, "xd_dashboard_learn_record",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/score/")
        .buildXml();
  }
}
