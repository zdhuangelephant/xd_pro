package com.xiaodou.mission.domain;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.model.UserPkDataDetailInstance;
import com.xiaodou.mission.engine.model.UserTollgateDataDetailInstance;


/**
 * @name @see com.xiaodou.mission.domain.UserCollectDataDetailModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 用户数据明细模型
 * @version 1.0
 */
public class UserCollectDataDetailModel {

  /** 默认构造方法 */
  public UserCollectDataDetailModel() {}

  /**
   * 带参构造方法
   * 
   * @param instance 用户PK数据明细实体
   */
  public UserCollectDataDetailModel(UserPkDataDetailInstance instance) {
    if (StringUtils.isNotBlank(instance.getId())) {
      this.id = instance.getId();
    }
    this.userId = instance.getUserId();
    this.module = instance.getModule();
    this.dataType = MissionConstant.USER_DATA_TYPE_PK;
    this.foreignId = instance.getTargetUserId();
    this.dataDetail = FastJsonUtil.toJson(instance);
  }

  /**
   * 带参构造方法
   * 
   * @param instance 用户闯关数据明细实体
   */
  public UserCollectDataDetailModel(UserTollgateDataDetailInstance instance) {
    if (StringUtils.isNotBlank(instance.getId())) {
      this.id = instance.getId();
    }
    this.userId = instance.getUserId();
    this.module = instance.getModule();
    this.dataType = MissionConstant.USER_DATA_TYPE_TOLLGATE;
    this.foreignId = instance.getCourseId();
    this.dataDetail = FastJsonUtil.toJson(instance);
  }

  /** id 主键 */
  private String id;
  /** module 所属模块 */
  private String module;
  /** userId 用户ID */
  private String userId;
  /** dataType 数据类型 */
  private String dataType;
  /** mainId 数据主表主键 */
  private String mainId;
  /** foreignId 核心外键 */
  private String foreignId;
  /** dataDetail 明细数据 */
  private String dataDetail;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getMainId() {
    return mainId;
  }

  public void setMainId(String mainId) {
    this.mainId = mainId;
  }

  public String getForeignId() {
    return foreignId;
  }

  public void setForeignId(String foreignId) {
    this.foreignId = foreignId;
  }

  public String getDataDetail() {
    return dataDetail;
  }

  public void setDataDetail(String dataDetail) {
    this.dataDetail = dataDetail;
  }

}
