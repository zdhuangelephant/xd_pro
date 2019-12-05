package com.xiaodou.forum.vo.forum;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.util.DateUtil;

public class Forum {
  /** 话题ID */
  private String id;
  /** 话题发布人 */
  private String people;
  /** 话题发布人岁数 */
  private String age;
  /** 话题发布人地址信息 */
  private String address;
  /** 话题发布人头像 */
  private String portrait;
  /** 话题标题 */
  private String title;
  /** 话题简介 */
  private String outline;
  /** 话题内容 */
  private String content;
  /** 话题图片 */
  private List<String> images;
  /**
   * 话题属性 1-精华话题 2-普通话题
   */
  private String type;
  /** 话题发布时间 */
  private String time;
  /** 话题分类 */
  private String classificationId;
  /**
   * 话题名称
   */
  private String classificationName;
  /** 评论数 */
  private String repliesCount;
  /** 是否点赞 */
  private String isPraise = "0";
  /** 点赞数 */
  private String praiseNumber = "0";

  public Forum() {}

  public Forum(ForumUserModel model) {
    if (null != model && null != model.getId()) {
      setId(model.getId().toString());
      if (StringUtils.isJsonNotBlank(model.getImages())) {
        setImages(JSON.parseArray(model.getImages(), String.class));
      }
      if (null != model && null != model.getUser()) {
        setAddress(model.getUser().getAddress());
        if (null != model.getUser().getAge()) setAge(model.getUser().getAge().toString());
        setPeople(model.getUser().getNickName());
        setPortrait(model.getUser().getPortrait());
      }
      setOutline(model.getOutline());
      setClassificationName(model.getCategoryName());
      if (null != model.getCategoryId()) setClassificationId(model.getCategoryId().toString());
      setRepliesCount(null == model.getRepliesNumber() ? "0" : model.getRepliesNumber().toString());
      setTime(null == model.getCreateTime() ? DateUtil.relativeDateFormat(new Timestamp(System
          .currentTimeMillis())) : DateUtil.relativeDateFormat(model.getCreateTime()));
      setTitle(model.getTitle());
      setContent(model.getContent());
      setType(null == model.getDigest() ? "0" : model.getDigest().toString());
      setIsPraise((StringUtils.isNotBlank(model.getIsPraise()) && "0".equals(model.getIsPraise()))
          ? "0"
          : "1");
      setPraiseNumber(model.getPraiseNumber().toString());
    }
  }

  public String getClassificationName() {
    return classificationName;
  }

  public void setClassificationName(String classificationName) {
    this.classificationName = classificationName;
  }

  public String getPeople() {
    return people;
  }

  public void setPeople(String people) {
    this.people = people;
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getRepliesCount() {
    return repliesCount;
  }

  public void setRepliesCount(String repliesCount) {
    this.repliesCount = repliesCount;
  }

  public String getClassificationId() {
    return classificationId;
  }

  public void setClassificationId(String classificationId) {
    this.classificationId = classificationId;
  }

  public String getOutline() {
    return outline;
  }

  public void setOutline(String outline) {
    this.outline = outline;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIsPraise() {
    return isPraise;
  }

  public void setIsPraise(String isPraise) {
    this.isPraise = isPraise;
  }

  public String getPraiseNumber() {
    return praiseNumber;
  }

  public void setPraiseNumber(String praiseNumber) {
    this.praiseNumber = praiseNumber;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

}
