package com.xiaodou.course.model.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

/**
 * 
 * @ClassName: VideoModel
 * @Description: 资源类型之一:视频Model
 * @author zhaoxu.yang
 * @date 2015年4月12日 上午10:57:55
 */
@Data
public class CourseResourceVideoModel {
  // 视频ID
  @GeneralField
  @Column(isMajor = true, sortBy = true, listValue = true)
  private Long id;

  // 课程Id
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long courseId;

  // 视频所属的大纲章节ID
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long chapterId;

  // 视频名称
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String name;

  // 视频所在的URL地址
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String url;

  // 文件下载地址
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String fileUrl;

  // 视频详细描述
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String detail;

  // 视频的关键字，方便全局搜索
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String keyPoint;
  
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String resourceType;

  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer status;


  // 章节名称
  @Column(persistent = false)
  private String chapterName;


  public static void main(String[] args) {
    MybatisXmlTool.getInstance(CourseResourceVideoModel.class, "xd_course_resource_video",
        "src\\main\\resources\\conf\\mybatis\\product").buildXml();

  }

}
