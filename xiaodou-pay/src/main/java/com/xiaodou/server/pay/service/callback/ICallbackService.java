package com.xiaodou.server.pay.service.callback;

public interface ICallbackService<Request, Response> {
  public abstract Response callback(Request request);
}
