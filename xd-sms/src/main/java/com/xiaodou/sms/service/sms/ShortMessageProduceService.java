package com.xiaodou.sms.service.sms;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.push.agent.model.ShortMessage;
import com.xiaodou.sms.common.enums.CascadeTemplateEnum;
import com.xiaodou.sms.common.enums.ResultType;
import com.xiaodou.sms.dao.ms.SmsTaskModelDao;
import com.xiaodou.sms.model.SmsChannelModel;
import com.xiaodou.sms.model.SmsTaskModel;
import com.xiaodou.sms.model.SmsTemplateCategoryModel;
import com.xiaodou.sms.service.facade.ISmsServiceFacade;
import com.xiaodou.sms.utils.VelocityUtil;
import com.xiaodou.sms.web.response.sms.MessageResponse;

@Service
public class ShortMessageProduceService {

  @Resource
  ISmsServiceFacade smsServiceFacade;
  @Resource
  SmsTaskModelDao smsTaskModelDao;

  public MessageResponse produce(ShortMessage messageRequest) {


    /**
     * TODO 生产短信service
     * 
     */
    try {
      MessageResponse response = new MessageResponse(ResultType.SUCCESS);
      // 查询template和template_type表的渠道号和messageContent
      SmsTemplateCategoryModel category =
          smsServiceFacade.querySmsTemplateCategoryById(messageRequest.getTemplateId());
      if (null == category) {
        response.setStatus(CascadeTemplateEnum.UnExistedResult.getCode());
        response.setStatusDesc(CascadeTemplateEnum.UnExistedResult.getDesc());
        return response;
      }
      // 通过查询出来的数据判断模板是否生效:0=停用,1-启用
      if (category.getStatus() == 0) {
        response.setStatus(CascadeTemplateEnum.UnAbledTemplate.getCode());
        response.setStatusDesc(CascadeTemplateEnum.UnAbledTemplate.getDesc());
        return response;
      }
      /**
       * TODO 可能需要做redis缓存判重
       */
      VelocityContext context = new VelocityContext();
      for (String key : messageRequest.getVariables().keySet()) {
        context.put(key, messageRequest.getVariables().get(key));
      }
      String message = null;
      try {
        message = VelocityUtil.transform(category.getMessageContent(), context);
      } catch (Exception e) {
        LoggerUtil.error("[模板解析失败]", e);
        response = new MessageResponse(ResultType.SYSFAIL);
        return response;
      }

      List<String> telephones = messageRequest.getTelephone();
      for (String telephone : telephones) {
        SmsTaskModel task = new SmsTaskModel();
        task.setMessage(message);
        List<String> channelIds =
            Lists.newArrayList(category.getCateGory().getChannelIds().split(","));
        // 遍历查询channel表，与其status字段做比较：通道状态 0=无效，1=有效
        List<SmsChannelModel> channelList = smsServiceFacade.queryNormalSmsChannelModel(channelIds);
        if (null == channelList || channelList.size() <= 0) {
          response.setStatus(CascadeTemplateEnum.UnAbledChannelId.getCode());
          response.setStatusDesc(CascadeTemplateEnum.UnAbledChannelId.getDesc());
          return response;
        } else {
          task.setChannelIdList(Lists.transform(channelList,
              new Function<SmsChannelModel, Integer>() {
                @Override
                public Integer apply(SmsChannelModel input) {
                  return input.getId();
                }
              }));
        }
        // 判断状态，然后在执行添加操作
        task.setStatus(0);
        task.setMsg("准备发送");
        task.setTemplateId(Integer.valueOf(messageRequest.getTemplateId()));
        task.setMobile(telephone);
        task.setCreateTime(new Timestamp(System.currentTimeMillis()));
        task.setCanRetryTime(category.getCateGory().getRetryTime());
        task.setTemplateTypeId(category.getCateGory().getId());
        
        // TODO 1、设置应用端类型；2、设置消息业务ID(UUID)
        task.setAppMessageId(messageRequest.getAppMessageId());
        task.setProductLine(messageRequest.getProductLine());
        
        // 将产生的短信内容(验证码)内容写入task中,并放入队列
        task = smsServiceFacade.addSmsTaskModelEntity(task);
        if (null == task) {
          response.setStatus(CascadeTemplateEnum.AddTaskFail.getCode());
          response.setStatusDesc(CascadeTemplateEnum.AddTaskFail.getDesc());
          return response;
        }



      }

      response.setStatus(0);
      response.setStatusDesc("生成短信成功");
      return response;
    } catch (NumberFormatException e) {
      e.printStackTrace();
      LoggerUtil.error("service层：短信生成异常", e);
      throw e;
    }
  }

  public static int NextInt(final int min, final int max) {
    Random rand = new Random();
    int tmp = Math.abs(rand.nextInt());
    return tmp % (max - min + 1) + min;
  }
}
