package com.xiaodou.autotest.core.proxy;

import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.model.AfterSet;
import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.autotest.core.model.Param;
import com.xiaodou.autotest.core.model.PreCondition;
import com.xiaodou.autotest.core.model.PreSet;
import com.xiaodou.autotest.core.model.TestCase;
import com.xiaodou.autotest.core.vo.ApiResult;
import com.xiaodou.autotest.core.vo.GlobalParamMap;
import com.xiaodou.autotest.core.vo.SandBoxContext;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.server.mapi.engine.proxy.AbstractApiProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description API组件执行代理
 * @version 1.0
 */
public abstract class AbstractApiProxy {

  /** name api名称 */
  private String name;
  /** api api组件模型 */
  private Api api;
  /** sandBox 沙盒环境 */
  private SandBoxContext sandBox;

  public String getName() {
    if (StringUtils.isNotBlank(name)) {
      return name;
    }
    if (null != api) {
      return api.getName();
    }
    return ActionConstant.DEFAULT_API_NAME;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Api getApi() {
    return api;
  }

  public void setApi(Api api) {
    this.api = api;
  }

  /**
   * 执行点拓展方法
   * 
   * @return Api执行结果
   */
  public abstract ApiResult excute0();

  public void excute(GlobalParamMap global) {
    this.sandBox = new SandBoxContext(global);
    if (null != api.getPreConds() && api.getPreConds().size() > 0) {
      for (PreCondition preCondition : api.getPreConds()) {
        if (preCondition.validate(sandBox)) {
          continue;
        }
        return;
      }
    }
    if (null != api.getPreSets() && api.getPreSets().size() > 0) {
      for (PreSet preSet : api.getPreSets()) {
        preSet.setVar(sandBox);
      }
    }
    if (null != api.getParams() && api.getParams().size() > 0) {
      for (Param param : api.getParams()) {
        param.fillParam(sandBox);
      }
    }
    ApiResult apiResult = excute0();
    apiResult.setApiUniqueId(api.getUniqueId());
    apiResult.setVar(sandBox);
    if (null != api.getTestCases() && api.getTestCases().size() > 0) {
      for (TestCase testCase : api.getTestCases()) {
        testCase.checkVar(sandBox);
      }
    }
    if (null != api.getAfterSets() && api.getAfterSets().size() > 0) {
      for (AfterSet afterSet : api.getAfterSets()) {
        afterSet.setVar(sandBox);
      }
    }
    api.setApiResult(apiResult);
    api.markExcute();
    sandBox.clear();
  }
}
