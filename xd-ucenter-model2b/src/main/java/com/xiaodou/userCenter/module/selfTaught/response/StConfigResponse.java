package com.xiaodou.userCenter.module.selfTaught.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.response.ConfigResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class StConfigResponse extends ConfigResponse<StConfigResponse.ConfigData> {

  public StConfigResponse(ResultType type) {
    super(type);
  }

  public StConfigResponse(UcenterResType type) {
    super(type);
  }

  public static class ConfigData {
    /** benkeList 本科专业列表 */
    List<ProductCategoryUtilModel> benkeList = Lists.newArrayList();
    /** zhuankeList 专科专业列表 */
    List<ProductCategoryUtilModel> zhuankeList = Lists.newArrayList();
    /** thirdlogin 三方登录 */
    private List<String> thirdlogin = Lists.newArrayList();
    /** shareplateform 分享平台 */
    private List<String> shareplateform = Lists.newArrayList();
    /** blankNotice 系统公告和活动 */
    private BlankNotice blankNotice = new BlankNotice();


    public List<ProductCategoryUtilModel> getBenkeList() {
      return benkeList;
    }

    public void setBenkeList(List<ProductCategoryUtilModel> benkeList) {
      this.benkeList = benkeList;
    }

    public String getBenkeCount() {
      return (null != benkeList) ? Integer.toString(benkeList.size()) : Integer.toString(0);
    }

    public List<ProductCategoryUtilModel> getZhuankeList() {
      return zhuankeList;
    }

    public void setZhuankeList(List<ProductCategoryUtilModel> zhuankeList) {
      this.zhuankeList = zhuankeList;
    }

    public String getZhuankeCount() {
      return (null != zhuankeList) ? Integer.toString(zhuankeList.size()) : Integer.toString(0);
    }

    public void setBlankNotice(BlankNotice blankNotice) {
      this.blankNotice = blankNotice;
    }

    public BlankNotice getBlankNotice() {
      return blankNotice;
    }

    public void setBlankNotice(BlankNoticeResponse blankNoticeResponse) {
      BlankNotice blankNotice = new BlankNotice();
      this.blankNotice = blankNotice.setBlankNotice(blankNoticeResponse);
    }

    public List<String> getThirdlogin() {
      return thirdlogin;
    }

    public void setThirdlogin(List<String> thirdlogin) {
      this.thirdlogin = thirdlogin;
    }

    public List<String> getShareplateform() {
      return shareplateform;
    }

    public void setShareplateform(List<String> shareplateform) {
      this.shareplateform = shareplateform;
    }

  }

  public static class Major {
    /** majorId 专业唯一标识 */
    private String majorId = StringUtils.EMPTY;
    /** majorName 专业名称 */
    private String majorName = StringUtils.EMPTY;

    public String getMajorId() {
      return majorId;
    }

    public void setMajorId(String majorId) {
      this.majorId = majorId;
    }

    public String getMajorName() {
      return majorName;
    }

    public void setMajorName(String majorName) {
      this.majorName = majorName;
    }
  }

  /**
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @description 首页弹出公告实体类
   * @version 1.0
   */
  public static class BlankNotice {
    // 展示方式：0 每次都展示，1 只展示一次，2 不展示
    private String type = StringUtils.EMPTY;
    // 展示地址 http://
    private String showUrl = StringUtils.EMPTY;
    // 跳转地址 http:// app://
    private String jumpUrl = StringUtils.EMPTY;

    public BlankNotice() {
      super();
    }

    public BlankNotice(String type, String showUrl, String jumpUrl) {
      super();
      this.type = type;
      this.showUrl = showUrl;
      this.jumpUrl = jumpUrl;
    }

    public BlankNotice setBlankNotice(BlankNoticeResponse blankNoticeResponse) {
      return new BlankNotice(blankNoticeResponse.getType().toString(),
          blankNoticeResponse.getShowUrl(), blankNoticeResponse.getJumpUrl());
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getShowUrl() {
      return showUrl;
    }

    public void setShowUrl(String showUrl) {
      this.showUrl = showUrl;
    }

    public String getJumpUrl() {
      return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
      this.jumpUrl = jumpUrl;
    }
  }
}
