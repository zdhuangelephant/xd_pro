package com.xiaodou.mission.vo.message;

import java.util.UUID;

import lombok.Data;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.service.cache.impl.UserMissionListCacheImpl.UserTaskStatistic;

/**
 * @name @see com.xiaodou.mission.vo.message.UpdateScoreMessage.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 更新成绩消息
 * @version 1.0
 */
public class UpdateScoreMessage extends AbstractMessagePojo {

  /** ASYNC_UPDATE_SCORE 更新分数 */
  private final static String ASYNC_UPDATE_SCORE = "updateScore";

  public UpdateScoreMessage(BaseEvent event, UserTaskStatistic taskStatistic) {
    super(ASYNC_UPDATE_SCORE);
    setCustomTag(UUID.randomUUID().toString());
    MissionScore score = new MissionScore();
    score.setUserId(event.getUserId());
    score.setModule(event.getModule());
    score.setCourseId(event.getCourseId());
    score.setFinishMissionCount(taskStatistic.getCompleteCount());
    score.setMissionCount(taskStatistic.getTotalCount());
    setTransferObject(score);
  }

  @Data
  public class MissionScore {
    private String userId;
    private String module;
    private String courseId;
    private Integer missionCount;
    private Integer finishMissionCount;
  }
}
