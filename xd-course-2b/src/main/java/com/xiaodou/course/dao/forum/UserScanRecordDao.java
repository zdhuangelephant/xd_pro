package com.xiaodou.course.dao.forum;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.model.forum.UserScanRecordModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.course.dao.forum.UserScanRecordDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月10日
 * @description 用户知识分享查看记录操作Dao
 * @version 1.0
 */
@Repository("userScanRecordDao")
public class UserScanRecordDao extends BaseDao<UserScanRecordModel> {}
