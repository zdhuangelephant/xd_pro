package com.xiaodou.live.vo.response;

import java.util.List;

import com.xiaodou.live.vo.model.ListSerieModel;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name SerieListResponse 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月25日
 * @description 系列直播列表响应类
 * @version 1.0
 */
public class SerieListResponse extends ResultInfo {
  public SerieListResponse(ResultType type){
    super(type);
  }
  
  /** seriesList 系列直播列表 */
  private List<ListSerieModel> seriesList;
  /** pageNo 页码 */
  private String pageNo;
  public List<ListSerieModel> getSeriesList() {
    return seriesList;
  }
  public void setSeriesList(List<ListSerieModel> seriesList) {
    this.seriesList = seriesList;
  }
  public String getPageNo() {
    return pageNo;
  }
  public void setPageNo(String pageNo) {
    this.pageNo = pageNo;
  }

}
