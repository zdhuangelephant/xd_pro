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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.message.AdminShortMessage;
import com.xiaodou.ms.model.sms.SmsMerchantModel;
import com.xiaodou.ms.model.sms.SmsTemplateModel;
import com.xiaodou.ms.service.message.AdminShortMessageService;
import com.xiaodou.ms.service.sms.SmsMerchantService;
import com.xiaodou.ms.service.sms.SmsTemplateService;
import com.xiaodou.ms.service.sms.SmsTemplateTypeService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.model.ShortMessage;
import com.xiaodou.summer.dao.pagination.Page;

@Controller("shortMessageController")
@RequestMapping("shortMessage")
public class ShortMessageController extends BaseController {
  @Resource
  AdminShortMessageService adminShortMessageService;
  @Resource
  SmsMerchantService smsMerchantService;
  @Resource
  SmsTemplateTypeService smsTemplateTypeService;
  @Resource
  SmsTemplateService smsTemplateService;

  /**
   * 查询 短信列表
   * 
   * @return
   */
  @RequestMapping("/shortList")
  public ModelAndView shortList(Integer page, String merchantId) {
    merchantId = ("0".equals(merchantId)) ? null : merchantId;
    page = (null == page || 0 == page) ? 1 : page;
    ModelAndView modelAndView = new ModelAndView("/message/shortMessage/list");
    Page<AdminShortMessage> shortPage =
        adminShortMessageService.queryShortMessage(page, merchantId);
    List<SmsMerchantModel> merchantLists = smsMerchantService.queryAllMerchant();
    if (null != shortPage && null != shortPage.getResult()) {
      for (AdminShortMessage adminShortMessage : shortPage.getResult()) {
        SmsTemplateModel templateModel =
            smsTemplateService
                .findTemplateById(Integer.parseInt(adminShortMessage.getTemplateId()));
        adminShortMessage.setTemplateModel(templateModel);
      }
    }
    modelAndView.addObject("shortPage", shortPage);
    modelAndView.addObject("merchantLists", merchantLists);
    modelAndView.addObject("merchantId", merchantId);
    return modelAndView;
  }

  /**
   * 短信 添加页面
   */
  @RequestMapping("/addShort")
  public ModelAndView addShort() {
    ModelAndView modelAndView = new ModelAndView("/message/shortMessage/add");
    Page<SmsTemplateModel> templateList = smsTemplateService.queryTemplateByStatus(1);// 有效模板
    if (null != templateList) {
      modelAndView.addObject("templateList", templateList.getResult());
    }
    List<SmsMerchantModel> merchantLists = smsMerchantService.queryAllMerchant();
    modelAndView.addObject("merchantLists", merchantLists);
    return modelAndView;
  }

