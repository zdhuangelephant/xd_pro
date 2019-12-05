package com.xiaodou.resources.vo.product;

import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.resources.enums.product.CourseStatus;
import com.xiaodou.resources.model.admin.Admin;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.util.DateUtils;

/**
 * @name CourseInfo CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月24日
 * @description 课程信息模型
 * @version 1.0
 */
public class CourseInfo {

  /** courseId 课程ID */
  private String courseId = StringUtils.EMPTY;
  /** courseName 课程名称 */
  private String courseName = StringUtils.EMPTY;
  /** cImgUrl 课程封面图片 */
  private String courseImgUrl = StringUtils.EMPTY;
  /** ownerId 用户ID */
  private String ownerId = StringUtils.EMPTY;
  /** ownerPortrait 用户头像 */
  private String ownerPortrait = StringUtils.EMPTY;
  /** ownerName 用户姓名 */
  private String ownerName = StringUtils.EMPTY;
  /** ownerDesc 用户描述 */
  private String ownerDesc = StringUtils.EMPTY;
  /** certify 认证信息 */
  private String certify = StringUtils.EMPTY;
  /** bgIUrl 介绍背景图 */
  private String bgImgUrl = StringUtils.EMPTY;
  /** price 课程价格 */
  private String price = StringUtils.EMPTY;
  /** courseStatus 课程状态 */
  private String courseStatus = StringUtils.EMPTY;
  /** courseStatusDesc 课程状态描述 */
  private String courseStatusDesc = StringUtils.EMPTY;
  /** courseTime 课程周期 */
  private String courseTime = StringUtils.EMPTY;
  /** courseDesc 课程描述 */
  private String courseDesc = StringUtils.EMPTY;
  /** buyStatus 用户购买状态 */
  private String buyStatus = StringUtils.EMPTY;
  /** signNum 报名人数 */
  private String signNum = String.valueOf(0);
  /** courseWeek 课程周 */
  private String courseWeek = StringUtils.EMPTY;

  public String getCourseWeek() {
    return courseWeek;
  }

  public void setCourseWeek(String courseWeek) {
    this.courseWeek = courseWeek;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getOwnerPortrait() {
    return ownerPortrait;
  }

  public void setOwnerPortrait(String ownerPortrait) {
    this.ownerPortrait = ownerPortrait;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public String getOwnerDesc() {
    return ownerDesc;
  }

  public void setOwnerDesc(String ownerDesc) {
    this.ownerDesc = ownerDesc;
  }

  public String getCertify() {
    return certify;
  }

  public void setCertify(String certify) {
    this.certify = certify;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getCourseStatus() {
    return courseStatus;
  }

  public void setCourseStatus(String courseStatus) {
    this.courseStatus = courseStatus;
  }

  public String getCourseStatusDesc() {
    return courseStatusDesc;
  }

  public void setCourseStatusDesc(String courseStatusDesc) {
    this.courseStatusDesc = courseStatusDesc;
  }

  public String getCourseTime() {
    return courseTime;
  }

  public void setCourseTime(String courseTime) {
    this.courseTime = courseTime;
  }

  public String getCourseDesc() {
    return courseDesc;
  }

  public void setCourseDesc(String courseDesc) {
    this.courseDesc = courseDesc;
  }

  public String getBuyStatus() {
    return buyStatus;
  }

  public void setBuyStatus(String buyStatus) {
    this.buyStatus = buyStatus;
  }

  public String getSignNum() {
    return signNum;
  }

  public void setSignNum(String signNum) {
    this.signNum = signNum;
  }


  public String getCourseImgUrl() {
    return courseImgUrl;
  }

  public void setCourseImgUrl(String courseImgUrl) {
    this.courseImgUrl = courseImgUrl;
  }

  public String getBgImgUrl() {
    return bgImgUrl;
  }

  public void setBgImgUrl(String bgImgUrl) {
    this.bgImgUrl = bgImgUrl;
  }

  public CourseInfo getCourseInfoByProduct(ProductModel productModel) throws ParseException {
    if (null != productModel) {
      this.courseDesc = productModel.getBriefInfo();
      if (null != productModel.getId()) this.courseId = productModel.getId().toString();
      this.courseImgUrl = productModel.getImageUrl();
      this.courseName = productModel.getName();
      this.price = productModel.getPayAmount().toString();
      this.courseTime =
          String.format(
              "%s至%s",
              null != productModel.getBeginApplyTime() ? DateUtil.SDF_YMD.format(productModel
                  .getBeginApplyTime()) : "-", null != productModel.getEndApplyTime()
                  ? DateUtil.SDF_YMD.format(productModel.getEndApplyTime())
                  : "-");
      this.bgImgUrl = productModel.getBgImgUrl();
      this.signNum = productModel.getCurrApplyCount().toString();
      Timestamp beginTime = productModel.getBeginApplyTime();
      Timestamp endTime = productModel.getEndApplyTime();
      Timestamp nowTime = new Timestamp(System.currentTimeMillis());
      if (null != beginTime && nowTime.before(beginTime)) {
        this.courseStatus = CourseStatus.coming.getCode();
        this.courseStatusDesc = CourseStatus.coming.getMsg();
      } else if (null != endTime && nowTime.after(endTime)) {
        this.courseStatus = CourseStatus.end.getCode();
        this.courseStatusDesc = CourseStatus.end.getMsg();
      } else if ((null != beginTime && nowTime.after(beginTime) && null != endTime && nowTime
          .before(endTime))) {
        this.courseStatus = CourseStatus.progress.getCode();
        this.courseStatusDesc = CourseStatus.progress.getMsg();
        String beginWeek = "-";
        String endWeek = "-";
        if (null != beginTime) {
          beginWeek =
              String.valueOf(DateUtils.getDiffWeeks(beginTime.toString(), nowTime.toString()));
          if (null != endTime)
            endWeek = String.valueOf(DateUtils.getDiffWeeks(beginTime.toString(), endTime.toString()));
        }
        this.courseWeek = String.format("进行至第%s周,共%s周", beginWeek, endWeek);
      }
    }
    return this;
  }

  public CourseInfo getCourseInfoByAdmin(Admin admin) {
    if (null != admin) {
      this.ownerDesc = "";
      if (null != admin.getId()) this.ownerId = admin.getId().toString();
      this.ownerName = admin.getRealName();
      this.ownerPortrait = "";
      this.certify = admin.getMerchant();
    }
    return this;
  }
}
