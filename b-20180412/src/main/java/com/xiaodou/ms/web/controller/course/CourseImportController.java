package com.xiaodou.ms.web.controller.course;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.ms.service.course.CourseImportService;
import com.xiaodou.ms.web.controller.BaseController;

/**
 * @name @see com.xiaodou.ms.web.controller.course.CourseImportController.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月27日
 * @description 课程导入控制器
 * @version 1.0
 */
@Controller("courseImportController")
@RequestMapping("/course")
public class CourseImportController extends BaseController {

  @Resource
  CourseImportService courseImportService;

  @RequestMapping("/import_from_excel")
  @ResponseBody
  public String importFromExcel(String url) {
    try {
      Boolean isSuccess = courseImportService.importCourseFromExcel(url);
      return isSuccess.toString();
    } catch (Exception e) {
      return Boolean.FALSE.toString();
    }
  }

}
