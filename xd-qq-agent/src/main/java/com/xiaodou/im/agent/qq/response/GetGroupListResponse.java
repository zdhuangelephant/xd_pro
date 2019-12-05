package com.xiaodou.im.agent.qq.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.im.agent.qq.constant.TransType;

import java.util.List;
import java.util.Map;

/**
 * { "retcode": 0, "result": { "gmasklist": [], "gnamelist": [ { "flag": 1090520065, "name":
 * "test_qun", "gid": 1501743717, "code": 2156320117 } ], "gmarklist": [] } }
 * <p/>
 * Date: 2014/12/11 Time: 11:04
 * 
 * @author Tian.Dong
 */
public class GetGroupListResponse extends BaseResponse {
  @JSONField(name = "retcode")
  private int retCode;
  @JSONField(name = "errmsg")
  private String errMsg;
  private GetGroupListResult result;

  public static TransType getResponseType() {
    return TransType.JSON;
  }

  /**
   * "gmasklist": [], "gnamelist": [ { "flag": 1090520065, "name": "test_qun", "gid": 1501743717,
   * "code": 2156320117 } ], "gmarklist": []
   */
  @SuppressWarnings("rawtypes")
  public static class GetGroupListResult {
    @JSONField(name = "gmasklist")
    private List<Map> gmaskList;
    @JSONField(name = "gnamelist")
    private List<GName> gnameList;
    @JSONField(name = "gmarklist")
    private List<Map> gmarkList;

    public List<Map> getGmaskList() {
      return gmaskList;
    }

    public void setGmaskList(List<Map> gmaskList) {
      this.gmaskList = gmaskList;
    }

    public List<GName> getGnameList() {
      return gnameList;
    }

    public void setGnameList(List<GName> gnameList) {
      this.gnameList = gnameList;
    }

    public List<Map> getGmarkList() {
      return gmarkList;
    }

    public void setGmarkList(List<Map> gmarkList) {
      this.gmarkList = gmarkList;
    }

    public static class GName {
      private long flag;
      private String name;
      private long gid;
      private long code;

      public long getFlag() {
        return flag;
      }

      public void setFlag(int flag) {
        this.flag = flag;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public long getGid() {
        return gid;
      }

      public void setGid(long gid) {
        this.gid = gid;
      }

      public long getCode() {
        return code;
      }

      public void setCode(long code) {
        this.code = code;
      }
    }
  }


  public int getRetCode() {
    return retCode;
  }

  public void setRetCode(int retCode) {
    this.retCode = retCode;
  }

  public GetGroupListResult getResult() {
    return result;
  }

  public void setResult(GetGroupListResult result) {
    this.result = result;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }
}
