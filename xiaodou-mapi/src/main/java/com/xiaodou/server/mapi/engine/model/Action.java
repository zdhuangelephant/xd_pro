package com.xiaodou.server.mapi.engine.model;

import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.server.mapi.engine.ActionTool;
import com.xiaodou.server.mapi.engine.proxy.AbstractApiProxy;

/**
 * @name @see com.xiaodou.server.mapi.engine.model.Action.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description 注册行为,根据包名、名称与版本号确定唯一坐标
 * @version 1.0
 */
public class Action implements Comparable<Action> {

  /** mpackage 包名 */
  private String mpackage;
  /** name 名称 */
  private String name;
  /** version 版本号 */
  private String version;
  /** api api组建模型 */
  private AbstractApiProxy api;
  /** previous 行为链,指向前一版本 */
  private Action previous;
  /** special 临时版本 */
  private Boolean special = Boolean.FALSE;

  public Boolean isSpecial() {
    return special;
  }

  public void setSpecial(Boolean special) {
    this.special = special;
  }

  public String getKey() {
    return ActionTool.getKey(mpackage, name);
  }

  public Action getPrevious() {
    return previous;
  }

  public void setPrevious(Action previous) {
    this.previous = previous;
  }

  public String getMpackage() {
    return mpackage;
  }

  public void setMpackage(String mpackage) {
    this.mpackage = mpackage;
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

  public AbstractApiProxy getApi() {
    return api;
  }

  public void setApi(AbstractApiProxy api) {
    this.api = api;
  }

  @Override
  public int compareTo(Action other) {
    return this.version.compareTo(other.version);
  }

  public Action getSuitable(String version) {
    int compareTo = this.version.compareTo(version);
    if (compareTo == 0) return this;
    if (!special && compareTo < 0) return this;
    if (null != previous) return previous.getSuitable(version);
    return null;
  }

  public HttpResult excute(Object... paramPojo) {
    if (null == this.api) return null;
    try {
      return this.api.excute(paramPojo);
    } catch (Exception e) {
      LoggerUtil.error("调用API异常", e);
      return null;
    }
  }

}
