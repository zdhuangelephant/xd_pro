package com.xiaodou.mysqladmin.system.entity;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@Data
@EqualsAndHashCode(callSuper = true)
@CollectionName("xd_mya_user_role")
public class Role extends MongoBaseModel {

  @Pk
  private String rid;
  private String userId;
  private String userName;
  private String name;
  private String token;
  private String salt;
  private String password;
  private String type;

  public boolean checkPassword(String password) {
    if (StringUtils.isOrBlank(this.salt, this.password)) {
      return false;
    }
    Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    String _password = md5PasswordEncoder.encodePassword(password, this.salt);
    return _password.equals(this.password);
  }

  public void setNewPassword(String newPassword) {
    if (StringUtils.isBlank(this.salt)) {
      this.salt = RandomUtil.randomString(6);
    }
    Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    this.password = md5PasswordEncoder.encodePassword(newPassword, this.salt);
  }

}
