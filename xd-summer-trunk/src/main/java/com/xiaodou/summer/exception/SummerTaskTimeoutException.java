package com.xiaodou.summer.exception;

import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @Location xd-summer/com.xiaodou.summer.exception.SummerTaskTimeoutException.java.java
 * @Description 任务超时异常
 * @Author <a href="mailto:zhaodan@corp.51xiaodou.com">Administrator</a>
 * @Date 上午1:19:20
 */
public class SummerTaskTimeoutException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8665534678998540108L;

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
			return FastJsonUtil.toJson(this);
		}

		TaskInfo(Thread t, long startTime, long validTime, long queuingTime) {
			this.queuingTime = queuingTime;
			this.startTime = startTime;
			this.validTime = validTime;
			this.threadName = t.getName();
		}
	}

	public SummerTaskTimeoutException(Thread t, long startTime, long validTime,
			long queuingTime) {
		super(String.format("SummerTaskExecutor 执行任务超时.TaskInfo:%s",
				new TaskInfo(t, startTime, validTime, queuingTime).toString()));
	}

}
