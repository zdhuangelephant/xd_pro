package com.xiaodou.ms.vo.course;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;

import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.util.anno.ExcelColumn;
import com.xiaodou.ms.web.request.course.ResourceCourseCreateRequest;

/**
 * @name @see com.xiaodou.ms.vo.course.ImportCourse.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月27日
 * @description Excel导入课程数据
 * @version 1.0
 */
@Data
public class ImportCourse {
  private Long categoryId;
  private String courseName;
  @ExcelColumn("章")
  private String chapterName;
  @ExcelColumn("节")
  private String itemName;
  @ExcelColumn("点")
  private String keyPointName;
  private Integer index;

  public ResourceCourseCreateRequest getResourceCourseRequest() {
    ResourceCourseCreateRequest request = new ResourceCourseCreateRequest();
    request.setCategoryId(categoryId);
    request.setName(courseName);
    request.setDetail(courseName);
    return request;
  }

  public CourseChapterModel getChapterCreateRequest(ImportInfoHolder holder) {
    holder.setItemIndex(0);
    CourseSubjectModel subject = holder.getSubjectHolder();
    CourseChapterModel request = new CourseChapterModel();
    String reg = "第[\u4E00-\u9FA5]*章";
    Pattern r = Pattern.compile(reg);
    Matcher m = r.matcher(chapterName);
    if (m.find()) {
      String sindex = m.group(0);
      String name = chapterName.substring(sindex.length());
      request.setSindex(sindex.trim());
      request.setName(name.trim());
    } else {
      request.setName(chapterName.trim());
    }
    request.setDetail(chapterName);
    request.setParentId(0l);
    request.setSubjectId(subject.getId());
    request.setListOrder(index * 100);
    return request;
  }

  public CourseChapterModel getItemCreateRequest(ImportInfoHolder holder) {
    CourseSubjectModel subject = holder.getSubjectHolder();
    CourseChapterModel chapter = holder.getChapterHolder().get(getChapterKey());
    CourseChapterModel request = new CourseChapterModel();
    String reg = "第[\u4E00-\u9FA5]*节";
    Pattern r = Pattern.compile(reg);
    Matcher m = r.matcher(itemName);
    if (m.find()) {
      String sindex = m.group(0);
      String name = itemName.substring(sindex.length());
      request.setSindex(sindex.trim());
      request.setName(name.trim());
    } else {
      request.setName(itemName.trim());
    }
    request.setDetail(itemName);
    request.setParentId(chapter.getId());
    request.setSubjectId(subject.getId());
//    request.setListOrder(index * 100 + index);
    holder.setItemIndex(holder.getItemIndex() + 1);
    request.setListOrder(holder.getItemIndex());
    return request;
  }

  public CourseKeywordModel getKeywordModel(ImportInfoHolder holder) {
    CourseChapterModel item = holder.getItemHolder().get(getItemKey());
    CourseKeywordModel keywordModel = new CourseKeywordModel();
    keywordModel.setChapterId(item.getId());
    keywordModel.setName(keyPointName);
    keywordModel.setDetail(keyPointName);
    keywordModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    keywordModel.setImportanceLevel(6);
    keywordModel.setChapterName(item.getName());
    return keywordModel;
  }

  public String getChapterKey() {
    return String.format("%s-%s-%s", courseName, categoryId, chapterName);
  }

  public String getItemKey() {
    return String.format("%s-%s-%s-%s", courseName, categoryId, chapterName, itemName);
  }

  public String getKeyPointKey() {
    return String.format("%s-%s-%s-%s-%s", courseName, categoryId, chapterName, itemName,
        keyPointName);
  }

}
