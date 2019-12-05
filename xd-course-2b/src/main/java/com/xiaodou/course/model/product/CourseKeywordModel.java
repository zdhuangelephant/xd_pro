package com.xiaodou.course.model.product;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

/**
 * 
 * @ClassName: KeywordModel
 * @Description: 章节的关键点(可以认为是知识点，标签)
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午9:55:38
 */
@Data
public class CourseKeywordModel {

  // 关键知识点的ID
  @GeneralField
  @Column(isMajor = true, sortBy = true, listValue = true)
  private Long id;

  // 关键知识点所属的章节ID
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long chapterId;

  // 描述
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String detail;

  // 关键知识点名称
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String name;

  // 创建时间
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Timestamp createTime;

  // 章节名称
  @Column(persistent = false)
  private String chapterName;

  // 重要程度
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer importanceLevel;

  // 是否被选择
  @Column(persistent = false)
  private Integer isSelected = 0;

  
  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(CourseKeywordModel.class, "xd_course_keyword",
            "src\\main\\resources\\conf\\mybatis\\product")
        .buildXml();

  }

}
