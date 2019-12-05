package com.xiaodou.ms.web.controller.mission;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.mission.MissionModel;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseResourceHtml5Service;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.service.mission.MissionService;
import com.xiaodou.ms.service.product.FinalExamService;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.service.product.RegionService;
import com.xiaodou.ms.vo.MissionVo;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.mission.MissionRequest;
import com.xiaodou.ms.web.request.product.ProductQueryConds;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.mission.SearchCourseName4MissionResponse;
import com.xiaodou.summer.dao.pagination.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;



/**
 * Created by zhouhuan on 16/6/20.
 */
@Controller("missionController")
@RequestMapping("/mission")
public class MissionController extends BaseController {
  @Resource
  MissionService missionService;
  @Resource
  FinalExamService finalExamService;
  // 课程章节
  @Resource
  CourseChapterService courseChapterService;
  @Resource
  CourseSubjectService courseSubjectService;
  // 资源
  @Resource
  CourseResourceHtml5Service courseResourceHtml5Service;
  // 产品
  @Resource
  ProductService productService;
  // 产品条目
  @Resource
  ProductItemService productItemService;
  
  /**
   * 改造后的地域 
   *  */
  @Resource
  RegionService regionService;

  /**
   * 系统任务列表
   * 
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView list(Integer page) {
    page = (null == page || 0 == page) ? 1 : page;
    try {
      ModelAndView modelAndView = new ModelAndView("/mission/sysMissionList");
      Page<MissionModel> missionlistPage =
          missionService.missionTableTree(page, null, "statics", "-1");
      if (null != missionlistPage) {
        for (MissionModel missionModel : missionlistPage.getResult()) {
          if (StringUtils.isNotBlank(missionModel.getCourseId())) {
            ProductModel subject =
                productService.findSubjectById(Long.parseLong(missionModel.getCourseId()));
            if (null != subject) {
              missionModel.setCourseName(subject.getName());
            }
          }
        }
      }
      Page<MissionModel> likePage = new Page<MissionModel>();
      likePage.setPageNo(page);
      likePage.setPageSize(20);
      List<MissionModel> subList = Lists.newArrayList();
      if (missionlistPage.getResult().size() - (page - 1) * likePage.getPageSize() < likePage
          .getPageSize()) {
        subList.addAll(missionlistPage.getResult().subList((page - 1) * likePage.getPageSize(),
            missionlistPage.getResult().size()));
      } else {
        subList.addAll(missionlistPage.getResult().subList((page - 1) * likePage.getPageSize(),
            (page - 1) * likePage.getPageSize() + likePage.getPageSize()));
      }
      likePage.setResult(subList);
      likePage.setTotalCount(missionlistPage.getResult().size());
      likePage.setTotalPage((missionlistPage.getResult().size() + likePage.getPageSize() - 1)
          / likePage.getPageSize());
      modelAndView.addObject("missionlist", likePage);
      return modelAndView;
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      throw e;
    }
  }

  /**
   * 系统任务添加页面
   * 
   * @return
   */
  @RequestMapping("/sysMissionAdd")
  public ModelAndView sysMissionAdd() {
    ModelAndView modelAndView = new ModelAndView("/mission/sysMissionAdd");
    List<RegionModel> queryAllRegion = regionService.queryAllRegion();
    modelAndView.addObject("regionList",queryAllRegion );
    return modelAndView;
  }

  /**
   * 系统任务添加
   * 
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView doAdd(MissionRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = missionService.addMission(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 系统任务修改页面
   * 
   * @return
   */
  @RequestMapping("/sysMissionEditPage")
  public ModelAndView sysMissionEditPage(String id) {
    MissionModel missionModel = missionService.findMissionById(id);
    List<RegionModel> queryAllRegion = regionService.queryAllRegion();
    ModelAndView modelAndView = new ModelAndView("/mission/sysMissionEdit");
    modelAndView.addObject("regionList",queryAllRegion );
    modelAndView.addObject("mission", missionModel);
    return modelAndView;
  }

