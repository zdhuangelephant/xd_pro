package com.xiaodou.st.dashboard.service.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.dao.admin.AdminDao;
import com.xiaodou.st.dashboard.domain.admin.AdminDO;
import com.xiaodou.st.dashboard.util.RandomUtils;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * 
 * @name AdminService CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月29日
 * @description TODO
 * @version 1.0
 */
public class AdminService {

  private AdminDao adminDao;



  public AdminDao getAdminDao() {
    return adminDao;
  }

  public void setAdminDao(AdminDao adminDao) {
    this.adminDao = adminDao;
  }

  /**
   * 增加管理员
   * 
   * @param admin
   */
  public Boolean addAdmin(AdminDO admin) {
    String salt = RandomUtils.generateString(6);
    admin.setSalt(salt);
    Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    String password = md5PasswordEncoder.encodePassword(admin.getPassword(), salt);
    admin.setPassword(password);
    return null != this.adminDao.addEntity(admin);
  }

  /**
   * 删除管理员
   * 
   * @param adminId
   */
  public Boolean deleteAdmin(Integer adminId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", adminId);
    Boolean result = this.adminDao.deleteEntityByCond(cond);
    return result;
  }

  /**
   * 更新管理员
   * 
   * @param admin
   */
  public Boolean updateAdmin(AdminDO admin) {
    // 判断密码是否为空
    if (StringUtils.isNotBlank(admin.getPassword())) {
      String salt = RandomUtils.generateString(6);
      admin.setSalt(salt);
      Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
      String password = md5PasswordEncoder.encodePassword(admin.getPassword(), salt);
      admin.setPassword(password);
    }else{
      admin.setSalt(null);
      admin.setPassword(null);
    }
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", admin.getId());
    return this.adminDao.updateEntityByCond(cond, admin);
  }

  /**
   * 查询管理员
   * 
   * @param cond
   */
  public List<AdminDO> queryAdmin(Map<String, Object> cond) {
    QueryParam queryParam = new QueryParam();
    queryParam.addInputs(cond);
    queryParam.addOutputs(CommUtil.getAllField(AdminDO.class));
    return this.adminDao.findEntityListByCond(queryParam, null).getResult();
  }

  /**
   * 查询管理员
   * 
   * @param adminId
   */
  public AdminDO findAdminById(Integer adminId) {
    AdminDO admin = new AdminDO();
    admin.setId(adminId);
    return this.adminDao.findEntityById(admin);
  }

  /**
   * 根据用户名查找用户
   * 
   * @param userName
   * @return
   */
  public AdminDO findAdminByUserName(String userName) {
    Map<String, Object> cond = new HashMap<String, Object>();
    try {
      String[] array = userName.split(Constants.USER);
      cond.put("role", array[0]);
      cond.put("userName", array[1]);
    } catch (Exception e) {
      LoggerUtil.error("校验用户名与权限异常", e);
      return null;
    }
    List<AdminDO> admins = this.queryAdmin(cond);
    if (admins.size() == 0) {
      return null;
    } else {
      return admins.get(0);
    }
  }

  /**
   * 验证密码
   * 
   * @param adminId
   * @param password
   * @return
   */
  public Boolean validatePassword(AdminDO admin, String password) {
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

  /**
   * 
   * @description 查询第三级单位下面的班级管理员列表
   * @author 李德洪
   * @Date 2017年8月30日
   * @param poiltUnitId
   * @return
   */
  public List<AdminDO> listClassAdmin(Long poiltUnitId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("role", Constants.POILT_UNIT_ROLE);
    cond.put("unitId", poiltUnitId);
    cond.put("childRole", Constants.POILT_UNIT_CHILD_ROLE);
    List<AdminDO> adminList = this.queryAdmin(cond);
    return adminList;
  }

}
