package com.xiaodou.autopractise.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autopractise.enums.UserAbility;
import com.xiaodou.autopractise.enums.UserStatus;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

/**
 * @name @see com.xiaodou.autopractise.domain.UserInfo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月28日
 * @description 用户信息模型
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends MongoBaseModel {
  /** userId 用户ID */
  @Pk
  private String userId;
  /** name 姓名 */
  private String name;
  /** userName 用户名 */
  private String userName;
  /** passwd 密码 */
  private String passwd;
  /** finish 是否完成 */
  private Boolean finish = Boolean.FALSE;
  /** status 初始化状态 */
  private Integer status = UserStatus.INIT.getCode();
  /** ability 用户能力值 */
  private Integer ability = UserAbility.ALLRIGHT.getCode();
}
