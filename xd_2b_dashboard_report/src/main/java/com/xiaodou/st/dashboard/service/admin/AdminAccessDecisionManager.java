package com.xiaodou.st.dashboard.service.admin;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by zyp on 14-9-3.
 * <p/>
 * 投票器
 */
public class AdminAccessDecisionManager implements AccessDecisionManager {

  @Override
  public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
    if (configAttributes == null) {
      throw new AccessDeniedException("没有权限访问!");
    }
    Boolean isAnonymous = false;
    //判断是否为匿名用户
    if (authentication instanceof AnonymousAuthenticationToken) {
      isAnonymous = true;
    }
    Iterator<ConfigAttribute> iterator = configAttributes.iterator();
    while (iterator.hasNext()) {
      ConfigAttribute configAttribute = iterator.next();
      //所需要的角色
      String needPermission = ((SecurityConfig) configAttribute).getAttribute();
      //如果不是匿名用户并且本权限没有配置
      if (needPermission.equals("permitAll")) {
        if (isAnonymous) {
          throw new AccessDeniedException("没有权限访问!");
        } else {
          return;
        }
      }
      //拥有的角色和所需的角色的对比  超级管理员直接放过
      for (GrantedAuthority ga : authentication.getAuthorities()) {
        if (needPermission.equals(ga.getAuthority()) || ga.getAuthority().equals("超级管理员")) {
          return;
        }
      }
    }
    throw new AccessDeniedException("没有权限访问!");
  }



  @Override
  public boolean supports(ConfigAttribute configAttribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}
