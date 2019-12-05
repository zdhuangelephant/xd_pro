package com.xiaodou.dashboard.web.controller.jmsg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.constant.Constant;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageBody;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageConfig;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageConsumersConfig;
import com.xiaodou.dashboard.model.jsmg.MessageLog;
import com.xiaodou.dashboard.model.jsmg.MessageQueueSetting;
import com.xiaodou.dashboard.model.jsmg.Relation;
import com.xiaodou.dashboard.model.jsmg.ServerQueueSetting;
import com.xiaodou.dashboard.service.facade.DashboardJmsgServiceFacade;
import com.xiaodou.dashboard.service.jmsg.JmsgService;
import com.xiaodou.dashboard.vo.jmsg.request.LogListRequest;
import com.xiaodou.dashboard.vo.jmsg.request.MessageConfigRequest;
import com.xiaodou.dashboard.vo.jmsg.request.MessageConsumersRequest;
import com.xiaodou.dashboard.vo.jmsg.request.MessageQueueSettingRequest;
import com.xiaodou.dashboard.vo.jmsg.request.RelationRequest;
import com.xiaodou.dashboard.vo.jmsg.request.ServerQueueSettingRequest;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

@Controller
@RequestMapping("/jmsg")
public class JmsgController {

  /** alarmService 报警服务service */
  @Resource
  JmsgService jmsgService;
  @Resource
  DashboardJmsgServiceFacade dashboardJmsgServiceFacade;

