package com.xiaodou.ms.service.admin;


import com.xiaodou.ms.dao.admin.AdminRoleDao;
import com.xiaodou.ms.dao.admin.RoleDao;
import com.xiaodou.ms.dao.admin.RolePrivilegeDao;
import com.xiaodou.ms.model.admin.AdminRole;
import com.xiaodou.ms.model.admin.Role;
import com.xiaodou.ms.model.admin.RolePrivilege;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyp on 14-9-2.
 */
public class AdminRoleService {

  private RolePrivilegeDao rolePrivilegeDao;

  private RoleDao roleDao;

  private AdminRoleDao adminRoleDao;

  public RolePrivilegeDao getRolePrivilegeDao() {
    return rolePrivilegeDao;
  }

  public void setRolePrivilegeDao(RolePrivilegeDao rolePrivilegeDao) {
    this.rolePrivilegeDao = rolePrivilegeDao;
  }

  public RoleDao getRoleDao() {
    return roleDao;
  }

  public void setRoleDao(RoleDao roleDao) {
    this.roleDao = roleDao;
  }

  public AdminRoleDao getAdminRoleDao() {
    return adminRoleDao;
  }

  public void setAdminRoleDao(AdminRoleDao adminRoleDao) {
    this.adminRoleDao = adminRoleDao;
  }

  /**
   * 添加角色
   *
   * @param role
   */
  public void addRole(Role role) {
    this.roleDao.addEntity(role);
  }

  /**
   * 删除角色
   *
   * @param roleId
   * @return
   */
  public Boolean deleteRole(Integer roleId) {
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",roleId);
    Boolean result = this.roleDao.deleteEntity(cond);
    this.deleteRoleAllPrivilege(roleId);
    this.deleteRoleAdminsRelations(roleId);
    return result;
  }

  /**
   * 更新角色
   *
   * @param role
   * @return
   */
  public Boolean updateRole(Role role) {
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",role.getId());
    return this.roleDao.updateEntity(cond,role);
  }

  /**
   * 根据条件查询角色列表
   *
   * @param cond
   * @return
   */
  public List<Role> queryRolesByCond(Map<String, Object> cond) {
    return this.roleDao.queryListByCondWithOutPage(cond, null).getResult();
  }

  /**
   * 根据id查询角色
   *
   * @param roleId
   * @return
   */
  public Role findRoleById(Integer roleId) {
    Role role = new Role();
    role.setId(roleId);
    return this.roleDao.findEntityById(role);
  }

  /**
   * 删除本角色的全部权限
   *
   * @param roleId
   */
  public void deleteRoleAllPrivilege(Integer roleId) {
    Map<String,Object> cond = new HashMap<>();
    cond.put("roleId",roleId);
    this.rolePrivilegeDao.deleteEntity(cond);
  }

  /**
   * 查询某角色的全部权限
   *
   * @param roleId
   * @return
   */
  public List<RolePrivilege> queryRolePrivileges(Integer roleId) {
    Map<String, Object> con = new HashMap<String, Object>();
    con.put("roleId", roleId);
    return this.rolePrivilegeDao.queryListByCondWithOutPage(con, new HashMap()).getResult();
  }

  /**
   * 查询某角色下的全部管理员
   *
   * @param roleId
   */
  public List<AdminRole> queryRoleAdmins(Integer roleId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("roleId", roleId);
    return this.adminRoleDao.queryListByCondWithOutPage(cond, new HashMap()).getResult();
  }

  /**
   * 删除角色管理员管理关系
   *
   * @param roleId
   */
  public void deleteRoleAdminsRelations(Integer roleId) {
    Map<String,Object> cond = new HashMap<>();
    cond.put("roleId",roleId);
    this.adminRoleDao.deleteEntity(cond);
  }

  /**
   * 添加管理员角色
   * @param adminId
   * @param roleIds
   */
  public void addAdminRole(Integer adminId,List<Integer> roleIds){
    for (Integer roleId:roleIds){
      AdminRole adminRole = new AdminRole();
      adminRole.setRoleId(roleId);
      adminRole.setAdminId(adminId);
      adminRoleDao.addEntity(adminRole);
    }
  }

  /**
   * 删除管理员角色
   * @param adminId
   * @param roleIds
   * @return
   */
  public Boolean deleteAdminRole(Integer adminId,List<Integer> roleIds){
    Map<String,Object> cond = new HashMap<>();
    cond.put("adminId",adminId);
    cond.put("roleIds",roleIds);
    return adminRoleDao.deleteEntity(cond);
  }
}
