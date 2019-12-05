package com.xiaodou.st.dashboard.service.task.student;

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
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.util.LockFactory;
import com.xiaodou.summer.util.SpringWebContextHolder;
/**
 * 
 * @name UpdateStudent 
 * CopyRright (c) 2017 by 李德洪
 *
 * @author 李德洪
 * @date 2017年10月17日
 * @description 更新报名信息下的学生信息
 * @version 1.0
 */
@HandlerMessage(value = "UpdateStudent", type = MessageType.Single)
public class UpdateStudent extends AbstractDefaultWorker {
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
      StudentDO sdo = FastJsonUtil.fromJson(message.getMessageBodyJson(), StudentDO.class);
      Map<String, Object> inputs = Maps.newHashMap();
      inputs.put("studentId", sdo.getId());
      ApplyDO applyDO = new ApplyDO();
      applyDO.setStudentName(sdo.getRealName());
      applyDO.setTelephone(sdo.getTelephone());
      applyDO.setAdmissionCardCode(sdo.getAdmissionCardCode());
      applyDO.setAdminId(sdo.getAdminId());
      applyDO.setAdminName(sdo.getAdminName());
      applyDO.setClassId(sdo.getClassId());
      applyDO.setClassName(sdo.getClassName());
      applyDO.setPilotUnitId(sdo.getPilotUnitId());
      applyDO.setPilotUnitName(sdo.getPilotUnitName());
      stServiceFacade.updateApplyByCond(inputs, applyDO);
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
    LoggerUtil.error("学生信息 异步更新 失败", t);
  }
}
