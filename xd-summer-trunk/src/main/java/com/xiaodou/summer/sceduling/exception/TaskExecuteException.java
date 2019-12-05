package com.xiaodou.summer.sceduling.exception;

public class TaskExecuteException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6056711224130146342L;

    public TaskExecuteException(Thread currentThread, Throwable t) {
        super(String.format("任务执行异常.Thread : %s", currentThread.getName()), t);
    }

}
