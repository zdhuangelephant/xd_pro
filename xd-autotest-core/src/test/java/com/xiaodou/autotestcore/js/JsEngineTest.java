package com.xiaodou.autotestcore.js;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @name @see com.xiaodou.autotestcore.js.JsEngineTest.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月9日
 * @description Js引擎测试类
 * @version 1.0
 */
public class JsEngineTest {
  public static void main(String[] args) throws ScriptException {
    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine se = sem.getEngineByName("JavaScript");
    se.eval("function test(name, age){return name + \"'s age is \" + age;}");
    se.getContext().setAttribute("name", "zhaodan", ScriptContext.GLOBAL_SCOPE);
    se.getContext().setAttribute("age", "28", ScriptContext.ENGINE_SCOPE);
    se.getContext().setAttribute("age", "21", ScriptContext.GLOBAL_SCOPE);
    System.out.println(se.eval("test(name, age)"));
    se.getContext().removeAttribute("age", ScriptContext.ENGINE_SCOPE);
    System.out.println(se.eval("test(name, age)"));
  }
}
