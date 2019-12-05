package com.xiaodou.ms.web.controller.knowledge;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.model.knowledge.ForumClassify;
import com.xiaodou.ms.model.knowledge.ForumModel;
import com.xiaodou.ms.model.knowledge.ForumStatus;
import com.xiaodou.ms.model.knowledge.ForumType;
import com.xiaodou.ms.model.knowledge.PushRecordModel;
import com.xiaodou.ms.model.major.MajorCourseModel;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.service.common.QiniuService;
import com.xiaodou.ms.service.knowledge.ForumService;
import com.xiaodou.ms.service.knowledge.PushRecordService;
import com.xiaodou.ms.service.major.MajorCourseService;
import com.xiaodou.ms.service.product.RegionService;
import com.xiaodou.ms.util.FileUtils;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.knowledge.ShareKnowledgeAddRequest;
import com.xiaodou.ms.web.request.knowledge.ShareKnowledgeEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.selftaught.SearchAuthor4ForumResponse;
import com.xiaodou.ms.web.response.selftaught.SearchTitle4ForumResponse;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name ForumController CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月7日
 * @description 知识分享Controller
 * @version 1.0
 */
@Controller("forumController")
@RequestMapping("/knowledge/forum")
public class ForumController extends BaseController {

  @Resource
  QiniuService qiniuService;

  @Resource
  ForumService forumService;

  @Resource
  RegionService regionService;

  @Resource
  MajorCourseService majorCourseService;

  @Resource
  PushRecordService pushRecordService;

  /**
   * 模糊查询Tag标签列表
   * 
   * @return
   */
  @RequestMapping("/searchCourse")
  @ResponseBody
  public String searchCourse(String courseName, String region) {
    SearchAuthor4ForumResponse res = new SearchAuthor4ForumResponse(ResultType.SUCCESS);
    List<MajorCourseModel> courseList =
        majorCourseService.searchCourseWithRegion(courseName, region);
    if (null == courseList || courseList.size() == 0) return FastJsonUtil.toJson(res);
    for (MajorCourseModel course : courseList)
      res.getAuthorList().add(
          String.format("<p value=\"%s\">%s</p>", course.getId(), course.getName()));
    return FastJsonUtil.toJson(res);
  }

  /**
   * 模糊查询作者列表
   * 
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView authorList(String forumTitle, String authorId, String authorName,
      String forumType, String forumClassify, Integer page) {
    if (StringUtils.isNotBlank(forumTitle)) {
      try {
        forumTitle = URLDecoder.decode(new String(Base64Utils.decode(forumTitle)), "utf8");
      } catch (UnsupportedEncodingException e) {
        forumTitle = StringUtils.EMPTY;
      }
    }
    if (StringUtils.isNotBlank(authorName)) {
      try {
        authorName = URLDecoder.decode(new String(Base64Utils.decode(authorName)), "utf8");
      } catch (UnsupportedEncodingException e) {
        authorName = StringUtils.EMPTY;
      }
    }
    ModelAndView modelAndView = new ModelAndView("/knowledge/forumList");
    Page<ForumModel> forumList =
        forumService.search(forumTitle, authorId, forumType, forumClassify, page);
    modelAndView.addObject("forumTitle", forumTitle);
    modelAndView.addObject("authorId", authorId);
    modelAndView.addObject("authorName", authorName);
    modelAndView.addObject("forumType", forumType);
    modelAndView.addObject("forumClassify", forumClassify);
    if (null != forumList && null != forumList.getResult() && forumList.getResult().size() > 0)
      for (ForumModel forum : forumList.getResult()) {
        forum.setForumTypeDesc(ForumType.getByCode(forum.getForumType()).getValue());
        forum.setForumClassifyDesc(ForumClassify.getByCode(forum.getForumClassify()).getValue());
      }

    // 1: 资源管理 2:校园之声 3:定时任务
    String flag = "noIsPush";
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("typeId", 1);
    inputs.put("currentTime", XdmsConstant.sdf.format(new Date()));
    List<PushRecordModel> lists = pushRecordService.findListByCond(inputs);
    if (lists != null && lists.size() > 0) {
      PushRecordModel target = lists.get(0);
      Integer currCount = target.getIsPush();
      if (currCount < Integer.parseInt(FileUtils.CONFIG.getProperties(
          "quanwang.resource.push.exceeds").trim())) {
        flag = "isPush";
      }
    } else if (FileUtils.CONFIG.getProperties("quanwang.resource.push.exceeds").trim().equals("0")) {
      flag = "noIsPush";
    } else {
      flag = "isPush";
    }
    String tag = FileUtils.CONFIG.getProperties("xd.jpush.environment").trim();
    modelAndView.addObject("tag", tag);
    modelAndView.addObject("flag", flag);
    modelAndView.addObject("forumList", forumList);
    return modelAndView;
  }

  /**
   * 预览页面
   * 
   * @return
   */
  @RequestMapping("/show")
  public ModelAndView show(String forumId) {
    ModelAndView modelAndView = new ModelAndView("/knowledge/show");
    ForumModel forum = forumService.findById(forumId);
    modelAndView.addObject("forum", forum);
    return modelAndView;
  }

