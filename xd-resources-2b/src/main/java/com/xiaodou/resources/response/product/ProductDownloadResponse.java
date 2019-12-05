package com.xiaodou.resources.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.vo.product.ResourceDescription;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.course.web.response.product.ProductDownloadResponse.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月20日
 * @description 课件批量下载接口返回值
 * @version 1.0
 */
public class ProductDownloadResponse extends BaseResponse {

  public ProductDownloadResponse(ResultType resultType) {
    super(resultType);
  }

  /** resourceList 课件列表 */
  private List<ResourceMap> resourceList = Lists.newArrayList();

  public List<ResourceMap> getResourceList() {
    return resourceList;
  }

  public void setResourceList(List<ResourceMap> resourceList) {
    this.resourceList = resourceList;
  }

  /**
   * @name @see com.xiaodou.course.web.response.product.ResourceMap.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年10月20日
   * @description 课件地址<-->节ID映射关系类
   * @version 1.0
   */
  public static class ResourceMap extends ResourceDescription {
    /** itemId 节ID */
    private String itemId;
    /** 资源Id */
    private String resourceId;

    public String getItemId() {
      return itemId;
    }

    public void setItemId(String itemId) {
      this.itemId = itemId;
    }

    public String getResourceId() {
      return resourceId;
    }

    public void setResourceId(String resourceId) {
      this.resourceId = resourceId;
    }

  }

}
