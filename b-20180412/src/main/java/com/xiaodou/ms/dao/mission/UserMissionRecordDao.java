package com.xiaodou.ms.dao.mission;

import org.springframework.stereotype.Repository;

import com.xiaodou.ms.dao.MissionBaseDao;
import com.xiaodou.ms.model.mission.UserMissionRecordModel;

/**
 * @name @see com.xiaodou.ms.dao.mission.UserMissionRecordDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年9月18日
 * @description 用户任务记录操作Dao
 * @version 1.0
 */
@Repository("userMissionRecordDao")
public class UserMissionRecordDao extends MissionBaseDao<UserMissionRecordModel> {}
