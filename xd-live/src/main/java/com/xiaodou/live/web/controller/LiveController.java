package com.xiaodou.live.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.live.service.LiveDetailService;
import com.xiaodou.live.service.LiveListService;
import com.xiaodou.live.vo.request.LiveDetailRequest;
import com.xiaodou.live.vo.request.LiveListRequest;
import com.xiaodou.live.vo.request.RecommendListRequest;
import com.xiaodou.live.vo.request.SerieDetailRequest;
import com.xiaodou.live.vo.request.SerieListRequest;

/**
 * @name LiveController 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月24日
 * @description 直播服务控制器
 * @version 1.0
 */
@RequestMapping("live")
@Controller("liveController")
public class LiveController {
  
  /** liveListService 直播列表service */
  @Resource
  LiveListService liveListService;
  
  /** liveDetailService 直播详情service */
  @Resource
  LiveDetailService liveDetailService;

  /**
   * 1、直播列表接口
   * @param request 直播列表请求
   * @return 直播列表响应
   */
  @RequestMapping("list")
  @ResponseBody
  public String list(LiveListRequest request){
    return liveListService.list(request).toString0();
  }
  
  /**
   * 2、直播－系列直播（列表／分页）
   * @param request 系列直播列表请求
   * @return 系列直播列表响应
   */
  @RequestMapping("serie_list")
  @ResponseBody
  public String serieList(SerieListRequest request) {
    return liveListService.serieList(request).toString0();
  }
  
  /**
   * 3、直播－推荐直播（列表／分页）
   * @param request 推荐直播列表请求
   * @return 推荐直播列表相应
   */
  @RequestMapping("recommend_list")
  @ResponseBody
  public String recommendList(RecommendListRequest request) {
    return liveListService.recommendList(request).toString0();
  }

  /**
   * 4、系列直播详情
   * @param request 系列直播详情请求
   * @return 系列直播详情响应
   */
  @RequestMapping("serie_detail")
  @ResponseBody
  public String serieDetail(SerieDetailRequest request) {
    return liveDetailService.serieDetail(request).toString0();
  }

  /**
   * 5、直播详情
   * @param request 直播详情请求
   * @return 直播详情响应
   */
  @RequestMapping("live_detail")
  @ResponseBody
  public String liveDetail(LiveDetailRequest request) {
    return liveDetailService.liveDetail(request).toString0();
  }

}
