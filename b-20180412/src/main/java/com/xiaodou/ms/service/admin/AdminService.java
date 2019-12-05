package com.xiaodou.ms.service.admin;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.dao.admin.AdminRoleDao;
import com.xiaodou.ms.dao.admin.AdminDao;
import com.xiaodou.ms.model.admin.Admin;
import com.xiaodou.ms.model.admin.AdminRole;
import com.xiaodou.ms.model.admin.Role;
import com.xiaodou.ms.util.RandomUtils;
import com.xiaodou.summer.dao.pagination.Page;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyp on 14-9-2.
 */
public class AdminService {

  private AdminDao adminDao;

  private AdminRoleDao adminRoleDao;

  private AdminRoleService adminRoleService;

  public AdminDao getAdminDao() {
    return adminDao;
  }

  public void setAdminDao(AdminDao adminDao) {
    this.adminDao = adminDao;
  }

  public AdminRoleDao getAdminRoleDao() {
    return adminRoleDao;
  }

  public void setAdminRoleDao(AdminRoleDao adminRoleDao) {
    this.adminRoleDao = adminRoleDao;
  }

  public AdminRoleService getAdminRoleService() {
    return adminRoleService;
  }

  public void setAdminRoleService(AdminRoleService adminRoleService) {
    this.adminRoleService = adminRoleService;
  }

  /**
   * 增加管理员
   *
   * @param admin
   */
  public void addAdmin(Admin admin) {
    String salt = RandomUtils.generateString(6);
    admin.setSalt(salt);
    Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    String password = md5PasswordEncoder.encodePassword(admin.getPassword(), salt);
    admin.setPassword(password);
    this.adminDao.addEntity(admin);
  }

  /**
   * 删除管理员
   *
   * @param adminId
   */
  public Boolean deleteAdmin(Integer adminId) {
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",adminId);
    Boolean result = this.adminDao.deleteEntity(cond);
    this.deleteAdminRolesReltion(adminId);
    return result;
  }

  /**
   * 更新管理员
   *
   * @param admin
   */
  public Boolean updateAdmin(Admin admin) {
    //判断密码是否为空
    if (StringUtils.isNotBlank(admin.getPassword())) {
      String salt = RandomUtils.generateString(6);
      admin.setSalt(salt);
      Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
      String password = md5PasswordEncoder.encodePassword(admin.getPassword(), salt);
      admin.setPassword(password);
    } else {
      admin.setSalt(null);
    }
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",admin.getId());
    return this.adminDao.updateEntity(cond,admin);
  }

  /**
   * 查询管理员
   *
   * @param cond
   */
  public List<Admin> queryAdmin(Map<String, Object> cond) {
    Map<String,Object> output = new HashMap<>();
    return this.adminDao.queryListByCondWithOutPage(cond, output).getResult();
  }

  /**
   * 查询管理员
   *
   * @param adminId
   */
  public Admin findAdminById(Integer adminId) {
    Admin admin = new Admin();
    admin.setId(adminId);
    return this.adminDao.findEntityById(admin);
  }

  /**
   * 根据用户名查找用户
   *
   * @param userName
   * @return
   */
  public Admin findAdminByUserName(String userName) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("userName", userName);
    List<Admin> admins = this.queryAdmin(cond);
    if (admins.size() == 0) {
      return null;
    } else {
      return admins.get(0);
    }
  }

  /**
   * 按照管理员Id删除关系
   *
   * @param adminId
   */
  public void deleteAdminRolesReltion(Integer adminId) {
    Map<String,Object> cond = new HashMap<>();
    cond.put("adminId",adminId);
    this.adminRoleDao.deleteEntity(cond);
  }

  /**
   * 查询管理员的角色列表
   *
   * @param adminId
   * @return
   */
  public List<Role> queryAdminRoles(Integer adminId) {
    List<Role> roles = new ArrayList<Role>();
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("adminId", adminId);
    Map<String,Object> output = new HashMap<>();
    Page<AdminRole> adminAdminRoles = this.adminRoleDao.queryListByCondWithOutPage(cond, output);
    for (AdminRole adminRole : adminAdminRoles.getResult()) {
      roles.add(this.adminRoleService.findRoleById(adminRole.getRoleId()));
    }
    return roles;
  }

  /**
   * 验证密码
   *
   * @param adminId
   * @param password
   * @return
   */
  public Boolean validatePassword(Integer adminId, String password) {
    Admin admin = this.findAdminById(adminId);
    if (admin == null) {
      return false;
    } else {
      Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
      if (md5PasswordEncoder.encodePassword(password, admin.getSalt()).equals(admin.getPassword())) {
        return true;
      } else {
        return false;
      }
    }
  }
}
