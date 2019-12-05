package com.xiaodou.oms.vo.result.pay;

import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Date: 2014/6/30
 * Time: 16:49
 *
 * @author Tian.Dong
 */
public class GetTokenVO extends ResultInfo {
  public GetTokenVO(ResultType resultType) {
    super(resultType);
  }
  public GetTokenVO(int code,String desc){
    this.setRetcode(Integer.toString(code));
    this.setRetdesc(desc);
    try {
      InetAddress addr = InetAddress.getLocalHost();
      this.setServerIp(addr.getHostAddress()); // 获得本机IP
    } catch (UnknownHostException e) {}
  }

  private String token = "";

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
