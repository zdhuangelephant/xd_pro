package com.xiaodou.common.info.ding.req;

import java.util.List;

import lombok.Data;

import com.google.common.collect.Lists;
import com.xiaodou.common.info.ding.IDingReq;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.common.info.ding.req.AbstractgDingReq.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知消息参数类公共逻辑实现
 * @version 1.0
 */
@Data
public abstract class AbstractDingReq implements IDingReq {

  public AbstractDingReq(List<String> urls) {
    this.urls = urls;
  }

  /** urls 钉钉机器人列表 */
  private List<String> urls = Lists.newArrayList();

  public abstract boolean check();

  @Override
  public String info() {
    if (check()) {
      return FastJsonUtil.toJson(this);
    } else {
      throw new IllegalArgumentException("DingMes:Check Param Fail.");
    }
  }

  @Override
  public List<String> getUrls() {
    return urls;
  }

}
