package com.xiaodou.control.service.mail;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.control.prop.MailProp;
import com.xiaodou.control.queue.QueueService;
import com.xiaodou.control.vo.SendInfoVo;

@Service("mailService")
public class MailService {
	@Resource
	QueueService queueService;

	/**
	 * 发邮件
	 * 
	 * @param action
	 * @param failCount
	 */
	public void sendMail(List <BaseNodeModel> baseNodeList) {
		SendInfoVo infovo = new SendInfoVo();
		infovo.setName("警告:AOS部分机器失去连接");	
		StringBuffer mailData=new StringBuffer("<table border='8'>");
		mailData.append("<tr><th>mac</th><th>ip</th><th>别名</th><th>上次连接时间</th>");
		for(BaseNodeModel baseNode:baseNodeList){	
			mailData.append("<tr>");
			mailData.append("<td>"+baseNode.getMac()+"</td>");
			mailData.append("<td>"+baseNode.getIp()+"</td>");
			mailData.append("<td>"+baseNode.getAlias()+"</td>");
			mailData.append("<td>"+baseNode.getTime()+"</td>");
			mailData.append("</tr>");
		}
		mailData.append("</table>");
		infovo.setMailInfo(mailData.toString());
		infovo.setMail(MailProp.getParams("recipient"));
		infovo.setCreateDate(new Date());
		queueService.sendMail(infovo);
	}
}
