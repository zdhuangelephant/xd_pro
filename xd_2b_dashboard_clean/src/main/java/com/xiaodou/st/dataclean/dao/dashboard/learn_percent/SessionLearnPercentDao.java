package com.xiaodou.st.dataclean.dao.dashboard.learn_percent;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dataclean.model.domain.dashboard.learnpercent.SessionLearnPercentModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.st.dataclean.dao.dashboard.learn_percent.SessionLearnPercentDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 工作台每日趋势
 * @version 1.0
 */
/**
 * @name @see com.xiaodou.st.dataclean.dao.dashboard.learn_percent.SessionLearnPercentDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 工作台每日趋势
 * @version 1.0
 */
/**
 * @name @see com.xiaodou.st.dataclean.dao.dashboard.learn_percent.SessionLearnPercentDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 工作台每日趋势
 * @version 1.0
 */
@Repository("sessionLearnPercentDao")
public class SessionLearnPercentDao extends BaseDao<SessionLearnPercentModel> {

  public Double querySessionLearnTimeByCond(Map<String, Object> inputArgument) {
    // TODO Auto-generated method stub
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", inputArgument);
    Double count =
        (Double) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".querySessionLearnTimeByCond", cond);
    if (count != null) {
      return count;
    } else {
      return (double) 0;
    }
  }

  public Double getAvgSessionMissionPercentModelByCond(Map<String, Object> inputArgument) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", inputArgument);
    Double count =
        (Double) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".queryAvgSessionMissionPercentByCond", cond);
    if (count != null) {
      return count;
    } else {
      return (double) 0;
    }
  }


}
