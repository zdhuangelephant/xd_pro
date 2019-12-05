package com.xiaodou.oms.vo.result;

public class ResultInfoObject<T> extends ResultInfo {

  private T object = null;

  public T getObject() {

    return this.object;
  }

  public void setObject(T object) {

    this.object = object;
  }

  public ResultInfoObject() {

  }

  public ResultInfoObject(int retcode, String retdesc, String serverIp) {
    super(Integer.toString(retcode), retdesc, serverIp);
  }

  public ResultInfoObject(ResultType type) {
    super(type);
  }
}
