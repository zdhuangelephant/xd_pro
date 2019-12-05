package com.xiaodou.st.dashboard.util;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @name @see com.xiaodou.st.dataclean.util.LockFactory.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月5日
 * @description 锁工厂
 * @version 1.0
 */
public class LockFactory {
  private static final ReentrantLock CATEGORY_SESSION_LOCK = new ReentrantLock();
  private static final ReentrantLock CATEGORY_UNIT_SESSION_LOCK = new ReentrantLock();

  private static final ReentrantLock CATEGORY_UNIT_SESSION_LEARN_LOCK = new ReentrantLock();
  private static final ReentrantLock SESSION_LEARN_LOCK = new ReentrantLock();

  private static final ReentrantLock CATEGORY_UNIT_PRODUCT_AVG_SCORE_PERCENT = new ReentrantLock();
  
  
  /** LOGIN_INFO_DOOR 异步消息用户登陆信息  */
  private static final ReentrantLock LOGIN_INFO_DOOR = new ReentrantLock();
  
  /** ALARM_INFO_DOOR 异步消息用户预警信息  */
  private static final ReentrantLock ALARM_INFO_DOOR = new ReentrantLock();

  public static final void getAlarmInfoDoor() {
    ALARM_INFO_DOOR.lock();
  }
  
  public static final void returnAlarmInfoDoor() {
    ALARM_INFO_DOOR.unlock();
  }
  public static final void getLoginInfoDoor() {
    LOGIN_INFO_DOOR.lock();
  }

  public static final void returnLoginInfoDoor() {
    LOGIN_INFO_DOOR.unlock();
  }
  
  public static final void getCategorySessionLock() {
    CATEGORY_SESSION_LOCK.lock();
  }

  public static final void returnCategorySessionLock() {
    CATEGORY_SESSION_LOCK.unlock();
  }

  public static final void getCategoryUnitSessionLock() {
    CATEGORY_UNIT_SESSION_LOCK.lock();
  }

  public static final void returnCategoryUnitSessionLock() {
    CATEGORY_UNIT_SESSION_LOCK.unlock();
  }

  public static final void getCategoryUnitSessionLearnLock() {
    CATEGORY_UNIT_SESSION_LEARN_LOCK.lock();
  }

  public static final void returnCategoryUnitSessionLearnLock() {
    CATEGORY_UNIT_SESSION_LEARN_LOCK.unlock();
  }

  public static final void getSessionLearnLock() {
    SESSION_LEARN_LOCK.lock();
  }

  public static final void returnSessionLearnLock() {
    SESSION_LEARN_LOCK.unlock();
  }
  public static final void getCategoryUnitProductAvgScorePercent() {
    CATEGORY_UNIT_PRODUCT_AVG_SCORE_PERCENT.lock();
  }

  public static final void returnCategoryUnitProductAvgScorePercent() {
    CATEGORY_UNIT_PRODUCT_AVG_SCORE_PERCENT.unlock();
  }
}
