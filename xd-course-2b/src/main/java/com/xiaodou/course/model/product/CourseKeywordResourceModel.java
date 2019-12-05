package com.xiaodou.course.model.product;

import java.util.Date;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

/**
 * 
 * @ClassName: KeywordResourceModel
 * @Description: 关键词与资源绑定Model
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午10:32:35
 */
@Data
public class CourseKeywordResourceModel {

  // 关联ID
  @GeneralField
  @Column(isMajor = true, sortBy = true)
  private Integer id;

  // 对应资源的ID(比如，视频，文档，题库)
  @GeneralField
  @Column(canUpdate = true, sortBy = false, listValue = true)
  private Integer resourceId;

  // 关键知识点的ID
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer keywordId;

  // 关联资源类型：(比如文档，视频，题库)
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer resourceType;

  // 创建时间
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Date createTime;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(CourseKeywordResourceModel.class, "xd_course_keyword_resource",
        "src\\main\\resources\\conf\\mybatis\\product").buildXml();

  }


}
