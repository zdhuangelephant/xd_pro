package com.xiaodou.ms.service.admin;

import com.xiaodou.ms.model.admin.Privilege;
import com.xiaodou.ms.model.admin.Role;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.*;

/**
 * Created by zyp on 14-9-3.
 */
public class AdminSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

  private AdminPrivilegeService adminPrivilegeService;

  private static Map<String, Collection<ConfigAttribute>> privilegeMap = null;

  /**
   * 构造函数初始化
   */
  public AdminSecurityMetadataSource(AdminPrivilegeService adminPrivilegeService) {
    this.adminPrivilegeService = adminPrivilegeService;
    this.loadPrivilegeDefine();
  }

  public void loadPrivilegeDefine() {
    //if (privilegeMap == null) {
      privilegeMap = new HashMap<String, Collection<ConfigAttribute>>();
      List<Privilege> privilegeList = this.adminPrivilegeService.queryAllPrivileges();
      for (Privilege privilege : privilegeList) {
        Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
        //获取本权限关联的全部角色
        List<Role> roles =
          this.adminPrivilegeService.queryRolesByPrivilege(privilege.getId());
        for (Role role : roles) {
          ConfigAttribute configAttribute = new SecurityConfig(role.getRoleName());
          configAttributes.add(configAttribute);
          privilegeMap.put(privilege.getUrl(), configAttributes);
        }
      }
    //}
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
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    //获得请求的URL地址
    String requestUrl = ((FilterInvocation) object).getRequestUrl();
    if (privilegeMap == null) {
      this.loadPrivilegeDefine();
    }
    //动态资源管理
    if (privilegeMap.get(requestUrl) == null) {
      Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
      Privilege privilege = this.adminPrivilegeService.queryPrivilegesByUrl(requestUrl);
      if (privilege != null) {
        List<Role> roles =
          this.adminPrivilegeService.queryRolesByPrivilege(privilege.getId());
        for (Role role : roles) {
          ConfigAttribute configAttribute = new SecurityConfig(role.getRoleName());
          configAttributes.add(configAttribute);
          privilegeMap.put(privilege.getUrl(), configAttributes);
        }
      }
    }
    //如果没有配置本url 那么除去匿名用户外都可以访问
    if (privilegeMap.get(requestUrl) == null) {
      Collection<ConfigAttribute> returnCollection = new ArrayList<ConfigAttribute>();
      returnCollection.add(new SecurityConfig("permitAll"));
      return returnCollection;
    } else {
      return privilegeMap.get(requestUrl);
    }
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
