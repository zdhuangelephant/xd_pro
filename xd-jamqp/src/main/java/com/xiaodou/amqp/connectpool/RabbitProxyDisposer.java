package com.xiaodou.amqp.connectpool;

import java.util.LinkedList;
import java.util.Queue;

import com.xiaodou.amqp.exception.AmqpClientException;

public class RabbitProxyDisposer {

  private Queue<RabbitProxy> _disposableProxyQueue = new LinkedList<>();
  private Object _lock = new Object();

  public RabbitProxyDisposer() {
    ThreadGroup tg = Thread.currentThread().getThreadGroup();
    Thread t = new Thread(tg, new DisposeTask(), "Proxy Disposer");
    t.setDaemon(true);
    t.start();
  }

  public void addDisposableProxy(RabbitProxy proxy) throws AmqpClientException {
    if (proxy != null) {
      synchronized (_lock) {
        try {
          _disposableProxyQueue.add(proxy);
        } catch (Exception e) {
          throw new AmqpClientException("Add disposable proxy failed: ", e);
        } finally {
          _lock.notify();
        }
      }
    }
  }

  private class DisposeTask implements Runnable {

    @Override
    public void run() {
      while (true) {
        RabbitProxy proxy;
        synchronized (_lock) {
          proxy = _disposableProxyQueue.poll();
          while (proxy == null) { // get first proxy in the queue
            try {
              _lock.wait();
            } catch (InterruptedException e) {
            }
            proxy = _disposableProxyQueue.poll();
          }

          long timeoutMillis = getTimeoutMillis(proxy);
          while (timeoutMillis > 0) { // wait for timeout to dispose
            try {
              _lock.wait(timeoutMillis); 
            } catch (InterruptedException e) {
            }
            timeoutMillis = getTimeoutMillis(proxy);
          }
        }

        // must invoke outside the lock to avoid dead lock when
        // disposed listener called
        proxy.disPose();
      }
    }

    private long getTimeoutMillis(RabbitProxy proxy) {
      long timeElapsed = System.currentTimeMillis() - proxy.getCreatedTime();
      long disposeTimeout = proxy.getConnectionTimeOut() + 5 * 1000; // wait for 5s more
      long timeoutMillis = disposeTimeout - timeElapsed;
      return timeoutMillis > 0 ? timeoutMillis : 0;
    }

  }

}
