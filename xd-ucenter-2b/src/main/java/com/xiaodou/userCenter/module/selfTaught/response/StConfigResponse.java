package com.xiaodou.userCenter.module.selfTaught.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel;
import com.xiaodou.userCenter.module.domain.vo.RegionVO;
import com.xiaodou.userCenter.response.BaseResultInfo;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class StConfigResponse extends BaseResultInfo {

  public StConfigResponse(ResultType type) {
    super(type);
  }

  public StConfigResponse(UcenterResType type) {
    super(type);
  }

  private ConfigData data;
  
  public static class CategoryConfigData extends ConfigData{
    /** benkeList 本科专业列表 */
    List<ProductCategoryUtilModel> benkeList = Lists.newArrayList();
    /** zhuankeList 专科专业列表 */
    List<ProductCategoryUtilModel> zhuankeList = Lists.newArrayList();

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

  }
  
  @Data
  public static class ConfigData {
    /** thirdlogin 三方登录 */
    private List<String> thirdlogin = Lists.newArrayList();
    /** shareplateform 分享平台 */
    private List<String> shareplateform = Lists.newArrayList();
    /** blankNotice 系统公告和活动 */
    private BlankNotice blankNotice = new BlankNotice();
    /*feedBackCategoryList 意见反馈 类型列表*/
    private List<String> feedbackCategoryList = Lists.newArrayList();
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  public static class RegionConfigData extends ConfigData{
    /**regionList 地域列表*/
    private List<RegionVO> regionList = Lists.newArrayList();
    private String regionCount = String.valueOf(0);
  }
  
  /**
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @description 首页弹出公告实体类
   * @version 1.0
   */
  @Data
  public static class BlankNotice {
    /** type  显示频次 0:每日首次， 1：每次启动 2：一次性*/
    private Short type;
    /** jumpUrl 跳转地址 http:// app:// */
    private String jumpUrl;
    /** title 开屏通知标题 */
    private String title;
    /** image 展示地址 http:// */
    private String image;
    /** isVisible 是否显示 */
    private Short isVisible;
    
    public BlankNotice() {
      super();
    }

    public BlankNotice(Short type, String jumpUrl,String title,String image,Short isVisible) {
      super();
      this.type = type;
      this.jumpUrl = jumpUrl;
      this.title = title;
      this.image = image;
      this.isVisible = isVisible;
    }

    public BlankNotice initBlankNotice(BlankNoticeResponse response) {
      return new BlankNotice(response.getType(), response.getJumpUrl(), response.getTitle(), response.getImage(), response.getIsVisible());
    }
  }
}
