/*
 * Copyright 2002-2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.xiaodou.summer.sceduling.threadfactory;

import java.util.concurrent.ExecutorService;

/**
 * Base class for classes that are setting up a <code>java.util.concurrent.ExecutorService</code>
 * (typically a {@link java.util.concurrent.ThreadPoolExecutor}). Defines common configuration
 * settings and common lifecycle handling.
 * 
 * @author Juergen Hoeller
 * @see ExecutorService
 * @see java.util.concurrent.Executors
 * @see java.util.concurrent.ThreadPoolExecutor
 * @since 3.0
 */
@Deprecated
public class ExecutorSupportFactory extends TaskThreadFactory {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  private ExecutorService executor;

  private boolean waitForTasksToCompleteOnShutdown = true;

  public ExecutorService getExecutor() {
    return executor;
  }

  public void setExecutor(ExecutorService executor) {
    this.executor = executor;
  }

  /**
   * Set whether to wait for scheduled tasks to complete on shutdown.
   * <p>
   * Default is "false". Switch this to "true" if you prefer fully completed tasks at the expense of
   * a longer shutdown phase.
   * 
   * @see ExecutorService#shutdown()
   * @see ExecutorService#shutdownNow()
   */
  public void setWaitForTasksToCompleteOnShutdown(boolean waitForJobsToCompleteOnShutdown) {
    this.waitForTasksToCompleteOnShutdown = waitForJobsToCompleteOnShutdown;
  }

  /**
   * Calls <code>shutdown</code> when the BeanFactory destroys the task executor instance.
   * 
   * @see #shutdown()
   */
  public void destroy() {
    shutdown();
  }

  /**
   * Perform a shutdown on the ThreadPoolExecutor.
   * 
   * @see ExecutorService#shutdown()
   */
  public void shutdown() {
    if (this.waitForTasksToCompleteOnShutdown) {
      this.executor.shutdown();
    } else {
      this.executor.shutdownNow();
    }
  }

}
