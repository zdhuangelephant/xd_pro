package com.xiaodou.ms.web.request.admin;

import com.xiaodou.ms.web.request.BaseRequest;
import org.springframework.validation.Errors;

/**
 * Created by zyp on 14-9-26.
 */
public class BaseConfigRequest extends BaseRequest {

  /**
   * 站点名
   */
  private String siteName;

  /**
   * 站点url
   */
  private String siteUrl;

  /**
   * 站点标题
   */
  private String siteTitle;

  /**
   * 站点关键词
   */
  private String siteKeywords;

  /**
   * 站点描述
   */
  private String siteDescription;

  /**
   * 站点联系人
   */
  private String contactMan;

  /**
   * 站点联系手机号
   */
  private String contactMobile;

  /**
   * 站点联系邮箱
   */
  private String contactEmail;

  /**
   * 站点联系QQ
   */
  private String contactQQ;

  /**
   * 站点联系电话
   */
  private String contactTel;

  /**
   * icp证号
   */
  private String icp;

  /**
   * 是否启用gzip压缩
   */
  private String gzip;

  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public String getSiteUrl() {
    return siteUrl;
  }

  public void setSiteUrl(String siteUrl) {
    this.siteUrl = siteUrl;
  }

  public String getSiteTitle() {
    return siteTitle;
  }

  public void setSiteTitle(String siteTitle) {
    this.siteTitle = siteTitle;
  }

  public String getSiteKeywords() {
    return siteKeywords;
  }

  public void setSiteKeywords(String siteKeywords) {
    this.siteKeywords = siteKeywords;
  }

  public String getSiteDescription() {
    return siteDescription;
  }

  public void setSiteDescription(String siteDescription) {
    this.siteDescription = siteDescription;
  }

  public String getContactMan() {
    return contactMan;
  }

  public void setContactMan(String contactMan) {
    this.contactMan = contactMan;
  }

  public String getContactMobile() {
    return contactMobile;
  }

  public void setContactMobile(String contactMobile) {
    this.contactMobile = contactMobile;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getContactQQ() {
    return contactQQ;
  }

  public void setContactQQ(String contactQQ) {
    this.contactQQ = contactQQ;
  }

  public String getContactTel() {
    return contactTel;
  }

  public void setContactTel(String contactTel) {
    this.contactTel = contactTel;
  }

  public String getIcp() {
    return icp;
  }

  public void setIcp(String icp) {
    this.icp = icp;
  }

  public String getGzip() {
    return gzip;
  }

  public void setGzip(String gzip) {
    this.gzip = gzip;
  }

  @Override
  public void validate(Object o, Errors errors) {
    //    super.validate(o, errors);
  }
}
