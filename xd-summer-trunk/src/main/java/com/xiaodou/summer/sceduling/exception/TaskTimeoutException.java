/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaodou.summer.sceduling.exception;

import com.alibaba.fastjson.JSON;

/**
 * @author Juergen Hoeller
 * @see TaskRejectedException
 * @since 2.0.3
 */
public class TaskTimeoutException extends TaskRejectedException {

    /**serialVersionUID */
  private static final long serialVersionUID = 1L;

    /**
     * Create a new <code>TaskTimeoutException</code>
     * with the specified detail message and no root cause.
     *
     * @param msg the detail message
     */
    public TaskTimeoutException(String msg) {
        super(msg);
    }

    /**
     * Create a new <code>TaskTimeoutException</code>
     * with the specified detail message and the given root cause.
     *
     * @param msg   the detail message
     * @param cause the root cause (usually from using an underlying
     *              API such as the <code>java.util.concurrent</code> package)
     * @see java.util.concurrent.RejectedExecutionException
     */
    public TaskTimeoutException(String msg, Throwable cause) {
        super(msg, cause);
    }

    private static class TaskInfo {
        @SuppressWarnings("unused")
        String threadName;
        @SuppressWarnings("unused")
        long startTime;
        @SuppressWarnings("unused")
        long validTime;
        @SuppressWarnings("unused")
        long queuingTime;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

        TaskInfo(Thread t, long startTime, long validTime, long queuingTime) {
            this.queuingTime = queuingTime;
            this.startTime = startTime;
            this.validTime = validTime;
            this.threadName = t.getName();
        }
    }

    public TaskTimeoutException(Thread t, long startTime, long validTime, long queuingTime) {
        super(String.format("SummerTaskExecutor 执行任务超时.TaskInfo:%s", new TaskInfo(t, startTime, validTime, queuingTime).toString()));
    }

}