  /**
   * 添加 短信
   */
  @RequestMapping("/doAddShort")
  public ModelAndView doAddShort(
      @ModelAttribute("adminShortMessage") AdminShortMessage adminShortMessage, Errors errors,
      HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      if (errors.hasErrors()) {
        return this.showMessage("添加短信失败", this.getErrMsg(errors), "/shortMessage/add", false);
      }
      // 暂时由前端自行控制，当需求完善时再改动
      // SmsTemplateModel templateModel =
      // smsTemplateService.findTemplateById(Integer.parseInt(adminShortMessage.getTemplateId()));
      // String messageContent = templateModel.getMessageContent();
      // String variableKey =
      // messageContent.substring(messageContent.indexOf("${"),
      // messageContent.indexOf("}"));
      // adminShortMessage.setVariables("{"+variableKey+","+adminShortMessage.getVariable()+"}");
      // adminShortMessage.setTelephone("["+adminShortMessage.getTelephone()+"]");
      adminShortMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
      adminShortMessage.setCreateUser(getUser().getRealName());
      adminShortMessage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      adminShortMessage.setUpdateUser(getUser().getRealName());
      adminShortMessageService.addShortMessage(adminShortMessage);
      return this.showMessage("成功", "添加短信成功", "", false);
    } else {
      ModelAndView modelAndView = new ModelAndView("/message/shortMessage/add");
      return modelAndView;
    }
  }

  /**
   * 短信 编辑页面
   * 
   * @param adminShortMessageId
   * @return
   */
  @RequestMapping("/editShort")
  public ModelAndView editShort(String adminShortMessageId) {
    ModelAndView modelAndView = new ModelAndView("/message/shortMessage/edit");
    AdminShortMessage shortMessage =
        adminShortMessageService.findAdminShortMessageById(Long.valueOf(adminShortMessageId));
    Page<SmsTemplateModel> templateList = smsTemplateService.queryTemplateByStatus(1);// 有效模板
    if (null != templateList) {
      modelAndView.addObject("templateList", templateList.getResult());
    }
    List<SmsMerchantModel> merchantLists = smsMerchantService.queryAllMerchant();
    modelAndView.addObject("merchantLists", merchantLists);
    modelAndView.addObject("shortMessage", shortMessage);
    Map<String, String> variables =
        FastJsonUtil.fromJsons(shortMessage.getVariables(),
            new TypeReference<Map<String, String>>() {});
    modelAndView.addObject("variables", variables);
    return modelAndView;
  }

  /**
   * 编辑 短信
   * 
   * @return
   */
  @RequestMapping("/doEditShort")
  public ModelAndView doEditPush(
      @ModelAttribute("editShortMessage") AdminShortMessage editShortMessage, Errors errors,
      HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      if (errors.hasErrors()) {
        return this.showMessage("修改短信失败", this.getErrMsg(errors),
            "/shortMessage/edit?AdminShortMessageId=" + editShortMessage.getId(), false);
      }
      editShortMessage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      editShortMessage.setUpdateUser(getUser().getRealName());
      adminShortMessageService.updateShortMessage(editShortMessage);
      return this.showMessage("成功", "编辑短信成功", "/shortMessage/pushList", false);
    } else {
      ModelAndView modelAndView = new ModelAndView("/message/shortMessage/edit");
      Long adminShortMessageId = editShortMessage.getId();
      AdminShortMessage shortMessage =
          adminShortMessageService.findAdminShortMessageById(adminShortMessageId);
      modelAndView.addObject("shortMessage", shortMessage);
      return modelAndView;
    }
  }

  /**
   * 删除 短信
   * 
   */
  @RequestMapping("/deleteShort")
  public ModelAndView deleteShort(Long adminShortMessageId) {
    if (adminShortMessageId == null) {
      return this.showMessage("失败", "短信id不能为空", "/shortMessage/shortList", false);
    } else {
      adminShortMessageService.deleteShortMessage(adminShortMessageId);
      return this.showMessage("成功", "短信删除成功", "/shortMessage/shortList", false);
    }
  }

  /**
   * 发送 短信
   * 
   * @return
   */
  @RequestMapping("/sms")
  public ModelAndView shortMessage(String adminShortMessageId) {
    AdminShortMessage adminShortMessage =
        adminShortMessageService.findAdminShortMessageById(Long.valueOf(adminShortMessageId));
    if (adminShortMessage == null) {
      return this.showMessage("失败", "短信不能为空", "/shortMessage/shortList", false);
    } else if ("1".equals(adminShortMessage.getMessageStatus())) {
      return this.showMessage("失败", "短信已经发送", "/shortMessage/shortList", false);
    } else {
      ShortMessage shortMessage = new ShortMessage();
      List<String> telephoneList = Lists.newArrayList();
      Map<String, Object> variables = Maps.newHashMap();
      if (StringUtils.isJsonNotBlank(adminShortMessage.getTelephone())) {
        telephoneList =
            FastJsonUtil.fromJsons(adminShortMessage.getTelephone(),
                new TypeReference<List<String>>() {});
      }
      if (StringUtils.isJsonNotBlank(adminShortMessage.getVariables())) {
        variables =
            FastJsonUtil.fromJsons(adminShortMessage.getVariables(),
                new TypeReference<Map<String, Object>>() {});
      }
      shortMessage.setTelephone((String[]) telephoneList.toArray(new String[telephoneList.size()]));
      shortMessage.setTemplateId(adminShortMessage.getTemplateId());
      if (variables.size() != 1) {
        return this.showMessage("失败", "短信每次发送只能针对一种模板", "/shortMessage/shortList", false);
      }
      for (String key : variables.keySet()) {// 只有一组，因为每次发送只是针对一种模板类型
        shortMessage.setVariables(key, variables.get(key));
      }
      HttpResult httpResult = PushClient.push(shortMessage);
      int statusCode = httpResult.getStatusCode();
      httpResult.getStatusDesc();
      httpResult.getContent();
      if (statusCode == 200) {
        AdminShortMessage editshortMessage = new AdminShortMessage();
        editshortMessage.setId(Long.valueOf(adminShortMessageId));
        editshortMessage.setMessageStatus("1");
        adminShortMessageService.updateShortMessage(editshortMessage);
        return this.showMessage("成功", "短信发送成功", "/shortMessage/shortList", false);
      } else {
        return this.showMessage("失败", "短信发送失败", "/shortMessage/shortList", false);
      }
    }
  }

}