  /**
   * 列表
   * 
   * @return
   */
  @RequestMapping("/serverNodeList")
  public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView("/jmsg/serverNodeList");
    List<ServerQueueSetting> list = jmsgService.getAllServerQueueSettingList();
    modelAndView.addObject("serverNodeList", list);
    return modelAndView;
  }

  /**
   * 新增-修改
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/serverNodeAdd")
  public String serverNodeAdd(ServerQueueSettingRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      if (!StringUtils.isBlank(request.getId())) {
        jmsgService.editServerQueueSetting(request);
      } else {
        jmsgService.addServerQueueSetting(request);
      }
      map.put("msg", "true");
    } catch (Exception e) {
      map.put("error", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 删除
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/serverNodeDel")
  public String serverNodeDel(ServerQueueSettingRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      if (request.getId() != null) {
        jmsgService.delServerQueueSetting(request);
        map.put("msg", "true");
      } else {
        map.put("error", "没有找到此节点");
      }
    } catch (Exception e) {
      map.put("error", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 列表
   * 
   * @return
   */
  @RequestMapping("/queueList")
  public ModelAndView queueList() {
    ModelAndView modelAndView = new ModelAndView("/jmsg/queueList");
    List<MessageQueueSetting> list = jmsgService.getAllQueueList();
    modelAndView.addObject("queueList", list);
    return modelAndView;
  }

  /**
   * 新增-修改
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/queueAdd")
  public String queueAdd(MessageQueueSettingRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      if (!StringUtils.isBlank(request.getId())) {
        jmsgService.editQueue(request);
      } else {
        jmsgService.addQueue(request);
      }
      map.put("msg", "true");
    } catch (Exception e) {
      map.put("error", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 删除
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/queueDel")
  public String queueDel(MessageQueueSettingRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      if (request.getId() != null) {
        jmsgService.delQueue(request);
        map.put("msg", "true");
      } else {
        map.put("error", "没有找到此节点");
      }
    } catch (Exception e) {
      map.put("error", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 列表
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/relationList")
  public String relationList(RelationRequest request) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    List<MessageQueueSetting> queueList = jmsgService.getAllQueueList();
    List<Relation> relationList = jmsgService.getRelationList(request);
    map.put("queueList", queueList);
    map.put("relationList", relationList);
    return FastJsonUtil.toJson(map);
  }

  /**
   * 设置关联关系
   * 
   * @param adminId
   * @param needDeleteId
   * @param needAddId
   * @return
   */
  @RequestMapping("/editRelation")
  @ResponseBody
  public String editRelation(String groupId, String needDeleteId, String needAddId) {
    Map<String, Object> map = new HashMap<String, Object>();
    if (StringUtils.isNotBlank(needDeleteId)) {
      String[] needDeleteIdArray = needDeleteId.split(";");
      List<String> needDeleteIdList = new ArrayList<>();
      for (String id : needDeleteIdArray) {
        needDeleteIdList.add(id);
      }
      jmsgService.delRelation(groupId, needDeleteIdList);
    }

    if (StringUtils.isNotBlank(needAddId)) {
      String[] needAddIdArray = needAddId.split(";");
      List<String> needAddIdList = new ArrayList<>();
      for (String id : needAddIdArray) {
        needAddIdList.add(id);
      }
      jmsgService.addRelation(groupId, needAddIdList);
    }
    map.put("state", "success");
    return FastJsonUtil.toJson(map);
  }

  /**
   * 列表
   * 
   * @return
   */
  @RequestMapping("/configList")
  public ModelAndView configList() {
    ModelAndView modelAndView = new ModelAndView("/jmsg/configList");
    List<JmsgMessageConfig> list = jmsgService.getAllMessageConfig();
    modelAndView.addObject("list", list);
    return modelAndView;
  }

  /**
   * 新增-修改
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/configAdd")
  public String configAdd(MessageConfigRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      if (!StringUtils.isBlank(request.getId())) {
        jmsgService.editMessageConfig(request);
      } else {
        jmsgService.addMessageConfig(request);
      }
      map.put("msg", "true");
    } catch (Exception e) {
      map.put("error", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 删除
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/configDel")
  public String configDel(MessageConfigRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      if (request.getId() != null) {
        jmsgService.delMessageConfig(request);
        map.put("msg", "true");
      } else {
        map.put("error", "没有找到此节点");
      }
    } catch (Exception e) {
      map.put("error", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 列表
   * 
   * @return
   */
  @RequestMapping("/consumersList")
  public ModelAndView consumersList(String messageName) {
    ModelAndView modelAndView = new ModelAndView("/jmsg/consumersList");
    List<JmsgMessageConsumersConfig> list =
        jmsgService.getMessageConsumersConfigByName(messageName);
    modelAndView.addObject("list", list);
    modelAndView.addObject("messageName", messageName);
    return modelAndView;
  }

  /**
   * 新增-修改
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/consumersAdd")
  public String consumersAdd(MessageConsumersRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      if (!StringUtils.isBlank(request.getId())) {
        jmsgService.editMessageConsumersConfig(request);
      } else {
        jmsgService.addMessageConsumersConfig(request);
      }
      map.put("msg", "true");
    } catch (Exception e) {
      map.put("error", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 删除
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/consumersDel")
  public String consumersDel(MessageConsumersRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      if (request.getId() != null) {
        jmsgService.delMessageConsumersConfig(request);
        map.put("msg", "true");
      } else {
        map.put("error", "没有找到此节点");
      }
    } catch (Exception e) {
      map.put("error", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 日志
   * 
   * @return
   */
  @RequestMapping("/logList")
  public ModelAndView logList(LogListRequest request) {
    ModelAndView modelAndView = new ModelAndView("/jmsg/logList");
    SimpleDateFormat f = new SimpleDateFormat("_yy_MM_dd");
    String datepicker = request.getDatepicker();
    if (StringUtils.isNotBlank(datepicker)) {
      String a[] = request.getDatepicker().split("/");
      datepicker = "_" + a[2].substring(2) + "_" + a[0] + "_" + a[1];
    } else {
      datepicker = f.format(new Date());
    }
    IQueryParam param = new QueryParam();
    if (StringUtils.isNotBlank(request.getCustomTag()))
      param.addInput("customTagLike", request.getCustomTag().trim());
    if (StringUtils.isNotBlank(request.getMessageName()))
      param.addInput("messageNameLike", request.getMessageName().trim());
    if (StringUtils.isNotBlank(request.getResult()))
      param.addInput("result", request.getResult().trim());
    param.addSort("createTime", Sort.DESC);
    param.addInput("surfix", datepicker);
    param.addOutputs(CommUtil.getGeneralField(JmsgMessageBody.class));
    Page<JmsgMessageBody> page = new Page<>(Constant.PAGE_SIZE);
    if (StringUtils.isAllNotBlank(request.getMessageData(), request.getMessageName()))
      page.setPageSize(Integer.MAX_VALUE);
    page.setPageNo(request.getPageNo());
    page = dashboardJmsgServiceFacade.getPageMessageBody(param, page);
    if (null != page) {
      if (StringUtils.isNotBlank(request.getMessageData()) && null != page.getResult()
          && page.getResult().size() > 0) {
        List<JmsgMessageBody> jmbList = Lists.newArrayList();
        for (JmsgMessageBody jmb : page.getResult()) {
          String messageData = jmb.getMessageData().substring(4);
          byte[] decode = Base64.decodeBase64(messageData.getBytes());
          byte[] unzip = unZip(decode);
          String target = new String(unzip);
          if (target.indexOf(request.getMessageData().trim()) > 0) jmbList.add(jmb);
        }
        page.setResult(jmbList);
      }
      modelAndView.addObject("list", page.getResult());
      modelAndView.addObject("totalCount", page.getTotalCount());
      modelAndView.addObject("pageNo", page.getPageNo());
      modelAndView.addObject("totalPage", page.getTotalPage());
    }
    modelAndView.addObject("surfix", datepicker);
    modelAndView.addObject("datepicker", request.getDatepicker());
    modelAndView.addObject("messageName", request.getMessageName());
    modelAndView.addObject("messageData", request.getMessageData());
    modelAndView.addObject("customTag", request.getCustomTag());
    modelAndView.addObject("result", request.getResult());
    return modelAndView;
  }

  /**
   * 重发消息
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/reSendMessage")
  public String reSendMessage(String messageId, String surfix) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      SimpleDateFormat f = new SimpleDateFormat("_yy_MM_dd");
      if (StringUtils.isNotBlank(surfix)) {
        String a[] = surfix.split("/");
        surfix = "_" + a[2].substring(2) + "_" + a[0] + "_" + a[1];
      } else {
        surfix = f.format(new Date());
      }
      Map<String, Object> inputArgument = new HashMap<String, Object>();
      inputArgument.put("surfix", surfix);
      if (StringUtils.isNotBlank(messageId)) {
        inputArgument.put("messageId", messageId);
      } else {
        map.put("msg", "messageId不能为空");
        return FastJsonUtil.toJson(map);
      }
      Map<String, Object> outputField = new HashMap<String, Object>();
      CommUtil.getGeneralField(outputField, JmsgMessageBody.class);
      List<JmsgMessageBody> list =
          dashboardJmsgServiceFacade.getAllMessageBodyList(inputArgument, outputField);
      if (list != null && list.size() > 0) {
        AbstractMessagePojo pojo = new AbstractMessagePojo(list.get(0).getMessageName());
        pojo.setCustomTag(list.get(0).getCustomTag());
        DefaultMessage message = new DefaultMessage();
        message.setCustomTag(list.get(0).getCustomTag());
        message.setMessageName(list.get(0).getMessageName());
        message.setMessageID(UUID.fromString(list.get(0).getMessageId()));
        message.setSendTime(list.get(0).getMessageSendTime());
        String messageData = list.get(0).getMessageData().substring(4);
        byte[] decode = Base64.decodeBase64(messageData.getBytes());
        byte[] unzip = unZip(decode);
        String target = new String(unzip);
        message.setTransferObjectJSON(target);
        pojo.setTransferObject(message);
        RabbitMQSender.getInstance().send(pojo);
      } else {
        map.put("msg", "messageId不存在");
        return FastJsonUtil.toJson(map);
      }
      map.put("msg", "true");
    } catch (Exception e) {
      map.put("msg", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 详情
   * 
   * @return
   */
  @RequestMapping("/logDetail")
  public ModelAndView logDetail(String messageId, String surfix) {
    ModelAndView modelAndView = new ModelAndView("/jmsg/logDetail");
    Map<String, Object> inputArgument = new HashMap<String, Object>();
    if (StringUtils.isNotBlank(surfix)) inputArgument.put("surfix", surfix);
    if (StringUtils.isNotBlank(messageId)) inputArgument.put("messageId", messageId);
    Map<String, Object> outputField = new HashMap<String, Object>();
    CommUtil.getGeneralField(outputField, JmsgMessageBody.class);
    List<JmsgMessageBody> list =
        dashboardJmsgServiceFacade.getAllMessageBodyList(inputArgument, outputField);
    Map<String, Object> outputField2 = new HashMap<String, Object>();
    CommUtil.getGeneralField(outputField2, MessageLog.class);
    List<MessageLog> logList =
        dashboardJmsgServiceFacade.getAllMessageLogList(inputArgument, outputField2);
    if (list != null && list.size() > 0) {
      String messageData = list.get(0).getMessageData().substring(4);
      byte[] decode = Base64.decodeBase64(messageData.getBytes());
      byte[] unzip = unZip(decode);
      String target = new String(unzip);
      list.get(0).setMessageData(target);
      modelAndView.addObject("messageBody", list.get(0));
    }
    if (logList != null && logList.size() > 0) {
      modelAndView.addObject("messageLogList", logList);
    }
    return modelAndView;
  }


  public static byte[] unZip(byte[] data) {
    byte[] b = null;
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      ZipInputStream zip = new ZipInputStream(bis);
      while (zip.getNextEntry() != null) {
        byte[] buf = new byte[1024];
        int num;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((num = zip.read(buf, 0, buf.length)) != -1) {
          outputStream.write(buf, 0, num);
        }
        b = outputStream.toByteArray();
        outputStream.flush();
        outputStream.close();
      }
      zip.close();
      bis.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return b;
  }
}