  /**
   * 系统任务修改
   * 
   * @return
   */
  @RequestMapping("/sysMissionEdit")
  public ModelAndView sysMissionEdit(MissionRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        missionService.editSysMission(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("编辑成功", "", null, true);
      } else {
        return this.showMessage("编辑失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 动态任务列表
   * 
   * @return
 * @throws Exception 
   */
  @RequestMapping("/dynamicList")
  public ModelAndView dynamicList(Integer page, String courseId, String courseName) throws Exception {
    page = (null == page || 0 == page) ? 1 : page;
    try {
      ModelAndView modelAndView = new ModelAndView("/mission/dynamicList");
      ArrayList<MissionModel> totalList = Lists.newArrayList();
      if (StringUtils.isNotBlank(courseName)) {
    	  courseName = URLDecoder.decode(new String(Base64Utils.decode(courseName)), "utf8");
      } 
      
      // 任务列表
      Page<MissionModel> missionlistPage =
          missionService.missionTableTree(null, courseName, "dynamic", courseId);
      if (null != missionlistPage) {
        for (MissionModel missionModel : missionlistPage.getResult()) {
          if (StringUtils.isNotBlank(missionModel.getCourseId())) {
            ProductModel subject =
                productService.findSubjectById(Long.parseLong(missionModel.getCourseId()));
            if (null != subject) {
              missionModel.setCourseName(subject.getName());
            }
            // 进行模糊匹配
            if (StringUtils.isNotBlank(courseName)
                && StringUtils.isNotBlank(missionModel.getCourseName())
                && missionModel.getCourseName().contains(courseName)) {
              totalList.add(missionModel);
            }
          }
        }
      }
      Page<MissionModel> likePage = new Page<MissionModel>();
      if (StringUtils.isNotBlank(courseName)) {
        likePage.setPageNo(page);
        likePage.setPageSize(Page.DEFAULT_PAGESIZE);
        List<MissionModel> subList = Lists.newArrayList();
        if (totalList.size() - (page - 1) * likePage.getPageSize() < likePage.getPageSize()) {
          subList.addAll(totalList.subList((page - 1) * likePage.getPageSize(), totalList.size()));
        } else {
          subList.addAll(totalList.subList((page - 1) * likePage.getPageSize(),
              (page - 1) * likePage.getPageSize() + likePage.getPageSize()));
        }
        likePage.setResult(subList);
        likePage.setTotalCount(totalList.size());
        likePage
            .setTotalPage((totalList.size() + likePage.getPageSize() - 1) / likePage.getPageSize());
      } else {
        likePage.setPageNo(page);
        likePage.setPageSize(Page.DEFAULT_PAGESIZE);
        List<MissionModel> subList = Lists.newArrayList();
        if (missionlistPage.getResult().size() - (page - 1) * likePage.getPageSize() < likePage
            .getPageSize()) {
          subList.addAll(missionlistPage.getResult().subList((page - 1) * likePage.getPageSize(),
              missionlistPage.getResult().size()));
        } else {
          subList.addAll(missionlistPage.getResult().subList((page - 1) * likePage.getPageSize(),
              (page - 1) * likePage.getPageSize() + likePage.getPageSize()));
        }
        likePage.setResult(subList);
        likePage.setTotalCount(missionlistPage.getResult().size());
        likePage.setTotalPage((missionlistPage.getResult().size() + likePage.getPageSize() - 1)
            / likePage.getPageSize());
      }
      modelAndView.addObject("missionlist", likePage);

      
      // 下拉框的课程
      // 动态任务页面只显示未过期课程的任务
      ProductQueryConds req = new ProductQueryConds();
      req.setIsExpired("0");
      Page<ProductModel> courseSubjectList = productService.queryCourseByCode(req, null, "", "0");
      modelAndView.addObject("subjectList", courseSubjectList.getResult());

      modelAndView.addObject("courseId", courseId);
      modelAndView.addObject("courseName", courseName);
      return modelAndView;
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      throw e;
    }
  }

  /**
   * 动态任务添加页面构建
   * 
   * @param parentId
   * @return
   */
  @RequestMapping("/dynamicAddPage")
  public ModelAndView dynamicAddPage(String parentId,String courseName) {
    ModelAndView modelAndView = new ModelAndView("/mission/dynamicAdd");
    Page<ProductModel> courseSubjectList = productService.queryCourseByCode(null, null, "", "0");
    modelAndView.addObject("subjectList", courseSubjectList.getResult());
    modelAndView.addObject("courseName",courseName);
    return modelAndView;
  }

  /**
   * 动态任务添加
   * 
   * @return
   */
  @RequestMapping("/dynamicAdd")
  public ModelAndView dynamicAdd(MissionRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        List<MissionModel> list = missionService.findDyMissionById(request);
        if (list.size() == 0) {
          response = missionService.addDynamicMission(request);
        } else {
          response = new BaseResponse(ResultType.VALID_FAIL);
          response.setRetDesc("此课程已经添加了此类型的任务");
          return this.showMessage("添加失败", response.getRetDesc(), null, true);
        }
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 动态任务修改页面
   * 
   * @return
   */
  @RequestMapping("/dynamicMissionEditPage")
  public ModelAndView dynamicMissionEdit(String id) {
    MissionModel missionModel = missionService.findMissionById(id);
    ModelAndView modelAndView = new ModelAndView("/mission/dynamicEdit");
    Page<ProductModel> courseSubjectList = productService.queryCourseByCode(null, null, "", "0");
    modelAndView.addObject("subjectList", courseSubjectList.getResult());

    modelAndView.addObject("mission", missionModel);
    return modelAndView;
  }

  /**
   * 动态任务修改
   * 
   * @return
   */
  @RequestMapping("/dynamicMissionEdit")
  public ModelAndView dynamicMissionEdit(MissionRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        List<MissionModel> list = missionService.findDyMissionById(request);
        if (list.size() == 0) {
          missionService.editDyMission(request);
          response = new BaseResponse(ResultType.SUCCESS);
        } else {
          if (list.size() == 1 && list.get(0).getId().equals(request.getId())) {
            missionService.editDyMission(request);
            response = new BaseResponse(ResultType.SUCCESS);
          } else {
            response = new BaseResponse(ResultType.VALID_FAIL);
            response.setRetDesc("此课程已经添加了此类型的任务");
            return this.showMessage("修改失败", response.getRetDesc(), null, true);
          }
        }
        missionService.editDyMission(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("编辑成功", "", null, true);
      } else {
        return this.showMessage("编辑失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 标准任务列表
   * 
   * @return
   */
  @RequestMapping("/standardList")
  public ModelAndView standardList(String courseId, String chapterId) {
    try {
      ModelAndView modelAndView = new ModelAndView("/mission/standardList");
      if (chapterId == null || StringUtils.isBlank(chapterId)|| StringUtils.isNumeric(chapterId) ) {
        chapterId = "0";
      }
     
      // 获取所有的课程产品
      // 标准任务页面只显示未过期课程的任务
      ProductQueryConds req = new ProductQueryConds();
      req.setIsExpired("0");
      Page<ProductModel> courseSubjectList = productService.queryCourseByCode(req, null, "", "0");
      modelAndView.addObject("subjectList", courseSubjectList.getResult());
      if (courseId == null) {
        return modelAndView;
      }
      if (!StringUtils.isNumeric(courseId)) {
    	  return modelAndView;
      }
      
      
      // 获取Course
      ProductModel subject = null;
      if (StringUtils.isNotBlank(courseId)) {
    	  subject = productService.findSubjectById(Long.valueOf(courseId));
      } 
      modelAndView.addObject("product", subject);
      
      // 获取所遇到额标准任务
      List<MissionVo> missionList = Lists.newArrayList();
      List<MissionVo> productMissionList = null;
      if (StringUtils.isNotBlank(courseId)) {
    	  productMissionList = productItemService.queryAllItemMission(Long.valueOf(courseId));
      } 
      if (null != productMissionList && !productMissionList.isEmpty()) {
        missionList.addAll(productMissionList);
      }
      List<MissionVo> finalExamMissionList = null;
      if (StringUtils.isNotBlank(courseId)) {
    	  finalExamMissionList = finalExamService.queryAllRecordMission(Long.valueOf(courseId));
      }
      if (null != finalExamMissionList && finalExamMissionList.size() > 0) {
        missionList.addAll(finalExamMissionList);
      }
      if (null != missionList && !missionList.isEmpty()) {
        Collections.sort(missionList, new Comparator<MissionVo>() {
          @Override
          public int compare(MissionVo o1, MissionVo o2) {
            if (null == o2.getMissionOrder()) {
              return 1;
            }
            if (null == o1.getMissionOrder()) {
              return -1;
            }
            return o1.getMissionOrder().compareTo(o2.getMissionOrder());
          }
        });
      }
      
      modelAndView.addObject("courseId", courseId);
      modelAndView.addObject("missionList", missionList);
      return modelAndView;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 排序
   * 
   * @return
   */
  @RequestMapping("/order")
  @ResponseBody
  public String order(String orders) {
    String[] orderList = orders.split(";");
    for (String orderItem : orderList) {
      String[] split = orderItem.split(":");
      String id = split[0];
      Integer order = Integer.parseInt(split[1]);
      MissionModel mission = new MissionModel();
      mission.setId(id);
      mission.setMissionOrder(order);
      missionService.editMission(mission);
    }
    return "true";
  }

  /**
   * 标准任务增加
   * 
   * @param msJson
   * @return
   */
  @SuppressWarnings({"deprecation", "unchecked"})
  @RequestMapping("/standardAdd")
  @ResponseBody
  public String standardAdd(String msJson, Integer courseId, Integer chapterId) {
    JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(msJson);
    List<MissionVo> list = JSONArray.toList(jsonArray, MissionVo.class);
    ProductModel findSubjectById =null;;
    if (list.size()>0) {
    	Long realCourseId = Long.valueOf(list.get(0).getCourseId());
    	findSubjectById = productService.findSubjectById(realCourseId);
	}
    
    for (MissionVo missionModel : list) {
      if (null==findSubjectById) {
    	  missionModel.setModule("2");//没有的话就默认选择北京
	  }	
    	missionModel.setModule(findSubjectById.getModule());
      missionService.addStandardMission(missionModel);
    }
    return "true";
  }

  /**
   * 标准任务删除
   * 
   * @return
   */
  @RequestMapping("/standardDelete")
  public ModelAndView standardDelete(String id) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = missionService.deleteMission(id);
      if (aBoolean) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return this.showMessage(response.getRetDesc(), "", null, true);
    } catch (Exception e) {
      LoggerUtil.error("目录删除异常", e);
      throw e;
    }
  }

  /**
   * 标准任务状态改变接口
   * 
   * @return
   */
  @RequestMapping("/editState")
  @ResponseBody
  public String editState(String msJson, short state) {
    List<MissionModel> list =
        FastJsonUtil.fromJsons(msJson, new TypeReference<List<MissionModel>>() {});
    Integer hasMissionCount = 0;
    for (MissionModel missionModel : list) {
      if (StringUtils.isNotBlank(missionModel.getId())) {
        hasMissionCount++;
      }
      missionModel.setMissionState(state);
      missionService.editMissionState(missionModel);
    }
    if (hasMissionCount == list.size()) {
      return "成功";
    } else if (hasMissionCount == 0) {
      return "选定任务尚未生成,请确认任务生成状态.";
    } else {
      return "成功.\n部分选定任务尚未生成,请确认任务生成状态.";
    }
  }

  /**
   * 通用任务删除
   * 
   * @return
   */
  @RequestMapping("/delete")
  public ModelAndView delete(String id) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = missionService.deleteMission(id);
      if (aBoolean) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return this.showMessage(response.getRetDesc(), "", null, true);
    } catch (Exception e) {
      LoggerUtil.error("目录删除异常", e);
      throw e;
    }
  } 

  /**
   * 前端搜索时自动匹配 courseName
   * @return
   */
  @RequestMapping("/search4Name")
  @ResponseBody
  public String search4Name(String courseName) {
    SearchCourseName4MissionResponse res = new SearchCourseName4MissionResponse(ResultType.SUCCESS);
    
    Page<MissionModel> courseList = missionService.search4Name(courseName);
    if (null == courseList || null == courseList.getResult() || courseList.getResult().size() == 0)
      return FastJsonUtil.toJson(res);
    for (MissionModel mission : courseList.getResult())
      res.getCourseNameList().add(
          String.format("<p value=\"%s\">%s</p>", mission.getId(), mission.getCourseName()));
    return FastJsonUtil.toJson(res);
  }
}
