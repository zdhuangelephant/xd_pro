package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue;
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
public class ReCommendListRequest extends BaseRequest {
  /** 最后一条话题ID */
  private Integer threshold;
  /** 每页显示个数 */
  @LegalValue(value = "1", scope = ValueScope.GE)
  private String size = "20";
  private Integer resourcesId;
  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }
  public Integer getThreshold() {
	 return threshold;
  }

public void setThreshold(Integer threshold) {
	this.threshold = threshold;
}

public Integer getResourcesId() {
	return resourcesId;
}

public void setResourcesId(Integer resourcesId) {
	this.resourcesId = resourcesId;
}
}
