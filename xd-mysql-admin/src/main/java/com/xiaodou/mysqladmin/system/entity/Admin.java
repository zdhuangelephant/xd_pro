package com.xiaodou.mysqladmin.system.entity;

import java.sql.Timestamp;
import lombok.Data;
import com.xiaodou.common.util.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * @name @see com.xiaodou.mysqladmin.system.entity.Admin.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月20日
 * @description 管理员模型
 * @version 1.0
 */
@Data
public class Admin {
  /**
   * 管理员id
   */
  private Integer id;

  /**
   * 名称
   */
  private String userName;

  /**
   * 密码
   */
  private String password;

  /**
   * 盐值
   */
  private String salt;

  /**
   * 最后登陆ip
   */
  private String lastLoginIp;

  /**
   * 最后登陆时间
   */
  private Timestamp lastLoginTime;

  /**
   * 邮件
   */
  private String email;

  /**
   * 手机号码
   */
  private String telphone;

  /**
   * 真实姓名
   */
  private String realName;

  public boolean checkPassword(String password) {
    if (StringUtils.isOrBlank(this.salt, this.password)) {
      return false;
    }
    Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    String _password = md5PasswordEncoder.encodePassword(password, this.salt);
    return _password.equals(this.password);
  }
}
