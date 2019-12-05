package com.xiaodou.thrift.server;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.thrift.iface.RouteService;
import com.xiaodou.thrift.impl.RouteServiceImpl;

public class TRPCServer {

  private final static AtomicBoolean startStatus = new AtomicBoolean(false);

  public String scanPath = "com.xiaodou.thrift";

  public int serverPort = 18000;

  public String getScanPath() {
    return scanPath;
  }

  public void setScanPath(String scanPath) {
    this.scanPath = scanPath;
  }

  public int getServerPort() {
    return serverPort;
  }

  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }

  public void thriftStartServer() {
    try {
      if (startStatus.compareAndSet(false, true)) {
        TProcessor tprocessor =
            new RouteService.Processor<RouteService.Iface>(new RouteServiceImpl(scanPath));
        // NIO并发模型
        InetSocketAddress address = new InetSocketAddress(serverPort);
        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(address);
        TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
        tArgs.maxReadBufferBytes = 1024 * 1024l;
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(new TCompactProtocol.Factory());
        final TServer server = new TThreadedSelectorServer(tArgs);
        new Thread(new Runnable() {
          @Override
          public void run() {
            server.serve();
          }
        }).start();
      }
    } catch (TTransportException e) {
      LoggerUtil.error("启动thrift服务异常", e);
    } finally {
      startStatus.compareAndSet(true, false);
    }
  }
}
