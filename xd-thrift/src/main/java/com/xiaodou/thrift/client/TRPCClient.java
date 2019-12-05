package com.xiaodou.thrift.client;


import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.thrift.iface.RouteService;
import com.xiaodou.thrift.model.DefaultModel;
import com.xiaodou.thrift.model.TransferParam;
import com.xiaodou.thrift.util.ThriftUtil;

public class TRPCClient {
  // ip
  public String serverIp = "localhost";
  // 端口号
  public int serverPort = 18000;
  // 超时时间
  public int timeOut = 10000000;


  public String getServerIp() {
    return serverIp;
  }


  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }


  public int getServerPort() {
    return serverPort;
  }


  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }


  public int getTimeOut() {
    return timeOut;
  }


  public void setTimeOut(int timeOut) {
    this.timeOut = timeOut;
  }


  /**
   * 根据类名，方法名，方法的值获取到对应的值
   * 
   * @param className
   * @param funName
   * @param paramValue
   */



  public DefaultModel thriftStartClient(String className, String funName, Object... paramValue) {
    DefaultModel model = new DefaultModel();
    TTransport transport = null;
    try {
      transport = new TFramedTransport(new TSocket(serverIp, serverPort, timeOut));
      // 协议要和服务端一致
      TProtocol protocol = new TCompactProtocol(transport);
      RouteService.Client client = new RouteService.Client(protocol);
      transport.open();
      TransferParam transferParam = new TransferParam();
      if (paramValue.length > 0) {
        for (Object param : paramValue)
          if (null == param)
            transferParam.add(null);
          else
            transferParam.add(ThriftUtil.changeResult(param, param.getClass()));
      }
      model = client.routeService(className, funName, FastJsonUtil.toJson(transferParam));
    } catch (TTransportException e) {
      LoggerUtil.error("TTransportException异常", e);
      e.printStackTrace();
    } catch (TException e) {
      LoggerUtil.error("TException异常", e);
    } finally {
      if (null != transport) {
        transport.close();
      }
    }
    return model;
  }
}
