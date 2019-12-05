package com.xiaodou.server.mapi.domain;

import java.sql.Timestamp;
import java.util.Date;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 
 * @name UserLearnRecordDayModel
 * @CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年5月31日
 * @description TODO
 * @version 1.0
 */
public class UserLearnRecordDayModel {

  // id
  @Column(isMajor = true)
  private Integer id;

  // 用户Id
  @Column(canUpdate = false)
  private Integer userId;

  // 产品Id
  @Column(canUpdate = false)
  private Integer productId;

  // appId
  @Column(canUpdate = false)
  private Integer moduleId;

  // 章节Id
  @Column(canUpdate = true)
  private Integer chapterId;

  // 条目Id
  @Column(canUpdate = true)
  private Integer itemId;

  // 学习时长
  @Column(canUpdate = true)
  private Integer learnTime;

  // 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题）
  private Short learnType;

  // 记录时间(按天算)
  private Date recordTime;

  // 创建时间
  private Timestamp createTime;


  public Short getLearnType() {
    return learnType;
  }

  public void setLearnType(Short learnType) {
    this.learnType = learnType;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Date getRecordTime() {
    return recordTime;
  }

  public void setRecordTime(Date recordTime) {
    this.recordTime = recordTime;
  }

  public Integer getLearnTime() {
    return learnTime;
  }

  public void setLearnTime(Integer learnTime) {
    this.learnTime = learnTime;
  }

  public Integer getModuleId() {
    return moduleId;
  }

  public void setModuleId(Integer moduleId) {
    this.moduleId = moduleId;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getChapterId() {
    return chapterId;
  }

  public void setChapterId(Integer chapterId) {
    this.chapterId = chapterId;
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(UserLearnRecordDayModel.class, "xd_user_learn_record_day",
        "D:\\work\\workspace_xiaodou\\xd-course-2b/src/main/resources/conf/mybatis/user/")
        .buildXml();
  }
}
