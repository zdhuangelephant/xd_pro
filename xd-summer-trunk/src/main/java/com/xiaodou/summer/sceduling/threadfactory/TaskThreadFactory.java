package com.xiaodou.summer.sceduling.threadfactory;

import java.io.Serializable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.sceduling.exception.ITaskErrorHandler;
import com.xiaodou.summer.sceduling.exception.TaskErrorHandler;

/**
 * @name @see com.xiaodou.common.scedule.threadfactory.TaskThreadFactory.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月27日
 * @description 任务线程工厂
 * @version 1.0
 */
@Deprecated
public class TaskThreadFactory implements Serializable, ThreadFactory {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  private String threadNamePrefix = getDefaultThreadNamePrefix();

  private int threadPriority = Thread.NORM_PRIORITY;

  private boolean daemon = false;

  private ThreadGroup threadGroup = (System.getSecurityManager() != null) ? System
      .getSecurityManager().getThreadGroup() : Thread.currentThread().getThreadGroup();

  private AtomicInteger threadCount = new AtomicInteger(0);

  private boolean threadNamePrefixSet = false;

  private ITaskErrorHandler taskErrorHandler = new TaskErrorHandler();


  /**
   * Create a new CustomizableThreadCreator with default thread name prefix.
   */
  public TaskThreadFactory() {}

  public TaskThreadFactory(ThreadGroup threadGroup, String threadNamePrefix) {
    setThreadGroup(threadGroup);
    setThreadNamePrefix(threadNamePrefix);
  }

  public TaskThreadFactory(String threadNamePrefix) {
    this(null, threadNamePrefix);
  }

  public TaskThreadFactory(ThreadGroup threadGroup) {
    this(threadGroup, null);
  }

  /**
   * Specify the prefix to use for the names of newly created threads. Default is
   * "SimpleAsyncTaskExecutor-".
   */
  public void setThreadNamePrefix(String threadNamePrefix) {
    if (StringUtils.isNotBlank(threadNamePrefix)) {
      this.threadNamePrefix = threadNamePrefix;
      this.threadNamePrefixSet = true;
    }
  }

  /**
   * Return the thread name prefix to use for the names of newly created threads.
   */
  public String getThreadNamePrefix() {
    return this.threadNamePrefix;
  }

  /**
   * Set the priority of the threads that this factory creates. Default is 5.
   * 
   * @see Thread#NORM_PRIORITY
   */
  public void setThreadPriority(int threadPriority) {
    this.threadPriority = threadPriority;
  }

  /**
   * Return the priority of the threads that this factory creates.
   */
  public int getThreadPriority() {
    return this.threadPriority;
  }

  /**
   * Set whether this factory is supposed to create daemon threads, just executing as long as the
   * application itself is running.
   * <p>
   * Default is "false": Concrete factories usually support explicit cancelling. Hence, if the
   * application shuts down, Runnables will by default finish their execution.
   * <p>
   * Specify "true" for eager shutdown of threads which still actively execute a Runnable.
   * 
   * @see Thread#setDaemon
   */
  public void setDaemon(boolean daemon) {
    this.daemon = daemon;
  }

  /**
   * Return whether this factory should create daemon threads.
   */
  public boolean isDaemon() {
    return this.daemon;
  }

  /**
   * Specify the name of the thread group that threads should be created in.
   * 
   * @see #setThreadGroup
   */
  public void setThreadGroupName(String name) {
    this.threadGroup = new ThreadGroup(name);
  }

  /**
   * Specify the thread group that threads should be created in.
   * 
   * @see #setThreadGroupName
   */
  public void setThreadGroup(ThreadGroup threadGroup) {
    if (null != threadGroup) {
      this.threadGroup = threadGroup;
    }
  }

  /**
   * Return the thread group that threads should be created in (or <code>null</code>) for the
   * default group.
   */
  public ThreadGroup getThreadGroup() {
    return this.threadGroup;
  }


  /**
   * Template method for the creation of a Thread.
   * <p>
   * Default implementation creates a new Thread for the given Runnable, applying an appropriate
   * thread name.
   * 
   * @param runnable the Runnable to execute
   * @see #nextThreadName()
   */
  public Thread newThread(Runnable runnable) {
    Thread thread = new Thread(getThreadGroup(), runnable, nextThreadName());
    thread.setPriority(threadPriority);
    thread.setDaemon(daemon);
    thread.setUncaughtExceptionHandler(taskErrorHandler);
    return thread;
  }

  /**
   * Return the thread name to use for a newly created thread.
   * <p>
   * Default implementation returns the specified thread name prefix with an increasing thread count
   * appended: for example, "SimpleAsyncTaskExecutor-0".
   * 
   * @see #getThreadNamePrefix()
   */
  protected String nextThreadName() {
    return getThreadNamePrefix() + threadCount.incrementAndGet();
  }

  /**
   * Build the default thread name prefix for this factory.
   * 
   * @return the default thread name prefix (never <code>null</code>)
   */
  protected String getDefaultThreadNamePrefix() {
    return getClass().getSimpleName() + "-";
  }


  /**
   * Set the RejectedExecutionHandler to use for the ThreadPoolExecutor. Default is the
   * ThreadPoolExecutor's default abort policy.
   * 
   * @see java.util.concurrent.ThreadPoolExecutor.AbortPolicy
   */
  public void setTaskErrorHandler(ITaskErrorHandler taskErrorHandler) {
    if (null != taskErrorHandler) {
      this.taskErrorHandler = taskErrorHandler;
    }
  }

  public ITaskErrorHandler getTaskErrorHandler() {
    return taskErrorHandler;
  }

  public boolean isThreadNamePrefixSet() {
    return threadNamePrefixSet;
  }
}
