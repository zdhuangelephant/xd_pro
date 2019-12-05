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
public class ForumListByColumnRequest extends BaseRequest {
  /** 最后一条话题ID */
  private String resourcesId;

  /** pageNo 请求页码 */
  @LegalValue(value = "1", scope = ValueScope.GE)
  private Integer pageNo = 1;

  /** columnId 专栏ID */
  @NotEmpty
  private String columnId;


  /** 每页显示个数 */
  @LegalValue(value = "1", scope = ValueScope.GE)
  private Integer size = 20;

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public String getResourcesId() {
    return resourcesId;
  }

  public void setResourcesId(String resourcesId) {
    this.resourcesId = resourcesId;
  }


  public String getColumnId() {
    return columnId;
  }

  public void setColumnId(String columnId) {
    this.columnId = columnId;
  }

}
