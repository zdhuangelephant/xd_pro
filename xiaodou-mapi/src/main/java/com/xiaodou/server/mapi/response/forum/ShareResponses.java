package com.xiaodou.server.mapi.response.forum;

import com.xiaodou.share.response.ShareResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name ShareResponses 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年12月1日
 * @description 分享响应类
 * @version 1.0
 */
public class ShareResponses extends ShareResponse {
  public ShareResponses() {
    super(ResultType.SUCCESS);
  }

  public ShareResponses(ResultType type) {
    super(type);
  }

}
