package com.xiaodou.ucerCenter.agent.model.property;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.ucerCenter.agent.model.UserModel;

public class UserModelProperty {

  public static Map<String, Object> getAllInfo() {
    Map<String, Object> output = new HashMap<String, Object>();
    CommUtil.getAllField(output, UserModel.class);
    return output;
  }

  /**
   * 
   * 简要出参map
   * 
   * @return
   */
  public static Map<String, Object> getBriefInfo() {
    Map<String, Object> output = new HashMap<String, Object>();
    CommUtil.getGeneralField(output, UserModel.class);
    return output;
  }

  /**
   * 用户基本信息（不含密码）
   * 
   * @return
   */
  public static Map<String, Object> getBaseInfo() {
    Map<String, Object> output = new HashMap<String, Object>();
    CommUtil.getBasicField(output, UserModel.class);
    return output;
  }
}
