package com.xiaodou.dashboard.util;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.StringUtils;

public class EnvironMentUtil {
  private static String ONLINE_TYPE = "ONLINE";
  private static String ENV_TYPE_KEY = "env.type";

  private static String JOB_ROLE = "JOB";
  private static String NODE_TYPE_KEY = "node.type";

  public static String getEnvType() {
    return ConfigProp.getParams(ENV_TYPE_KEY);
  }

  public static boolean isOnline() {
    String envtype = ConfigProp.getParams(ENV_TYPE_KEY);
    return StringUtils.isNotBlank(envtype) && ONLINE_TYPE.equals(envtype);
  }

  public static boolean isJobNode() {
    String nodetype = ConfigProp.getParams(NODE_TYPE_KEY);
    return StringUtils.isNotBlank(nodetype) && JOB_ROLE.equals(nodetype);
  }
}
