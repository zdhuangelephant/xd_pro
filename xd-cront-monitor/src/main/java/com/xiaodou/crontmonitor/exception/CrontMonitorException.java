package com.xiaodou.crontmonitor.exception;

/**
 * @name @see com.xiaodou.crontmonitor.exception.CrontMonitorException.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月19日
 * @description 异常
 * @version 1.0
 */
public class CrontMonitorException extends RuntimeException {

  /** serialVersionUID  */
  private static final long serialVersionUID = 1652338667063261842L;
  
  /**
   * @name @see com.xiaodou.crontmonitor.exception.CrontMonitorExceptionHandle.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   *
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年12月19日
   * @description 异常类型枚举
   * @version 1.0
   */
  public enum ExceptionType {
    /** NameIsNull 任务名为空异常 */
    NameIsNull("任务名不能为空");
    ExceptionType(String message) {
      this.message = message;
    }
    /** message 异常信息 */
    private String message;
  }
  
  /**
   * 构造方法
   * @param type 异常类型
   */
  public CrontMonitorException(ExceptionType type) {
    super(type.message);
  }

}
