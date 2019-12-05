package com.xiaodou.autotest.web.model.operation;

import java.sql.Timestamp;
import java.util.List;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.autotest.web.constants.Cons;
import com.xiaodou.autotest.web.enums.TimingEnum;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.autotest.web.model.operation.Collection.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年8月15日
 * @description
 * @version 1.0
 */
public class Case {
  /** 主键 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false, isMajor = true)
  private String id;
  /** 名称 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String name;
  /** 详情 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String caseDesc = StringUtils.EMPTY;
  /** request数量 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long count;
  /** 用户ID */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String userId;
  /** 最新执行结果 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String results;

  /** 版本号 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String version = Cons.DEFAULT_VERSION;
  /** 产品线 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String businessLine = Cons.DEFAULT_BUSINESSLINE;
  /** 定时任务详情 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String timingTaskDesc;
  /** 定时任务详情 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer failCount = 0;
  /** 创建时间 */
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp createTime;
  /** 更新时间 */
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp updateTime;

  /** 是否分享 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String share = Cons.NO_SHARE;
  /** 失败次数 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer caseFailCount;
  /** 成功次数 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer caseSuccessCount;
  /** 最近5次成功失败的记录 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String record;

  @Column(persistent = false)
  private Double successRate;
  @Column(persistent = false)
  private Double nearlyFiveSuccessRate;
  @Column(persistent = false)
  private String lastTime;

  private List<CaseRequest> caseRequests;

  public List<CaseRequest> getCaseRequests() {
    return caseRequests;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCaseDesc() {
    return caseDesc;
  }

  public void setCaseDesc(String caseDesc) {
    this.caseDesc = caseDesc;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getFailCount() {
    return failCount;
  }

  public void setFailCount(Integer failCount) {
    this.failCount = failCount;
  }

  public String getResults() {
    return results;
  }

  public void setResults(String results) {
    this.results = results;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getBusinessLine() {
    return businessLine;
  }

  public void setBusinessLine(String businessLine) {
    this.businessLine = businessLine;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public String getTimingTaskDesc() {
    return timingTaskDesc;
  }

  public void setTimingTaskDesc(String timingTaskDesc) {
    this.timingTaskDesc = timingTaskDesc;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(Case.class, "case", "E:/work3/autotest/src/main/resources/conf/mybatis/")
        .buildXml();
  }

  public String getShare() {
    return share;
  }

  public void setShare(String share) {
    this.share = share;
  }


  public void setRecord(String record) {
    this.record = record;
  }

  public Double getSuccessRate() {
    return successRate;
  }

  public void setSuccessRate(Double successRate) {
    this.successRate = successRate;
  }

  public Double getNearlyFiveSuccessRate() {
    return nearlyFiveSuccessRate;
  }

  public void setNearlyFiveSuccessRate(Double nearlyFiveSuccessRate) {
    this.nearlyFiveSuccessRate = nearlyFiveSuccessRate;
  }

  public String getLastTime() {
    return lastTime;
  }

  public void setLastTime(String lastTime) {
    this.lastTime = lastTime;
  }


  public Integer getCaseFailCount() {
    return caseFailCount;
  }


  public void setCaseFailCount(Integer caseFailCount) {
    this.caseFailCount = caseFailCount;
  }


  public Integer getCaseSuccessCount() {
    return caseSuccessCount;
  }


  public void setCaseSuccessCount(Integer caseSuccessCount) {
    this.caseSuccessCount = caseSuccessCount;
  }


  public String getRecord() {
    return record;
  }


  public void setCaseRequests(List<CaseRequest> caseRequests) {
    this.caseRequests = caseRequests;
  }



}
