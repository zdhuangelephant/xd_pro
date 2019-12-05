package com.xiaodou.ucenter.domain.response;

import java.sql.Timestamp;

import com.xiaodou.ucenter.domain.model.BaseUserDO;

import lombok.Data;

@Data
public class BaseUserInfo {
  /** module 初始注册/导入地域码 */
  private String module;
  /**
   * 小逗自生产账号（唯一）
   */
  private String xdUniqueId;

  /**
   * 手机号
   */
  private String telephone;

  /**
   * qq账号
   */
  private String qq;

  /**
   * 微信账号
   */
  private String weixin;

  /**
   * 微博账号
   */
  private String weibo;

  /**
   * 游客账号
   */
  private String tourist;

  /**
   * 注册账号类型/主账号类型
   */
  private String mainAccount;
  /**
   * 创建时间
   */
  private Timestamp createTime;

  public static BaseUserInfo init(BaseUserDO userModel) {
    BaseUserInfo userBaseInfo = new BaseUserInfo();
    userBaseInfo.setModule(userModel.getModule());
    userBaseInfo.setCreateTime(userModel.getCreateTime());
    userBaseInfo.setMainAccount(userModel.getMainAccount());
    userBaseInfo.setQq(userModel.getQq());
    userBaseInfo.setTelephone(userModel.getTelephone());
    userBaseInfo.setTourist(userModel.getTourist());
    userBaseInfo.setWeibo(userModel.getWeibo());
    userBaseInfo.setWeixin(userModel.getWeixin());
    userBaseInfo.setXdUniqueId(userModel.getXdUniqueId());
    return userBaseInfo;
  }
}
