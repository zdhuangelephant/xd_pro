package com.xiaodou.ucenter.domain.request;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.ucenter.constant.UcenterModelConstant;
import com.xiaodou.ucenter.domain.model.BaseUserDO;
import com.xiaodou.ucenter.util.IDGenerator;
import com.xiaodou.ucenter.util.RandomNumberUtil;
import com.xiaodou.ucenter.util.UcenterUtil;

@EqualsAndHashCode(callSuper = false)
@Data
public class RegistRequest extends BaseValidatorPojo {
  /** platform 只允许手机号注册 */
  @NotEmpty
  @LegalValueList({UcenterModelConstant.PLATFORM_TELEPHONE})
  private String platform;
  /** telephone local登录必填项 手机号 */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_TELEPHONE)
  private String telephone;
  /** 密码 */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_TELEPHONE)
  private String password;
  /** salt 注册时系统自动生成掩码 */
  private String salt = RandomNumberUtil.getRandomString(4, 6);
  /** qq qq账号 */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_QQ)
  private String qq;
  /** weixin 微信账号 */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_WEIXIN)
  private String weixin;
  /** weibo 微博账号 */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_WEIBO)
  private String weibo;
  /** tourist 游客账号 */
  @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_TOURIST)
  private String tourist;
  /** module 注册地域码值 */
  private String module;

  /** xdUniqueId 小逗自生产账号（唯一 */
  // @NotEmpty
  // private String xdUniqueId;

  public BaseUserDO setTelRegistUserInfo() {
    BaseUserDO model = new BaseUserDO();
    model.setTelephone(this.getTelephone());
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    model.setMainAccount(UcenterModelConstant.PLATFORM_TELEPHONE);
    model.setSalt(salt);
    model.setPassword(UcenterUtil.getPasswd(getPassword() + getSalt()));
    model.setXdUniqueId(IDGenerator.getSeqID());
    model.setModule(module);
    return model;
  }

  public BaseUserDO setThirdRegistUserInfo() {
    BaseUserDO model = new BaseUserDO();
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    model.setMainAccount(this.getPlatform());
    model.setXdUniqueId(IDGenerator.getSeqID());
    model.setModule(module);
    switch (this.getPlatform()) {
      case UcenterModelConstant.PLATFORM_TOURIST:
        model.setTourist(this.getTourist());
        break;
      case UcenterModelConstant.PLATFORM_QQ:
        model.setQq(this.getQq());
        break;
      case UcenterModelConstant.PLATFORM_WEIBO:
        model.setWeibo(this.getWeibo());
        break;
      case UcenterModelConstant.PLATFORM_WEIXIN:
        model.setWeixin(this.getWeixin());
        break;
      default:
        break;
    }
    return model;
  }
}
