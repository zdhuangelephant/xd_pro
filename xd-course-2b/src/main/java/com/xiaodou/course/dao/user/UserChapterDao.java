package com.xiaodou.course.dao.user;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.user.UserChapterRecordModel;

/**
 * Created by ldh on 16/2/26.
 */
@Repository("userChapterDao")
public class UserChapterDao extends BaseProcessDao<UserChapterRecordModel> {}
