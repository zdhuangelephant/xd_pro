package com.xiaodou.ms.web.controller.knowledge;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.model.knowledge.ForumClassify;
import com.xiaodou.ms.model.knowledge.ForumModel;
import com.xiaodou.ms.model.knowledge.ForumType;
import com.xiaodou.ms.model.knowledge.PushRecordModel;
import com.xiaodou.ms.service.knowledge.ForumService;
import com.xiaodou.ms.service.knowledge.PushRecordService;
import com.xiaodou.ms.util.FileUtils;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.knowledge.SchoolAudioAddRequest;
import com.xiaodou.ms.web.request.knowledge.SchoolAudioEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name SchoolAudioController CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月15日
 * @description 校园之声编辑控制器
 * @version 1.0
 */
@Controller("schoolAudioController")
@RequestMapping("/knowledge/school_audio")
public class SchoolAudioController extends BaseController {
  @Resource
  ForumService forumService;

  @Resource
  PushRecordService pushRecordService;
  
  
  /**
   * 校园之声列表
   * 
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView audioList(Integer page) {
    ModelAndView modelAndView = new ModelAndView("/knowledge/schoolAudioList");
    Page<ForumModel> forumList =
        forumService.search(null, null, ForumType.CAMPUSVOICE.getCode(),
            ForumClassify.AUDIO.getCode(), page);
    
    // modified by zdh at 2017-07-13
    String flag = "noIsPush";
    HashMap<String, Object> inputs = Maps.newHashMap();
	  inputs.put("typeId", 2);
	  inputs.put("currentTime", XdmsConstant.sdf.format(new Date()));
	  List<PushRecordModel> lists = pushRecordService.findListByCond(inputs);
	  if (lists != null && lists.size() > 0) {
		PushRecordModel target = lists.get(0);
		Integer currCount = target.getIsPush();
		if (currCount < Integer.parseInt(FileUtils.CONFIG.getProperties("kechengdingyue.resource.push.exceeds").trim())) {
			flag = "isPush";
		}
	  }else if(FileUtils.CONFIG.getProperties("kechengdingyue.resource.push.exceeds").trim().equals("0")){
		  flag = "noIsPush";
	  }
	  else{
		  flag = "isPush";
	  }
	String tag = FileUtils.CONFIG.getProperties("xd.jpush.environment").trim(); 
	  
	modelAndView.addObject("tag", tag);
    modelAndView.addObject("flag", flag);
    
    modelAndView.addObject("audioList", forumList);
    return modelAndView;
  }

  /**
   * 校园之声添加页面
   * xd.jpush.environment
   * @return
   */
  @RequestMapping("add")
  public ModelAndView audioAdd() {
    ModelAndView modelAndView = new ModelAndView("/knowledge/schoolAudioAdd");
    return modelAndView;
  }

  /**
   * 公告编辑
   * 
   * @param forumId
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView editForum(String forumId) {
    ForumModel forum = forumService.findById(forumId);
    ModelAndView modelAndView = new ModelAndView("/knowledge/schoolAudioEdit");
    modelAndView.addObject("forum", forum);
    return modelAndView;
  }

  /**
   * 校园之声添加
   * 
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView addForum(SchoolAudioAddRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = forumService.addForum(request);
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
   * 资源公告修改
   * 
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView updateForum(SchoolAudioEditRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = forumService.updateForum(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("编辑成功", "",
            "/knowledge/school_audio/edit?forumId=" + request.getForumId(), true);
      } else {
        return this.showMessage("编辑失败", response.getRetDesc(),
            "/knowledge/school_audio/edit?forumId=" + request.getForumId(), true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录编辑异常", e);
      throw e;
    }
  }

  /**
   * 资源公告删除
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
   * 推送消息  
   */
  @RequestMapping("/pushForum")
  @ResponseBody
  public String pushForum(String forumId,String typeId,String tag) {
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
	  if (lists != null && lists.size() > 0 ) {
		 for (PushRecordModel pushRecordModel : lists) {
			if (pushRecordModel.getIsPush() <= Integer.parseInt(FileUtils.CONFIG.getProperties("kechengdingyue.resource.push.exceeds").trim())) {
				response = forumService.pushForum(forumId,typeId,pushRecordModel.getIsPush(),tag);
				break;
			}
		}
	  }else{
		  response = forumService.pushForum(forumId,typeId,0,tag);
	  }
	  return FastJsonUtil.toJson(response);
  }
  
}
