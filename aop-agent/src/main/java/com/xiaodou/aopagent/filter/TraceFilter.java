package com.xiaodou.aopagent.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.xiaodou.aopagent.util.TraceWrapper;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * 拦截赋值
 * 
 * @author zhouhuan
 * 
 */
public class TraceFilter implements Filter {

	public static enum TraceEnum {
		/** traceId 会话标识符 */
		traceId,
		/** projectId 程序标识符 */
		projectId,
		/** processId 进程标识符 */
		processId,
		/** executionTime 执行时间 */
		executionTime,
		/** personalityData 个性化数据 */
		personalityData,
		/**  父类ID */
		parentId,
		/** myId 当前ID */
		myId,
	    /** clientType 设备类型 */
	    clientType
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		// TODO 1、入口 解密所有请求
		TraceModel traceModel = null;
		PrintWriter out = null;
		try {
			// 初始化登录参数
			traceModel = new TraceModel(req);
			TraceWrapper.getWrapper().setTraceParam(traceModel);
		} catch (Exception e) {
			LoggerUtil.error("请求入参获取异常", e);
			out = response.getWriter();
			out.print("{\"retCode\":\"-1\",\"retInfo\":\"Get The HTTP request parameter exception..\"}");
			return;
		}
		chain.doFilter(request, response);
	}

	

	@Override
	public void destroy() {

	}

	/**
	 * @name TraceModel CopyRright (c) 2015 by <a
	 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
	 * @date 2018年4月24日
	 * @description 业务流程模型类
	 * @version 1.0
	 * @waste
	 */
	public static class TraceModel {
		public TraceModel() {
		}

		public TraceModel(HttpServletRequest req) {
			if(req.getParameter("traceId")!=null&&req.getParameter("traceId").length()>0){
				traceId=req.getParameter("traceId");
			}else{
				traceId = buildTraceId(req.getHeader(TraceEnum.clientType.toString()), req.getHeader(TraceEnum.traceId.toString()));
			}
		    processId = req.getHeader(TraceEnum.processId.toString());  
			projectId = "0";
		    if (StringUtils.isBlank(processId)) {
		    	processId = "0";
		    }
		    if(req.getParameter("myId")!=null&&req.getParameter("myId").length()>0){
				myId=req.getParameter("myId");
			}
		}

		private String buildTraceId(String clientType, String traceId) {
			if (StringUtils.isBlank(clientType))
				clientType = "server";
			if (StringUtils.isBlank(traceId))
				traceId = UUID.randomUUID().toString();
			return String.format("%s-%s", clientType, traceId);
		}

		/** traceId 会话标识符 */
		private String traceId;
		/** projectId 程序标识符 */
		private String projectId;
		/** processId 进程标识符 */
		private String processId;
		/** executionTime 执行时间 */
		private String executionTime;
		/** personalityData 个性化数据 */
		private String personalityData;
		private String parentId;
		private String myId;
		public String getTraceId() {
			return traceId;
		}

		public void setTraceId(String traceId) {
			this.traceId = traceId;
		}

		public String getProjectId() {
			return projectId;
		}

		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}

		public String getProcessId() {
			return processId;
		}

		public void setProcessId(String processId) {
			this.processId = processId;
		}

		public String getExecutionTime() {
			return executionTime;
		}

		public void setExecutionTime(String executionTime) {
			this.executionTime = executionTime;
		}

		public String getPersonalityData() {
			return personalityData;
		}

		public void setPersonalityData(String personalityData) {
			this.personalityData = personalityData;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public String getMyId() {
			return myId;
		}

		public void setMyId(String myId) {
			this.myId = myId;
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}
}
