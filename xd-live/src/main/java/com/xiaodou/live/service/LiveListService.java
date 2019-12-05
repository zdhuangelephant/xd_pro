package com.xiaodou.live.service;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.live.domain.LiveInfo;
import com.xiaodou.live.domain.LiveSerie;
import com.xiaodou.live.service.facede.LiveServiceFacade;
import com.xiaodou.live.vo.model.ListModel;
import com.xiaodou.live.vo.model.ListSerieModel;
import com.xiaodou.live.vo.request.LiveListRequest;
import com.xiaodou.live.vo.request.RecommendListRequest;
import com.xiaodou.live.vo.request.SerieListRequest;
import com.xiaodou.live.vo.response.LiveListResponse;
import com.xiaodou.live.vo.response.RecommendListResponse;
import com.xiaodou.live.vo.response.SerieListResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name LiveListService CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年8月24日
 * @description 直播列表service
 * @version 1.0
 */
@Service("liveListService")
public class LiveListService extends AbstractLiveService {

  /** liveServiceFacade 直播领域逻辑Facade */
  @Resource
  LiveServiceFacade liveServiceFacade;

  /**
   * 1、直播列表接口
   * 
   * @param request 直播列表请求
   * @return 直播列表响应
   */
  public LiveListResponse list(LiveListRequest request) {
    LiveListResponse response = new LiveListResponse(ResultType.SUCCESS);
    // 设置推荐直播
    SET_LIVE: {
      IQueryParam liveParam = new QueryParam();
      liveParam.addInput("reviewResult", "1");
      liveParam.addInput("timeOverLower", new Timestamp(System.currentTimeMillis()));
      Page<LiveInfo> livePage = new Page<>();
      livePage.setPageNo(1);
      livePage.setPageSize(20);
      livePage = liveServiceFacade.queryLiveList(liveParam, livePage);
      if (null == livePage || null == livePage.getResult() || livePage.getResult().size() == 0)
        break SET_LIVE;
      for (LiveInfo live : livePage.getResult()) {
        response.getRecommendList().add(new ListModel(live));
      }
    }
    // 设置系列直播
    SET_SERIE: {
      IQueryParam serieParam = new QueryParam();
      serieParam.addInput("reviewResult", "1");
      serieParam.addInput("timeOverLower", new Timestamp(System.currentTimeMillis()));
      Page<LiveSerie> seriePage = new Page<>();
      seriePage.setPageNo(1);
      seriePage.setPageSize(20);
      seriePage = liveServiceFacade.querySerieList(serieParam, seriePage);
      if (null == seriePage || null == seriePage.getResult() || seriePage.getResult().size() == 0)
        break SET_SERIE;
      for (LiveSerie serie : seriePage.getResult()) {
        response.getSeriesList().add(new ListSerieModel(serie));
      }
    }
    return response;
  }

  /**
   * 2、直播－系列直播（列表／分页）
   * 
   * @param request 系列直播列表请求
   * @return 系列直播列表响应
   */
  public SerieListResponse serieList(SerieListRequest request) {
    SerieListResponse response = new SerieListResponse(ResultType.SUCCESS);
    Integer pageNo = request.getPageNo() + 1;
    IQueryParam serieParam = new QueryParam();
    serieParam.addInput("reviewResult", "1");
    serieParam.addInput("timeOverLower", new Timestamp(System.currentTimeMillis()));
    Page<LiveSerie> seriePage = new Page<>();
    seriePage.setPageNo(pageNo);
    seriePage.setPageSize(20);
    seriePage = liveServiceFacade.querySerieList(serieParam, seriePage);
    if (null == seriePage || null == seriePage.getResult() || seriePage.getResult().size() == 0)
      return response;
    for (LiveSerie serie : seriePage.getResult()) {
      response.getSeriesList().add(new ListSerieModel(serie));
    }
    response.setPageNo(pageNo.toString());
    return response;
  }

  /**
   * 3、直播－推荐直播（列表／分页）
   * 
   * @param request 推荐直播列表请求
   * @return 推荐直播列表相应
   */
  public RecommendListResponse recommendList(RecommendListRequest request) {
    RecommendListResponse response = new RecommendListResponse(ResultType.SUCCESS);
    Integer pageNo = request.getPageNo() + 1;
    IQueryParam liveParam = new QueryParam();
    liveParam.addInput("reviewResult", "1");
    liveParam.addInput("timeOverLower", new Timestamp(System.currentTimeMillis()));
    Page<LiveInfo> livePage = new Page<>();
    livePage.setPageNo(pageNo);
    livePage.setPageSize(20);
    livePage = liveServiceFacade.queryLiveList(liveParam, livePage);
    if (null == livePage || null == livePage.getResult() || livePage.getResult().size() == 0)
      return response;
    for (LiveInfo live : livePage.getResult()) {
      response.getRecommendList().add(new ListModel(live));
    }
    response.setPageNo(pageNo.toString());
    return response;
  }

}
