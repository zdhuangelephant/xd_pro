package com.xiaodou.summer.sceduling.concurrent;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.exception.SummerTaskTimeoutException;

/**
 * SummerTask基础抽象实现类 自定义Task需要继承该抽象类
 * 
 * @author zhaodan
 */
public abstract class SummerTask implements ISummerTask {

  private static final Long TIME_UNIT = 1000L;

  public SummerTask() {
    this(0);
  }

  public SummerTask(long validaty) {
    if (validaty < 0)
      throw new IllegalArgumentException(String.format("Illegal Value %d For Validaty.", validaty));
    this._startPoint = System.currentTimeMillis();
    this._validaty = validaty;
  }

  private long _startPoint;
  private long _validaty;

  public void setValidaty(long validaty) {
    this._validaty = validaty * TIME_UNIT;
  }

  public final boolean check() {
    long currentTimeMillis = System.currentTimeMillis();
    if (this._validaty > 0 && ((this._startPoint + this._validaty) < currentTimeMillis)) {
      LoggerUtil.error("summer task 任务超时", new SummerTaskTimeoutException(Thread.currentThread(),
          _startPoint, _validaty, currentTimeMillis - _startPoint));
      return false;
    }
    return true;
  }
}
