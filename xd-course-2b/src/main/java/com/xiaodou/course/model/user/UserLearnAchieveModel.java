package com.xiaodou.course.model.user;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * 
 * @name UserLearnAchieveModel
 * @CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年6月18日
 * @description 记录用户最近学习课程至第几章第几节
 * @version 1.0
 */
@Data
public class UserLearnAchieveModel {

  // id
  @Column(isMajor = true)
  private Long id;

  // 用户Id
  @Column(canUpdate = false)
  private Long userId;

  // app模块id
  @Column(canUpdate = false)
  private Integer moduleId;

  // 产品Id
  @Column(canUpdate = false)
  private Long productId;

  // 章节Id
  @Column(canUpdate = true)
  private Long chapterId;

  // 条目Id
  @Column(canUpdate = true)
  private Long itemId;

  // 创建时间
  @Column(canUpdate = false)
  private Timestamp createTime;


  

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(UserLearnAchieveModel.class, "xd_user_learn_achieve",
        "D:\\work\\workspace_xiaodou\\xd-course-2b/src/main/resources/conf/mybatis/user/")
        .buildXml();
  }
}
