package com.xiaodou.jmsg.server.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.xiaodou.amqp.connectpool.receiverdispatch.MessageListener;
import com.xiaodou.amqp.connectpool.receiverdispatch.RabbitReceiverDispatcher;
import com.xiaodou.amqp.sedecodehelper.CodecHelper;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.jmsg.model.MessageConfig;
import com.xiaodou.jmsg.server.broadcastresponse.BroadcastResponse;
import com.xiaodou.jmsg.server.constant.InitConstant;
import com.xiaodou.jmsg.server.model.MessageBody;
import com.xiaodou.jmsg.server.model.MessageQueueSetting;
import com.xiaodou.jmsg.server.model.ServerQueueSetting;
import com.xiaodou.jmsg.server.model.sqlite.FailMessage;
import com.xiaodou.jmsg.server.service.facade.JmsgServerSqliteServiceFacade;
import com.xiaodou.jmsg.server.util.HostHelper;
import com.xiaodou.jmsg.server.vo.JmsgAlarm;
import com.xiaodou.jmsg.server.vo.FailJmsgFilter;
import com.xiaodou.jmsg.service.ConfigService;
import com.xiaodou.jmsg.service.RabbitConnectionPoolService;
import com.xiaodou.summer.util.SpringWebContextHolder;

@Component
@Service("messageServerService")
public class MessageServerService implements MessageListener {
  public static void main(String[] args) {
    System.out.println(HostHelper.getHostName());
  }
  public static volatile Map<String,FailJmsgFilter>  failMap= new HashMap<String,FailJmsgFilter>();
  private static List<String> _queueNames = new ArrayList<>();
  public static volatile Map<String,String> uuidMap=new HashMap<String,String>();

  static {
    /* setQueueNames(null); */
  }

  public static void setQueueNames(ServerQueueSetting setting) {
    List<String> _queueNames = new ArrayList<>();
    String serverName = InitConstant.serverName;
    LoggerUtil.debug("loadServerSetting : " + serverName);
    if (setting == null) {
      MessageQueueSettingService m = new MessageQueueSettingService();
      setting = m.getServerSetting(serverName);
    }
    RabbitConnectionPoolService.getPool();
    if (setting != null) {
      if (setting.isEnable()) {
        for (String key : setting.getQueueSettings().keySet()) {
          MessageQueueSetting queueSetting = setting.getQueueSettings().get(key);
          RabbitReceiverDispatcher.getInstance().registerListener(MessageServerService.class,
              queueSetting.getQueueName(), queueSetting.getParallelCount(), false,
              queueSetting.getQos());
          LoggerUtil.debug("registerListener: " + queueSetting.getQueueName());

          if (!_queueNames.contains(queueSetting.getQueueName())) {
            _queueNames.add(queueSetting.getQueueName());
          }
        }
      }
      MessageServerService._queueNames = _queueNames;
    } else {
      LoggerUtil.debug("No server setting of: +" + serverName);
    }
  }

