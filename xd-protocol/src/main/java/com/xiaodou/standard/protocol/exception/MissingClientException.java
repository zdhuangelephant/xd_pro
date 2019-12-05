package com.xiaodou.standard.protocol.exception;

/**
 * @name @see com.xiaodou.standard.protocol.exception.MissingClientException.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月12日
 * @description 客户端缺失异常
 * @version 1.0
 */
public class MissingClientException extends RuntimeException {

  /** serialVersionUID */
  private static final long serialVersionUID = 7630209256055434320L;

  public MissingClientException() {}

  public MissingClientException(String message) {
    super(message);
  }
}
