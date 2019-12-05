package com.xiaodou.share.service;

import com.xiaodou.share.prop.ShareProp;
import com.xiaodou.share.request.ShareRequest;
import com.xiaodou.share.response.ShareResponse;

/**
 * @name @see com.xiaodou.share.service.AbstractShareService.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 主干共享service方法
 * @version 1.0
 */
public abstract class AbstractShareService<T extends ShareRequest> {

  /**
   * 拓展处理shareResonse方法
   * 
   * @param response
   * @return
   */
  public abstract ShareResponse processResponse(T request, ShareResponse response);

  public ShareResponse doMain(String module, T request) {
    return processResponse(request, ShareProp.getResponse(module, request.getShareType()));
  }

}
