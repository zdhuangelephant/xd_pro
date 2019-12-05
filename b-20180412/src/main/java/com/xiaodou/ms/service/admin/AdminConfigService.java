package com.xiaodou.ms.service.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.ms.dao.admin.AdminConfigDao;
import com.xiaodou.ms.model.admin.AdminConfig;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 14-9-2.
 */
public class AdminConfigService {

  private AdminConfigDao adminConfigDao;

  public AdminConfigDao getAdminConfigDao() {
    return adminConfigDao;
  }

  public void setAdminConfigDao(AdminConfigDao adminConfigDao) {
    this.adminConfigDao = adminConfigDao;
  }

  /**
   * 添加配置
   * 
   * @param adminConfig
   */
  public void addConfig(AdminConfig adminConfig) {
    this.adminConfigDao.addEntity(adminConfig);
  }

  /**
   * 修改配置
   * 
   * @param adminConfig
   */
  public Boolean editConfig(AdminConfig adminConfig) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("configKey", adminConfig.getConfigKey());
    return this.adminConfigDao.updateEntityByCond(input, adminConfig);
    // return this.adminConfigDao.updateEntity(adminConfig);
  }

  /**
   * 删除配置
   * 
   * @param module
   * @param key
   */
  public Boolean deleteConfig(String module, String key) {
    // AdminConfig adminConfig = new AdminConfig();
    // adminConfig.setSystemModule(module);
    // adminConfig.setConfigKey(key);
    Map<String, Object> input = Maps.newHashMap();
    input.put("systemModule", module);
    input.put("configKey", key);
    return this.adminConfigDao.deleteEntityByCond(input);
    // return this.adminConfigDao.deleteEntity(adminConfig);
  }


  /**
   * 根据条件查询配置
   * 
   * @param cond
   */
  public List<AdminConfig> queryConfigsByCond(Map<String, Object> cond) {
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    return this.adminConfigDao.findEntityListByCond(param, null).getResult();
    //return this.adminConfigDao.findEntityListByCond(cond, null).getResult();
  }

  /**
   * 查询
   * 
   * @param module
   * @param key
   * @return
   */
  public AdminConfig findConfig(String module, String key) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("systemModule", module);
    cond.put("configKey", key);
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    Page<AdminConfig> adminConfigs = this.adminConfigDao.findEntityListByCond(param, null);
    if (adminConfigs.getResult().size() == 0) {
      return null;
    } else {
      return adminConfigs.getResult().get(0);
    }
  }


}
