package com.xiaodou.live.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.live.domain.LiveSerie;
import com.xiaodou.live.service.facede.LiveServiceFacade;
import com.xiaodou.live.vo.request.LiveDetailRequest;
import com.xiaodou.live.vo.request.SerieDetailRequest;
import com.xiaodou.live.vo.response.LiveDetailResponse;
import com.xiaodou.live.vo.response.SerieDetailResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name LiveDetailService 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月26日
 * @description 直播详情service
 * @version 1.0
 */
@Service("liveDetailService")
public class LiveDetailService extends AbstractLiveService {

  /** liveServiceFacade 直播领域逻辑Facade */
  @Resource
  LiveServiceFacade liveServiceFacade;
  
  /**
   * 4、系列直播详情
   * @param request 系列直播详情请求
   * @return 系列直播详情响应
   */
  public SerieDetailResponse serieDetail(SerieDetailRequest request) {
    SerieDetailResponse result = new SerieDetailResponse(ResultType.SUCCESS);
    LiveSerie serie = liveServiceFacade.querySerieById(request.getLiveSerieId());
    result.setSerieInfo(serie);
    return result;
  }

  /**
   * 5、直播详情
   * @param request 直播详情请求
   * @return 直播详情响应
   */
  public LiveDetailResponse liveDetail(LiveDetailRequest request) {
    return new LiveDetailResponse(ResultType.SUCCESS);
  }
  
}
