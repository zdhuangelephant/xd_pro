package com.xiaodou.jmsg.server.broadcastresponse;

public class BroadcastResponseItem {
  private boolean error;
  private boolean failed;
  private String url;
  private int interval;
  private String exceptionMsg;
  private String response;
  private int responseCode;

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }

  public boolean isFailed() {
    return failed;
  }

  public void setFailed(boolean failed) {
    this.failed = failed;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getInterval() {
    return interval;
  }

  public void setInterval(int interval) {
    this.interval = interval;
  }

  public String getExceptionMsg() {
    return exceptionMsg;
  }

  public void setExceptionMsg(String exceptionMsg) {
    this.exceptionMsg = exceptionMsg;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public boolean isFailedOrError() {
    if (this.error == true || this.failed == true) {
      return true;
    } else {
      return false;
    }
  }

  public int getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(int responseCode) {
    this.responseCode = responseCode;
  }
}
