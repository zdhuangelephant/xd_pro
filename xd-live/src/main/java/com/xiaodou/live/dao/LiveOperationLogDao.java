package com.xiaodou.live.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.live.domain.LiveOperationLog;
import com.xiaodou.summer.dao.BaseDao;

/**
 * @name LiveOperationLogDao 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月29日
 * @description 直播操作日志Dao
 * @version 1.0
 */
@Repository("liveOperationLogDao")
public class LiveOperationLogDao extends BaseDao<LiveOperationLog> {}