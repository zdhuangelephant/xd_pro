package com.xiaodou.resources.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.enums.product.ResourceType;
import com.xiaodou.resources.model.product.CourseProduct;
import com.xiaodou.resources.model.product.CourseProductChapter;
import com.xiaodou.resources.model.product.CourseProductResource;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.vo.product.ChapterResource;
import com.xiaodou.resources.vo.product.ResourceVo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name CourseCheckResponse CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月24日
 * @description 课程资源响应类
 * @version 1.0
 */
public class CourseResourceResponse extends BaseResponse {
  public CourseResourceResponse() {}

  public CourseResourceResponse(ResultType resultType) {
    super(resultType);
  }

  public CourseResourceResponse(ProductResType resultType) {
    super(resultType);
  }

  /** score 考核成绩 */
  private String score = String.valueOf(-1);
  private List<ChapterResource> directory = Lists.newArrayList();
  /* finalExam 期末考试 */
  private ResourceVo finalExam;

  public ResourceVo getFinalExam() {
    return finalExam;
  }

  public void setFinalExam(ResourceVo finalExam) {
    this.finalExam = finalExam;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public List<ChapterResource> getDirectory() {
    return directory;
  }

  public void setDirectory(List<ChapterResource> directory) {
    this.directory = directory;
  }

  public void setProduct(CourseProduct product) {
    if (null != product.getScore())
      this.score = QuesBaseConstant.D_FORMAT.format(product.getScore());
    if (null != product.getChapterList() && product.getChapterList().size() > 0) {
      for (CourseProductChapter productChapter : product.getChapterList()) {
        if (null == productChapter) continue;
        ChapterResource chapter = new ChapterResource();
        chapter.initFromProductChapter(productChapter);
        this.directory.add(chapter);
      }
    }
    if (null != product.getResourceList() && product.getResourceList().size() > 0) {
      for (CourseProductResource productResource : product.getResourceList()) {
        if (null == productResource) continue;
        if (ResourceType.FINAL.getTypeId().equals(productResource.getResourceType())) {
          ResourceVo finalExam = new ResourceVo();
          finalExam.getResourceFromProductItem(productResource);
          this.finalExam = finalExam;
        }
      }
    }

  }

}
