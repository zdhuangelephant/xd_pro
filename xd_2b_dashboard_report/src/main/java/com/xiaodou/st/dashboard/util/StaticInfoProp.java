package com.xiaodou.st.dashboard.util;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.FileUtil;


public class StaticInfoProp {
  /**
   * 配置文件
   */
  private static FileUtil config = FileUtil.getInstance("/conf/custom/env/static_info.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (config == null) synchronized (ConfigProp.class) {
      if (config == null) config = FileUtil.getInstance("/conf/custom/env/static_info.properties");
    }
    return config;
  }

  /**
   * 获取参数值
   * 
   * @param code 参数key
   * @return 参数值
   */
  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }

  public static String productExamDate() {
    return getParams("product.examDate");
  }

  public static String productBeginApplyTime() {
    return getParams("product.beginApplyTime");
  }

  public static String productEndApplyTime() {
    return getParams("product.endApplyTime");
  }
  
  public static String productPriceRate() {
    return getParams("product.priceRate");
  }

  public static String quartzCronExpressionStatus() {
    return getParams("quartz.cronExpression.status");
  }
  
  public static String quartzCronExpression() {
    return getParams("quartz.cronExpression");
  }
  
  public static String goEasyCommonkey(){
    return getParams("commonkey");
  }
  public static String goEasySubscribekey(){
    return getParams("subscribekey");
  }
  
  public static String batchBtnVisibly(){
	  return getParams("report.batch.import.button.visibly");
  }
  
}
