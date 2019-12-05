package com.xiaodou.frameworkcache.page.model;

import lombok.Data;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;

/**
 * @name @see com.xiaodou.frameworkcache.page.model.PageInfo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月7日
 * @description 页面静态化模型类
 * @version 1.0
 */
@Data
@CollectionName("xd-fw-cache-page")
public class PageInfo {
  @Pk
  private String key;
  /** pageClassType 页面类型 */
  private String pageClassType;
  /** pageInfo 页面信息 */
  private String pageInfo;
  /** sCreateTime 创建时间-展示用 */
  private String sCreateTime;
  /** createTime 创建时间 */
  private Long createTime;
  /** validityTime 有效时间 */
  private Long validityTime;
  /** sLimitTime 过期时间-展示用 */
  private String sLimitTime;
  /** limitTime 过期时间 */
  private Long limitTime;
}
