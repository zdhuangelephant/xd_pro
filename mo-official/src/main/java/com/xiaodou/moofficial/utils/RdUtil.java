package com.xiaodou.moofficial.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class RdUtil {

	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	private static final String regEx_space = "\\s*|\t|\r|\n|&nbsp;";// 定义空格回车换行符
	private static final String regEx_w = "<w[^>]*?>[\\s\\S]*?<\\/w[^>]*?>";// 定义所有w标签
	private static final String regEx_special = "\\&[a-zA-Z]{1,20};";

	/**
	 * @param htmlStr
	 * @return 删除Html标签
	 * @author LongJin
	 */
	public static String delHTMLTag(String htmlStr) {
		if (htmlStr ==null) {
			return "";
		}
		Pattern p_w = Pattern.compile(regEx_w, Pattern.CASE_INSENSITIVE);
		Matcher m_w = p_w.matcher(htmlStr);
		htmlStr = m_w.replaceAll(""); // 过滤script标签

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern
				.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
		
		Pattern p_special = Pattern
				.compile(regEx_special, Pattern.CASE_INSENSITIVE);
		Matcher m_special = p_special.matcher(htmlStr);
		htmlStr = m_special.replaceAll(""); // 过滤特殊字符&nbsp;

		htmlStr = htmlStr.replaceAll(" ", ""); // 过滤
		return htmlStr.trim(); // 返回文本字符串
	}

	public static String getHtmlContent(String content, int num) {
		content = delHTMLTag(content);
		if (StringUtils.isBlank(content))
			return StringUtils.EMPTY;
		if (content.length() > num)
			return delHTMLTag(content).substring(0, num - 1);
		return content;
	}
}
