package com.xiaodou.resources.dao.user;

import org.springframework.stereotype.Repository;

import com.xiaodou.resources.dao.BaseProcessDao;
import com.xiaodou.resources.model.user.UserChapterRecordModel;

/**
 * Created by ldh on 16/2/26.
 */
@Repository("userChapterDao")
public class UserChapterDao extends BaseProcessDao<UserChapterRecordModel> {}
