package com.xiaodou.mission.vo.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.mission.web.controller.TaskStatusResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月29日
 * @description 任务状态响应类
 * @version 1.0
 */
public class TaskCompleteStatusResponse extends ResultInfo {

  public TaskCompleteStatusResponse(ResultType type) {
    super(type);
  }

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
