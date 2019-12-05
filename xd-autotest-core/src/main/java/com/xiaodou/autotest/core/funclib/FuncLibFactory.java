package com.xiaodou.autotest.core.funclib;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.xiaodou.autotest.core.vo.GlobalParamMap;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.autotest.core.funclib.FuncLibFactory.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月9日
 * @description 脚本方法库工厂
 * @version 1.0
 */
public class FuncLibFactory {

  /**
   * @description 注册脚本结果枚举
   * @version 1.0
   */
  public enum RegistScriptResult {
    /** Success 成功 */
    Success(0),
    /** Fail 失败 */
    Fail(-1);
    RegistScriptResult(Integer code) {
      this.code = code;
    }

    private Integer code;
    private Exception exception;

    public Integer getCode() {
      return code;
    }

    public void setCode(Integer code) {
      this.code = code;
    }

    public Exception getException() {
      return exception;
    }

    public void setException(Exception exception) {
      this.exception = exception;
    }

    public boolean isRetOk() {
      return Success.equals(this);
    }
  }

  /** scriptLib 脚本库 */
  private static final StringBuilder SCRIPT_LIB = new StringBuilder(200);

  /**
   * 注册脚本
   * 
   * @param script 脚本文件
   * @return 注册结果
   */
  public static RegistScriptResult registScript(String script) {
    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine se = sem.getEngineByName("JavaScript");
    try {
      se.eval(script);
      SCRIPT_LIB.append(script);
      return RegistScriptResult.Success;
    } catch (ScriptException e) {
      RegistScriptResult result = RegistScriptResult.Fail;
      result.setException(e);
      return result;
    }
  }

  public static void registGlobalScriptContext(GlobalParamMap globalParam) {
    if (null == SCRIPT_LIB || SCRIPT_LIB.length() == 0) {
      return;
    }
    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine se = sem.getEngineByName("JavaScript");
    try {
      se.eval(SCRIPT_LIB.toString());
      globalParam.setFuncEngine(se);
    } catch (ScriptException e) {
      LoggerUtil.error("FuncLibFactory:注册JS方法库失败.", e);
    }
  }

}
