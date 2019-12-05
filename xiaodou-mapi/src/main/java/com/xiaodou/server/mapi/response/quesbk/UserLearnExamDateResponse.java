package com.xiaodou.server.mapi.response.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserLearnExamDateResponse extends BaseResponse {

  public UserLearnExamDateResponse() {}

  public UserLearnExamDateResponse(ResultType type) {
    super(type);
  }

  private List<LearnRecordData> examDateList = Lists.newArrayList();

  public List<LearnRecordData> getExamDateList() {
    return examDateList;
  }

  public void setExamDateList(List<LearnRecordData> examDateList) {
    this.examDateList = examDateList;
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
      return StringUtils.isNotBlank(learnTime) ? MathUtil.getIntStringValue(Double
          .valueOf(learnTime) / 60) : "0";
    }

    public void setLearnTime(String learnTime) {
      this.learnTime = learnTime;
    }
  }
  
}
