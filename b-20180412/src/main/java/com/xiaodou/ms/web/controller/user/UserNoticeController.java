package com.xiaodou.ms.web.controller.user;

import java.sql.Timestamp;
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
import com.xiaodou.ms.model.knowledge.PushRecordModel;
import com.xiaodou.ms.model.user.BlankNoticeModel;
import com.xiaodou.ms.model.user.UserNoticeModel;
import com.xiaodou.ms.service.common.QiniuService;
import com.xiaodou.ms.service.knowledge.PushRecordService;
import com.xiaodou.ms.service.product.ModuleSlideService;
import com.xiaodou.ms.service.user.BlankNoticeService;
import com.xiaodou.ms.service.user.UserNoticeService;
import com.xiaodou.ms.util.FileUtils;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.user.BlankNoticeRequest;
import com.xiaodou.ms.web.request.user.UserNoticeCreateRequest;
import com.xiaodou.ms.web.request.user.UserNoticeEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Controller("userNoticeController")
@RequestMapping("/userNotice")
public class UserNoticeController extends BaseController {

  private Short OwnContent = 1;
  private Short OutLink = 2;

  @Resource
  QiniuService qiniuService;

  @Resource
  UserNoticeService userNoticeService;

  @Resource
  BlankNoticeService blankNoticeService;

  @Resource
  PushRecordService pushRecordService;

//  @Resource
//  private ProductModuleService productModuleService;

  
  @Resource
  ModuleSlideService moduleSlideService;
  /**
   * 公告目录
   */
  @RequestMapping("/list")
  public ModelAndView resourceUserNoticeList() {
    try {
      ModelAndView modelAndView = new ModelAndView("/user/userNoticeList");
      List<UserNoticeModel> userNoticeLists = userNoticeService.queryAllUserNotice();

      // 1: 资源管理 2:校园之声 3:定时任务
      String flag = "noIsPush";
      HashMap<String, Object> inputs = Maps.newHashMap();
      inputs.put("typeId", 4);
      inputs.put("currentTime", XdmsConstant.sdf.format(new Date()));
      List<PushRecordModel> lists = pushRecordService.findListByCond(inputs);
      if (lists != null && lists.size() > 0) {
        PushRecordModel target = lists.get(0);
        Integer currCount = target.getIsPush();
        if (currCount < Integer.parseInt(FileUtils.CONFIG.getProperties(
            "quanwang.resource.push.exceeds").trim())) {
          flag = "isPush";
        }
      } else if (FileUtils.CONFIG.getProperties("quanwang.resource.push.exceeds").trim()
          .equals("0")) {
        flag = "noIsPush";
      } else {
        flag = "isPush";
      }

	  String tag = FileUtils.CONFIG.getProperties("xd.jpush.environment").trim(); 
	  modelAndView.addObject("tag", tag);
      modelAndView.addObject("flag", flag);

      modelAndView.addObject("userNoticeLists", userNoticeLists);
      return modelAndView;
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      throw e;
    }
  }


  /**
   * 公告预览页面
   * 
   * @return
   */
  @RequestMapping("/show")
  public ModelAndView show(Long categoryId) {
    ModelAndView modelAndView = new ModelAndView("/editor/show");
    UserNoticeModel userNoticeModel =
        userNoticeService.findUserNoticeById(categoryId);
    modelAndView.addObject("data", userNoticeModel.getContent());
    return modelAndView;
  }

