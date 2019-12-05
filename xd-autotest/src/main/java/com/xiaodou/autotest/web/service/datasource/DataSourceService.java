package com.xiaodou.autotest.web.service.datasource;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.autotest.core.datasource.mysql.ConnectionFactory;
import com.xiaodou.autotest.core.datasource.mysql.DataSourceConfig;
import com.xiaodou.autotest.web.model.datasource.DataSourceData;
import com.xiaodou.autotest.web.request.DataSourceRequest;
import com.xiaodou.autotest.web.request.JsFunctionRequest;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;

@Service("dataSourceService")
public class DataSourceService {
	@Resource
	RequestServiceFacade requestServiceFacade;

	public DataSourceData insertDataSource(DataSourceRequest data) {
		DataSourceData dataSource=new DataSourceData();
		dataSource.setId(UUID.randomUUID().toString());
		dataSource.setAlias(data.getAlias());
		dataSource.setDriverUrl(data.getDriverUrl());
		dataSource.setPassword(data.getPassword());
		dataSource.setUserName(data.getUserName());
		return requestServiceFacade.saveDataSourceData(dataSource);
	}


	public Boolean updateDataSource(DataSourceRequest data) {
		DataSourceData dataSource=new DataSourceData();
		dataSource.setId(data.getId());
		dataSource.setAlias(data.getAlias());
		dataSource.setDriverUrl(data.getDriverUrl());
		dataSource.setPassword(data.getPassword());
		dataSource.setUserName(data.getUserName());
		return requestServiceFacade.updateDataSourceData(dataSource);
	}

	public Boolean removeDataSource(String id) {
		return requestServiceFacade.deleteDataSourceData(id);
	}
	

	
   public void registDataSource(DataSourceRequest data){
	   DataSourceConfig config = new DataSourceConfig();
	    config.setAlias(data.getAlias());
	    config.setDriverUrl(data.getDriverUrl());
	    config.setUserName(data.getUserName());
	    config.setPassword(data.getPassword());
	    ConnectionFactory.registMysqlDataSource(config);
   }


 
    
}
