package com.xiaodou.autopractise.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.autopractise.domain.PaperInfo;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

@Repository
public class PaperInfoDao extends MongoBaseDao<PaperInfo> {}
