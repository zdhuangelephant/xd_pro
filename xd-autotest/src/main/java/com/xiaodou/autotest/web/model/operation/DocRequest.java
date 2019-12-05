package com.xiaodou.autotest.web.model.operation;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.model.Param;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.autotest.web.model.operation.Request.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年8月15日
 * @description
 * @version 1.0
 */
public class DocRequest implements Comparable<DocRequest> {
  /** 主键 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false, isMajor = true)
  private String id;
  /** 请求唯一ID */
  @GeneralField
  @Column(canUpdate = false, sortBy = false)
  private String uniqueId;

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  /** 名称 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String name;
  /** 详情 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String desc;
  /** 默认方法 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String method = ActionConstant.DEFAULT_METHOD.toString();
  /** param */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String params;
  /** collectionId */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String docId;
  /** 协议类型 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String protocol = ActionConstant.DEFAULT_PROTOCOL.toString();
  /** 版本号 */
  private String version = "0.0.0";
  /** special 临时版本 */
  private Boolean special = Boolean.FALSE;
  /** previous 行为链,指向前一版本 */
  @JSONField(serialize = false, deserialize = false)
  private DocRequest previous;
  /** 请求url */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String url;
  /** 格式 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String format = ActionConstant.DEFAULT_FORMAT.toString();
  /** 前置校验条件 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String preConds;
  /** 前置设置列表 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String preSets;
  /** 测试条目列表 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String testCases;
  /** 后置设置列表 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String afterSets;
  /** 出参列表 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String outParams;
  /** 用户ID */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String userId;
  /** 排序 */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer sort;
  /** 创建时间 */
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp createTime;
  /** 更新时间 */
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp updateTime;

  private List<Param> paramList = Lists.newArrayList();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    if (StringUtils.isNotBlank(version)) {
      this.version = version;
    }
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

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public String getDocId() {
    return docId;
  }

  public void setDocId(String docId) {
    this.docId = docId;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getPreConds() {
    return preConds;
  }

  public void setPreConds(String preConds) {
    this.preConds = preConds;
  }

  public String getPreSets() {
    return preSets;
  }

  public void setPreSets(String preSets) {
    this.preSets = preSets;
  }

  public String getTestCases() {
    return testCases;
  }

  public void setTestCases(String testCases) {
    this.testCases = testCases;
  }

  public String getAfterSets() {
    return afterSets;
  }

  public void setAfterSets(String afterSets) {
    this.afterSets = afterSets;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(DocRequest.class, "doc_request",
            "D:/snippets/eclipseWorks/xd-autotest/src/main/resources/conf/mybatis/operation")
        .buildXml();
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public String getOutParams() {
    return outParams;
  }

  public void setOutParams(String outParams) {
    this.outParams = outParams;
  }

  public List<Param> getParamList() {
    return paramList;
  }

  public void setParamList(List<Param> paramList) {
    this.paramList = paramList;
  }

  @Override
  public int compareTo(DocRequest previous) {
    return this.version.compareTo(previous.version);
  }

  public void setPrevious(DocRequest previous) {
    if (null != previous) {
      this.previous = previous;
    }
  }

  public DocRequest getPrevious() {
    return previous;
  }

  public DocRequest getSuitable(String version) {
    if (StringUtils.isBlank(version)) {
      return this;
    }
    int compareTo = this.version.compareTo(version);
    if (compareTo == 0) {
      return this;
    }
    if (!special && compareTo < 0) {
      return this;
    }
    if (null != previous) {
      return previous.getSuitable(version);
    }
    return null;
  }

}
