package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.summer.validator.enums.ValueScope;

/**
 * 话题列表
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午7:34:22
 */
@OverComeField(field = {"uid", "module"}, annotiation = AnnotationType.NotEmpty)
public class ForumListRequest extends BaseRequest{
  /** 分类ID */
  @NotEmpty
  private String classificationId;

  /** 最后一条话题ID */
  private String resourcesId;

  /** 每页显示个数 */
  @LegalValue(value = "1", scope = ValueScope.GE)
  private String size = "20";
  public String getClassificationId() {
    return classificationId;
  }

  public void setClassificationId(String classificationId) {
    this.classificationId = classificationId;
  }


  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }



  public String getResourcesId() {
    return resourcesId;
  }

  public void setResourcesId(String resourcesId) {
    this.resourcesId = resourcesId;
  }



}
