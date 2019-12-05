package com.xiaodou.resources.model.user;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 
 * @name UserLearnAchieveModel
 * @CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年6月18日
 * @description 记录用户最近学习课程至第几章第几节
 * @version 1.0
 */
public class UserLearnAchieveModel {

  // id
  @Column(isMajor = true)
  private Integer id;

  // 用户Id
  @Column(canUpdate = false)
  private Integer userId;

  // app模块id
  @Column(canUpdate = false)
  private Integer moduleId;

  // 产品Id
  @Column(canUpdate = false)
  private Integer productId;

  // 章节Id
  @Column(canUpdate = true)
  private Integer chapterId;

  // 条目Id
  @Column(canUpdate = true)
  private Integer itemId;

  // 创建时间
  @Column(canUpdate = false)
  private Timestamp createTime;


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
    MybatisXmlTool.getInstance(UserLearnAchieveModel.class, "xd_user_learn_achieve",
        "D:\\work\\workspace_xiaodou\\xd-course-2b/src/main/resources/conf/mybatis/user/")
        .buildXml();
  }
}
