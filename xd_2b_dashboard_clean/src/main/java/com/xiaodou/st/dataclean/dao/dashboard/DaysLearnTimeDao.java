package com.xiaodou.st.dataclean.dao.dashboard;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dataclean.model.domain.dashboard.DaysLearnTimeModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;
/**
 * @name @see com.xiaodou.st.dataclean.dao.dashboard.DaysLearnTimeDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 每日学习时长统计表已经弃用
 * @version 1.0
 */
@Repository("daysLearnTimeDao")
public class DaysLearnTimeDao extends BaseDao<DaysLearnTimeModel> {
	public Integer queryDaysLearnTimeCount(Map<String, Object> inputArgument) {
		// TODO Auto-generated method stub
		Map<String, Object> cond = Maps.newHashMap();
		cond.put("input", inputArgument);
		 Integer count=(Integer) this.getSqlSession().selectOne(
					getEntityClass().getSimpleName() + ".queryDaysLearnTimeCount",
					cond);
		    if(count!=null){
		    	return count;
		    }else{
		    	return 0;
		    }
	}
}
