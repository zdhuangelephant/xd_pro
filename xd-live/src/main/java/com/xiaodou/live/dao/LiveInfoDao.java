package com.xiaodou.live.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.live.domain.LiveInfo;
import com.xiaodou.summer.dao.BaseDao;

/**
 * @name LiveInfoDao 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月29日
 * @description 直播信息Dao
 * @version 1.0
 */
@Repository("liveInfoDao")
public class LiveInfoDao extends BaseDao<LiveInfo> {}