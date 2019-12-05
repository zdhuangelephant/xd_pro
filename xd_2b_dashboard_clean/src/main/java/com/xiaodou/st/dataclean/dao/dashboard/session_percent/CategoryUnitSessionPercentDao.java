package com.xiaodou.st.dataclean.dao.dashboard.session_percent;

import org.springframework.stereotype.Repository;

import com.xiaodou.st.dataclean.model.domain.dashboard.sessionpercent.CategoryUnitSessionPercentModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;
/**
 * @name @see com.xiaodou.st.dataclean.dao.dashboard.session_percent.CategoryUnitSessionPercentDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 专业-试点单位学期平均数据
 * @version 1.0
 */
@Repository("categoryUnitSessionPercentDao")
public class CategoryUnitSessionPercentDao extends
		BaseDao<CategoryUnitSessionPercentModel> {

}
