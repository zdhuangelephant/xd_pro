package com.xiaodou.mission.engine.model.mission;

import java.sql.Timestamp;

import org.springframework.util.Assert;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.UserMissionRecordModel;
import com.xiaodou.mission.engine.MissionEnums.MissionConditionType;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.vo.message.CreateRedBonusMessage;

/**
 * @name @see com.xiaodou.mission.engine.model.mission.DynamicRecord.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 动态任务类型数据
 * @version 1.0
 */
public class DynamicRecord extends AbstractTaskRecord {

  public DynamicRecord() {
    setRecordStatus(MissionConstant.RECORD_STATUS_NORMAL);
  }

  @Override
  protected void createRecord(Context context) {
    if (null == context.getMissionOperationFacade()) {
      return;
    }
    if (MissionConditionType.dailyPractice.equals(getMission().getCondType())
        || MissionConstant.TASK_STATUS_RECEIVED != getIsReached()) {
      UserMissionRecordModel domainModel = buildDomain();
      if (MissionConstant.TRUE != getIsReached()) {
        setFinishTime(new Timestamp(System.currentTimeMillis()));
      }
      context.getMissionOperationFacade().insertUserMissionRecord(domainModel);
      Assert.isTrue(StringUtils.isNotBlank(domainModel.getModule()));
      RabbitMQSender.getInstance().send(new CreateRedBonusMessage(domainModel));
    }
  }

  @Override
  protected void afterStore(Context context) {
    // 非动态任务标准流程,此处保留空实现
  }

}
