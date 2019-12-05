package com.xiaodou.oms.vo.input.order;

import java.util.Map;

import com.xiaodou.oms.vo.input.Page;
import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * <p>查询GorderList参数Pojo</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月1日
 */
public class GorderListPojo extends BasePojo {
  
  /**
   * 分页参数
   */
  @NotEmpty
  private Page page;
  
  /**
   * 查询参数
   */
  @NotEmpty
  private Gorder queryParams;
  
  /**
   * 查询返回的属性信息
   */
  @NotEmpty
  private Map<String, Object> outputProperties;

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public Gorder getQueryParams() {
    return queryParams;
  }

  public void setQueryParams(Gorder queryParams) {
    this.queryParams = queryParams;
  }

  public Map<String, Object> getOutputProperties() {
    return outputProperties;
  }

  public void setOutputProperties(Map<String, Object> outputProperties) {
    this.outputProperties = outputProperties;
  }

}
