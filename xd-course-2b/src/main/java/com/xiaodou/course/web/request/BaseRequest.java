package com.xiaodou.course.web.request;

import com.xiaodou.summer.source.jdbc.helper.RealSqlSessionKeyHolder;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * @name @see com.xiaodou.course.web.request.BaseRequest.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 *
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2016年5月6日
 * @description 用户相关属性
 * @version 1.0
 */
public class BaseRequest extends BaseValidatorPojo {

  @NotEmpty
  private String uid;

  @NotEmpty
  private String module;
  
  @NotEmpty
  private String typeCode;

  private String majorId;

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
//    setRealSqlSessionKey(module);
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }

  public void setBaseRequest(BaseRequest pojo) {
    if (null == pojo) return;
    this.uid = pojo.getUid();
    this.module = pojo.getModule();
    this.typeCode = pojo.getTypeCode();
  }
  
  /**
   * @description 设置多数据源key
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年6月20日
   * @param module
   */
  public void setRealSqlSessionKey(String module) {
      RealSqlSessionKeyHolder.getHolder().setIsUsekey(true);
      RealSqlSessionKeyHolder.getHolder().setRealSqlSessionKey(module);
  }

}
