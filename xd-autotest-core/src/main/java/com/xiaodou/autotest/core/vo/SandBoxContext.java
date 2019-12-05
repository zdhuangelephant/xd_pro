package com.xiaodou.autotest.core.vo;

import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionTool;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.autotest.core.vo.SandBoxContext.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月9日
 * @description 沙盒上下文
 * @version 1.0
 */
public class SandBoxContext {

  /** globalParamMap 全局参数集合 */
  private GlobalParamMap globalParamMap;
  /** partParamMap 局部参数集合 */
  private Map<String, Object> partParamMap = Maps.newHashMap();

  public SandBoxContext(GlobalParamMap globalParamMap) {
    this.globalParamMap = globalParamMap;
  }

  public void setGlobalParam(String name, Object value) {
    globalParamMap.setParam(name, value);
  }

  public void setParam(String name, Object value) {
    partParamMap.put(name, value);
    if (null != globalParamMap && null != globalParamMap.getFuncEngine()) {
      globalParamMap.getFuncEngine().getContext()
          .setAttribute(name, value, ScriptContext.ENGINE_SCOPE);
    }
  }

  public Object getParam(String name) {
    Object var = ActionTool.getParams(name, partParamMap);
    if (null != var) {
      return var;
    }
    return globalParamMap.getParam(name);
  }

  public Object getFuncVal(String evalPattern) {
    if (null == globalParamMap || null == globalParamMap.getFuncEngine()) {
      return null;
    }
    try {
      return globalParamMap.getFuncEngine().eval(evalPattern);
    } catch (ScriptException e) {
      LoggerUtil.error("SandBoxContext#getFuncVal() fail.", e);
      return null;
    }
  }

  public void clear() {
    if (!partParamMap.isEmpty() && null != globalParamMap && null != globalParamMap.getFuncEngine()) {
      for (String key : partParamMap.keySet()) {
        globalParamMap.getFuncEngine().getContext()
            .removeAttribute(key, ScriptContext.ENGINE_SCOPE);
      }
    }
    partParamMap.clear();
    partParamMap = null;
  }

}
