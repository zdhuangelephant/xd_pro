package com.xiaodou.mysqladmin.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

/**
 * @name @see com.xiaodou.mysqladmin.system.entity.DataSource.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月20日
 * @description TODO
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@CollectionName("xd_mya_data_source")
public class DataSource extends MongoBaseModel {
  @Pk
  private String sid;
  private String driver;
  private String url;
  private String dataSourceName;
  private String userName;
  private String passwrod;
  private String port;
  private String ip;
}
