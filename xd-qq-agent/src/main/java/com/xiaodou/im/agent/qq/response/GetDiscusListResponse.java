package com.xiaodou.im.agent.qq.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.im.agent.qq.constant.TransType;

import java.util.List;

/**
 * Date: 2014/12/11
 * Time: 11:04
 *
 * @author Tian.Dong
 */
public class GetDiscusListResponse {
  @JSONField(name = "retcode")
  private int retCode;
  @JSONField(name = "errmsg")
  private String errMsg;
  private GetDiscusListResult result;

  public static TransType getResponseType() {
    return TransType.JSON;
  }

  /**
   * "dnamelist": [
   * {
   * "name": "流浪的孩子、oms机器人",
   * "did": 2775320792
   * }
   * ]
   */
  public static class GetDiscusListResult {

    @JSONField(name = "dnamelist")
    private List<DName> dnameList;

    public List<DName> getDnameList() {
      return dnameList;
    }

    public void setDnameList(List<DName> dnameList) {
      this.dnameList = dnameList;
    }

    public static class DName {
      private String name;
      private long did;

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public long getDid() {
        return did;
      }

      public void setDid(long did) {
        this.did = did;
      }
    }
  }


  public int getRetCode() {
    return retCode;
  }

  public void setRetCode(int retCode) {
    this.retCode = retCode;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  public GetDiscusListResult getResult() {
    return result;
  }

  public void setResult(GetDiscusListResult result) {
    this.result = result;
  }
}
