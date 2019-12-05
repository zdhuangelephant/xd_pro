package com.xiaodou.st.dashboard.service.task.grade;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dashboard.domain.grade.ClassDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;


@HandlerMessage(value = "UpdateClassStudentCount", type = MessageType.Single)
public class UpdateClassStudentCount extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      IStServiceFacade stServiceFacade = SpringWebContextHolder.getBean("stServiceFacade");
      // Long classId = Long.valueOf(message.getMessageBodyJson());
      List<Long> classIdList =
          FastJsonUtil.fromJsons(message.getMessageBodyJson(), new TypeReference<List<Long>>() {});
      // 修改班级学生数
      for (Long classId : classIdList) {
        Map<String, Object> inputs = Maps.newHashMap();
        inputs.put("classId", classId);
        Page<StudentDO> page =
            stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), null);
        ClassDO classDO = new ClassDO();
        classDO.setId(classId);
        classDO.setStudentCount(null != page ? page.getTotalCount() : 0);
        if (!stServiceFacade.updateClass(classDO))
          LoggerUtil.error("增加学生时，异步修改班级("+classId+")学生数失败！", new Exception());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("增加学生时，异步修改班级学生数失败！", t);
  }
}
