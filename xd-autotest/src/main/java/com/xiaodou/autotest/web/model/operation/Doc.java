package com.xiaodou.autotest.web.model.operation;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
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
public class Doc implements Comparable<Doc> {
  /** 主键 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false, isMajor = true)
  private String id;
  /** 文档唯一ID */
  @GeneralField
  @Column(canUpdate = false, sortBy = false)
  private String uniqueId;
  /** 名称 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String name;
  /** 详情 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String desc;

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
  private String version = "0.0.0";
  /** special 临时版本 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Boolean special = Boolean.FALSE;
  /** previous 行为链,指向前一版本 */
  @JSONField(serialize = false, deserialize = false)
  private Doc previous;
  /** request数量 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long count;
  /** 产品线 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String businessLine;
  /** 创建时间 */
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp createTime;
  /** 更新时间 */
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp updateTime;

  private String mockUrl;

  @Column(persistent = false)
  private String historyTime;
  
  @Column(persistent = false)
  private List<DocRequest> docReqList;

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

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(Doc.class, "doc", "E:/work3/autotest/src/main/resources/conf/mybatis/")
        .buildXml();
  }

  @Override
  public int compareTo(Doc previous) {
    return this.version.compareTo(previous.version);
  }

  public void setPrevious(Doc previous) {
    if (null != previous) {this.previous = previous;}
  }

  public Doc getPrevious() {
    return previous;
  }

  public Doc getSuitable(String version) {
    if (StringUtils.isBlank(version)) {return this;}
    int compareTo = this.version.compareTo(version);
    if (compareTo == 0) {return this;}
    if (!special && compareTo < 0) {return this;}
    if (null != previous) {return previous.getSuitable(version);}
    return null;
  }

  public String getMockUrl() {
    return mockUrl;
  }

  public void setMockUrl(String mockUrl) {
    this.mockUrl = mockUrl;
  }

  public String getHistoryTime() {
    return historyTime;
  }

  public void setHistoryTime(String historyTime) {
    this.historyTime = historyTime;
  }

  public List<DocRequest> getDocReqList() {
    return docReqList;
  }

  public void setDocReqList(List<DocRequest> docReqList) {
    this.docReqList = docReqList;
  }
  

}
