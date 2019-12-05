package com.xiaodou.st.dataclean.dao.dashboard.last_week;

import org.springframework.stereotype.Repository;

import com.xiaodou.st.dataclean.model.domain.dashboard.lastweek.LastWeekMissionPercentModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.st.dataclean.dao.dashboard.last_week.LastWeekMissionPercentDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月6日
 * @description 上周每日任务完成度数据Dao
 * @version 1.0
 */
@Repository("lastWeekMissionPercentDao")
public class LastWeekMissionPercentDao extends BaseDao<LastWeekMissionPercentModel> {}
