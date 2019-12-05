package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.ValueScope;

/**
 * 话题列表
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午7:34:22
 */

public class ResourcesListDynamicRequest extends BaseRequest {
  /** 最后一条话题ID */
  private String resourcesId;
  /** 每页显示个数 */
  @LegalValue(value = "1", scope = ValueScope.GE)
  private String size = "20";
  @NotEmpty
  private String ids;
  
  private String resourcesType;
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

  public String getIds() {
		return ids;
  }

	public void setIds(String ids) {
		this.ids = ids;
	}


	public String getResourcesType() {
		return resourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}
}
