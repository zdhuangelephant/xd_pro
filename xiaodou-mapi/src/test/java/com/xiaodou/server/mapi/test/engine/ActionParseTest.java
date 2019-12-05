package com.xiaodou.server.mapi.test.engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xiaodou.common.util.StringUtils;

public class ActionParseTest {
  private static String regex = "(^\\$\\{)*\\$\\{(.+?)\\}(^\\})*";
  private static Pattern pattern = Pattern.compile(regex);
  private static String target = "123123${server.ucenter.host}/common/upToken";

  public static void main(String[] args) {
    if (StringUtils.isBlank(target)) return;
    Matcher matcher = pattern.matcher(target);
    StringBuffer sbr = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(sbr, "http://192.168.31.88:8002");
    }
    matcher.appendTail(sbr);
    System.out.println(sbr.toString());
  }
}
