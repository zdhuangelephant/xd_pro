package com.xiaodou.st.dataclean.dao.raw;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.st.dataclean.dao.raw.RawDataFinishMissionDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月5日
 * @description 完成任务模型Dao
 * @version 1.0
 */
@Repository("rawDataFinishMissionDao")
public class RawDataFinishMissionDao extends BaseDao<RawDataFinishMissionModel> {

	public Integer getCurrentFinishMissionStudentCount(
			Map<String, Object> input4) {
		Map<String, Object> cond = Maps.newHashMap();
		cond.put("input", input4);
	    Integer count=(Integer) this.getSqlSession().selectOne(
	            getEntityClass().getSimpleName() + ".getCurrentFinishMissionStudentCount", cond);
	    if(count!=null){
	    	return count;
	    }else{
	    	return 0;
	    }
	}
	}
