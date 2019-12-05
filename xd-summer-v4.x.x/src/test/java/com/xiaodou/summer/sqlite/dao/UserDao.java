package com.xiaodou.summer.sqlite.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.summer.dao.jdbc.BaseDao;
import com.xiaodou.summer.sqlite.domain.User;

@Repository("userDao")
public class UserDao extends BaseDao<User> {}
