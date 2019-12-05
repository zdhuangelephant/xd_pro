package com.xiaodou.logmonitor.domain;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("server_model")
public class ServerModel extends MongoBaseModel {
  /** id 主键ID */
  @Pk
  private String id;
  /** mac mac地址 */
  private String mac;
  /** ip IP地址 */
  private String ip;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }
}
