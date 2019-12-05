package com.xiaodou.mission.engine.model;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.domain.UserCollectDataDetailModel;

/**
 * @name @see com.xiaodou.mission.engine.model.UserPkDataDetailInstance.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 用户PK数据明细实体
 * @version 1.0
 */
public class UserPkDataDetailInstance {

  /** 默认构造方法 */
  public UserPkDataDetailInstance() {}

  /**
   * 带参构造方法
   * 
   * @param model 用户数据明细模型
   */
  public UserPkDataDetailInstance(UserCollectDataDetailModel model) {
    if (null == model) {
      return;
    }
    this.id = model.getId();
    this.module = model.getModule();
    this.userId = model.getUserId();
    this.mainId = model.getMainId();
    this.targetUserId = model.getForeignId();
    if (null != model && StringUtils.isJsonNotBlank(model.getDataDetail())) {
      UserPkDataDetailInstance instance =
          FastJsonUtil.fromJson(model.getDataDetail(), UserPkDataDetailInstance.class);
      this.pkTotalTimesSameperson = instance.pkTotalTimesSameperson;
      this.pkWinTimesSameperson = instance.pkWinTimesSameperson;
      this.pkWinPercentSameperson = instance.pkWinPercentSameperson;
      this.pkFailTimesSameperson = instance.pkFailTimesSameperson;
      this.pkFailPercentSameperson = instance.pkFailPercentSameperson;
    }
  }

  /** id 主键 */
  private String id;
  /** module 所属模块 */
  private String module;
  /** userId 用户ID */
  private String userId;
  /** mainId 数据主表主键 */
  private String mainId;
  /** foreignId 核心外键 */
  private String targetUserId;
  /** pkTotalTimesSameperson 对同一个人的PK次数 */
  private Integer pkTotalTimesSameperson = 0;
  /** pkWinTimesSameperson 对同一个人的PK胜利次数 */
  private Integer pkWinTimesSameperson = 0;
  /** pkWinPercentSameperson 对同一个人的PK胜率 */
  private Double pkWinPercentSameperson = 0d;
  /** pkFailTimesSameperson 对同一个人的PK失败次数 */
  private Integer pkFailTimesSameperson = 0;
  /** pkFailPercentSameperson 对同一个人的PK失败率 */
  private Double pkFailPercentSameperson = 0d;

  public String getId() {
    return id;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getMainId() {
    return mainId;
  }

  public void setMainId(String mainId) {
    this.mainId = mainId;
  }

  public String getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(String targetUserId) {
    this.targetUserId = targetUserId;
  }

  public Integer getPkTotalTimesSameperson() {
    return pkTotalTimesSameperson;
  }

  public void setPkTotalTimesSameperson(Integer pkTotalTimesSameperson) {
    this.pkTotalTimesSameperson = pkTotalTimesSameperson;
  }

  public Integer getPkWinTimesSameperson() {
    return pkWinTimesSameperson;
  }

  public void setPkWinTimesSameperson(Integer pkWinTimesSameperson) {
    this.pkWinTimesSameperson = pkWinTimesSameperson;
  }

  public Double getPkWinPercentSameperson() {
    return pkWinPercentSameperson;
  }

  public void setPkWinPercentSameperson(Double pkWinPercentSameperson) {
    this.pkWinPercentSameperson = pkWinPercentSameperson;
  }

  public Integer getPkFailTimesSameperson() {
    return pkFailTimesSameperson;
  }

  public void setPkFailTimesSameperson(Integer pkFailTimesSameperson) {
    this.pkFailTimesSameperson = pkFailTimesSameperson;
  }

  public Double getPkFailPercentSameperson() {
    return pkFailPercentSameperson;
  }

  public void setPkFailPercentSameperson(Double pkFailPercentSameperson) {
    this.pkFailPercentSameperson = pkFailPercentSameperson;
  }



}
