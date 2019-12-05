package com.xiaodou.common.util;

import org.apache.log4j.MDC;

/**
 * 日志跟踪工具类 ClassName: TraceUtils <br/>
 * Function: ADD FUNCTION. <br/>
 * date: 2015年4月27日 上午10:46:44 <br/>
 *
 * @author Guanguo.Gao
 * @version
 * @since JDK 1.6
 */
public class TraceUtils {

    public static final String TRACE_ID_KEY = "traceId";

    /**
     * 开始跟踪
      * beginTrace
      * @Title: beginTrace
      * @Description: 
      * @param traceId
     */
    public static void beginTrace(String traceId) {
	MDC.put(TRACE_ID_KEY, traceId);
    }

    /**
     * 获取traceId
      * getTrace
      * @Title: getTrace
      * @Description: 
      * @return
     */
    public static String getTrace() {
	Object obj = MDC.get(TRACE_ID_KEY);
	if (obj != null) {
	    return obj.toString();
	}
	return StringUtils.EMPTY;
    }

    /**
     * 结束一次Trace, 清除traceId.
     */
    public static void endTrace() {
	MDC.remove(TRACE_ID_KEY);
    }

}
