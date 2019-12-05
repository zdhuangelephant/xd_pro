package com.xiaodou.mission.constants;

import java.text.DecimalFormat;

/**
 * @name @see com.xiaodou.mission.constants.MissionConstant.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 任务系统常量接口
 * @version 1.0
 */
public interface MissionConstant {

  /** D_FORMAT double format */
  public static final DecimalFormat D_FORMAT = new DecimalFormat("######0.00");

  /** DEFAULT_THROHOLD_RECHED 默认达成阀值 */
  public static final String DEFAULT_THROHOLD_RECHED = "1";

  /** DEFAULT_THROHOLD_UNRECHED 默认未达成阈值 */
  public static final String DEFAULT_THROHOLD_UNRECHED = "0";

  /** TRUE 逻辑:真 */
  public static final Integer TRUE = 1;

  /** FALSE 逻辑:假 */
  public static final Integer FALSE = 0;

  /** PASS_LINE 默认及格线 */
  public static final Double PASS_LINE = 60d;

  /** PRODUCT_RELATION_INUSE 产品专业关系:有效 */
  public static final Integer PRODUCT_RELATION_INUSE = 1;

  /** PRODUCT_RELATION_UNUSE 产品专业关系:无效 */
  public static final Integer PRODUCT_RELATION_UNUSE = 0;

  /** MISSON_STATE_INUSE 任务状态 有效 */
  public static final Short MISSON_STATE_INUSE = 1;

  /** MISSON_STATE_UNUSE 任务状态 无效 */
  public static final Short MISSON_STATE_UNUSE = 0;

  /** MISSION_RECORD_STATUS_CREATE 用户任务记录状态:创建 */
  public static final Integer MISSION_RECORD_STATUS_CREATE = 1;

  /** MISSION_RECORD_STATUS_MODIFY 用户任务记录状态:修改 */
  public static final Integer MISSION_RECORD_STATUS_MODIFY = 2;

  public static final String CREDIT_CHANGE_TYPE_MISSION_FINISH = "任务完成";

  /** USER_DATA_TYPE_PK 数据类型PK */
  public static final String USER_DATA_TYPE_PK = "1";

  /** USER_DATA_TYPE_TOLLGATE 数据类型闯关 */
  public static final String USER_DATA_TYPE_TOLLGATE = "2";

  /** DELAY_TASK 延迟任务 */
  public static final Integer DELAY_TASK = -7;

  /** TASK_STATUS_UNREACH 任务状态-未完成 */
  public static final Integer TASK_STATUS_UNREACH = 0;

  /** TASK_STATUS_REACHED 任务状态-已完成 */
  public static final Integer TASK_STATUS_REACHED = 1;

  /** TASK_STATUS_RECEIVED 任务状态-已领取 */
  public static final Integer TASK_STATUS_RECEIVED = 2;

  /** TASK_STATUS_UNDISPLAY 任务状态-不展示 */
  public static final Integer TASK_STATUS_UNDISPLAY = -9;

  /** COMMON_MODULE 通用模块 */
  public static final String COMMON_MODULE = "-1";

  /** COMMON_MAJOR_ID 通用专业 */
  public static final String COMMON_MAJOR_ID = "-1";

  /** COMMON_COURSE_ID 通用课程ID */
  public static final String COMMON_COURSE_ID = "-1";

  /** COMMON_COURSE_NAME 通用课程名 */
  public static final String COMMON_COURSE_NAME = "系统任务";

  /** RECORD_STATUS_INEFFECTIVE 记录状态-自动生成 */
  public static final Integer RECORD_STATUS_AUTO = 0;

  /** RECORD_STATUS_NORMAL 记录状态-正常 */
  public static final Integer RECORD_STATUS_NORMAL = 1;

  /** RECORD_STATUS_BACKUP 记录状态-归档 */
  public static final Integer RECORD_STATUS_BACKUP = 99;

  /** ASYNC_SYSTEM_MESSAGE 系统消息异步消息名 */
  public final static String ASYNC_SYSTEM_MESSAGE = "systemmessage";

  /********************************** Number ************************************/
  /** INTEGER_ZERO 0 */
  public final static Integer INTEGER_ZERO = 0;
  /** DOUBLE_ZERO 0 */
  public final static Double DOUBLE_ZERO = 0d;
}
