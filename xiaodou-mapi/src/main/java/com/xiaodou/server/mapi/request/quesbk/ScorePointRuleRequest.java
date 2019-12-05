package com.xiaodou.server.mapi.request.quesbk;

import static com.xiaodou.server.mapi.constant.SelfTaughtConstant.MODULE;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.request.MapiBaseRequest;

/**
 * @name @see com.xiaodou.server.mapi.request.quesbk.ScorePointRuleRequest.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月24日
 * @description 计分点规则请求类
 * @version 1.0
 */
public class ScorePointRuleRequest extends MapiBaseRequest {

  @Override
  public String getModule() {
    String version = getVersion();
    if (StringUtils.isBlank(version)) {
      return MODULE;
    }
    int compareTo = version.compareTo("1.5.0");
    if (!(compareTo < 0)) {
      String module = getUserFromWrapper().getRegion();
      // 老用户用新版本
      if (StringUtils.isEmpty(module)) {
        return MODULE;
      } else {
        return module;
      }
    } else {
      return MODULE;
    }
  }
}
