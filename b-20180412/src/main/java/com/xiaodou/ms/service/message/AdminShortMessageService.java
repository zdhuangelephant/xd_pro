package com.xiaodou.ms.service.message;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.ms.dao.message.AdminShortMessageDao;
import com.xiaodou.ms.model.message.AdminShortMessage;
import com.xiaodou.summer.dao.pagination.Page;

@Service("adminShortMessageService")
public class AdminShortMessageService {
  @Resource
  AdminShortMessageDao adminShortMessageDao;

  /**
   * 查询后台短信
   * 
   * @param cond
   */
  public Page<AdminShortMessage> queryShortMessage(Integer pageNo, String merchantId) {
    Map<String, Object> cond = Maps.newHashMap();
    if (merchantId != null && !merchantId.equals("")) {
      cond.put("merchantId", merchantId);
    }
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("merchantId", "");
    output.put("templateId", "");
    output.put("telephone", "");
    output.put("variables", "");
    output.put("createTime", "");
    output.put("createUser", "");
    output.put("updateTime", "");
    output.put("updateUser", "");
    Page<AdminShortMessage> page = new Page<AdminShortMessage>();
    page.setPageNo(pageNo);
    page.setPageSize(20);
    Page<AdminShortMessage> pushPage = adminShortMessageDao.queryListByCond(cond, output, page);
    return pushPage;
  }

  /**
   * 查询后台短信
   * 
   * @param adminShortMessageId
   */
  public AdminShortMessage findAdminShortMessageById(Long adminShortMessageId) {
    AdminShortMessage AdminShortMessage = new AdminShortMessage();
    AdminShortMessage.setId(adminShortMessageId);
    return adminShortMessageDao.findEntityById(AdminShortMessage);
  }

  /**
   * 增加后台发送短信
   * 
   * @param adminShortMessage
   */
  public void addShortMessage(AdminShortMessage adminShortMessage) {
    this.adminShortMessageDao.addEntity(adminShortMessage);
  }

  /**
   * 删除短信
   * 
   * @param adminShortMessageId
   */
  public Boolean deleteShortMessage(Long adminShortMessageId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", adminShortMessageId);
    Boolean result = adminShortMessageDao.deleteEntity(cond);
    // this.deleteAdminRolesReltion(AdminShortMessageId);
    return result;
  }

  /**
   * 更新短信内容
   * 
   * @param adminShortMessage
   */
  public Boolean updateShortMessage(AdminShortMessage adminShortMessage) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", adminShortMessage.getId());
    return adminShortMessageDao.updateEntity(cond, adminShortMessage);
  }



}
