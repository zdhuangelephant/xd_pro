package com.xiaodou.st.dashboard.constants;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.xiaodou.st.dashboard.util.StandardProp;

public class Constants {

  public static final String MODULE = "2";
  /**USER 分割符*/
  public static final String USER = ":";
  /* WAITINGPAYMENT 待付款 */
  public static final short WAITINGPAYMENT = 0;
  /* WAITINGPAYMENT 已付款 */
  public static final short ALREADYPAYMENT = 1;
  /* NONPAYMENT 未付款 */
  public static final short NOTPAYMENT = 2;
  /* 关闭 */
  public static final Short CLOSEPAYMENT = -1;
  /** CANDELETESTUDENT 可以删除学生 */
  public static final Short CANDELETESTUDENT = 0;
  /** CANNOTDELETESTUDENT 不可以删除该学生 */
  public static final Short CANNOTDELETESTUDENT = 1;
  /* student */
  /* 学生状态 0、未注册，1、注册成功，2、注册失败，已经存在该学生3、注册异常，4、成功导入*/
  public static final short NOT_REGISTER = 0;
  public static final short SUCCESS_REGISTER = 1;
  public static final short FAIL_REGISTER = 2;
  public static final short ERROR_REGISTER = 3;
  public static final short SUCCESS_IMPORT = 4;

  /* 课程是否报名(0,沒有報名的課程 1,已經報名的課程) */
  public static final Short NO_APPLY = 0;
  public static final Short HAS_APPLY = 1;

  /* 角色1、第一级单位，2、第二级单位，3、第三级单位  4、超级管理员 */
  public static final Short SELF_TAUGHT_UNIT_ROLE = 1;
  public static final Short CHIEF_UNIT_ROLE = 2;
  public static final Short POILT_UNIT_ROLE = 3;
  public static final Short MANAGE_UNIT_ROLE = 4;
  
  public static final Long SELF_TAUGHT_UNIT_ID = 1l;

  /*子权限（现只有第三级单位用到）0、默认权限1、子权限*/
  public static final Short POILT_UNIT_PARENT_ROLE = 0; 
  public static final Short POILT_UNIT_CHILD_ROLE = 1; 
  
  /*
   * learnType;// 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题,51每日一练）
   */
  public static final String LearnType_11 = "11";
  public static final String LearnType_12 = "12";
  public static final String LearnType_21 = "21";
  public static final String LearnType_22 = "22";
  public static final String LearnType_31 = "31";
  public static final String LearnType_41 = "41";
  public static final String LearnType_51 = "51";

  public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("######0.00");
  

  // 获取格式化对象
  public static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();

  public static final NumberFormat SCORE_FORMAT = NumberFormat.getNumberInstance();
  static {
    PERCENT_FORMAT.setMinimumFractionDigits(2);
    SCORE_FORMAT.setMaximumFractionDigits(2);
  }
  

  /* 读取状态(0:未读，1:已读) */
  public static final Short HAS_READ_STATUS = 1;
  public static final Short UN_READ_STATUS = 0;

  /* 系统预判结果 1:是本人0::不是本人 */
  public static final Short IS_RESULT = 1;
  public static final Short IS_NOT_RESULT = 0;

  /*报名状态（0、后台报名完成1、业务系统报名成功2、已经购买该课程3、报名异常）*/
  public static final short APPLY_SUCCESS = 0;
  public static final short BUSINESS_APPLY_SUCCESS = 1;
  public static final short BUSINESS_APPLY_ALREADY = 2;
  public static final short BUSINESS_APPLY_ERROR = 3;
  
  /*TriggerType 触发报警的类型 0：登录 1：人脸识别*/
  public static final short LOGIN_TRIGGER=0;
  public static final short FACE_TRIGGER=1;
  
  public static final Short ROLE_ON = 0;
  public static final Short ROLE_OFF = 1;
  
  public static final Integer PRIVILEGE_SHOW = 1;

  public static final Integer PRIVILEGE_HIDDEN = 0;
  
  public static final String TARGETTYPE ="iframe-tab";
  
  public static final String MAN ="男";
  public static final String WOMAN ="女";
  
