
package com.xiaodou.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


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


    /**
     * 获取访问者IP与hostname
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request 请求包
     * @return
     */
    public static Map<String,Object> getIpAddr(HttpServletRequest request) throws Exception{
        return getOnlyIpAddr(request,false);
    }
    
    /**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request 请求包
     * @param flag 是否只获取IP
     * @return
     */
    public static Map<String,Object> getOnlyIpAddr(HttpServletRequest request, boolean flag) throws Exception{
        String hostAddr;
        InetAddress inetAddress;
        String hostName;
        Map<String,Object> result = new HashMap<String,Object>();

        String ip = request.getHeader("X-Real-IP");
        if ( !StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip) ) {

            hostAddr = ip;
            if(flag){
              result.put("hostAddr",hostAddr);
            }else{
              inetAddress = InetAddress.getByName(hostAddr);
              if( inetAddress != null){
                hostName = inetAddress.getCanonicalHostName();
                result.put("hostAddr",hostAddr);
                result.put("hostName",hostName);
              }
            }

            return result;
        }

        ip = request.getHeader("X-Forwarded-For");
        if ( !StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip) ) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                hostAddr = ip.substring(0, index);
            } else {
                hostAddr = ip;
            }
        } else {
            hostAddr = request.getRemoteAddr();
        }

        if(flag){
          result.put("hostAddr",hostAddr);
        }else{
          inetAddress = InetAddress.getByName(hostAddr);
          if( inetAddress != null){
            hostName = inetAddress.getCanonicalHostName();
            result.put("hostAddr",hostAddr);
            result.put("hostName",hostName);
          }
        }

        return result;
    }

}
