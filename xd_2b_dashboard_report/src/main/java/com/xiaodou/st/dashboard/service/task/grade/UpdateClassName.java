package com.xiaodou.st.dashboard.service.task.grade;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.grade.ClassDO;
import com.xiaodou.st.dashboard.domain.score.ScoreDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.util.LockFactory;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UpdateClassName", type = MessageType.Single)
public class UpdateClassName extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      LockFactory.getCategoryUnitSessionLearnLock();
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      IStServiceFacade stServiceFacade = SpringWebContextHolder.getBean("stServiceFacade");
      ClassDO cdo = FastJsonUtil.fromJson(message.getMessageBodyJson(), ClassDO.class);
      Long classId = cdo.getId();
      String className = cdo.getClassName();
      Map<String, Object> inputs = Maps.newHashMap();
      inputs.put("classId", classId);
      StudentDO sdo = new StudentDO();
      sdo.setClassId(classId);
      sdo.setClassName(className);
      sdo.setAdmissionCardCode(null);
      stServiceFacade.updateStudentByCond(inputs, sdo);
      ApplyDO applyDO = new ApplyDO();
      applyDO.setClassId(classId);
      applyDO.setClassName(className);
      stServiceFacade.updateApplyByCond(inputs, applyDO);
      ScoreDO scoreDO = new ScoreDO();
      scoreDO.setClassId(classId);
      scoreDO.setClassName(className);
      stServiceFacade.updateScoreByCond(inputs, scoreDO);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      LockFactory.returnCategoryUnitSessionLearnLock();
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
    LoggerUtil.error("班级信息 异步更新 失败", t);
  }
}
