package com.xiaodou.crontab.engine.constant;

/**
 * @name @see com.xiaodou.crontab.engine.constant.CrontConstant.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月2日
 * @description 常量类
 * @version 1.0
 */
public interface CrontConstant {
  /** JOB_FIRST_VERSION 任务初始化版本号 */
  public static final int JOB_FIRST_VERSION = 1;
  /** JOB_PREPARE 任务状态-待执行 */
  public static final short JOB_PREPARE = 0;
  /** JOB_FINISH 任务执行-已完成 */
  public static final short JOB_FINISH = 1;
  /** CONFIG_INUSE 配置项-使用中 */
  public static final Integer CONFIG_INUSE = 1;
  /** CONFIG_UNUSE 配置项-未使用 */
  public static final Integer CONFIG_UNUSE = 0;
  /** TRUE 是 */
  public static final int TRUE = 1;
}