  @Override
  public boolean receive(String msg, String queueName) throws Exception {
    if (StringUtils.isNotBlank(msg)) {
      DefaultMessage message = FastJsonUtil.fromJson(msg, DefaultMessage.class);
      if (message.getReceiveTime() == null) {
        message.setReceiveTime(new Date());
      }
      message.setQueueName(queueName);
      Date receiveTime = new Date();
      StringBuilder sb = null;  
      MessageConfig mbc = null ; 
      MessageBody savedMessageBody = null; 
      long checkConfigTime = 0;
      System.out.println((message.getCustomTag()+"阶段1:"+new Date().toString()));
      check(message,sb,mbc,savedMessageBody,checkConfigTime,receiveTime); 
    }
    return true;
  }
  

  
  public void check(com.xiaodou.jmsg.entity.DefaultMessage message, StringBuilder sb,
	      MessageConfig mbc, MessageBody savedMessageBody, long checkConfigTime, Date receiveTime) {
		if (Calendar.getInstance().get(Calendar.SECOND) == 1) {
	      // 记录1/60的日志
	      sb = new StringBuilder();
	      sb.append("Broadcast|");
	    }
	    long startTime = System.currentTimeMillis();
	    // 1.l 消息为空或消息名为空则丢弃
	    if (message == null) {
	      return;
	    }
	    if (message.getMessageName().isEmpty()) {
	      return;
	    }

	    if (sb != null) {
	      sb.append(message.getMessageName());
	      sb.append("|");
	    }

	    // 1.1 配置信息检查
	    mbc = ConfigService.getMessageConfig(message.getMessageName());
	    if (mbc == null) {
	      LoggerUtil.debug("Can not find the message '%s' config!");
	      return;
	    }
	
	    if (mbc.consumers == null || mbc.consumers.size() <= 0) {
	      return;
	    }

	    checkConfigTime = System.currentTimeMillis();
	    if (sb != null) {
	      sb.append(String.format("Conf Check:%d|", checkConfigTime - startTime));
	    }
	    if (message.getSendFromClass().equals(
	        "com.xiaodou.dashboard.web.controller.jmsg.JmsgController")
	        || message.getSendFromClass().equals("com.xiaodou.logmonitor.bolt.JmsgMessageBolt")) {} 
	    else if (MessageProcessService.checkRepeatMessageByMemory(message)) {
	      // 检查内存,消息重复则退出
	      return;
	    }
	    System.out.println((message.getCustomTag()+"阶段2:"+new Date().toString()));
	    savedMessageBody = MessageProcessService.getSavedMessageBody(message);
	    System.out.println((message.getCustomTag()+"阶段3:"+new Date().toString()));
	    if (message.getSendFromClass().equals("com.xiaodou.dashboard.web.controller.jmsg.JmsgController")
	        || message.getSendFromClass().equals("com.xiaodou.logmonitor.bolt.JmsgMessageBolt")||
	        message.getSendFromClass().equals("com.xiaodou.dashboard.job.SyncJmsgMessageJob")) {}
	    else if (savedMessageBody != null && savedMessageBody.getProcessStatus().equals("2")) {
	      // 存在MessageBody且处理完成则重复,不再处理
	      return;
	    }
	    long checkRepeatTime = System.currentTimeMillis();
	    if (sb != null) {
	      sb.append(String.format("CheckRepeatMessage:%d", checkRepeatTime - checkConfigTime));
	    }
	    // 2.保存数据初步处理
	    save(message, sb, mbc, savedMessageBody, checkConfigTime, receiveTime);
	  }

	  public void save(com.xiaodou.jmsg.entity.DefaultMessage message, StringBuilder sb,
	      MessageConfig mbc, MessageBody savedMessageBody, long checkConfigTime, Date receiveTime) {
	    // 首次接收的消息，保存消息体.
		System.out.println((message.getCustomTag()+"阶段4:"+new Date().toString()));
	    if (!message.isSaved() && savedMessageBody == null) {
	      if (!MessageProcessService.createMessageBody(message, message.getQueueName())) {
	        return;
	      }
	    }
	    System.out.println((message.getCustomTag()+"阶段5:"+new Date().toString()));
	    long saveMessageTime = System.currentTimeMillis();
	    if (sb != null) {
	      sb.append(String.format("Save body:%d|", saveMessageTime - checkConfigTime));
	    }

	    // 首次接受的消息；初始化ConsumerExecutedResultDic，ConsumerTypeFullNameConsumerStrDic
	    if (message.getProcessCount() <= 0) {
	      // 首次接收的消息，所有consumer均未执行，相当于全都是失败
	      HashMap<String, Boolean> resultDic = new HashMap<>();
	      for (int i = 0; i < mbc.consumers.size(); i++) {
	        resultDic.put(mbc.consumers.get(i).getUrl(), false);
	      }
	      message.setConsumerExcutedResultDic(resultDic);
	    }

	    MessageProcessService.setMessageBeginProcessStatus(message);
	    System.out.println((message.getCustomTag()+"阶段6:"+new Date().toString()));
	    // 标识开始处理
	    // 3.处理以及异常处理
	    handle(message, sb, mbc, savedMessageBody, checkConfigTime, receiveTime);
	  }

