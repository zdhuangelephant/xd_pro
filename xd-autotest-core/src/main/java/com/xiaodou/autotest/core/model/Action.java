package com.xiaodou.autotest.core.model;

import java.util.LinkedList;
import java.util.Set;

import org.springframework.util.Assert;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.xiaodou.autotest.core.ActionProcesserCenter;
import com.xiaodou.autotest.core.funclib.FuncLibFactory;
import com.xiaodou.autotest.core.interfaces.ProcessApiException;
import com.xiaodou.autotest.core.proxy.AbstractApiProxy;
import com.xiaodou.autotest.core.util.ActionLoggerUtil;
import com.xiaodou.autotest.core.vo.GlobalParamMap;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.server.mapi.engine.model.Action.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description 注册行为,根据包名、名称与版本号确定唯一坐标
 * @version 1.0
 */
public class Action {

  /** businessLine 业务线 */
  private String businessLine;
  /** name 名称 */
  private String name;
  /** version 版本号 */
  private String version;
  /** api api组建模型 */
  private LinkedList<Api> apiList = Lists.newLinkedList();
  /** global 全局参数集合 */
  @JSONField(serialize = false, deserialize = false)
  private GlobalParamMap global;
  /** actionId */
  private String actionId;

  public Action() {
    this.global = new GlobalParamMap();
    FuncLibFactory.registGlobalScriptContext(this.global);
  }

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

  public LinkedList<Api> getApiList() {
    return apiList;
  }

  public void registApi(Api api) {
    Assert.hasText(api.getName(), "Api's name can't be null.");
    apiList.add(api);
  }

  public void registGlobalParam(String name, String value) {
    Assert.hasText(name, "GlobalParam's name can't be null.");
    Assert.hasText(value, "GlobalParam's value can't be null.");
    global.setParam(name, value);
  }

  public void excute() {
    ActionLoggerUtil.debug(String.format("Action[%s] Start.", actionId));
    for (Api api : apiList) {
      ActionLoggerUtil.debug(String.format("Action[%s].Api[%s] Start.", actionId, api.getUniqueId()));
      AbstractApiProxy proxy;
      try {
        proxy = api.getProtocol().getProxyType().newInstance();
        proxy.setName(api.getName());
        proxy.setApi(api);
        proxy.excute(global);
        ActionLoggerUtil.actionResult(this);
      } catch (Exception e) {
        Set<ProcessApiException> processerSet = ActionProcesserCenter.getProcesserSet(name);
        if (null == processerSet) {
          return;
        } else {
          for (ProcessApiException processer : processerSet) {
            processer.process(this, api, e);
          }
        }
      }
      ActionLoggerUtil.debug(String.format("Action[%s].Api[%s] Finish.", actionId, api.getUniqueId()));
    }
    global.clear();
    global = null;
    ActionLoggerUtil.debug(String.format("Action[%s] Finish.", actionId));
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

  public String getActionId() {
    return actionId;
  }

  public void setActionId(String actionId) {
    this.actionId = actionId;
  }

}
