package com.xiaodou.course.web.request.product;

import com.xiaodou.course.web.request.BaseRequest;

/**
 * @name @see com.xiaodou.course.web.request.product.PersonProductInfoRequest.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年7月24日
 * @description 用户产品信息请求类
 * @version 1.0
 */
public class PersonProductInfoRequest extends BaseRequest {

  public String getCacheKey() {
    return String.format("{type:personProductInfo,uid:%s}", getUid());
  }

}
