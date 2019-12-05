package com.xiaodou.resources.util;

import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.StringUtils;


public class StaticInfoProp {

  /**
   * 配置文件
   */
  private static FileUtil errFile = FileUtil.getInstance("/conf/custom/env/static_info.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (errFile == null)
      synchronized (StaticInfoProp.class) {
        if (errFile == null)
          errFile = FileUtil.getInstance("/conf/custom/env/static_info.properties");
      }
    return errFile;
  }

  /** DEFAULT_EXAMTYPE_RULE 走默认rule的练习类型 */
  private static final String DEFAULT_EXAMTYPE_RULE = "xd.quesbk.default.rule.examtype.%s";

  /** WRONG_RECORD_RIGHTTIMES_UPPER 错误记录判正确次数上限 */
  private static final String WRONG_RECORD_RIGHTTIMES_UPPER =
      "xd.quesbk.wrongrecord.righttimes.upper";

  /** PASS_STAR_LEVEL 闯关通过星级 */
  private static final String PASS_STAR_LEVEL = "xd.quesbk.starlevel.pass";

  /** PASS_STAR_LEVEL 闯关完成星级 */
  private static final String FINISH_STAR_LEVEL = "xd.quesbk.starlevel.finish";

  /**
   * 获取闯关通过星级
   * 
   * @return
   */
  public static String getPassStarLevel() {
    return getProperty().getProperties(PASS_STAR_LEVEL);
  }

  /**
   * 获取闯关完成星级
   * 
   * @return
   */
  public static String getFinishStarLevel() {
    return getProperty().getProperties(FINISH_STAR_LEVEL);
  }

  /**
   * 获取错误记录判正确次数上限
   * 
   * @return
   */
  public static Integer getWrongLimit() {
    return getProperty().getPropertiesInt(WRONG_RECORD_RIGHTTIMES_UPPER);
  }

  /**
   * 判断练习类型是否走默认规则
   * 
   * @param examTypeCode 练习类型码
   * @return
   */
  public static boolean isNotDefaultRule(String examTypeCode) {
    return StringUtils.isNotBlank(getProperty().getProperties(
        String.format(DEFAULT_EXAMTYPE_RULE, examTypeCode)));
  }

  /**
   * 获取参数值
   * 
   * @param code 参数key
   * @return 参数值
   */
  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }

  /**
   * 获取Int型配置项
   * 
   * @param code
   * @return
   */
  public static int getInt(String code) {
    return getProperty().getPropertiesInt(code);
  }

}
