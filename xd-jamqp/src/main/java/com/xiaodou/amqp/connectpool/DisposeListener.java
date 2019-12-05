package com.xiaodou.amqp.connectpool;

public interface DisposeListener {
  void proxyDisposed(RabbitProxy proxy);
}
