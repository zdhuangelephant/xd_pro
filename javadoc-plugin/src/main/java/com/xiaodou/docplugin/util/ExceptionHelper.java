package com.xiaodou.docplugin.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHelper {

	/*
	 * ��ȡ��ջ��Ϣ
	 */
	public static String getStackTrace(Exception e){
		String ret = "";
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		e.printStackTrace(pWriter);
		ret = sWriter.toString();
		return ret;
	}
}
