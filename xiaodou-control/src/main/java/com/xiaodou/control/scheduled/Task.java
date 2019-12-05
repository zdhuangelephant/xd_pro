package com.xiaodou.control.scheduled;

import java.sql.Timestamp;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.mail.MailService;
import com.xiaodou.control.util.DingDingMessageUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;
/**
 * @name @see com.xiaodou.jmsg.server.scheduled.Task.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 定时任务检测服务器连接状态
 * @version 1.0
 */
public class Task {
	public static void excute() {
		LoggerUtil.debug("定时检测HOST信息");
		checkNode();
	}
	
	public static void checkNode(){
		/*try{
		List<BaseNodeModel> failNodeList=Lists.newArrayList();
		MongoDbServiceFacade mongoDbServiceFacade =
	            SpringWebContextHolder.getBean("mongoDbServiceFacadeImpl");
		MailService mailService =
	            SpringWebContextHolder.getBean("mailService");
		List <BaseNodeModel> baseNodeList=mongoDbServiceFacade.getBaseNodeList(null);
		String failNode="";
		for(BaseNodeModel baseNode:baseNodeList){  
			if(baseNode.getTime()==null){
				failNodeList.add(baseNode);
				failNode+="   [mac:"+baseNode.getMac()+" alias:"+baseNode.getAlias()+" ip:"+baseNode.getIp()+" 上次连接时间:无]";
			}else{
				Timestamp ts=Timestamp.valueOf(baseNode.getTime());	
				if(System.currentTimeMillis()-ts.getTime()>600000){
					failNodeList.add(baseNode);
					failNode+="   [mac:"+baseNode.getMac()+" alias:"+baseNode.getAlias()+" ip:"+baseNode.getIp()+" 上次连接时间:"+baseNode.getTime()+"]";
				}
			}
		}
		if(failNodeList.size()>0){
			mailService.sendMail(failNodeList);	
			DingDingMessageUtil.sendMessage("[警告]-[以下是系统失去连接的主机]:"+failNode);
			
		}}catch(Exception e) {
			LoggerUtil.error("定时检测HOST信息失败",e);
		}*/
	}
	
}
