package com.xiaodou.course.dao.user;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.user.UserProductOrderModel;

@Repository("userProductOrderDao")
public class UserProductOrderDao extends BaseProcessDao<UserProductOrderModel> {}
