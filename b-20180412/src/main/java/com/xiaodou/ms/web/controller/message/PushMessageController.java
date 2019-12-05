package com.xiaodou.ms.web.controller.message;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.common.XDcity;
import com.xiaodou.ms.model.message.AdminPushMessage;
import com.xiaodou.ms.service.common.XDcityService;
import com.xiaodou.ms.service.message.AdminPushMessageService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.summer.dao.pagination.Page;

@Controller("pushMessageController")
@RequestMapping("pushMessage")
public class PushMessageController extends BaseController {

  @Resource
  AdminPushMessageService adminPushMessageService;
  @Resource
  XDcityService XDcityService;

  /**
   * 查询 消息列表
   * 
   * @return
   */
  @RequestMapping("/pushList")
  public ModelAndView pushList(Integer page) {
    page = (null == page || 0 == page) ? 1 : page;
    ModelAndView modelAndView = new ModelAndView("/message/pushMessage/list");
    Page<AdminPushMessage> pushPage = adminPushMessageService.queryPushMessage(page);
    modelAndView.addObject("pushPage", pushPage);
    return modelAndView;
  }

  /**
   * 消息推送 添加页面
   */
  @RequestMapping("/addPush")
  public ModelAndView addPush() {
    ModelAndView modelAndView = new ModelAndView("/message/pushMessage/add");
    return modelAndView;
  }