  /**************************** Error Info ****************************/
  public static final String ERROR_CLASSNAME_EMPTY = "班级名称为空，";
  public static final String ERROR_NAME_EMPTY = "姓名为空，";
  public static final String ERROR_GENDER_EMPTY = "性别为空，";
  public static final String ERROR_GENDER_ERROR = "性别不符合规范，";
  public static final String ERROR_PHONENUM_EMPTY = "手机号为空，";
  public static final String ERROR_PHONENUM_EXIST = "手机号已经存在";
  public static final String ERROR_PHONENUM_ERROR = "手机号不符合规范";
  public static final String ERROR_PHONENUM_EXIST_EXCEL = "手机号多次出现";
  public static final String ERROR_CARD_ERROR = "准考证号不符合规范(为12位有效数字) ";
  public static final String ERROR_CARD_EXIST = "准考证号已存在";
  public static final String ERROR_CARD_ERROR_EXCEL = "准考证号多次出现";
  /**************************** Error Info ****************************/
  
  /* 同步类型 1、同步学生2、同步课程3、同步准考证号 */
  public static final short SYNC_STUDENT =1;
  public static final short SYNC_APPLY = 2;
  public static final short SYNC_CARD = 3;
  
  /*0、不可以批量刪除1、可以批量刪除 */
  public static final Short CANOT_BATCH_DEL = 0;
  public static final Short CAN_BATCH_DEL = 1;
  
  
  public static final Short SHOW_STATUS_YES = 1;
  public static final Short SHOW_STATUS_NO = 0;
  
  
  /**************************** Error Info ****************************/
  
  public static final String PROMPT_HEADERERROR_MSG = "请严格按照下载列表填写上传  ";
  public static final String UNIQUE_SEPEATEOR = "@xiaodou@";
  
  public static final String PROMPT_SCORE_ERROR_NAME_EMPTY = "姓名为空  ";
  public static final String PROMPT_SCORE_ERROR_CARD_EMPTY = "准考证号为空  ";
  public static final String PROMPT_SCORE_ERROR_CARD_NOTEXISTS = "准考证号不存在  ";
  
  public static final String PROMPT_SCORE_ERROR_POILT_UNIT_NOTEXISTS = String.format("学生所在%s不存在  ", StandardProp.pilotUnitName());
  public static final String PROMPT_SCORE_ERROR_POILT_UNIT_MATCH = "该学生不属于本单位  ";
  
  public static final String PROMPT_SCORE_ERROR_CATCODE_EMPTY = "专业代码为空  ";
  public static final String PROMPT_SCORE_ERROR_CATCODE_ERROR = "专业代码不存在  ";
  public static final String PROMPT_SCORE_ERROR_CATCODE_CATNAME_MISMATCH = "专业名称与该专业代码不匹配  ";
  
  public static final String PROMPT_SCORE_ERROR_CATNAME_EMPTY = "专业名称为空  ";
  
  public static final String PROMPT_SCORE_ERROR_PRODUCT_EMPTY = "课程代码为空  ";
  public static final String PROMPT_SCORE_ERROR_PRODUCT_NOTEXISTS = " 课程代码不存在  ";
  
  public static final String PROMPT_SCORE_ERROR_PRODUCT_PRODUCTNAME_MISMATCH = "课程名称与该课程代码不匹配  ";
  public static final String PROMPT_SCORE_ERROR_PRODUCTNAME_EMPTY = "课程名称为空  ";
  public static final String PROMPT_SCORE_ERROR_PRODUCTNAME_NOTEXISTS = "课程名称不存在 ";
  
  public static final String PROMPT_SCORE_ERROR_DAILYSCORE_EMPTY = "线下成绩不能为空   ";
  public static final String PROMPT_SCORE_ERROR_DAILYSCORE_ERROR = "线下成绩输入错误(须是阿拉伯数字)  ";
  public static final String PROMPT_SCORE_ERROR_DAILYSCORE_EXCEEDS = "线下成绩输入错误,分数应该介于0~%s之间  ";
  
  public static final String PROMPT_SCORE_INVALIDE_PRIVILAGES = "该学生报名信息错误";

  public static final String FILE_TYPE_XLS = "xls";
  public static final String FILE_TYPE_XLSX = "xlsx";
  public static final String PROMPT_SCORE_REPEATED = "重复数据(准考证号+课程码值)";
  
  public static final String SCORE_VISIBLY = "on";
  
  /**************************** Error Info ****************************/
  
  public static final Integer DEFAULT_DAYS = 7;
}
