package com.xiaodou.autotest.core.deserialize.dmodel;

import java.util.LinkedList;

import com.google.common.collect.Lists;

/**
 * @name @see com.xiaodou.autotest.core.deserialize.dmodel.DAction.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月15日
 * @description 反序列化用Action
 * @version 1.0
 */
public class MidAction {

  /** businessLine 业务线 */
  private String businessLine;
  /** name 名称 */
  private String name;
  /** version 版本号 */
  private String version;
  /** api api组建模型 */
  private LinkedList<MidApi> apiList = Lists.newLinkedList();

  public MidAction() {}

  public String getBusinessLine() {
    return businessLine;
  }

  public void setBusinessLine(String businessLine) {
    this.businessLine = businessLine;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public LinkedList<MidApi> getApiList() {
    return apiList;
  }

  public void setApiList(LinkedList<MidApi> apiList) {
    this.apiList = apiList;
  }


}
