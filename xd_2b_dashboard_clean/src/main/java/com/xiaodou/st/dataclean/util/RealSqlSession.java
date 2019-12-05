package com.xiaodou.st.dataclean.util;

import com.xiaodou.summer.source.jdbc.helper.RealSqlSessionKeyHolder;

public class RealSqlSession {
	public static void setRealSqlSession(String module){
		  RealSqlSessionKeyHolder.getHolder().setIsUsekey(true);
		  RealSqlSessionKeyHolder.getHolder().setRealSqlSessionKey(module);
	}
}
