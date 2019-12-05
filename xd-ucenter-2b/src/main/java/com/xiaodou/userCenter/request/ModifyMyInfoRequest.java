package com.xiaodou.userCenter.request;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.module.domain.vo.ModuleInfoDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"phoneNum", "password",
    "confirmPassword", "checkCode", "publishId", "systemType", "module"})
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"sessionToken"})
@Data
@EqualsAndHashCode(callSuper = true)
public class ModifyMyInfoRequest extends BaseRequest {
  public ModifyMyInfoRequest() {}

  public ModifyMyInfoRequest(UserBaseInfo info) {
    super(info);
  }

  /** nickName 昵称 */
  private String nickName;
  /** portrait 头像 */
  private String portrait;
  /** age 年龄 */
  private Integer age;
  /** address 地址 */
  private String address;
  /** gender 性别 */
  @LegalValueList({"1", "2"})
  private Integer gender;
  @NotEmpty
  @LegalValueList({UcenterModelConstant.MODIFY_INFO, UcenterModelConstant.IMPROVE_INFO})
  private String type; // 操作类型
  /** major 用户已选专业专业(typeCode) */
  @NotEmpty(field = "type", value = UcenterModelConstant.IMPROVE_INFO)
  private String major;
  /** sign 签名 */
  private String sign;
  /** picList 图片 */
  private String picList;
  /** medalId 勋章id */
  private String medalId;
  /** medalName 勋章名称 */
  private String medalName;
  /** medalImg 勋章图片 */
  private String medalImg;

  /** module 地区 */
  private String region;
  /** moduleName 地区名称 */
  private String regionName;
  /** majorId 专业id */
  private String majorId;
  /** majorName 专业名称 */
  private String majorName;

  public void initModifyInfo(UserModel user, String headVersion) {
    if (StringUtils.isNotBlank(getNickName())) user.setNickName(getNickName()); // 设置昵称
    if (StringUtils.isNotBlank(getPortrait())) user.setPortrait(getPortrait()); // 设置头像
    if (null != getAge()) user.setAge(getAge()); // 设置年龄
    if (StringUtils.isNotBlank(getNickName())) user.setAddress(getAddress()); // 设置地址
    if (null != getGender()) user.setGender(getGender()); // 设置性别
    if (StringUtils.isNotBlank(getDeviceId())) user.setUsedDeviceId(getDeviceId()); // 设置使用过的设备
    if (StringUtils.isNotBlank(getClientIp())) user.setLatestDeviceIp(getClientIp()); // 设置最近登录设备
    // 设置moduleInfo信息
    user.setModuleInfo(FastJsonUtil.toJson(ModuleInfoDTO.getInstance(headVersion, user, this)));
  }
}
