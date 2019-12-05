package com.xiaodou.ms.vo.course;

import java.util.Map;

import lombok.Data;

import com.google.common.collect.Maps;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseSubjectModel;

/**
 * @name @see com.xiaodou.ms.vo.course.ImportInfoHolder.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月28日
 * @description 导入信息保持器
 * @version 1.0
 */
@Data
public class ImportInfoHolder {
  private CourseSubjectModel subjectHolder;
  private Map<String, CourseChapterModel> chapterHolder = Maps.newHashMap();
  private Map<String, CourseChapterModel> itemHolder = Maps.newHashMap();
  private Map<String, CourseKeywordModel> keywordHolder = Maps.newHashMap();
  
  private Integer itemIndex = 0;
}
