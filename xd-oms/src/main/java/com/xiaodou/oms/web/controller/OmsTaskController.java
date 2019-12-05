package com.xiaodou.oms.web.controller;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.service.message.MessageRecordService;
import com.xiaodou.oms.service.task.TaskService;
import com.xiaodou.oms.vo.input.task.BatchPojo;
import com.xiaodou.oms.vo.input.task.CheckMessageBatchPojo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;
import com.xiaodou.oms.web.BaseController;
import com.xiaodou.oms.web.ServiceHandler;

/**
 * Date: 2014/7/1 Time: 16:53
 * 
 * @author Tian.Dong & Guanguo.Gao
 */

@Controller
@RequestMapping("task")
public class OmsTaskController extends BaseController {

  @Resource
  private TaskService taskService;

  @Resource
  private MessageRecordService messageRecordService;

  private static final String preClassName = "com.xiaodou.oms.service.task.";

  @ResponseBody
  @RequestMapping(value = "sendPayRequest", params = {"mod", "top", "remainder"})
  public String sendPayRequest(@ModelAttribute("pojo") BatchPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<BatchPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(BatchPojo pojo) throws Exception {
        return taskService.sendPayRequest(pojo);
      }
    });
  }

  @ResponseBody
  @RequestMapping(value = "getPayType", params = {"mod", "top", "remainder"})
  public String getPayType(@ModelAttribute("pojo") BatchPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<BatchPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(BatchPojo pojo) throws Exception {
        return taskService.getPayType(pojo);
      }
    });
  }

  @RequestMapping("/clean_message")
  @ResponseBody
  public String clean_message() {
    ResultInfo resultInfo = new ResultInfo(ResultType.SUCCESS);
    try {
      Timestamp upperTimer =
          new Timestamp(System.currentTimeMillis()
              - Long.parseLong(ConfigProp.getParams("oms.messageDeleteTimeUpper")));
      Integer limit = Integer.parseInt(ConfigProp.getParams("oms.messageDeleteLimit"));
      messageRecordService.deleteLimitMessage(upperTimer, limit);
    } catch (Exception e) {
      LoggerUtil.error("[OmsTaskController][清除消息失败]", e);
      resultInfo = new ResultInfo(ResultType.SYSFAIL);
    }
    return JSON.toJSONString(resultInfo);
  }

  @RequestMapping(value = "/check_message_send")
  @ResponseBody
  public String checkMessage(CheckMessageBatchPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<CheckMessageBatchPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(CheckMessageBatchPojo pojo) throws Exception {
        return messageRecordService.checkMessageLostThenSend(pojo.getProductLine());
      }
    });
  }

  @RequestMapping(value = "/check_message_send_by_input")
  @ResponseBody
  public String checkMessageByInput(CheckMessageBatchPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<CheckMessageBatchPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(CheckMessageBatchPojo pojo) throws Exception {
        return messageRecordService.checkMessageLostThenSend(pojo);
      }
    });
  }

  /**
   * 供job调用， 查询新单、支付失败的订单，存入rabbitMQ中
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/unpay_gorder_to_mq")
  public String notPayGorderToMq() {

    ResultInfo resultInfo = taskService.unpayGorderToMq();
    return FastJsonUtil.toJson(resultInfo);
  }

  // @RequestMapping(value = "/messageConsume", method = RequestMethod.POST)
  // @ResponseBody
  // public String messageConsume(HttpServletRequest request) {
  // MessageResult messageResult = new MessageResult(MessageResultType.SUCCESS);
  // try {
  // Thread.sleep(FraudConf.getPushMessageSleep());
  // String messageName = request.getParameter("messageName");
  // String[] split = messageName.split("_");
  // String className = split[0];
  // String methondName = split[1];
  // Class consumeClass = Class.forName(preClassName + className);
  // Object bean = SpringWebContextHolder.getBean(consumeClass);
  // Method method = consumeClass.getMethod(methondName, HttpServletRequest.class);
  // method.invoke(bean, request);
  // return JSON.toJSONString(messageResult);
  // } catch (Exception e) {
  // LoggerUtil.error("异步消息消费失败", e);
  // // TODO 报警
  // // AlarmEntity alarmEntity = new AlarmEntity(AlarmEntityType.MESSAGE_CONSUME_ERR.getType());
  // // alarmEntity.setAlertMessage("[异步消息消费异常]" + "[" + e.getMessage() + "]");
  // // alarmEntity.setAlertTitle("[异步消息消费异常]" + "[" + e.getMessage() + "]");
  // // LoggerUtil.alarmInfo(alarmEntity);
  // messageResult = new MessageResult(MessageResultType.FAIL);
  // return JSON.toJSONString(messageResult);
  // } finally {
  // OrderLoggerUtil.loggerRabbitInfo("[" + request.getParameter("tag") + "]" + "["
  // + request.getParameter("messageName") + "][" + request.getParameter("message") + "]["
  // + JSON.toJSONString(messageResult) + "]");
  // }
  // }
}
