package com.xiaodou.ms.web.controller.major;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.model.major.MajorCourseModel;
import com.xiaodou.ms.model.major.MajorDataModel;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.service.major.MajorCourseService;
import com.xiaodou.ms.service.major.MajorDataService;
import com.xiaodou.ms.service.product.RegionService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.summer.dao.pagination.Page;
@Controller("majorController")
@RequestMapping("bjSelfTaught")
public class MajorController extends BaseController {

  @Resource
  MajorCourseService majorCourseService;
  @Resource
  MajorDataService majorDataService;
  @Resource
  RegionService regionService;
  /**
   * 查询 专业代码列表
   * major_data
   * @return
   */
  @RequestMapping("/majorData")
  public ModelAndView majorDataList(Integer page,String courseName,String region) {
    page = (null == page || 0 == page) ? 1 : page;
    ModelAndView modelAndView = new ModelAndView("/bjSelfTaught/majorDataList");
    
    String decodeCourseName = null;
    try {
      if (StringUtils.isNotBlank(courseName)) {
        decodeCourseName = URLDecoder.decode(new String(Base64Utils.decode(courseName)), "utf8");
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }  
    
    Page<MajorDataModel> majorDataList = majorDataService.searchCourse(page,decodeCourseName,region);
    if (majorDataList != null && majorDataList.getResult()!= null && majorDataList.getResult().size()>0) {
      for (MajorDataModel majorDataModel : majorDataList.getResult()) {
        majorDataModel.initModuleInfo(majorDataModel.getMajorInfo());
      }
    }
    
    List<RegionModel> queryAllRegion = regionService.queryAllRegion();
    modelAndView.addObject("regionList",queryAllRegion );
    modelAndView.addObject("region",region);
    modelAndView.addObject("majorDataList", majorDataList);
    modelAndView.addObject("courseName", decodeCourseName);
    return modelAndView;
  }

  /**
   * 查询 专业课程列表
   * major_course
   * @return
   */
  @RequestMapping("/majorCourse")
  public ModelAndView majorCourseList(Integer page,String courseName,String region) {
    page = (null == page || 0 == page) ? 1 : page;
    ModelAndView modelAndView = new ModelAndView("/bjSelfTaught/majorCourseList");
    
    String decodeCourseName =null;
    try {
      if (StringUtils.isNotBlank(courseName)) {
        decodeCourseName = URLDecoder.decode(new String(Base64Utils.decode(courseName)), "utf8");
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    
    Page<MajorCourseModel> courseList = majorCourseService.searchCoursePage(page,decodeCourseName,region);
    if (courseList!= null && courseList.getResult()!=null && courseList.getResult().size()>0) {
      for (MajorCourseModel course : courseList.getResult()) {
        course.initMajorCourseInfo(course.getMajorCourseInfo());
      }
    }
    List<RegionModel> queryAllRegion = regionService.queryAllRegion();
    modelAndView.addObject("regionList",queryAllRegion );
    modelAndView.addObject("majorCourseList", courseList);
    modelAndView.addObject("courseName", decodeCourseName);
    modelAndView.addObject("region", region);
    return modelAndView;
  }
  
}