  /**
   * 添加页面
   * 
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView createForum() {
    ModelAndView modelAndView = new ModelAndView("/knowledge/editor");
    ForumModel model = new ForumModel();
    model.setForumId(RandomUtil.randomNumber(9));
    List<RegionModel> regionList = regionService.queryAllRegion();
    modelAndView.addObject("regionList", regionList);
    modelAndView.addObject("action", "doAdd");
    modelAndView.addObject("forum", model);
    modelAndView.addObject("uploadToken", qiniuService.getUpToken(XdmsConstant.UP_TOKEN_SCOPE_DEF));
    return modelAndView;
  }

  /**
   * 资源添加
   * 
   * @return
   */
  @RequestMapping("/doAdd")
  @ResponseBody
  public String addForum(ShareKnowledgeAddRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = forumService.addForum(request);
      }
      return FastJsonUtil.toJson(response);
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  @RequestMapping("/release")
  @ResponseBody
  public String releaseToken() throws Exception {
    return qiniuService.relaseUpToken(XdmsConstant.UP_TOKEN_SCOPE_DEF);
  }

  /**
   * 编辑页面
   * 
   * @param forumId
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView editForum(String forumId) {
    ForumModel forum = forumService.findById(forumId);
    ModelAndView modelAndView = new ModelAndView("/knowledge/editor");
    List<RegionModel> regionList = regionService.queryAllRegion();
    modelAndView.addObject("regionList", regionList);
    modelAndView.addObject("action", "doEdit");
    if (null != forum) {
      for (RegionModel region : regionList) {
        if (forum.getRegion().equals(region.getModule())) {
          forum.setRegionName(region.getModuleName());
        }
      }
      forum.setForumTypeDesc(ForumType.getByCode(forum.getForumType()).getValue());
      forum.setForumClassifyDesc(ForumClassify.getByCode(forum.getForumClassify()).getValue());
      forum.setStatusDesc(ForumStatus.getByCode(forum.getStatus()).getValue());
    }
    modelAndView.addObject("forum", forum);
    modelAndView.addObject("uploadToken", qiniuService.getUpToken(XdmsConstant.UP_TOKEN_SCOPE_DEF));
    return modelAndView;
  }

  /**
   * 资源修改
   * 
   * @return
   */
  @RequestMapping("/doEdit")
  @ResponseBody
  public String updateForum(ShareKnowledgeEditRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = forumService.updateForum(request);
      }
      return FastJsonUtil.toJson(response);
    } catch (Exception e) {
      LoggerUtil.error("目录编辑异常", e);
      throw e;
    }
  }

  /**
   * 资源删除
   * 
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String deleteForum(String forumId) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = forumService.deleteForum(forumId);
      if (aBoolean) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return JSON.toJSONString(response);
    } catch (Exception e) {
      LoggerUtil.error("目录删除异常", e);
      throw e;
    }
  }

  /**
   * 模糊查询资源标题列表
   * 
   * @return
   */
  @RequestMapping("/search4Forum")
  @ResponseBody
  public String search4Forum(String forumTitle) {
    SearchTitle4ForumResponse res = new SearchTitle4ForumResponse(ResultType.SUCCESS);
    Page<ForumModel> forumList = forumService.search(forumTitle, null, null, null, null);
    if (null == forumList || null == forumList.getResult() || forumList.getResult().size() == 0)
      return FastJsonUtil.toJson(res);
    for (ForumModel forum : forumList.getResult())
      res.getForumTitleList().add(
          String.format("<p value=\"%s\">%s</p>", forum.getForumId(), forum.getForumTitle()));
    return FastJsonUtil.toJson(res);
  }


  /**
   * 推送消息
   */
  @RequestMapping("/pushForum")
  @ResponseBody
  public String pushForum(String forumId, String typeId, String tag) {
    BaseResponse response = null;
    if (StringUtils.isBlank(tag)) {
      response = new BaseResponse();
      response.setRetCode(ResultType.SYS_FAIL.getRetCode());
      response.setRetDesc("tag不允许为空");
      return FastJsonUtil.toJson(response);
    }

    // 查询推送记录，是否在可推送次数内
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("typeId", typeId);
    inputs.put("currentTime", XdmsConstant.sdf.format(new Date()));
    List<PushRecordModel> lists = pushRecordService.findListByCond(inputs);
    if (lists != null && lists.size() > 0) {
      for (PushRecordModel pushRecordModel : lists) {
        if (pushRecordModel.getIsPush() <= Integer.parseInt(FileUtils.CONFIG.getProperties(
            "quanwang.resource.push.exceeds").trim())) {
          response = forumService.pushForum(forumId, typeId, pushRecordModel.getIsPush(), tag);
          break;
        }
      }
    } else {
      response = forumService.pushForum(forumId, typeId, 0, tag);
    }

    return FastJsonUtil.toJson(response);
  }

}
