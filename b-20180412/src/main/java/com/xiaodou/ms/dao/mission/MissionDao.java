package com.xiaodou.ms.dao.mission;

import org.springframework.stereotype.Repository;

import com.xiaodou.ms.dao.MissionBaseDao;
import com.xiaodou.ms.model.mission.MissionModel;

/**
 * 
 * @ClassName: SysMissionDao
 * @Description: 系统任务
 * @author zhaoxu.yang
 * @param <MissionEnums>
 * @date 2015年4月11日 下午1:46:20
 */

@Repository("missionDao")
public class MissionDao extends MissionBaseDao<MissionModel> {
	
}
