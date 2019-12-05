package com.xiaodou.autotest.core.vo;

import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;

import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionTool;

/**
 * @name @see com.xiaodou.autotest.core.vo.GlobalParamMap.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月11日
 * @description 全局参数集
 * @version 1.0
 */
public class GlobalParamMap {

  /** funcEngine 方法引擎 */
  private ScriptEngine funcEngine;
  /** globalParamMap 全局参数 */
  private Map<String, Object> globalParamMap = Maps.newHashMap();

  public ScriptEngine getFuncEngine() {
    return funcEngine;
  }

  public void setFuncEngine(ScriptEngine funcEngine) {
    this.funcEngine = funcEngine;
  }

  public void setParam(String name, Object value) {
    globalParamMap.put(name, value);
    if (null != funcEngine && null != funcEngine.getContext()) {
      funcEngine.getContext().setAttribute(name, value, ScriptContext.GLOBAL_SCOPE);
    }
  }

  public Object getParam(String name) {
    return ActionTool.getParams(name, globalParamMap);
  }

  public void clear() {
    globalParamMap.clear();
    globalParamMap = null;
    funcEngine = null;
  }
}
