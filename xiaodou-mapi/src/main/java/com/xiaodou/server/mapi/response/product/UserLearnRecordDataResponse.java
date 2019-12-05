package com.xiaodou.server.mapi.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserLearnRecordDataResponse extends BaseResponse {

  public UserLearnRecordDataResponse() {}

  public UserLearnRecordDataResponse(ResultType type) {
    super(type);
  }

  private List<LearnRecordData> learnList = Lists.newArrayList();

  public List<LearnRecordData> getLearnList() {
    return learnList;
  }

  public void setLearnList(List<LearnRecordData> learnList) {
    this.learnList = learnList;
  }


  public static class LearnRecordData {
    /* 日期eg:2016-02-10 */
    private String recordTime = StringUtils.EMPTY;
    /* 学习时长 */
    private String learnTime = StringUtils.EMPTY;

    public LearnRecordData() {}

    public LearnRecordData(String recordTime) {
      this.recordTime = recordTime;
    }

    public LearnRecordData(String recordTime, String learnTime) {
      super();
      this.recordTime = recordTime;
      this.learnTime = learnTime;
    }

    public String getRecordTime() {
      return recordTime;
    }

    public void setRecordTime(String recordTime) {
      this.recordTime = recordTime;
    }

    public String getLearnTime() {
      return learnTime;
    }

    public void setLearnTime(String learnTime) {
      this.learnTime = learnTime;
    }
  }

}
