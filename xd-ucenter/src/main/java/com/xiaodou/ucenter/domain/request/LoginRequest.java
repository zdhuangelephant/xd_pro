package com.xiaodou.ucenter.domain.request;

import java.sql.Timestamp;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.ucenter.constant.UcenterModelConstant;
import com.xiaodou.ucenter.domain.model.BaseUserDO;
import com.xiaodou.ucenter.util.IDGenerator;
import com.xiaodou.ucenter.util.UcenterUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginRequest extends BaseValidatorPojo {
  /*
   * 第三方登录必填项
   */
  // local/qq/weibo/weixin;所属平台; device;游客模式登录
  @NotEmpty
  @LegalValueList({UcenterModelConstant.PLATFORM_TELEPHONE, UcenterModelConstant.PLATFORM_QQ,
      UcenterModelConstant.PLATFORM_WEIBO, UcenterModelConstant.PLATFORM_WEIXIN,
      UcenterModelConstant.PLATFORM_TOURIST})
  private String platform;

  /*
   * local登录必填项
   */
  /** 手机号 */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_TELEPHONE)
  private String telephone;

  /** 密码 */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_TELEPHONE)
  private String password;

  /**
   * qq账号
   */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_QQ)
  private String qq;

  /**
   * 微信账号
   */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_WEIXIN)
  private String weixin;

  /**
   * 微博账号
   */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_WEIBO)
  private String weibo;

  /**
   * 游客账号
   */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_TOURIST)
  private String tourist;

  /** XdUniqueId 小逗自生产账号（唯一） */
  // @NotEmpty
  // private String xdUniqueId;

  public boolean canRegist() {
    return StringUtils.isNotBlank(platform)
        && !platform.equals(UcenterModelConstant.PLATFORM_TELEPHONE);
  }

  public Map<String, Object> getQueryModelCond() {
    Map<String, Object> cond = Maps.newHashMap();
    if (StringUtils.isBlank(platform)) return cond;
    switch (platform) {
      case UcenterModelConstant.PLATFORM_TELEPHONE:
        cond.put("telephone", telephone);
        break;
      case UcenterModelConstant.PLATFORM_TOURIST:
        cond.put("tourist", tourist);
        break;
      case UcenterModelConstant.PLATFORM_QQ:
        cond.put("qq", qq);
        break;
      case UcenterModelConstant.PLATFORM_WEIBO:
        cond.put("weibo", weibo);
        break;
      case UcenterModelConstant.PLATFORM_WEIXIN:
        cond.put("weixin", weixin);
        break;
      default:
        break;
    }
    return cond;
  }

  public boolean checkPassword(BaseUserDO model) {
    if (UcenterModelConstant.PLATFORM_TELEPHONE.equals(platform)) {
      String passwordFromApp = UcenterUtil.getPasswd(password + model.getSalt());
      return model.getPassword().equals(passwordFromApp);
    }
    return true;
  }

  public void setRegistUserInfo(BaseUserDO model) {
    if (null == model) model = new BaseUserDO();
    if (StringUtils.isBlank(platform)) return;
    model.setXdUniqueId(IDGenerator.getSeqID());
    model.setMainAccount(platform);
    model.setCreateTime(new Timestamp(System.currentTimeMillis())); // 设置创建时间
    switch (platform) {
      case UcenterModelConstant.PLATFORM_QQ:
        model.setQq(qq);
        break;
      case UcenterModelConstant.PLATFORM_WEIBO:
        model.setWeibo(weibo);
        break;
      case UcenterModelConstant.PLATFORM_WEIXIN:
        model.setWeixin(weixin);
        break;
      case UcenterModelConstant.PLATFORM_TOURIST:
        model.setTourist(tourist);
        break;
      default:
        break;
    }
  }

}
