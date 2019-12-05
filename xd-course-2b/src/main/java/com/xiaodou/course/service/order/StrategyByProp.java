package com.xiaodou.course.service.order;

import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.course.service.order.Context.Strategy;

/**
 * @name @see com.xiaodou.course.service.order.StrategyByProp.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 *
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年3月29日
 * @description 获取配置文件策略
 * @version 1.0
 */
public class StrategyByProp implements Strategy{

  @Override
  public Boolean useStrategy(Long productId) {
    String conf = getParam("CANNOT_ORDER_PRODUCTIDS");
    if(StringUtils.isNotEmpty(conf) && null != productId){
      for(String pid : conf.split(",")){
        if(String.valueOf(productId).equals(pid)){
          return true;
        }
      }
    }
    return false;
  }

  private static FileUtil fileUtil = FileUtil.getInstance("/conf/custom/env/config.properties"); 
  
  private static FileUtil getPropertie(){
    if(null == fileUtil){
      synchronized (StrategyByProp.class) {
       if(null == fileUtil){
         fileUtil = FileUtil.getInstance("/conf/custom/env/config.properties");
       } 
      }
    }
    return fileUtil;
  }
 
  public static String getParam(String code){
    return getPropertie().getProperties(code);
  }
}
