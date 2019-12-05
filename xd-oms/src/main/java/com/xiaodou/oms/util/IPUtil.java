
package com.xiaodou.oms.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


/**
 * @ClassName: IPUtil

 * @Description: 获取服务器IP地址

 * @author Guanguo.Gao

 * @date 2014年7月16日 上午11:34:52

 * @version V1.0
 */
public class IPUtil {

    /**
     * @Description: 获得服务器ip
     * @return
     * @throws
     */
    @SuppressWarnings("rawtypes")
    public static String getServerIp(){
        try {
            if(OSInfo.isWindows()){
                return  InetAddress.getLocalHost().getHostAddress();
            }
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address){
                        return ip.getHostAddress();
                    } 
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

}
