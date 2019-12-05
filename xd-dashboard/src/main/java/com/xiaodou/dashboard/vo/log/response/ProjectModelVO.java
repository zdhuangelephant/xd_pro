package com.xiaodou.dashboard.vo.log.response;

import com.xiaodou.dashboard.model.log.ProjectModel;

/**
 * @name @see com.xiaodou.dashboard.vo.log.response.ProjectModelVO.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月7日
 * @description 项目传输模型
 * @version 1.0
 */
public class ProjectModelVO extends ProjectModel {

  /** excutePointShortName 执行点简称 */
  private String excutePointShortName;
  /** currHourTotalCount 当前小时内请求次数 */
  private Integer currHourTotalCount = 0;
  /** currHourErrorPercent 当前小时内异常率 */
  private Double currHourErrorPercent = 0d;
  /** currHourErrorPercent 当前小时内超时率 */
  private Double currHourOverTimePercent = 0d;
  /** oneHourAgoTotalCount 一小时前请求次数 */
  private Integer oneHourAgoTotalCount = 0;
  /** oneHourAgoErrorPercent 一小时前异常率 */
  private Double oneHourAgoErrorPercent = 0d;
  /** oneHourAgoErrorPercent 一小时前超时率 */
  private Double oneHourAgoOverTimePercent = 0d;
  /** twoHourAgoTotalCount 两小时前请求次数 */
  private Integer twoHourAgoTotalCount = 0;
  /** twoHourAgoErrorPercent 两小时前异常率 */
  private Double twoHourAgoErrorPercent = 0d;
  /** twoHourAgoErrorPercent 两小时前超时率 */
  private Double twoHourAgoOverTimePercent = 0d;
  public ProjectModelVO() {}

  public ProjectModelVO(ProjectModel projectModel) {
    setProjectId(projectModel.getProjectId());
    setProjectName(projectModel.getProjectName());
    setExcutePoint(projectModel.getExcutePoint());
    setExcutePointShortName(projectModel.getExcutePoint().substring(
        projectModel.getExcutePoint().lastIndexOf(".")));
    setAlarmEventId(projectModel.getAlarmEventId());
  }


  public String getExcutePointShortName() {
    return excutePointShortName;
  }

  public void setExcutePointShortName(String excutePointShortName) {
    this.excutePointShortName = excutePointShortName;
  }

  public Double getCurrHourErrorPercent() {
    return currHourErrorPercent;
  }

  public void setCurrHourErrorPercent(Double currHourErrorPercent) {
    this.currHourErrorPercent = currHourErrorPercent;
  }

  public Double getOneHourAgoErrorPercent() {
    return oneHourAgoErrorPercent;
  }

  public void setOneHourAgoErrorPercent(Double oneHourAgoErrorPercent) {
    this.oneHourAgoErrorPercent = oneHourAgoErrorPercent;
  }

  public Double getTwoHourAgoErrorPercent() {
    return twoHourAgoErrorPercent;
  }

  public void setTwoHourAgoErrorPercent(Double twoHourAgoErrorPercent) {
    this.twoHourAgoErrorPercent = twoHourAgoErrorPercent;
  }

  public Integer getCurrHourTotalCount() {
    return currHourTotalCount;
  }

  public void setCurrHourTotalCount(Integer currHourTotalCount) {
    this.currHourTotalCount = currHourTotalCount;
  }

  public Integer getOneHourAgoTotalCount() {
    return oneHourAgoTotalCount;
  }

  public void setOneHourAgoTotalCount(Integer oneHourAgoTotalCount) {
    this.oneHourAgoTotalCount = oneHourAgoTotalCount;
  }

  public Integer getTwoHourAgoTotalCount() {
    return twoHourAgoTotalCount;
  }

  public void setTwoHourAgoTotalCount(Integer twoHourAgoTotalCount) {
    this.twoHourAgoTotalCount = twoHourAgoTotalCount;
  }

public Double getTwoHourAgoOverTimePercent() {
	return twoHourAgoOverTimePercent;
}

public void setTwoHourAgoOverTimePercent(Double twoHourAgoOverTimePercent) {
	this.twoHourAgoOverTimePercent = twoHourAgoOverTimePercent;
}

public Double getOneHourAgoOverTimePercent() {
	return oneHourAgoOverTimePercent;
}

public void setOneHourAgoOverTimePercent(Double oneHourAgoOverTimePercent) {
	this.oneHourAgoOverTimePercent = oneHourAgoOverTimePercent;
}

public Double getCurrHourOverTimePercent() {
	return currHourOverTimePercent;
}

public void setCurrHourOverTimePercent(Double currHourOverTimePercent) {
	this.currHourOverTimePercent = currHourOverTimePercent;
}

}
