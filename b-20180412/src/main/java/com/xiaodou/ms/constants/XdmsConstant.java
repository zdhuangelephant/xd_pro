package com.xiaodou.ms.constants;

import java.text.SimpleDateFormat;

public interface XdmsConstant {

  /** MODULE 模块号 */
  public static String MODULE = "2";
  /** EXAM_DATE_TYPE 考期 */
  public static String[] EXAM_DATE_TYPE = {"上半年", "下半年", "整年"};
  /** EXAM_DATE_TYPE_UNKNOWN 未知考期 */
  public static String EXAM_DATE_TYPE_UNKNOWN = "未知考期";
  /** PRODUCT_RELATION_VALID 产品关系-有效 */
  public static Integer PRODUCT_RELATION_VALID = 1;
  /** PRODUCT_RELATION_INVALID 产品关系-无效 */
  public static Integer PRODUCT_RELATION_UNVALID = 0;
  /** PAGE_SIZE 分页记录条数 */
  public static Integer PAGE_SIZE = 10;
  public static String YES = "1";
  public static String NO = "0";
  public static String APPLY_TIME_UNMATCH = "课程%s与该专业考期不一致,无法完成添加操作;</br>";

  /** RULE_TYPE_DEFAULT 命题蓝图类型:默认 */
  public static Integer RULE_TYPE_DEFAULT = 0;

  /** RULE_TYPE_NORMAL 命题蓝图类型:正常 */
  public static Integer RULE_TYPE_NORMAL = 1;
  
  /** DEFAULT_EXAMRULE_NUM 默认命题蓝图数量*/
  public static Integer DEFAULT_EXAMRULE_NUM = 7;

  /** 新生入学指南 **/
  public static final String QUICK_START_COURSE = "00000";

  /** COMMON_MAJOR_ID 通用专业ID */
  public static final String COMMON_MAJOR_ID = "-1";

  /** COMMON_PRODUCT_ID 通用产品ID */
  public static final Integer COMMON_PRODUCT_ID = -1;

  /**************************** Error Info ****************************/
  public static final String ERROR_CLASSNAME_EMPTY = "班级名称为空，";
  public static final String ERROR_NAME_EMPTY = "姓名为空，";
  public static final String ERROR_GENDER_EMPTY = "性别为空，";
  public static final String ERROR_GENDER_ERROR = "性别不符合规范，";
  public static final String ERROR_PHONENUM_EMPTY = "手机号为空，";
  public static final String ERROR_PHONENUM_EXIST = "手机号已经存在，";
  public static final String ERROR_PHONENUM_ERROR = "手机号不符合规范，";
  public static final String ERROR_CARD_ERROR = "准考证号不符合规范，";
  public static final String ERROR_CARD_EXIST = "准考证号已存在，";
  /**************************** Error Info ****************************/

  /** INIT_STATUS 初始化状态 */
  public static final int INIT_STATUS = -1;

  /** NORMAL_STATUS 正常状态 */
  public static final int NORMAL_STATUS = 99;
  
  public static final int VISIBLEY = 1;
  public static final int UNVISIBLEY = 0;
  
  public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * 小逗通用前缀
   */
  public static final String XIAODOU = "xiaodou:";

  /**
   * 用户中心
   */
  public static final String USER_CENTER = XIAODOU + "user:center:";

  /**
   * 上传凭证
   */
  public static final String UP_TOKEN = USER_CENTER + "uptoken:";

  /** UP_TOKEN_SCOPE_DEF 七牛默认存储空间 */
  public static final String UP_TOKEN_SCOPE_DEF = "picture";


  public static final Integer SURVIVAL_TIME = 60;
  
  public static final String PORTRAIT = "http://7xigj3.com1.z0.glb.clouddn.com/85FC5E1D-2C4C-4675-AB4F-C8D3E24D4D74";
  public static final String FILE_TYPE_XLS = "xls";
  public static final String FILE_TYPE_XLSX = "xlsx";
  
  public static final String PROMPT_HEADERERROR_MSG = "请严格按照下载列表填写上传  ";
  public static final String UNIQUE_SEPEATEOR = "@xiaodou@";

  public static final Integer NORMAL_BINDING_ROBOTE = 10;

}