	  public void handle(com.xiaodou.jmsg.entity.DefaultMessage message, StringBuilder sb,
	      MessageConfig mbc, MessageBody savedMessageBody, long checkConfigTime, Date receiveTime) {
	    // 3 将消息发送到MessageBus进行广播
	    Date beginProcessTime = new Date();
	    long beforeBroadCast = System.currentTimeMillis();
	    System.out.print((message.getCustomTag()+"阶段7:"+new Date().toString()));
	    BroadcastResponse response = MessageBusService.broadcast(message, mbc,false);
	    System.out.print((message.getCustomTag()+"阶段8:"+new Date().toString()));

	    long afterBroadCast = System.currentTimeMillis();

	    if (sb != null) {
	      sb.append(String.format("Msg Broadcast:%d|", afterBroadCast - beforeBroadCast));
	    }
	    // 5 消息失败时，当消息没有达到最大失败次数时，进行重试；达到则存档

	    int result = 0;
	    int processStatus = 1;
	    if (response.isFailedOrError()) {
	     /* if (message.getFailedCount() != null
	          && Integer.valueOf(message.getFailedCount()) >= mbc.getMaxRetryCount()) {*/
	        // 达到最大失败次数
	        if (response.isError()) {
	          result = -1;
	        } else {
	          result = 1;
	        }
	        LoggerUtil.alarmInfo(new JmsgAlarm("错误消息:"+message.getCustomTag()));
	        // 将消息处理状态改为“已处理”
	        processStatus = 2;
	      /*} else {
	        if (response.isError()) {
	          result = -1;
	        } else {
	          result = 1;
	        }
	        // 未达到最大失败次数
	        MessageProcessService.ProcessFailedLessMax(message);
	      }*/
	    } else {
	      // 将消息处理状态改为“已处理”
	      processStatus = 2;
	    }
	    
	    
	    //错误消息处理
	    System.out.println((message.getCustomTag()+"阶段9:"+new Date().toString()));
	    JmsgServerSqliteServiceFacade jmsgServerSqliteServiceFacade = SpringWebContextHolder.getBean("jmsgServerSqliteServiceFacade");
	    String customTag=message.getCustomTag();
	    try{
		    if(result==0||result==1){	
		    	if(StringUtils.isNotBlank(customTag)){
		    		jmsgServerSqliteServiceFacade.delFailMessage(customTag);
		    	}
		    }else if(result==-1){
			    if(uuidMap.get(customTag)!=null&&message.getFailedCount() != null){
			    	FailMessage fm=jmsgServerSqliteServiceFacade.getFailMessageById(customTag);
			    	if(fm==null){
				    	String url=uuidMap.get(customTag);		    
				    	FailJmsgFilter jmsgFilter=failMap.get(url);
				    	if(jmsgFilter==null){	    		
				    		jmsgFilter=new FailJmsgFilter();
				    		jmsgFilter.setFirstFailTime(System.currentTimeMillis());
				    	}
		    		    jmsgFilter.AddUrlFailCount();
		    		    failMap.put(url,jmsgFilter);	
		    		    FailMessage failMessage=new FailMessage();
		    		    failMessage.setMessage(FastJsonUtil.toJson(message));
		    		    failMessage.setUniqueUrl(url);
		    		    failMessage.setCustomTag(customTag);
		    		    failMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
		    		    failMessage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		    		    //jmsgServerSqliteServiceFacade.addFailMessage(failMessage);
		    		    uuidMap.remove(customTag);
			    	}
			    }
		    }
	    }catch(Exception e){
	    	LoggerUtil.error("jmsgServerSqlite报错:", e);
	    }
	    System.out.println((message.getCustomTag()+"阶段10:"+new Date().toString()));
	    MessageProcessService.setMessageResult(message, processStatus, result);
	    System.out.print((message.getCustomTag()+"阶段11:"+new Date().toString()));
	    long recordResultTime = System.currentTimeMillis();
	    if (sb != null) {
	      sb.append(String.format("ProcessResult:%d\r\n", recordResultTime - afterBroadCast));
	    }

	    // 记录消息执行结果
	    MessageProcessService.saveMessageLog(message, beginProcessTime, receiveTime, afterBroadCast
	        - beforeBroadCast, response);
	    System.out.print((message.getCustomTag()+"阶段12:"+new Date().toString()));
	    long recordMessageLogTime = System.currentTimeMillis();
	    if (sb != null) {
	      sb.append(String.format("Log:%d|", recordMessageLogTime - recordResultTime));
	    }

	  }

}
