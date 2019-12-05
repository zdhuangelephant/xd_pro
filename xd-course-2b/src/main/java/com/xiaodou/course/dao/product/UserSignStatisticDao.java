package com.xiaodou.course.dao.product;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.model.user.UserSignStatistic;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name UserSignStatisticDao 
 * CopyRright (c) 2017 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月10日
 * @description 用户打卡统计记录Dao
 * @version 1.0
 */
@Repository("userSignStatisticDao")
public class UserSignStatisticDao extends BaseDao<UserSignStatistic> {}