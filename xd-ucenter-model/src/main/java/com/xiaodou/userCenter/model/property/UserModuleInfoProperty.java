package com.xiaodou.userCenter.model.property;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.userCenter.model.UserModuleInfoModel;

public class UserModuleInfoProperty {


	  public static Map<String, Object> getAllInfo() {
	    Map<String, Object> output = new HashMap<String, Object>();
	    CommUtil.getAllField(output, UserModuleInfoModel.class);
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
	    CommUtil.getGeneralField(output, UserModuleInfoModel.class);
	    return output;
	  }

	  /**
	   * 用户基本信息（不含密码）
	   * 
	   * @return
	   */
	  public static Map<String, Object> getBaseInfo() {
	    Map<String, Object> output = new HashMap<String, Object>();
	    CommUtil.getBasicField(output, UserModuleInfoModel.class);
	    return output;
	  }

}
