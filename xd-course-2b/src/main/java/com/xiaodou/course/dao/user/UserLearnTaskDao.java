package com.xiaodou.course.dao.user;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.user.UserLearnTaskModel;

/**
 * Created by zyp on 15/8/23.
 */
@Repository("userLearnTaskDao")
public class UserLearnTaskDao extends BaseProcessDao<UserLearnTaskModel> {
}
