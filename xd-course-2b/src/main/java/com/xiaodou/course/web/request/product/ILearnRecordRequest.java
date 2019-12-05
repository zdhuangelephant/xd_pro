package com.xiaodou.course.web.request.product;

/**
 * @name @see com.xiaodou.course.web.request.product.ILearnRecordRequest.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年8月6日
 * @description 记录学习时长抽象接口
 * @version 1.0
 */
public interface ILearnRecordRequest {

  Integer getLearnTime();

  String getUid();

  String getModule();

  String getTypeCode();

  String getCourseId();

  String getLearnType();

  String getChapterId();

  String getItemId();

}
