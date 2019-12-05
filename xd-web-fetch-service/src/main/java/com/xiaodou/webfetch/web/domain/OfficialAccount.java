package com.xiaodou.webfetch.web.domain;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@CollectionName("wechat_official_accounts")
public class OfficialAccount  extends MongoBaseModel {
  /** seflId 逻辑/业务主键 */
  @Pk
  private String seflId;
  /** title 文章标题 */
  private String title;
  /** cover 配文图片 */
  private String cover;
  /** itemidx 文章列表中的序号 */
  private String itemidx;
  /** appmsgid 微信公众号提供的文章唯一ID */
  private String appmsgid;
  /** link 文章链接 */
  private String link;
  /** aid 文章角标 */
  private String aid;
  /** digest 备注 */
  private String digest;
  /** updateTime 更新时间 */
  private String update_time;
//  private Timestamp updateTime = new Timestamp(System.currentTimeMillis());
}
