package com.xiaodou.im.response;

/**
 * @name @see com.xiaodou.im.vo.IMResponse.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月2日
 * @description 响应
 * @version 1.0
 */
public class IMResponse {

  /**
   * @name @see com.xiaodou.im.vo.IMResponseType.java
   * @CopyRright (c) 2016 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年2月2日
   * @description 响应类型
   * @version 1.0
   */
  public enum IMResponseType {
    SUCCESS, FAIL, DUPLICATE_USER
  }
  
  public IMResponse(IMResponseType type) {
    this.type = type;
  }
  
  /** type 响应类型 */
  private IMResponseType type;

  public IMResponseType getType() {
    return type;
  }

  public void setType(IMResponseType type) {
    this.type = type;
  }

  public boolean isRetOK() {
    return IMResponseType.SUCCESS.equals(type);
  }

  /** message 描述信息 */
  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
