package com.xiaodou.st.dataclean.task.examtotal.dashboardscore;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dataclean.model.domain.dashboard.score.ScoreDO;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataExamTotalModel;
import com.xiaodou.st.dataclean.service.QueueService;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class ScoreTask extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  private QueueService queueService;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    LockFactory.getScoreTaskLock();
    if (StringUtils.isBlank(message.getMessageBodyJson())) return;
    RawDataExamTotalModel rdet =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataExamTotalModel.class);
    RealSqlSession.setRealSqlSession(rdet.getModuleId());
    ScoreDO scoreDO = ScoreDO.getInstance(rdet);
    if (null != scoreDO) {
      // 试点单位
      scoreDO.setRoleType(rdet.getPioltUnit().getRole());
      scoreDO.setUnitId(rdet.getPioltUnit().getId());
      this.updateOrAddScore(scoreDO);
      scoreDO.setId(null);
      // 主考院校
      scoreDO.setRoleType(rdet.getCheifUnit().getRole());
      scoreDO.setUnitId(rdet.getCheifUnit().getId());
      this.updateOrAddScore(scoreDO);
      scoreDO.setId(null);
      // 自考办
      scoreDO.setRoleType(rdet.getTaughtUnit().getRole());
      scoreDO.setUnitId(rdet.getTaughtUnit().getId());
      this.updateOrAddScore(scoreDO);
    }
    if (null == queueService) queueService = SpringWebContextHolder.getBean("queueService");
    queueService.pushAfterExamTotal(rdet);
  }

  public void updateOrAddScore(ScoreDO scoreDO) {
    DashBoardServiceFacade dashBoardServiceFacade =
        SpringWebContextHolder.getBean("dashBoardServiceFacade");
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("productId", scoreDO.getProductId());
    inputs.put("studentId", scoreDO.getStudentId());
    inputs.put("roleType", scoreDO.getRoleType());
    inputs.put("unitId", scoreDO.getUnitId());
    Page<ScoreDO> page =
        dashBoardServiceFacade.listScore(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null != page && null != page.getResult() && !page.getResult().isEmpty()) {
      dashBoardServiceFacade.updateScoreByCond(inputs, scoreDO).toString();
    } else {
      scoreDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
      dashBoardServiceFacade.saveScore(scoreDO);
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("记录 学习记录 失败", t);
  }
}
