package com.xiaodou.ms.service.message;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xiaodou.ms.dao.message.AdminPushMessageDao;
import com.xiaodou.ms.model.message.AdminPushMessage;
import com.xiaodou.summer.dao.pagination.Page;

@Service("adminPushMessageService")
public class AdminPushMessageService {
  @Resource
  AdminPushMessageDao adminPushMessageDao;

  /**
   * 查询推送消息
   * 
   * @param cond
   */
  public Page<AdminPushMessage> queryPushMessage(Integer pageNo) {
    Map<String, Object> cond = new HashMap<String, Object>();
    // cond.put("module", module);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("messageStatus", "");
    output.put("messageType", "");
    output.put("targetType", "");
    output.put("messageContent", "");
    output.put("noticeContent", "");
    output.put("spreadRange", "");
    output.put("alias", "");
    output.put("tags", "");
    output.put("registrationIds", "");
    output.put("messageextras", "");
    output.put("noticeextras", "");
    output.put("createTime", "");
    output.put("createUser", "");
    output.put("updateTime", "");
    output.put("updateUser", "");
    Page<AdminPushMessage> page = new Page<AdminPushMessage>();
    page.setPageNo(pageNo);
    page.setPageSize(20);
    Page<AdminPushMessage> pushPage = adminPushMessageDao.queryListByCond(cond, output, page);
    return pushPage;
  }

  /**
   * 查询推送消息
   * 
   * @param adminPushMessageId
   */
  public AdminPushMessage findAdminPushMessageById(Long adminPushMessageId) {
    AdminPushMessage adminPushMessage = new AdminPushMessage();
    adminPushMessage.setId(adminPushMessageId);
    return adminPushMessageDao.findEntityById(adminPushMessage);
  }

  /**
   * 增加推送消息
   * 
   * @param adminPushMessage
   */
  public void addPushMessage(AdminPushMessage adminPushMessage) {
    // String salt = RandomUtils.generateString(6);
    // admin.setSalt(salt);
    // Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    // String password = md5PasswordEncoder.encodePassword(admin.getPassword(), salt);
    // admin.setPassword(password);
    // AdminPushMessage adminPushMessage = new AdminPushMessage();
    this.adminPushMessageDao.addEntity(adminPushMessage);
  }

  /**
   * 删除推送消息
   * 
   * @param adminPushMessageId
   */
  public Boolean deletePushMessage(Long adminPushMessageId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", adminPushMessageId);
    Boolean result = adminPushMessageDao.deleteEntity(cond);
    // this.deleteAdminRolesReltion(adminPushMessageId);
    return result;
  }

  /**
   * 更新推送消息
   * 
   * @param adminPushMessage
   */
  public Boolean updatePushMessage(AdminPushMessage adminPushMessage) {
    // 判断密码是否为空
    // if (StringUtils.isNotBlank(admin.getPassword())) {
    // String salt = RandomUtils.generateString(6);
    // admin.setSalt(salt);
    // Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    // String password = md5PasswordEncoder.encodePassword(admin.getPassword(), salt);
    // admin.setPassword(password);
    // } else {
    // admin.setSalt(null);
    // }
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", adminPushMessage.getId());
    return adminPushMessageDao.updateEntity(cond, adminPushMessage);
  }



}
