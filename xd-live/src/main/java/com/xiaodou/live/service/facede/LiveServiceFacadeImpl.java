package com.xiaodou.live.service.facede;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.live.constants.LiveConstants;
import com.xiaodou.live.dao.LiveInfoDao;
import com.xiaodou.live.dao.LiveOperationLogDao;
import com.xiaodou.live.dao.LiveSerieDao;
import com.xiaodou.live.domain.LiveInfo;
import com.xiaodou.live.domain.LiveOperationLog;
import com.xiaodou.live.domain.LiveSerie;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name LiveServiceFacadeImpl CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年8月26日
 * @description 直播领域逻辑Facade实现类
 * @version 1.0
 */
@Service("liveServiceFacade")
public class LiveServiceFacadeImpl implements LiveServiceFacade {

  /** liveInfoDao 直播信息Dao */
  @Resource
  LiveInfoDao liveInfoDao;

  /** liveSerieDao 系列直播Dao */
  @Resource
  LiveSerieDao liveSerieDao;

  /** liveOperationLogDao 直播操作日志Dao */
  @Resource
  LiveOperationLogDao liveOperationLogDao;

  @Override
  public void saveLive(LiveInfo liveInfo, String operator, String clientIp) {
    LiveInfo oldLiveInfo = liveInfoDao.findEntityById(liveInfo);
    if (null != oldLiveInfo) throw new RuntimeException("不能重复提交申请");
    // 保存草稿
    liveInfo.setReviewResult(LiveConstants.LIVE_REVIEWRESULT_DRAFT);
    liveInfoDao.addEntity(liveInfo);
    // 记录操作日志
    LiveOperationLog log = new LiveOperationLog();
    log.setLiveId(liveInfo.getId());
    log.setOperation("saveLive");
    log.setNote("保存草稿");
    log.setUserId(operator);
    log.setUserIp(clientIp);
    log.setOperationTime(new Timestamp(System.currentTimeMillis()));
    liveOperationLogDao.addEntity(log);
  }

  @Override
  public void applyLive(LiveInfo liveInfo, String operator, String clientIp) {
    LiveInfo oldLiveInfo = liveInfoDao.findEntityById(liveInfo);
    // 提交申请
    if (null != oldLiveInfo) {
      if (!oldLiveInfo.getReviewResult().equals(LiveConstants.LIVE_REVIEWRESULT_DRAFT))
        throw new RuntimeException("请求不是草稿状态，无法提交申请");
      liveInfo.setReviewResult(LiveConstants.LIVE_REVIEWRESULT_TOENROLL);
      liveInfoDao.updateEntityById(liveInfo);
    } else {
      liveInfo.setReviewResult(LiveConstants.LIVE_REVIEWRESULT_TOENROLL);
      liveInfoDao.addEntity(liveInfo);
    }
    // 记录操作日志
    LiveOperationLog log = new LiveOperationLog();
    log.setLiveId(liveInfo.getId());
    log.setOperation("applyLive");
    log.setNote("提交审核");
    log.setUserId(operator);
    log.setUserIp(clientIp);
    log.setOperationTime(new Timestamp(System.currentTimeMillis()));
    liveOperationLogDao.addEntity(log);
  }

  @Override
  public void recallLive(LiveInfo liveInfo, String operator, String clientIp) {
    LiveInfo oldLiveInfo = liveInfoDao.findEntityById(liveInfo);
    if (null == oldLiveInfo) throw new RuntimeException("待撤回申请不存在");
    if (!oldLiveInfo.getReviewResult().equals(LiveConstants.LIVE_REVIEWRESULT_TOENROLL))
      throw new RuntimeException("请求不是待审核状态，无法撤销");
    // 撤回申请
    liveInfo.setReviewResult(LiveConstants.LIVE_REVIEWRESULT_TOENROLL);
    liveInfoDao.updateEntityById(liveInfo);
    // 记录操作日志
    LiveOperationLog log = new LiveOperationLog();
    log.setLiveId(liveInfo.getId());
    log.setOperation("recallLive");
    log.setNote("撤回修改");
    log.setUserId(operator);
    log.setUserIp(clientIp);
    log.setOperationTime(new Timestamp(System.currentTimeMillis()));
    liveOperationLogDao.addEntity(log);
  }

  @Override
  public LiveInfo queryLiveById(String liveId) {
    LiveInfo info = new LiveInfo();
    info.setId(liveId);
    return liveInfoDao.findEntityById(info);
  }

  @Override
  public Page<LiveInfo> queryLiveList(IQueryParam param, Page<LiveInfo> page) {
    return liveInfoDao.findEntityListByCond(param, page);
  }

  @Override
  public void insertSerie(LiveSerie liveSerie) {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateSerieById(LiveSerie liveSerie) {
    // TODO Auto-generated method stub

  }

  @Override
  public LiveSerie querySerieById(String serieId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Page<LiveSerie> querySerieList(IQueryParam param, Page<LiveSerie> page) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Page<LiveOperationLog> queryOperationLogList(IQueryParam param, Page<LiveOperationLog> page) {
    // TODO Auto-generated method stub
    return null;
  }

}
