package com.xiaodou.resources.vo.product;

import java.sql.Timestamp;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.constant.product.CourseConstant;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.product.ProductItemModel.Resourcer;

/**
 * @name Resource CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月24日
 * @description 资源模型
 * @version 1.0
 */
public class ResourceVo {
  /** resourceId 资源ID */
  private String resourceId = StringUtils.EMPTY;
  /** resourceType 资源类型 */
  private String resourceType = StringUtils.EMPTY;
  /** resourceName 资源名称 */
  private String resourceName = StringUtils.EMPTY;
  /** resourceDesc 资源描述 */
  private String resourceDesc = StringUtils.EMPTY;
  /* resourcePic 资源图片 */
  private String resourcePic = StringUtils.EMPTY;
  /* deadline 截至时间 */
  private String deadline = CourseConstant.NODEADLINE;
  /** resourceUrl 资源地址 */
  private String resourceUrl = StringUtils.EMPTY;
  /**
   * resourceStatus 资源状态
   * 
   * @category 考核 -1:已结束，0：去测验，1：查看测验 资源状态
   * @category 讨论 -1：参与讨论，0：查看详情
   */
  private String resourceStatus = StringUtils.EMPTY;
  /** joinResult 参与结果 */
  private String joinResult = CourseConstant.NOJOINRESULT;

  private String score = "总分100";

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getResourcePic() {
    return resourcePic;
  }

  public void setResourcePic(String resourcePic) {
    this.resourcePic = resourcePic;
  }

  public String getDeadline() {
    return deadline;
  }

  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }

  public String getJoinResult() {
    return joinResult;
  }

  public void setJoinResult(String joinResult) {
    this.joinResult = joinResult;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public String getResourceDesc() {
    return resourceDesc;
  }

  public void setResourceDesc(String resourceDesc) {
    this.resourceDesc = resourceDesc;
  }

  public String getResourceUrl() {
    return resourceUrl;
  }

  public void setResourceUrl(String resourceUrl) {
    this.resourceUrl = resourceUrl;
  }

  public String getResourceStatus() {
    return resourceStatus;
  }

  public void setResourceStatus(String resourceStatus) {
    this.resourceStatus = resourceStatus;
  }

  public void getResourceFromProductItem(ProductItemModel resource) {
    if (null != resource) {
      if (null != resource.getId()) this.setResourceId(resource.getId().toString());
      this.setResourceName(resource.getName());
      if (null != resource.getScore())
        this.setScore(String.format("最终得分%s分，总分100", resource.getScore()));
      
      if (null != resource.getResourceType())
        this.setResourceType(resource.getResourceType().toString());
      this.setResourceDesc(resource.getDetail());
      if (null != resource.getDeadline()) {
        String _deadline = DateUtil.SDF_FULL.format(resource.getDeadline());
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        if (resource.getDeadline().after(nowTime) || resource.getDeadline().equals(nowTime)) {
          this.setDeadline(String.format("即将截止 %s", _deadline));
        } else {
          this.setDeadline(String.format("已截止 %s", _deadline));
        }
      }
      if (StringUtils.isJsonNotBlank(resource.getResource())) {
        Resourcer _resource = FastJsonUtil.fromJson(resource.getResource(), Resourcer.class);
        this.setResourceUrl(_resource.getFileUrl());
        this.setResourcePic(_resource.getCover());
      }
    }
  }
}
