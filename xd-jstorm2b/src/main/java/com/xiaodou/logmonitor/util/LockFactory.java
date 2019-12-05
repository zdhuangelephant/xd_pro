package com.xiaodou.logmonitor.util;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @name @see com.xiaodou.st.dataclean.util.LockFactory.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月16日
 * @description 锁工厂
 * @version 1.0
 */
public class LockFactory {
  private static final ReentrantLock CATEGORY_PROJECT_LOCK = new ReentrantLock();
 

  public static final void getCreateProjectLock() {
	  CATEGORY_PROJECT_LOCK.lock();
  }

  public static final void returnProjectLock() {
	  CATEGORY_PROJECT_LOCK.unlock();
  }

 
}
