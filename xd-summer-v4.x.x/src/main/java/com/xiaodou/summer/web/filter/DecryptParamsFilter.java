package com.xiaodou.summer.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.RSAUtils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

public class DecryptParamsFilter implements Filter {

	public class RequestDecrypt extends HttpServletRequestWrapper {
		private final HttpServletRequest rawRequest;

		public RequestDecrypt(HttpServletRequest rawRequest) {
			super(rawRequest);
			setRequest(rawRequest);
			this.rawRequest = rawRequest;
		}

		@Override
		public String getContextPath() {
			return rawRequest.getContextPath();
		}

		/**
		 * encrypted the value.
		 */
		@Override
		public String getParameter(String name) {
			String param = rawRequest.getParameter(name);
			if (StringUtils.isBlank(param))
				return param;
			String requestUrl = rawRequest.getRequestURI();
			if (StringUtils.isBlank(requestUrl))
				requestUrl = StringUtils.EMPTY;

			if (StringUtils.isNotBlank(ConfigProp.getParams(String.format(
					"req.data.ignore.decrypt.%s", requestUrl)))
					|| !"ON".equals(ConfigProp
							.getParams("req.data.need.decrypt")))
				return param;
			try {
				byte[] reqData = Base64Utils.decode(param);
				String privateKey = ConfigProp
						.getParams("req.data.private.key");
				byte[] res = RSAUtils.decryptByPrivateKey(reqData, privateKey);
				return new String(res, "utf-8");
			} catch (Exception e) {
				LoggerUtil.error("参数解密失败", e);
			}
			return null;
		}

		@Override
		public Object getAttribute(String name) {
			return rawRequest.getAttribute(name);
		}

		@Override
		public ServletRequest getRequest() {
			return rawRequest;
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequestWrapper quoted = new RequestDecrypt(
				(HttpServletRequest) request);
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		chain.doFilter(quoted, httpResponse);
	}

	@Override
	public void destroy() {
	}

}
