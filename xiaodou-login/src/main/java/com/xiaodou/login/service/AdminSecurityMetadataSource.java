package com.xiaodou.login.service;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.*;

/**
 * Created by zhouhuan on 17.08.15.
 */
public class AdminSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	/**
	 * 构造函数初始化
	 */
	public AdminSecurityMetadataSource() {
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 动态的获取资源
	 * 
	 * @param object
	 * @return
	 * @throws IllegalArgumentException
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		((FilterInvocation) object).getRequestUrl();
		Collection<ConfigAttribute> returnCollection = new ArrayList<ConfigAttribute>();
		returnCollection.add(new SecurityConfig("permitAll"));
		return returnCollection;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
