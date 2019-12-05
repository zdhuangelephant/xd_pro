package com.xiaodou.live.service.facede;

import com.xiaodou.live.domain.LiveInfo;
import com.xiaodou.live.domain.LiveOperationLog;
import com.xiaodou.live.domain.LiveSerie;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name LiveServiceFacade CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年8月26日
 * @description 直播领域逻辑Facade
 * @version 1.0
 */
public interface LiveServiceFacade {

  /** 直播 */

  /**
   * 保存草稿
   * 
   * @param liveInfo 直播实体
   */
  void saveLive(LiveInfo liveInfo, String operator, String clientIp);

  /**
   * 申请直播
   * 
   * @param liveInfo 直播实体
   */
  void applyLive(LiveInfo liveInfo, String operator, String clientIp);

  /**
   * 撤回修改申请
   * 
   * @param liveInfo 直播实体
   */
  void recallLive(LiveInfo liveInfo, String operator, String clientIp);

  /**
   * 根据ID查询直播实例
   * 
   * @param liveId 直播实例ID
   * @return 直播实例
   */
  LiveInfo queryLiveById(String liveId);

  /**
   * 根据条件查询直播实例列表
   * 
   * @param param 查询条件
   * @return 直播实例列表
   */
  Page<LiveInfo> queryLiveList(IQueryParam param, Page<LiveInfo> page);

  /** 系列直播 */

  /**
   * 插入系列直播实例
   * 
   * @param liveSerie 系列直播实例
   */
  void insertSerie(LiveSerie liveSerie);

  /**
   * 根据ID更新系列直播实例
   * 
   * @param liveSerie 系列直播实例
   */
  void updateSerieById(LiveSerie liveSerie);

  /**
   * 根据ID查询系列直播实例
   * 
   * @param serieId 系列直播ID
   * @return 系列直播实例
   */
  LiveSerie querySerieById(String serieId);

  /**
   * 根据条件查询系列直播实例列表
   * 
   * @param param 查询条件
   * @return 系列直播实例列表
   */
  Page<LiveSerie> querySerieList(IQueryParam param, Page<LiveSerie> page);

  /** 直播行为记录 */

  /**
   * 根据条件查询操作记录日志列表
   * 
   * @param param 查询条件
   * @return 操作日志列表
   */
  Page<LiveOperationLog> queryOperationLogList(IQueryParam param, Page<LiveOperationLog> page);

}
