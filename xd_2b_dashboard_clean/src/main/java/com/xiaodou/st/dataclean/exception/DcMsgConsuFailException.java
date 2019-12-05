package com.xiaodou.st.dataclean.exception;


/**
 * @name @see com.xiaodou.st.dataclean.exception.DcMsgConsuFailException.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年3月14日
 * @description 数据清洗消息消费失败异常
 * @version 1.0
 */
public class DcMsgConsuFailException extends RuntimeException {

  /** serialVersionUID */
  private static final long serialVersionUID = 3471798414784830396L;
  /** EVENT_BODY_NULL 消息体为空 */
  private static final String EVENT_BODY_NULL = "数据清洗消息异常.[Type:消息体为空]";
  /** UNKNOW_EVENT_TYPE 无法确定事件类型 */
  private static final String UNKNOW_EVENT_TYPE = "数据清洗消息异常.[Type:无法确定事件类型]";
  /** SRC_RECORD_NULL 源记录为空 */
  private static final String SRC_RECORD_NULL = "数据清洗消息异常.[Type:源记录为空]";
  /** CORE_PARAM_NULL 核心参数为空 */
  private static final String CORE_PARAM_NULL = "数据清洗消息异常.[Type:核心参数为空][Param:%s]";
  /** STUDENT_INFO_NULL 学生信息为空 */
  private static final String STUDENT_INFO_NULL = "数据清洗消息异常.[Type:学生信息为空][UserId:%s]";
  /** PILOT_INFO_NULL 试点单位为空 */
  private static final String PILOT_INFO_NULL = "数据清洗消息异常.[Type:试点单位为空][PilotUnitId:%s]";
  /** PRO_CATE_NULL_BY_TC 专业为空 */
  private static final String PRO_CATE_NULL_BY_TC = "数据清洗消息异常.[Type:专业为空][TypeCode:%s]";
  /** PRO_CATE_NULL_BY_ID 专业为空 */
  private static final String PRO_CATE_NULL_BY_ID = "数据清洗消息异常.[Type:专业为空][MajorId:%s]";
  /** PRO_INFO_NULL 课程产品为空 */
  private static final String PRO_INFO_NULL = "数据清洗消息异常.[Type:课程产品为空][ProductId:%s]";
  /** PRO_APPLY_TIME_CHECK_FAIL 课程产品考期校验失败 */
  private static final String PRO_APPLY_TIME_CHECK_FAIL =
      "数据清洗消息异常.[Type:课程产品考期校验失败][BeginApplyTime:%s][EndApplyTime:%s]";
  /** APPLY_INFO_CHECK_FAIL 报名信息校验失败 */
  private static final String APPLY_INFO_CHECK_FAIL =
      "数据清洗消息异常.[Type:报名信息校验失败][StudentId:%s][ProductCategoryId:%s]";
  /** TAUGHT_INFO_CHECK_FAIL 自考办信息校验失败 */
  private static final String TAUGHT_INFO_CHECK_FAIL = "数据清洗消息异常.[Type:自考办信息校验异常]";
  /** PRO_CATE_CHEF_FAIL 主考院校专业挂载异常 */
  private static final String PRO_CATE_CHEF_FAIL =
      "数据清洗消息异常.[Type:主考院校专业挂载异常][ProductCategoryId:%s][ChefUnitId:%s]";
  
  /** USER_SCOREPOINT_FAIL 用户积分点条目明细挂载信息异常*/
  private static final String USER_SCOREPOINT_FAIL =
      "数据清洗消息异常.[Type:用户积分点条目明细挂载信息异常][module:%s][courseId:%s][ChefUnitId:%s][userId:%s][ruleType:%s]";

  /**
   * 私有构造方法
   * 
   * @param temp 异常信息挂载模板
   * @param params 异常信息填充参数
   */
  private DcMsgConsuFailException(String temp, Object... params) {
    super(String.format(temp, params));
  }

  private Boolean onlyLog = Boolean.FALSE;

  public Boolean getOnlyLog() {
    return onlyLog;
  }

  public void setOnlyLog(Boolean onlyLog) {
    this.onlyLog = onlyLog;
  }

  /**
   * 消息体为空
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException eventBodyNull() {
    return new DcMsgConsuFailException(EVENT_BODY_NULL);
  }

  /**
   * 无法确定事件类型
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException unknowEventType() {
    return new DcMsgConsuFailException(UNKNOW_EVENT_TYPE);
  }

  /**
   * 空记录
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException srcRecordNull() {
    return new DcMsgConsuFailException(SRC_RECORD_NULL);
  }

  /**
   * 核心参数为空
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException coreParamNull(Object paramName) {
    return new DcMsgConsuFailException(CORE_PARAM_NULL, paramName);
  }

  /**
   * 学生信息为空
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException studentInfoNull(Object userId) {
    DcMsgConsuFailException dcMsgConsuFailException =
        new DcMsgConsuFailException(STUDENT_INFO_NULL, userId);
    // 设置只记录Alarm日志
    dcMsgConsuFailException.setOnlyLog(Boolean.TRUE);
    return dcMsgConsuFailException;
  }

  /**
   * 试点单位信息为空
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException pilotInfoNull(Object pilotUnitId) {
    return new DcMsgConsuFailException(PILOT_INFO_NULL, pilotUnitId);
  }

  /**
   * 专业信息为空
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException proCateNullByTypeCode(Object typeCode) {
    return new DcMsgConsuFailException(PRO_CATE_NULL_BY_TC, typeCode);
  }

  /**
   * 专业信息为空
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException proCateNullById(Object id) {
    return new DcMsgConsuFailException(PRO_CATE_NULL_BY_ID, id);
  }

  /**
   * 课程产品信息为空
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException proInfoNull(Object productId) {
    return new DcMsgConsuFailException(PRO_INFO_NULL, productId);
  }

  /**
   * 产品考期信息校验异常
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException proApplyTimeCheckFail(Object beginApplyTime,
      Object endApplyTime) {
    return new DcMsgConsuFailException(PRO_APPLY_TIME_CHECK_FAIL, beginApplyTime, endApplyTime);
  }

  /**
   * 报名信息校验异常
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException applyInfoCheckFail(Object studentId,
      Object productCategoryId) {
    return new DcMsgConsuFailException(APPLY_INFO_CHECK_FAIL, studentId, productCategoryId);
  }

  /**
   * 自考办信息校验异常
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException taughtInfoCheckFail() {
    return new DcMsgConsuFailException(TAUGHT_INFO_CHECK_FAIL);
  }

  /**
   * 主考院校专业挂载信息异常
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException proCateChefFail(Object productCategoryId, Object chefUnitId) {
    return new DcMsgConsuFailException(PRO_CATE_CHEF_FAIL, productCategoryId, chefUnitId);
  }
  
  /**
   * 用户积分点条目明细挂载信息异常
   * 
   * @return 数据清洗消息消费失败异常
   */
  public static DcMsgConsuFailException userScorePointFail(Object module,Object courseId,Object chefUnitId,Object userId) {
    return new DcMsgConsuFailException(USER_SCOREPOINT_FAIL, module, courseId,chefUnitId,userId);
  }
}
