package com.xiaodou.resources.request.product;

import org.springframework.validation.Errors;

import com.xiaodou.share.request.ShareRequest;
import com.xiaodou.ucenter.agent.toC.util.UserTokenWrapper;
import com.xiaodou.ucenter.response.base.BaseUserModel;

/**
 * @name @see com.xiaodou.course.web.request.product.CourseShareRequest.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 课程分享pojo
 * @version 1.0
 */
public class CourseShareRequest extends ShareRequest {

  public BaseUserModel getUserModel() {
    return UserTokenWrapper.getWrapper().getUserModel();
  }

  @Override
  public Errors validate() {
    Errors errors = super.validate();
    if (errors.hasErrors()) return errors;
    if (!"1".equals(getShareType()) && !"2".equals(getShareType()) && !"5".equals(getShareType()))
      errors.rejectValue("shareType", null, "shareType has a unvalid value, plz check it.");
    return errors;
  }

}
