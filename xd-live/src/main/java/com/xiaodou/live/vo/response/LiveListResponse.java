package com.xiaodou.live.vo.response;

import java.util.List;

import com.xiaodou.live.constants.LiveConstants;
import com.xiaodou.live.vo.model.ListModel;
import com.xiaodou.live.vo.model.ListSerieModel;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name LiveListResponse 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月24日
 * @description 直播列表响应类
 * @version 1.0
 */
public class LiveListResponse extends ResultInfo {
  public LiveListResponse(ResultType type){
    super(type);
  }

  /** seriesList 系列直播列表 */
  private List<ListSerieModel> seriesList;
  /** recommendList 推荐直播列表 */
  private List<ListModel> recommendList;
  /** pageNo 页码 */
  private String pageNo = LiveConstants.S_FIRST_PAGENO;
  public List<ListSerieModel> getSeriesList() {
    return seriesList;
  }
  public void setSeriesList(List<ListSerieModel> seriesList) {
    this.seriesList = seriesList;
  }
  public List<ListModel> getRecommendList() {
    return recommendList;
  }
  public void setRecommendList(List<ListModel> recommendList) {
    this.recommendList = recommendList;
  }
  public String getPageNo() {
    return pageNo;
  }

}
