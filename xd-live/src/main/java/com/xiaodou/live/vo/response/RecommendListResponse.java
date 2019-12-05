package com.xiaodou.live.vo.response;

import java.util.List;

import com.xiaodou.live.vo.model.ListModel;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name RecommendListResponse 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月26日
 * @description 推荐直播列表相应
 * @version 1.0
 */
public class RecommendListResponse extends ResultInfo {
  public RecommendListResponse(ResultType type){
    super(type);
  }
  
  /** recommendList 推荐直播列表 */
  private List<ListModel> recommendList;
  /** pageNo 页码 */
  private String pageNo;
  public List<ListModel> getRecommendList() {
    return recommendList;
  }
  public void setRecommendList(List<ListModel> recommendList) {
    this.recommendList = recommendList;
  }
  public String getPageNo() {
    return pageNo;
  }
  public void setPageNo(String pageNo) {
    this.pageNo = pageNo;
  }

}
