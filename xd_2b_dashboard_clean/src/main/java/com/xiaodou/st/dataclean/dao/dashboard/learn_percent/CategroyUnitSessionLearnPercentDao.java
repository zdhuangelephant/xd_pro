package com.xiaodou.st.dataclean.dao.dashboard.learn_percent;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dataclean.model.domain.dashboard.learnpercent.CategroyUnitSessionLearnPercentModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.st.dataclean.dao.dashboard.learn_percent.SessionLearnPercentDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 试点单位-专业-每日趋势
 * @version 1.0
 */
@Repository("categroyUnitSessionLearnPercentDao")
public class CategroyUnitSessionLearnPercentDao
    extends BaseDao<CategroyUnitSessionLearnPercentModel> {
  public Double queryAllMissionPercent(Map<String, Object> input4) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input4);
    Double count =
        (Double) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".queryAllMissionPercent", cond);
    if (count != null) {
      return count;
    } else {
      return (double) 0;
    }
  }

  public Double queryAvgMissionPercent(Map<String, Object> input4) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input4);
    Double count =
        (Double) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".queryAvgMissionPercent", cond);
    if (count != null) {
      return count;
    } else {
      return (double) 0;
    }
  }
  
  public Double queryCateGorySessionLearnTimeByCond(Map<String, Object> inputArgument) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", inputArgument);
    Double count =
        (Double) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".queryCateGorySessionLearnTimeByCond", cond);
    if (count != null) {
      return count;
    } else {
      return (double) 0;
    }
  }
  
  public Double queryAvgCateGorySessionLearnTimeByCond(Map<String, Object> inputArgument) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", inputArgument);
    Double count =
        (Double) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".queryAvgCateGorySessionLearnTimeByCond", cond);
    if (count != null) {
      return count;
    } else {
      return (double) 0;
    }
  }

  public Double queryAvgLearnActive(Map<String, Object> inputArgument) {
	    Map<String, Object> cond = Maps.newHashMap();
	    cond.put("input", inputArgument);
	    Double count =
	        (Double) this.getSqlSession().selectOne(
	            getEntityClass().getSimpleName() + ".queryAvgLearnActive", cond);
	    if (count != null) {
	      return count;
	    } else {
	      return (double) 0;
	    }
	  }
}
