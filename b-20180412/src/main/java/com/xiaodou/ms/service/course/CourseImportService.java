package com.xiaodou.ms.service.course;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.util.ExcelUtil;
import com.xiaodou.ms.vo.course.ImportCourse;
import com.xiaodou.ms.vo.course.ImportInfoHolder;
import com.xiaodou.ms.web.request.course.ResourceCourseCreateRequest;
import com.xiaodou.ms.web.response.course.ResourceCourseCreateResponse;

@Service
public class CourseImportService {

  @Resource
  CourseSubjectService courseSubjectService;

  @Resource
  CourseChapterService courseChapterService;

  @Resource
  CourseKeywordService courseKeywordService;

  public boolean importCourseFromExcel(String urlString) {
    try {
      Map<String, List<ImportCourse>> iCourseMap = readExcel(urlString);
      for (Entry<String, List<ImportCourse>> entry : iCourseMap.entrySet()) {
        List<ImportCourse> iCourseList = entry.getValue();
        ImportInfoHolder holder = new ImportInfoHolder();
        Integer chapterIndex = 1;
        for (ImportCourse iCourse : iCourseList) {
          iCourse.setIndex(chapterIndex);
          importCourse0(iCourse, holder);
          importChapter0(iCourse, holder);
          importItem0(iCourse, holder);
          importKeyword0(iCourse, holder);
          chapterIndex++;
        }
        CourseSubjectModel subject = holder.getSubjectHolder();
        courseChapterService.cleanTree(subject.getId());
      }
      return true;
    } catch (Exception e) {
      LoggerUtil.error("导入课程失败", e);
      return false;
    }
  }

  private Map<String, List<ImportCourse>> readExcel(String urlString) throws Exception {
    URL url = new URL(urlString);
    URLConnection conn = url.openConnection();
    InputStream is = conn.getInputStream();
    Map<String, List<ImportCourse>> courseMap = ExcelUtil.excelToList(is, ImportCourse.class);
    for (Entry<String, List<ImportCourse>> entry : courseMap.entrySet()) {
      for (ImportCourse course : entry.getValue()) {
        String key = entry.getKey();
        if (key.indexOf("-") > 0) {
          String courseName = key.split("-")[0];
          course.setCourseName(courseName);
          Long categoryId = Long.parseLong(key.split("-")[1]);
          course.setCategoryId(categoryId);
        } else {
          course.setCourseName(key);
        }
      }
    }
    return courseMap;
  }

  private void importCourse0(ImportCourse iCourse, ImportInfoHolder holder) {
    if (null != holder.getSubjectHolder()) {
      return;
    }
    ResourceCourseCreateRequest request = iCourse.getResourceCourseRequest();
    ResourceCourseCreateResponse response = courseSubjectService.addSubject(request);
    CourseSubjectModel subject = response.getSubjectModel();
    holder.setSubjectHolder(subject);
  }

  private void importChapter0(ImportCourse iCourse, ImportInfoHolder holder) {
    String chapterKey = iCourse.getChapterKey();
    if (holder.getChapterHolder().containsKey(chapterKey)) {
      return;
    }
    CourseChapterModel request = iCourse.getChapterCreateRequest(holder);
    CourseChapterModel chapter = courseChapterService.addChapter0(request);
    holder.getChapterHolder().put(chapterKey, chapter);
  }

  private void importItem0(ImportCourse iCourse, ImportInfoHolder holder) {
    String itemKey = iCourse.getItemKey();
    if (holder.getItemHolder().containsKey(itemKey)) {
      return;
    }
    CourseChapterModel request = iCourse.getItemCreateRequest(holder);
    CourseChapterModel item = courseChapterService.addChapter0(request);
    holder.getItemHolder().put(itemKey, item);
  }

  private void importKeyword0(ImportCourse iCourse, ImportInfoHolder holder) {
    String keywordKey = iCourse.getKeyPointKey();
    if (holder.getKeywordHolder().containsKey(keywordKey)) {
      return;
    }
    CourseKeywordModel keyword = iCourse.getKeywordModel(holder);
    keyword = courseKeywordService.addKeyword(keyword);
    holder.getKeywordHolder().put(keywordKey, keyword);
  }

}
