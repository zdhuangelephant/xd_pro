package com.xiaodou.mysqladmin.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

/**
 * @name @see com.xiaodou.mysqladmin.system.entity.SerachHistory.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月18日
 * @description 用户搜索历史记录
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@CollectionName("xd_mya_user_search_history")
public class SearchHistory extends MongoBaseModel {
  @Pk
  private String rId;
  private String userId;
  private String userName;
  private String dbName;
  private String sqls;
  private String createTimestamp;
}