  /**
   * 公告添加页面
   * 
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView resourceUserHelpAdd() {
    ModelAndView modelAndView = new ModelAndView("/editor/new");
    modelAndView.addObject("action", "doAdd");
    modelAndView.addObject("id", "1");
    modelAndView.addObject("title", "新公告");
    modelAndView.addObject("html", null);
    try {
      String uploadToken = qiniuService.getUpToken(XdmsConstant.UP_TOKEN_SCOPE_DEF);
      modelAndView.addObject("uploadToken", uploadToken);
    } catch (Exception e) {
      LoggerUtil.error("获取七牛上传凭证失败", e);
    }
    return modelAndView;
  }

  /**
   * 资源公告添加
   * 
   * @return
   */
  @RequestMapping("/doAdd")
  @ResponseBody
  public String resourceUserNoticeDoAdd(UserNoticeCreateRequest request, Errors errors)
      throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = userNoticeService.addUserNotice(request);
      }
      return FastJsonUtil.toJson(response);
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 公告编辑
   * 
   * @param categoryId
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView resourceUserNoticeEdit(Long categoryId) {
    UserNoticeModel userNoticeModel =
        userNoticeService.findUserNoticeById(categoryId);
    if (OutLink == userNoticeModel.getType()) {
      ModelAndView modelAndView = new ModelAndView("/user/userNoticeEdit");
      modelAndView.addObject("userNoticeModel", userNoticeModel);
      return modelAndView;
    }
    ModelAndView modelAndView = new ModelAndView("/editor/editor");
    modelAndView.addObject("action", "doEdit");
    modelAndView.addObject("id", categoryId);
    modelAndView.addObject("title", userNoticeModel.getTitle());
    modelAndView.addObject("html", userNoticeModel.getContent());
    modelAndView.addObject("uploadToken", qiniuService.getUpToken(XdmsConstant.UP_TOKEN_SCOPE_DEF));
    return modelAndView;
  }

  /**
   * 编辑设置外链
   * 
   * @param categoryId 公告ID
   * @return
   */
  @RequestMapping("/editOutLink")
  public ModelAndView editOutLink(Long categoryId) {
    ModelAndView modelAndView = new ModelAndView("/user/userNoticeEdit");
    UserNoticeModel userNoticeModel =
        userNoticeService.findUserNoticeById(categoryId);
    if (OutLink != userNoticeModel.getType()) {
      userNoticeModel.setType(OutLink);
      userNoticeModel.setContent(null);
    }
    modelAndView.addObject("userNoticeModel", userNoticeModel);
    return modelAndView;
  }

  @RequestMapping("/cancelOutLink")
  @ResponseBody
  public String cancelOutLink(Long catId) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    UserNoticeModel userNoticeModel = userNoticeService.findUserNoticeById(catId);
    if (null == userNoticeModel) {
      return new BaseResponse(ResultType.SYS_FAIL).toString();
    }
    userNoticeModel.setType(OwnContent);
    userNoticeService.editUserNotice(userNoticeModel);
    return response.toString();
  }

  /**
   * 资源公告修改
   * 
   * @return
   */
  @RequestMapping("/doEdit")
  @ResponseBody
  public String resourceUserNoticeDoEdit(UserNoticeEditRequest request, Errors errors)
      throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));

      } else {
        response = userNoticeService.editUserNotice(request);
      }
      return FastJsonUtil.toJson(response);
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
  public String redourceUserNoticeDel(Integer catId) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = userNoticeService.deleteUserNotice(catId);
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


  // blankNotice
  /**
   * 开屏列表查看
   * 
   * @return
   */

  @RequestMapping("/blankNotice")
  public ModelAndView blankNoticeList(Short isVisible, Integer page) {
    ModelAndView mv = new ModelAndView("/user/openScreenNoticeList");
    page = (null == page || 0 == page) ? 1 : page;
    Page<BlankNoticeModel> blankNoticePage = blankNoticeService.queryBlankNotice(isVisible, page);
//    if (null != blankNoticePage && blankNoticePage.getResult().size() != 0) {
//      for (BlankNoticeModel bnModel : blankNoticePage.getResult()) {
//        if (bnModel == null) {
//          continue;
//        }
//        IQueryParam param = new QueryParam();
//        param.addInput("module", bnModel.getModule());
//        param.addOutput("name", "");
//        Page<ProductModuleModel> pageModel = productModuleService.findModuleByCond(param, null);
//        if (pageModel != null && pageModel.getResult().size() != 0) {
//          bnModel.setModuleName(pageModel.getResult().get(0).getName());
//        }
//      }
//    }

    mv.addObject("data", blankNoticePage);
    mv.addObject("isVisible", isVisible);
    return mv;
  }

  /**
   * 开屏修改跳转
   * 
   * @return
   */
  @RequestMapping("/blankNoteceEdit")
  public ModelAndView blankNoticeEdit(Long id) {
    BlankNoticeModel blankNoticeModel = blankNoticeService.findBlankNoticeById(id);
    IQueryParam param = new QueryParam();
   // param.addInput("module", blankNoticeModel.getModule());
    param.addOutput("name", "");
    //Page<ProductModuleModel> page = productModuleService.findModuleByCond(param, null);
//    if (page != null && page.getResult().size() != 0) {
//      String name = page.getResult().get(0).getName();
//      //blankNoticeModel.setModuleName(name);
//    }
    ModelAndView modelAndView = new ModelAndView("/user/openScreenNoticeEdit");
    modelAndView.addObject("blankNotice", blankNoticeModel);
    // 获取所有的module
//    List<ProductModuleModel> productModuleList = productModuleService.queryAllModule();
//    modelAndView.addObject("moduleList", productModuleList);
    return modelAndView;
  }


  /**
   * 开屏修改
   * 
   * @return
   */
  @RequestMapping("/doBlankNoteceEdit")
  public ModelAndView blankNoticeDoEdit(BlankNoticeRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        BlankNoticeModel entity = new BlankNoticeModel();
        entity.setId(request.getId());
        entity.setImage(request.getImage());
        entity.setIsInnerLink(request.getIsInnerLink());
        entity.setIsVisible(request.getIsVisible());
        entity.setJumpUrl(request.getJumpUrl());
        //entity.setModule(request.getModule());
        entity.setTitle(request.getTitle());
        entity.setType(request.getType());
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        blankNoticeService.editBlankNotice(entity);
        response = new BaseResponse(ResultType.SUCCESS);
      }

      if (response.getRetCode() == 0) {
        return this.showMessage("编辑成功", "", null, true);
      } else {
        return this.showMessage("编辑失败", response.getRetDesc(), "/userNotice/blankNoteceEdit?id="
            + request.getId(), true);
      }

    } catch (Exception e) {
      LoggerUtil.error("开屏编辑异常", e);
      throw e;
    }
  }

  @RequestMapping("/blankNoteceDelete")
  @ResponseBody
  public String blankNoteceDelete(Long id) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = blankNoticeService.delete(id);
      if (aBoolean) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return JSON.toJSONString(response);
    } catch (Exception e) {
      LoggerUtil.error("删除异常", e);
      throw e;
    }
  }


  /**
   * 公告推送
   */
  /**
   * 推送消息
   */
  @RequestMapping("/pushForum")
  @ResponseBody
  public String pushForum(Long id, String typeId,String tag) {
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
          response = userNoticeService.pushForum(id, typeId, pushRecordModel.getIsPush(), tag);
          break;
        }
      }
    } else {
      response = userNoticeService.pushForum(id, typeId, 0, tag);
    }

    return FastJsonUtil.toJson(response);
  }
  
  
  /**
   * 去往开屏通知新增页面
   */
  @RequestMapping("/openScreenNoticeAdd")
  public ModelAndView openScreenNoticeAdd() {
    ModelAndView modelAndView = new ModelAndView("/user/openScreenNoticeAdd");
    try {
      // 获取所有的module
//      List<ProductModuleModel> productModuleList = productModuleService.queryAllModule();
//      modelAndView.addObject("moduleList", productModuleList);
    } catch (Exception e) {
      LoggerUtil.error("跳往新增页面失败", e);
    }
    return modelAndView;
  }
  
  
  
  /**
   * 开屏通知添加
   * 
   * @return
   */
  @RequestMapping("/doOpenScreenNoticeAdd")
  public ModelAndView doOpenScreenNoticeAdd(BlankNoticeRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
          response = blankNoticeService.doOpenScreenNoticeAdd(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("开屏通知创建异常", e);
      throw e;
    }
  }

}
