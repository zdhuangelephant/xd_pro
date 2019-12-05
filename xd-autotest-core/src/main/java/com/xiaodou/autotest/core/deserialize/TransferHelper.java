package com.xiaodou.autotest.core.deserialize;

import java.util.List;

import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.xiaodou.autotest.core.deserialize.dmodel.MidAction;
import com.xiaodou.autotest.core.deserialize.dmodel.MidApi;
import com.xiaodou.autotest.core.deserialize.dmodel.MidParam;
import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.autotest.core.model.AfterSet;
import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.autotest.core.model.PreCondition;
import com.xiaodou.autotest.core.model.PreSet;
import com.xiaodou.autotest.core.model.TestCase;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.autotest.core.deserialize.TransferHelper.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月15日
 * @description 转换帮助类:dmodel->model
 * @version 1.0
 */
public class TransferHelper {

  public static List<Action> transferList(List<MidAction> dActionList) {
    if (null == dActionList || dActionList.size() == 0) {
      return null;
    }
    List<Action> actionList = Lists.newArrayList();
    for (MidAction dAction : dActionList) {
      actionList.add(transfer(dAction));
    }
    return actionList;
  }

  public static Action transfer(MidAction dAction) {
    Assert.notNull(dAction, "Transfer Action can't be null");
    Assert.hasText(dAction.getName(), "Transfer Action's name can't be null");
    Assert.hasText(dAction.getBusinessLine(), "Transfer Action's businessLine can't be null");
    Assert.hasText(dAction.getVersion(), "Transfer Action's version can't be null");
    Assert.notNull(dAction.getApiList(), "Transfer Action's apiList can't be null");
    Action a = new Action();
    a.setBusinessLine(dAction.getBusinessLine());
    a.setName(dAction.getName());
    a.setVersion(dAction.getVersion());
    for (MidApi dApi : dAction.getApiList()) {
      a.registApi(transfer(dApi));
    }
    return a;
  }

  private static Api transfer(MidApi dApi) {
    Assert.notNull(dApi, "Transfer Api can't be null");
    Assert.hasText(dApi.getName(), "Transfer Api name can't be null");
    Assert.hasText(dApi.getUniqueId(), "Transfer Api uniqueId can't be null");
    Api a = new Api(dApi.getName());
    a.setFormat(dApi.getFormat());
    a.setApiResult(dApi.getApiResult());
    a.setMethod(dApi.getMethod());
    a.setName(dApi.getName());
    a.setUniqueId(dApi.getUniqueId());
    a.setProtocol(dApi.getProtocol());
    a.setResultDataFormat(dApi.getResultDataFormat());
    a.setRetryTime(dApi.getRetryTime());
    a.setSpecial(dApi.getSpecial());
    a.setStatus(dApi.getStatus());
    a.setTimeOut(dApi.getTimeOut());
    a.setUrl(dApi.getUrl());
    a.setVersion(dApi.getVersion());
    for (MidParam dParam : dApi.getParams()) {
      if (null == dParam) {
        continue;
      }
      a.registParam(dParam.getName(), dParam.getDataType(), dParam.getParamType(),
          dParam.getDesc(), null);
      if (StringUtils.isNotBlank(dParam.getValue())) {
        a.setParamValue(dParam.getName(), dParam.getParamType(), dParam.getValue());
      }
    }
    for (PreCondition preCond : dApi.getPreConds()) {
      if (null == preCond) {
        continue;
      }
      a.pushPreCond(preCond.getKey(), preCond.getTargetValue(), preCond.getCondition());
    }
    for (PreSet preSet : dApi.getPreSets()) {
      if (null == preSet) {
        continue;
      }
      a.pushPreSet(preSet.getKey(), preSet.getDataSource(), preSet.getAssignment(),
          preSet.getScope(), preSet.getDataType());
    }
    for (TestCase testCase : dApi.getTestCases()) {
      if (null == testCase) {
        continue;
      }
      a.pushTestCase(testCase.getName(), testCase.getTestKey(), testCase.getTestValue(),
          testCase.getDataType());
    }
    for (AfterSet afterSet : dApi.getAfterSets()) {
      if (null == afterSet) {
        continue;
      }
      a.pushAfterSet(afterSet.getResultkey(), afterSet.getMappingKey(), afterSet.getDataType());
    }
    return a;
  }
}
