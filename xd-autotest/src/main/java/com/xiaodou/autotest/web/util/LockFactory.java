package com.xiaodou.autotest.web.util;

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
  private static final ReentrantLock HEADER_GROUP_LOCK = new ReentrantLock();
  private static final ReentrantLock HEADER_DETAIL_LOCK = new ReentrantLock();


  public static final void getHeaderGroupLock() {
	  HEADER_GROUP_LOCK.lock();
  }

  public static final void returnHeaderGroupLock() {
	  HEADER_GROUP_LOCK.unlock();
  }

  public static final void getHeaderDetailLock() {
	  HEADER_DETAIL_LOCK.lock();
  }

  public static final void returnHeaderDetailLock() {
	  HEADER_DETAIL_LOCK.unlock();
  }

}
