package com.xiaodou.autopractise.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.autopractise.domain.UserPaperRecord;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

@Repository
public class UserPaperRecordDao extends MongoBaseDao<UserPaperRecord> {}
