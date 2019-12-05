package com.xiaodou.autotest.web.processor;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.datasource.mysql.ConnectionFactory;
import com.xiaodou.autotest.core.datasource.mysql.DataSourceConfig;
import com.xiaodou.autotest.web.model.datasource.DataSourceData;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;
/**
 * @name @see com.xiaodou.autotest.web.model.operation.Request.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年8月15日
 * @description
 * @version 1.0
 */
public class RegistDataSourceProcessor implements ApplicationListener<ContextRefreshedEvent> {
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {

    Map<String, Object> input = Maps.newHashMap();
    RequestServiceFacade requestServiceFacade =
        SpringWebContextHolder.getBean("requestServiceFacade");
    List<DataSourceData> dataSourceList = requestServiceFacade.getDataSourceDataByCond(input);
    for (DataSourceData data : dataSourceList) {
      DataSourceConfig config = new DataSourceConfig();
      config.setAlias(data.getAlias());
      config.setDriverUrl(data.getDriverUrl());
      config.setUserName(data.getUserName());
      config.setPassword(data.getPassword());
      ConnectionFactory.registMysqlDataSource(config);
    }
  }
}
