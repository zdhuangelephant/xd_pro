package com.xiaodou.standard.protocol.exception;

import java.io.IOException;

/**
 * @name @see com.xiaodou.standard.protocol.exception.StartUpFailException.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 启动失败异常-IO类型异常
 * @version 1.0
 */
public class StartUpFailException extends IOException {

  /** serialVersionUID */
  private static final long serialVersionUID = 7451818477751107119L;

  /** EXCEPTION_DESC_TMP 异常描述模板 */
  private static final String EXCEPTION_DESC_TMP = "StartUpFailException:[%s][Port:%s]";

  /** DEFAULT_EXCEPTION_DESC 默认异常描述 */
  private static final String DEFAULT_EXCEPTION_DESC = "StartUpFail.";

  public StartUpFailException() {}

  public StartUpFailException(Integer port, IOException srcException) {
    this(DEFAULT_EXCEPTION_DESC, port, srcException);
  }

  protected StartUpFailException(String desc, Integer port, IOException srcException) {
    super(String.format(EXCEPTION_DESC_TMP, desc, port), srcException);
  }
}
