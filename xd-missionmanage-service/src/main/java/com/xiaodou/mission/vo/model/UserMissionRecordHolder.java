package com.xiaodou.mission.vo.model;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.domain.CascadeMissionRecordModel;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;
import com.xiaodou.mission.engine.model.mission.MissionRecordFactory;

/**
 * @name UserMissionRecordHolder CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月13日
 * @description 用户任务数据holder
 * @version 1.0
 */
public class UserMissionRecordHolder {
  private Map<String, AbstractMissionRecord> missionIdMap = Maps.newHashMap();
  private Map<Long, AbstractMissionRecord> recordIdMap = Maps.newHashMap();

  public void initUserMissionList(List<CascadeMissionRecordModel> userMissionList) {
    if (null != userMissionList && !userMissionList.isEmpty()) {
      for (CascadeMissionRecordModel model : userMissionList) {
        AbstractMissionRecord record = MissionRecordFactory.buildMissionRecord(model);
        if (null == record) {
          continue;
        }
        if (StringUtils.isNotBlank(record.getMissionId())) {
          missionIdMap.put(record.getMissionId(), record);
        }
        if (null != record.getId()) {
          recordIdMap.put(record.getId(), record);
        }
      }
    }
  }

  public AbstractMissionRecord getUserRecordByMissionId(String missionId) {
    if (StringUtils.isBlank(missionId) || null == missionIdMap || missionIdMap.isEmpty()) {
      return null;
    }
    return missionIdMap.get(missionId);
  }

  public AbstractMissionRecord getUserRecordByRecordId(Long recordId) {
    if (null == recordId || null == recordIdMap || recordIdMap.isEmpty()) {
      return null;
    }
    return recordIdMap.get(recordId);
  }

}
