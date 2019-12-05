package com.xiaodou.course.web.response.product;

import java.util.List;

import com.xiaodou.course.vo.product.ModuleSlide;
import com.xiaodou.course.vo.product.ProductListDetail;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zyp on 15/8/16.
 */
public class ProductListResponse extends BaseResponse {

  private TimeInfo timeInfo;

  // 幻灯片
  private List<ModuleSlide> imageUrlList;

  // 商品列表
  private List<ProductListDetail> courseList;

  public TimeInfo getTimeInfo() {
    return timeInfo;
  }

  public void setTimeInfo(TimeInfo timeInfo) {
    this.timeInfo = timeInfo;
  }

  public List<ModuleSlide> getImageUrlList() {
    return imageUrlList;
  }

  public void setImageUrlList(List<ModuleSlide> imageUrlList) {
    this.imageUrlList = imageUrlList;
  }

  public List<ProductListDetail> getCourseList() {
    return courseList;
  }

  public void setCourseList(List<ProductListDetail> courseList) {
    this.courseList = courseList;
  }

  public ProductListResponse(ResultType resultType) {
    super(resultType);
  }


  public static class TimeInfo {
    /** examTipsTime 考前押题时间 单位秒 */
    private Integer examTipsTime = 1234500;
    /** f2fLiveTime 直播试讲时间 单位秒 */
    private Integer f2fLiveTime = 1234500;

    public Integer getExamTipsTime() {
      return examTipsTime;
    }

    public void setExamTipsTime(Integer examTipsTime) {
      this.examTipsTime = examTipsTime;
    }

    public Integer getF2fLiveTime() {
      return f2fLiveTime;
    }

    public void setF2fLiveTime(Integer f2fLiveTime) {
      this.f2fLiveTime = f2fLiveTime;
    }
  }
}
