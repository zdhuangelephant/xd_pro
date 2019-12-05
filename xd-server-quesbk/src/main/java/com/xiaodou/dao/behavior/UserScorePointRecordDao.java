package com.xiaodou.dao.behavior;

import org.springframework.stereotype.Repository;

import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.dao.behavior.UserScorePointRecordDao.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月13日
 * @description 慕享用户积分点条目明细表操作Dao
 * @version 1.0
 */
@Repository("userScorePointRecordDao")
public class UserScorePointRecordDao extends BaseDao<UserScorePointRecord> {}
