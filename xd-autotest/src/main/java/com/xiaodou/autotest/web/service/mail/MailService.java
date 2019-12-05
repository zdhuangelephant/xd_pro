package com.xiaodou.autotest.web.service.mail;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.httpclient.NameValuePair;
import org.springframework.stereotype.Service;

import com.xiaodou.autotest.core.ActionEnum.ApiStatus;
import com.xiaodou.autotest.core.ActionEnum.DataFormat;
import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.autotest.core.model.TestCase;
import com.xiaodou.autotest.web.prpo.MailProp;
import com.xiaodou.autotest.web.service.queue.QueueService;
import com.xiaodou.autotest.web.vo.SendInfoVo;

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
	public void sendMail(Action action,Integer failCount) {
		SendInfoVo infovo = new SendInfoVo();
		infovo.setName(action.getName());	
		infovo.setCount(action.getApiList().size());
		StringBuffer mailData=new StringBuffer();
		mailData.append("<div>");
		mailData.append("<ul>");
		int j=0;
		for(Api api:action.getApiList()){
			j++;
			if(api.getStatus()==ApiStatus.UnExcute){
				mailData.append("<a style='display: block;' herf='#"+api.getUniqueId()+"'><h2  style='color:red;'>"+j+":"+api.getName()+"</h2></a><br>");
		    }else{
		    	if(!api.getApiResult().getHasError()){
		    		mailData.append("<a style='display: block;' herf='#"+api.getUniqueId()+"'><h2  style='color:green;'>"+j+":"+api.getName()+"</h2></a><br>");
		    	}else{
		    		mailData.append("<a style='display: block;' herf='#"+api.getUniqueId()+"'><h2  style='color:red;'>"+j+":"+api.getName()+"</h2></a><br>");
		    	}
		    }		
			
		}
		mailData.append("<hr/>");
		int i=0;
		for(Api api:action.getApiList()){
			i++;
			if(api.getStatus()==ApiStatus.UnExcute){
		    	mailData.append("<a id='"+api.getUniqueId()+"'><h2  style='color:red;'>"+i+":"+api.getName()+"</h2></a><li><span class='pass-method'><strong>method:</strong>"+api.getMethod()+"</span><br><span><strong>请求地址:</strong>"+api.getUrl()+"</span><br><span><strong>状态:</strong>UnExcute</span></li>");
		    }else{
		    	if(!api.getApiResult().getHasError()){
		    		mailData.append("<a id='"+api.getUniqueId()+"'><h2  style='color:green;'>"+i+":"+api.getName()+"</h2></a><li><span><strong>method:</strong>"+api.getMethod()+"</span><br><span><strong>请求地址:</strong>"+api.getUrl()+"</span><br><span><strong>HTTP状态码:</strong>"+api.getApiResult().getHttpStatus() + "OK<br><strong>耗时:</strong>:"+api.getApiResult().getCostTime() + "ms</span></li>");
		    	}else{
		    		mailData.append("<a id='"+api.getUniqueId()+"'><h2  style='color:red;'>"+i+":"+api.getName()+"</h2></a><li><span class='pass-method'><strong>method:</strong>"+api.getMethod()+"</span><br><span><strong>请求地址:</strong>"+api.getUrl()+"</span><br><span><strong>HTTP状态码:</strong>"+api.getApiResult().getHttpStatus()+ "OK <br><strong>耗时:</strong>:"+api.getApiResult().getCostTime()+ "ms</span></li>");
		    	}
		    }		
		    //requestHeader
		    Map<String,String> requestHeaderMap=api.getApiResult().getHeader();
		    if(requestHeaderMap.size()>0){
		    	mailData.append("<li><p>Request Headers</p>");
		    	mailData.append("<table border=2'><tr><th style='width: 20%;'>参数名称</th><th style='width: 80%;'>参数值</th></tr>");
		    	for(String key:requestHeaderMap.keySet()){
					  mailData.append("<tr><td>"+key+"</td><td>"+requestHeaderMap.get(key)+"</td></tr>");
				}
				mailData.append("</table></li>"); 
		    }
		    
		    
		  //requestBody
		    DataFormat requestBodyFormat=api.getApiRequest().getFormat();
		    if(requestBodyFormat==DataFormat.jSon){
		    	String requestBody=(String) api.getApiRequest().getBody();
		    	if(requestBody!=null){
		    		mailData.append("<li><p>Request Body</p>");
		    		mailData.append("<div class='out-param'><div style='padding:15px0;width:100%;overflow:hidden;'></div><textarea style='width: 100%;border: 1px solid #3883fa;resize: none;min-height: 100px;overflow: hidden !important;' id='outParamTextarea'>"+requestBody+"</textarea></div>");
		   		}
		    }else{
		    	@SuppressWarnings("unchecked")
				List <NameValuePair> requestBodyList =(List<NameValuePair>) api.getApiRequest().getBody();
		    	if(requestBodyList.size()>0){
		    		mailData.append("<li><p>Request Body</p>");
			    	mailData.append("<table border=2'><tr><th style='width: 20%;'>参数名称</th><th style='width: 80%;'>参数值</th></tr>");		
			    	for(NameValuePair requestBody:requestBodyList){
			    		mailData.append("<tr><td>"+requestBody.getName()+"</td><td>"+requestBody.getValue()+"</td></tr>");		
			    	}
			    	mailData.append("</table></li>"); 
		    	}
		    }  
		  //responseHeader
		    if(api.getStatus()==ApiStatus.Excuted){
		    	Map<String,String> responseHeaderMap=api.getApiResult().getHeader();
		    	if(responseHeaderMap.size()>0){
				    	mailData.append("<li><p>Response Header</p>");
				    	mailData.append("<table border=2'><tr><th style='width: 20%;'>参数名称</th><th style='width: 80%;'>参数值</th></tr>");
				     	for(String key:responseHeaderMap.keySet()){
							  mailData.append("<tr><td>"+key+"</td><td>"+responseHeaderMap.get(key)+"</td></tr>");
						}
				     	mailData.append("</table></li>");
		    		}
		    	}
		    	
		    	//responseBody
		    	String responseBody=api.getApiResult().getSrcResult();
		    	if(responseBody!=null){
		    		mailData.append("<li><p>Response Body</p>");
		    		mailData.append("<div class='out-param'><div style='padding:15px0;width:100%;overflow:hidden;'></div><textarea style='width: 100%;border: 1px solid #3883fa;resize: none;min-height: 100px;overflow: hidden !important;' id='outParamTextarea'>"+responseBody+"</textarea></div>");
				}
				
				//testCase
				if(!api.getApiResult().getHasError()){ 
					LinkedList<TestCase> testCaseList=api.getTestCases();
					if(testCaseList.size()>0){
						mailData.append("<li><p>TestCase</p>");
						mailData.append("<table border=2'><tr><th style='width: 20%;'>用例名称</th><th style='width: 80%;'>执行结果</th></tr>");
						for(TestCase t:testCaseList){
							mailData.append("<tr><td>"+t.getName()+"</td><td>"+t.getTestResult()+"</td></tr>");
						}
						mailData.append("</table></li>");
					}
				}
				mailData.append("<hr/>");
		}	
		mailData.append("</ul></div>");
		infovo.setMailInfo(mailData.toString());
		infovo.setMail(MailProp.getParams("recipient"));
		infovo.setFailCount(failCount);
		infovo.setCreateDate(new Date());
		queueService.sendMail(infovo);
	}
}