  /**
   * 添加 推送消息
   */
  @RequestMapping("/doAddPush")
  public ModelAndView doAddPush(
      @ModelAttribute("adminPushMessage") AdminPushMessage adminPushMessage, Errors errors,
      HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      // newAdminRequest.validate(newAdminRequest, errors);
      if (errors.hasErrors()) {
        return this.showMessage("添加推送消息失败", this.getErrMsg(errors), null, true);
      }
      adminPushMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
      adminPushMessage.setCreateUser(getUser().getRealName());
      adminPushMessage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      adminPushMessage.setUpdateUser(getUser().getRealName());
      adminPushMessageService.addPushMessage(adminPushMessage);
      return this.showMessage("成功", "添加推送消息成功", null, true);
    } else {
      ModelAndView modelAndView = new ModelAndView("/message/pushMessage/add");
      return modelAndView;
    }
  }

  /**
   * 消息推送 编辑页面
   * 
   * @param adminPushMessageId
   * @return
   */
  @RequestMapping("/editPush")
  public ModelAndView editPush(String adminPushMessageId) {
    ModelAndView modelAndView = new ModelAndView("/message/pushMessage/edit");
    AdminPushMessage pushMessage =
        adminPushMessageService.findAdminPushMessageById(Long.valueOf(adminPushMessageId));
    modelAndView.addObject("pushMessage", pushMessage);
    return modelAndView;
  }

  /**
   * 编辑 推送消息
   * 
   * @return
   */
  @RequestMapping("/doEditPush")
  public ModelAndView doEditPush(
      @ModelAttribute("editPushMessage") AdminPushMessage editPushMessage, Errors errors,
      HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      if (errors.hasErrors()) {
        return this.showMessage("修改推送消息失败", this.getErrMsg(errors),
            "/pushMessage/edit?adminPushMessageId=" + editPushMessage.getId(), false);
      }
      if (StringUtils.isBlank(editPushMessage.getMessageExtras())
          || StringUtils.isBlank(editPushMessage.getNoticeExtras())) {
        return this.showMessage("修改推送消息失败,必填参数不能为空。", this.getErrMsg(errors),
            "/pushMessage/edit?adminPushMessageId=" + editPushMessage.getId(), false);
      }
      editPushMessage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      editPushMessage.setUpdateUser(getUser().getRealName());
      adminPushMessageService.updatePushMessage(editPushMessage);
      return this.showMessage("成功", "编辑推送消息成功", "/pushMessage/pushList", false);
    } else {
      ModelAndView modelAndView = new ModelAndView("/message/pushMessage/edit");
      Long adminPushMessageId = editPushMessage.getId();
      AdminPushMessage pushMessage =
          adminPushMessageService.findAdminPushMessageById(adminPushMessageId);
      modelAndView.addObject("pushMessage", pushMessage);
      return modelAndView;
    }
  }

  /**
   * 删除 推送消息
   * 
   */
  @RequestMapping("/deletePush")
  public ModelAndView deletePush(Long adminPushMessageId) {
    if (adminPushMessageId == null) {
      return this.showMessage("失败", "消息id不能为空", "/pushMessage/pushList", false);
    } else {
      // adminService.deleteAdmin(adminId);
      adminPushMessageService.deletePushMessage(adminPushMessageId);
      return this.showMessage("成功", "消息删除成功", "/pushMessage/pushList", false);
    }
  }

  /**
   * 根据城市名查找对应id
   * 
   */
  @RequestMapping("/findCity")
  @ResponseBody
  public String findCity(String province, String city, String area) {
    XDcity xdcity = null;
    if (StringUtils.isBlank(area) || ("0").equals(area)) {
      if (StringUtils.isBlank(city) || ("0").equals(city)) {
        xdcity = XDcityService.findCityByOne(province);
      } else {
        xdcity = XDcityService.findCityByTwo(city, province);
      }
    } else {
      xdcity = XDcityService.findCityByTwo(area, city);
    }
    return FastJsonUtil.toJson(xdcity);
  }

  /**
   * 推送 消息
   * 
   * @return
   */
  @RequestMapping("/push")
  public ModelAndView pushMessage(String adminPushMessageId) {
    AdminPushMessage pushMessage =
        adminPushMessageService.findAdminPushMessageById(Long.valueOf(adminPushMessageId));
    if (pushMessage == null) {
      return this.showMessage("失败", "推送消息不能为空", "/pushMessage/pushList", false);
    } else {
      List<String> registrationIdsList = null;
      List<String> aliasList = null;
      List<String> tagsList = null;
      Map<String, String> messageExtrasMap = null;// new HashMap<String, String>();
      Map<String, String> noticeExtrasMap = null;// new HashMap<String, String>();
      String registrationIds = pushMessage.getRegistrationIds();
      if (StringUtils.isJsonNotBlank(registrationIds)) {
        registrationIdsList =
            FastJsonUtil.fromJsons(registrationIds, new TypeReference<List<String>>() {});
      }
      if (StringUtils.isJsonNotBlank(pushMessage.getAlias())) {
        aliasList =
            FastJsonUtil.fromJsons(pushMessage.getAlias(), new TypeReference<List<String>>() {});
      }
      if (StringUtils.isJsonNotBlank(pushMessage.getTags())) {
        tagsList =
            FastJsonUtil.fromJsons(pushMessage.getTags(), new TypeReference<List<String>>() {});
      }
      if (StringUtils.isJsonNotBlank(pushMessage.getMessageExtras())) {
        messageExtrasMap =
            FastJsonUtil.fromJsons(pushMessage.getMessageExtras(),
                new TypeReference<Map<String, String>>() {});
      }
      if (StringUtils.isJsonNotBlank(pushMessage.getNoticeExtras())) {
        noticeExtrasMap =
            FastJsonUtil.fromJsons(pushMessage.getNoticeExtras(),
                new TypeReference<Map<String, String>>() {});
      }
      Message ms = new Message();
     // ms.setModule(XdmsConstant.MODULE);
      ms.setMessageContent(pushMessage.getMessageContent());
      ms.setNoticeContent(pushMessage.getNoticeContent());
      // MessageType w = MessageType.valueOf(MessageType.class, messageType);
      if ("0".equals(pushMessage.getMessageType())) ms.setMessageType(MessageType.ALL);
      if ("2".equals(pushMessage.getMessageType())) ms.setMessageType(MessageType.NOTICE);
      if ("3".equals(pushMessage.getMessageType())) ms.setMessageType(MessageType.MESSAGE);
      if ("0".equals(pushMessage.getSpreadRange())) ms.setScope(SpreadRange.ALL);
      if ("1".equals(pushMessage.getSpreadRange())) ms.setScope(SpreadRange.ALIAS);
      if ("2".equals(pushMessage.getSpreadRange())) ms.setScope(SpreadRange.TAG);
      if ("3".equals(pushMessage.getSpreadRange())) ms.setScope(SpreadRange.REGISTRATION_ID);
      if ("4".equals(pushMessage.getSpreadRange())) ms.setScope(SpreadRange.ALIAS_TAG);
      if ("5".equals(pushMessage.getSpreadRange())) ms.setScope(SpreadRange.ALIAS_REGISTRATION_ID);
      if ("6".equals(pushMessage.getSpreadRange())) ms.setScope(SpreadRange.TAG_REGISTRATION_ID);
      if ("7".equals(pushMessage.getSpreadRange()))
        ms.setScope(SpreadRange.ALIAS_TAG_REGISTRATION_ID);
      // ms.setScope(SpreadRange.valueOf(SpreadRange.class,pushMessage.getSpreadRange()));
      if ("0".equals(pushMessage.getTargetType())) ms.setTarget(TargetType.ALL);
      if ("1".equals(pushMessage.getTargetType())) ms.setTarget(TargetType.ANDROID);
      if ("2".equals(pushMessage.getTargetType())) ms.setTarget(TargetType.IOS);
      // ms.setTarget(TargetType.valueOf(TargetType.class,pushMessage.getTargetType()));
      if (null != registrationIdsList)
        ms.addRegister(registrationIdsList.toArray(new String[registrationIdsList.size()]));
      if (null != aliasList) ms.addReciever(aliasList.toArray(new String[aliasList.size()]));
      if (null != tagsList) ms.addGroup((String[]) tagsList.toArray(new String[tagsList.size()]));
      // messageextras.put("retcode", "20409");
      // messageextras.put("retdesc", "token已失效，请重新登录");
      ms.setMessageextras(messageExtrasMap);// 消息参数
      ms.setNoticeextras(noticeExtrasMap);
      HttpResult httpResult = PushClient.push(ms);
      int statusCode = httpResult.getStatusCode();
      httpResult.getStatusDesc();
      httpResult.getContent();
      if (statusCode == 200) {
        AdminPushMessage editPushMessage = new AdminPushMessage();
        editPushMessage.setId(Long.valueOf(adminPushMessageId));
        editPushMessage.setMessageStatus(1);
        adminPushMessageService.updatePushMessage(editPushMessage);
        return this.showMessage("成功", "消息发送成功", "/pushMessage/pushList", false);
      } else
        return this.showMessage("失败", "消息推送失败", "/pushMessage/pushList", false);
    }
  }
}
