package com.xiaodou.control.service.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Created by zyp on 14-9-4.
 */
public class IpTokenBasedRememberMeServices extends
		TokenBasedRememberMeServices {

	private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

	public HttpServletRequest getContext() {
		return requestHolder.get();
	}

	public void setContext(HttpServletRequest httpServletRequest) {
		requestHolder.set(httpServletRequest);
	}

	/**
	 * 用户登录时cookie的处理
	 * 
	 * @param request
	 * @param response
	 * @param successfulAuthentication
	 */
	@Override
	public void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication successfulAuthentication) {
		try {
			requestHolder.set(request);
			super.onLoginSuccess(request, response, successfulAuthentication);
		} finally {
			setContext(null);
		}
	}

	/**
	 * 生成remeberMe cookie值
	 * 
	 * @param tokenExpiryTime
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public String makeTokenSignature(long tokenExpiryTime, String username,
			String password) {
		return DigestUtils
				.md5DigestAsHex((username + ":" + tokenExpiryTime + ":"
						+ password + ":" + getKey() + ":" + getUserIpAddress(getContext()))
						.getBytes());
	}

	/**
	 * 设置cookie
	 * 
	 * @param tokens
	 * @param maxAge
	 * @param request
	 * @param response
	 */
	@Override
	protected void setCookie(String[] tokens, int maxAge,
			HttpServletRequest request, HttpServletResponse response) {
		String[] tokensWithIpAddress = Arrays.copyOf(tokens, tokens.length + 1);
		tokensWithIpAddress[tokensWithIpAddress.length - 1] = getUserIpAddress(request);
		super.setCookie(tokensWithIpAddress, maxAge, request, response);
	}

	/**
	 * 获取用户的Ip地址
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	private String getUserIpAddress(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteAddr();
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			setContext(request);
			String ipAddressToken = cookieTokens[cookieTokens.length - 1];
			if (!getUserIpAddress(request).equals(ipAddressToken)) {
				throw new InvalidCookieException(
						"cookie IP Address did not contain a matching ip(contained '"
								+ ipAddressToken + "')");
			}
			return super.processAutoLoginCookie(
					Arrays.copyOf(cookieTokens, cookieTokens.length - 1),
					request, response);
		} finally {
			setContext(null);
		}
	}

}
