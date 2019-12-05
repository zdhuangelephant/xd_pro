package com.xiaodou.server.mapi.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.enums.ProduceResType;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserTaskCompleteResponse extends BaseResponse {

  public UserTaskCompleteResponse() {}

  public UserTaskCompleteResponse(ResultType resultType) {
    super(resultType);
  }
  // ProduceResType
  public UserTaskCompleteResponse(ProduceResType resultType) {
    super(resultType);
  }

  /** taskCompleteTime 必做任务完成的时间片段 */
  private List<String> taskCompleteList = Lists.newArrayList();
  /** beginExamTime 考期开启日 */
  private String beginExamTime = "";
  /** endExamTime 考期结束日 */
  private String endExamTime = "";

  public List<String> getTaskCompleteList() {
    return taskCompleteList;
  }

  public void setTaskCompleteList(List<String> taskCompleteList) {
    this.taskCompleteList = taskCompleteList;
  }

  public String getBeginExamTime() {
    return beginExamTime;
  }

  public void setBeginExamTime(String beginExamTime) {
    this.beginExamTime = beginExamTime;
  }

  public String getEndExamTime() {
    return endExamTime;
  }

  public void setEndExamTime(String endExamTime) {
    this.endExamTime = endExamTime;
  }

}
