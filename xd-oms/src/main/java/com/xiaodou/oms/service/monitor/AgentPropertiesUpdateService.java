package com.xiaodou.oms.service.monitor;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.rabbit.util.ConfigHelper;
import com.xiaodou.jmsg.rabbit.util.RabbitConnectionPoolHelper;
import com.xiaodou.oms.statemachine.engine.APIContext;

/**
 * Date: 2014/9/28
 * Time: 15:18
 *
 * @author Tian.Dong
 */
@Service
public class AgentPropertiesUpdateService {

  public void refresh(String fileName){
    if(fileName.equals("oms_pay_mapping.properties")) {
      refreshOmsPayMapping();
    } else if(fileName.equals("apiLib.properties")) {
      refreshApiLibProperties();
    } else if(fileName.equals("jmsgdb.properties")){
      refreshJMSGClientProperties();
    } else if(fileName.equals("query_payment.properties")) {
      //      refreshQueryPaymentProperties();
    }
  }

  private void refreshJMSGClientProperties() {
    try {
      //调用loadSqlSessionFactory
      Method loadSqlSessionFactoryMethod = ConfigHelper.class.getDeclaredMethod("loadSqlSessionFactory");
      loadSqlSessionFactoryMethod.setAccessible(true);
      loadSqlSessionFactoryMethod.invoke(null);

      //调用readConfigs
      Method readConfigsMethod = ConfigHelper.class.getDeclaredMethod("readConfigs");
      readConfigsMethod.setAccessible(true);
      readConfigsMethod.invoke(null);

      //调用RabbitConnectionPoolHelper.initPool
      Method initPoolMethod = RabbitConnectionPoolHelper.class.getDeclaredMethod("initPool");
      initPoolMethod.setAccessible(true);
      initPoolMethod.invoke(null);

    } catch (Exception e){
      LoggerUtil.error("refreshJMSGClientProperties异常",e);
    }
  }

  private void refreshApiLibProperties() {
    try {
      APIContext.init("conf/core/ApiLib.xml");
    } catch (Exception e) {
      LoggerUtil.error("refreshApiLibProperties异常",e);
    }
  }

  private void refreshOmsPayMapping(){
//    PayMerchant.init();
    /*
    * md5校验也用到了这个文件，
    * 但是由于之前已经对FileUtil做了reload
    * 故此不用处理
    */
  }
}
