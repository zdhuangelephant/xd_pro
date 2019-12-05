package com.xiaodou.oms.vo.result.pay;

import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Date: 2014/7/4
 * Time: 11:11
 *
 * @author Tian.Dong
 */
public class ContinuePayVO extends ResultInfo {
  public ContinuePayVO(ResultType resultType) {
    super(resultType);
  }
  public ContinuePayVO(int code,String desc){
    String ip = null;
    try {
      InetAddress addr = InetAddress.getLocalHost();
      ip = addr.getHostAddress().toString();// 获得本机IP
    } catch (UnknownHostException e) {}
    this.setRetcode(Integer.toString(code));
    this.setRetdesc(desc);
    this.setServerIp(ip);
  }

}
