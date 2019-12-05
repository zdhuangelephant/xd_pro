package com.xiaodou.jmsg.server.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.StatusLine;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSONObject;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.jmsg.model.MessageConfig;
import com.xiaodou.jmsg.model.MessageConsumersConfig;
import com.xiaodou.jmsg.server.broadcastresponse.BroadcastResponse;
import com.xiaodou.jmsg.server.broadcastresponse.BroadcastResponseItem;
import com.xiaodou.jmsg.server.constant.InitConstant;
import com.xiaodou.jmsg.server.prpo.ConfigProp;
import com.xiaodou.jmsg.server.prpo.JmsgServerSqliteProp;
import com.xiaodou.jmsg.server.vo.FailJmsgFilter;

public class MessageBusService {
	/**
	 * 消息包装-发送-错误处理
	 * 
	 * @return
	 */
	public static BroadcastResponse broadcast(DefaultMessage message,
			MessageConfig config,boolean isAuto) {
		message.setProcessCount(message.getProcessCount() + 1);
		BroadcastResponse response = new BroadcastResponse();
		List<MessageConsumersConfig> consumers = config.consumers;
		if (consumers == null) {
			response.setError(true);
		} else {
			for (MessageConsumersConfig consumer : consumers) {
				if (consumer != null) {
					if (!message.getConsumerExcutedResultDic().containsKey(
							consumer.getUrl())
							|| !message.getConsumerExcutedResultDic().get(
									consumer.getUrl())) {
						processMessage(message, config, response, consumer);
					}
				}
			}
		}

		if (response.isFailedOrError()) {
			if (message.getFailedCount() != null) {
				Integer count = Integer.valueOf(message.getFailedCount()) + 1;
				message.setFailedCount(count.toString());
			} else {
				message.setFailedCount("1");
			}
		}

		return response;
	}

	/**
	 * 消息发送-错误处理
	 * 
	 * @return
	 */
	private static void processMessage(DefaultMessage message,
			MessageConfig config, BroadcastResponse response,
			MessageConsumersConfig consumer) {

		String url = consumer.getUrl();
		if (MessageServerService.failMap.get(url) != null) {
			if ((System.currentTimeMillis() - MessageServerService.failMap.get(url).getFirstFailTime()) > InitConstant.overTime) {
				MessageServerService.failMap.get(url).setFirstFailTime(System.currentTimeMillis());
				MessageServerService.failMap.get(url).setUrlFailCount(0);
			}
		}
		HttpClient httpclient = new HttpClient();
		httpclient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(consumer.getTimeOut());
		httpclient.getHttpConnectionManager().getParams()
				.setSoTimeout(consumer.getTimeOut());
		PostMethod postMethod = new PostMethod(url);
		BroadcastResponseItem resItem = new BroadcastResponseItem();
		resItem.setUrl(url);
		String str = "";
		HttpResult httpResult = new HttpResult();
		NameValuePair[] data = {
				new NameValuePair("message", message.getTransferObjectJSON()),
				new NameValuePair("messageName", message.getMessageName()),
/*				new NameValuePair("traceId", message.getTraceId()),
				new NameValuePair("myId", message.getMyId()),*/
				new NameValuePair("tag", message.getCustomTag()), };
		httpResult.setStartTime(System.currentTimeMillis());
		
		try {
			if (MessageServerService.failMap.get(url) != null&& MessageServerService.failMap.get(url).getUrlFailCount() > InitConstant.maxFailCount) {
				throw new Exception();
			}
			postMethod.setRequestBody(data);
			postMethod.addRequestHeader("Accept",
					"application/json;charset=UTF-8");
			postMethod.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			long startTime = System.currentTimeMillis();
			httpclient.executeMethod(postMethod);
			long endTime = System.currentTimeMillis();
			StatusLine status = postMethod.getStatusLine();
			Integer code = status.getStatusCode();
			InputStream resStream = postMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					resStream));
			StringBuilder strBuilder = new StringBuilder();
			String resTemp;
			while ((resTemp = br.readLine()) != null) {
				strBuilder.append(resTemp);
			}
			str = strBuilder.toString();
			resItem.setResponse(str);
			JSONObject result = JSONObject.parseObject(str);
			boolean success = true;
			httpResult.setEndTime(System.currentTimeMillis());
			httpResult.setContent(str);
			if (code < 200 || code >= 300 || result.isEmpty()) {
				resItem.setError(true);
				resItem.setFailed(false);
				resItem.setInterval((int) (endTime - startTime));
				resItem.setExceptionMsg("MessageConsumer return null!");
				resItem.setResponseCode(-1);
				success = false;
			} else {
				if (result.getIntValue("responseCode") < 0) {
					response.setError(true);
					success = false;
				} else if (result.getIntValue("responseCode") > 0) {
					response.setFailed(true);
					success = false;
				}

				resItem.setError(result.getIntValue("responseCode") < 0);
				resItem.setFailed(result.getIntValue("responseCode") > 0);
				resItem.setResponseCode(result.getIntValue("responseCode"));
				resItem.setInterval((int) (endTime - startTime));
				if (result.getString("exceptionMessage") != null) {
					resItem.setExceptionMsg(result
							.getString("exceptionMessage"));
				}
			}
			if (success) {
				message.getConsumerExcutedResultDic().put(consumer.getUrl(),
						true);
			}
		} catch (Exception e) {
			response.setError(true);
			resItem.setError(true);
			resItem.setFailed(false);
			resItem.setInterval(0);
			resItem.setResponseCode(-1);
			resItem.setExceptionMsg(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		InOutEntity msg = new InOutEntity();
		msg.setTargetUrl(url);
		msg.setParams(FastJsonUtil.toJson(data));
		msg.setResult(httpResult);
		LoggerUtil.inOutInfo(msg);
		if(response.isError()){
			if (MessageServerService.failMap.get(url) != null) {
				MessageServerService.failMap.get(url).setUrlFailCount(MessageServerService.failMap.get(url).getUrlFailCount()+1);
			}
			MessageServerService.uuidMap.put(message.getCustomTag(), url);
		}
		response.addResponseItem(resItem);
	}

	public static void createTable(){
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName(JmsgServerSqliteProp.getParams("sqlite_jdbc.driverClassName"));
	      String url=JmsgServerSqliteProp.getParams("sqlite_jdbc.url")+ConfigProp.getParams("config.projectName")+JmsgServerSqliteProp.getParams("sqlite_jdbc.dbName");
	      c = DriverManager.getConnection(url);
	      stmt = c.createStatement();
	      String sql = "create table fail_message(custom_tag varchar(30) PRIMARY KEY,message varchar(50000),unique_url varchar(500),message_state varchar(10),create_time timestamp,update_time timestamp);";
	      stmt.executeUpdate(sql);
	      sql ="create index custom_index on fail_message(custom_tag);";
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch (Exception e) {
	    	LoggerUtil.error("建表失败", e);
	    }
	}
}
