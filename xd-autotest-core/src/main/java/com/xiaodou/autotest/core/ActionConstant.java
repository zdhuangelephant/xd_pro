package com.xiaodou.autotest.core;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import com.xiaodou.autotest.core.ActionEnum.ApiStatus;
import com.xiaodou.autotest.core.ActionEnum.ConditionScope;
import com.xiaodou.autotest.core.ActionEnum.DataFormat;
import com.xiaodou.autotest.core.ActionEnum.Format;
import com.xiaodou.autotest.core.ActionEnum.Method;
import com.xiaodou.autotest.core.ActionEnum.ParamType;
import com.xiaodou.autotest.core.ActionEnum.Protocol;

/**
 * @name @see com.xiaodou.server.mapi.engine.ActionConstant.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description 注册Action用常量池
 * @version 1.0
 */
public class ActionConstant {

  /** DEFAULT_PARAM_TMP 默认参数值模板 */
  public static String DEFAULT_PARAM_TMP = "${%s}";

  /** DEFAULT_AUTOREGIST_TMP 默认自动注入参数值模板 */
  public static String DEFAULT_AUTOREGIST_TMP = "$%s$";

  /** regex 引用值通配符 */
  public static String REGEX = "\\$\\{";

  /** AUTO_REGIST_REGEX 自动注入通配符 */
  public static String AUTO_REGIST_REGEX = "(?<=\\$)([a-zA-Z]+?)(?=\\$)";

  /** AUTO_REGIST_PATTERN 自动注入通配符-匹配模式 */
  public static Pattern AUTO_REGIST_PATTERN = Pattern.compile(AUTO_REGIST_REGEX);

  /** D_FORMAT double format */
  public static final DecimalFormat D_FORMAT = new DecimalFormat("######0.00");

  /** DEFAULT_API_STATUS 默认执行状态 */
  public static final ApiStatus DEFAULT_API_STATUS = ApiStatus.UnExcute;

  /** DEFAULT_PROTOCOL 默认协议 */
  public static final Protocol DEFAULT_PROTOCOL = Protocol.http;

  /** DEFAULT_METHOD 默认方法 */
  public static final Method DEFAULT_METHOD = Method.get;

  /** DEFAULT_FORMAT 默认数据传输格式 */
  public static final Format DEFAULT_FORMAT = Format.normal;

  /** DEFAULT_PARAM_TYPE 默认参数类型 */
  public static final ParamType DEFAULT_PARAM_TYPE = ParamType.QueryParam;

  /** DEFAULT_DATA_TYPE 默认数据类型 */
  public static final DataFormat DEFAULT_DATA_TYPE = DataFormat.jSon;

  /** DEFAULT_TIMEOUT 默认超时时间 ms */
  public static final Integer DEFAULT_TIMEOUT = 15000;

  /** DEFAULT_RETRY 默认重试次数 */
  public static final Integer DEFAULT_RETRY = 2;

  /** DEFAULT_API_NAME 默认API名称 */
  public static final String DEFAULT_API_NAME = "未命名接口";

  /** SEPERATOR_VALUE 值列表分隔符 */
  public static final String SEPERATOR_VALUE = ",";

  /** SEPERATOR_DATASOURCE_VALUE 数据源值列表分隔符 */
  public static final String SEPERATOR_DATASOURCE_VALUE = "#dsv#";

  /** SEPERATOR_PAIR 键值对分隔符 */
  public static final String SEPERATOR_PAIR = "=";

  /** SEPERATOR_DATASOURCE 数据源分隔符 */
  public static final String SEPERATOR_DATASOURCE = "#ds#";

  /** DEFAULT_TEST_CASE_NAME 默认用例模板 */
  public static final String DEFAULT_TEST_CASE_NAME = "%s_用例_%s";

  /** DEFAULT_CONTACT_KEY 默认连接键 */
  public static final String DEFAULT_CONTACT_KEY = "%s.%s";

  /** DEFAULT_CONDITION 默认条件 */
  public static final ConditionScope DEFAULT_CONDITION = ConditionScope.eq;
}
