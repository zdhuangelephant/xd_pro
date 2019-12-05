package com.xiaodou.mission.engine.model;

import java.util.Map;

import lombok.Data;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.service.facade.MissionOperationFacade;

/**
 * @name @see com.xiaodou.mission.engine.model.Context.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月14日
 * @description 驱动引擎上下文
 * @version 1.0
 */
@Data
public class Context {

  /** event 事件 */
  private BaseEvent event;

  /** currentThreshold 当前时间阀值 */
  private String currentThreshold = StringUtils.EMPTY;

  /** missionOperationFacade 数据操作类 */
  private MissionOperationFacade missionOperationFacade;

  /** coreParam 关键参数 */
  private UserCollectDataInstance coreParam = new UserCollectDataInstance();

  /** otherParam 其它参数 */
  private Map<String, Object> otherParam = Maps.newHashMap();

  /** 共享数据 */
  private Map<String, Object> shares = Maps.newHashMap();

}
