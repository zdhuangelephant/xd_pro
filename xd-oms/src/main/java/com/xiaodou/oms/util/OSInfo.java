
package com.xiaodou.oms.util;



/**
 * @ClassName: OSInfo

 * @Description: 获得操作系统

 * @author Guanguo.Gao

 * @date 2014年7月16日 下午12:20:30

 * @version V1.0
 */
public class OSInfo {

    private static String OS = System.getProperty("os.name").toLowerCase();
    
    private OSInfo(){}
    
    public static boolean isLinux(){
        return OS.indexOf("linux")>=0;
    }
    
    public static boolean isWindows(){
        return OS.indexOf("windows") >= 0;
    }
}
