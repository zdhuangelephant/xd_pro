package com.xiaodou.server.mapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RdUtil {

  /*
   * private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //
   * 定义script的正则表达式 private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
   * // 定义style的正则表达式 private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
   */private static final String regEx_space = "\t|\r|\n";// 定义空格回车换行符
  // private static final String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符
  // private static final String regEx_w = "<w[^>]*?>[\\s\\S]*?<\\/w[^>]*?>";// 定义所有w标签
  private static final String regEx_special = "\\&[a-zA-Z]{1,10};";// 定义一些特殊字符的正则表达式
                                                                   // 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


  /**
   * @param htmlStr
   * @return 删除Html标签
   * @author LongJin
   */
  public static String delHTMLTag(String htmlStr) {
    if (htmlStr == null) {
      return "";
    }

    Pattern p_nbsp = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
    Matcher m_nbsp = p_nbsp.matcher(htmlStr);
    htmlStr = m_nbsp.replaceAll(""); // 过滤&nbsp;标签

    Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
    Matcher m_space = p_space.matcher(htmlStr);
    htmlStr = m_space.replaceAll(""); // 过滤空格回车标签

    // htmlStr = htmlStr.replaceAll(" ", ""); // 过滤
    return htmlStr.trim(); // 返回文本字符串
  }

  public static String getHtmlContent(String content) {
    return content = delHTMLTag(content);
  }
}
