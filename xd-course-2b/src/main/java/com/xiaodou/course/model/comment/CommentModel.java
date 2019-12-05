package com.xiaodou.course.model.comment;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSON;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

/**
 * @name @see com.xiaodou.course.model.comment.CommentModel.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月5日
 * @description 评论Model
 * @version 1.0
 */
@Data
public class CommentModel {
  /**
   * 主键
   */
  @BaseField
  @GeneralField
  @Column(isMajor = true, betweenScope = true, persistent = true, sortBy = true, listValue = true)
  private Long id;

  /** courseId 课程ID */
  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private Long courseId;

  /** chapterId 章ID */
  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private Long chapterId;

  /**
   * 节ID
   */
  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private Long itemId;

  /**
   * 内容
   */
  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private String content;

  /**
   * 类型
   */
  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private Integer type;

  /**
   * 评论人ID
   */
  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private Long replyId;

  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private Long targetId;

  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private Long targetCommentId;

  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private String images;

  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private String targetContent;

  /**
   * 创建时间
   */
  @BaseField
  @GeneralField
  @Column(sortBy = true)
  private Timestamp createTime;

  /**
   * 操作人
   */
  @BaseField
  @Column(sortBy = true)
  private String operator;

  /**
   * 操作IP
   */
  @BaseField
  @Column(sortBy = true)
  private String operatorip;

  public void setOperatorip(String operatorip) {
    this.operatorip = operatorip == null ? null : operatorip.trim();
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }


  public static void main(String[] args) {
    MybatisXmlTool.getInstance(CommentModel.class, "xd_course_product_item_comment",
        "src/main/resources/conf/mybatis/comment/").buildXml();;
  }



}
