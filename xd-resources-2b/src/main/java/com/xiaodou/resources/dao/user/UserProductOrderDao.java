package com.xiaodou.resources.dao.user;

import org.springframework.stereotype.Repository;

import com.xiaodou.resources.dao.BaseProcessDao;
import com.xiaodou.resources.model.user.UserProductOrderModel;

@Repository("userProductOrderDao")
public class UserProductOrderDao extends BaseProcessDao<UserProductOrderModel> {}
