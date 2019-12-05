package com.xiaodou.webfetch.web.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.summer.dao.mongo.MongoBaseDao;
import com.xiaodou.webfetch.web.domain.OfficialAccount;

@Repository("officialAccountDao")
public class OfficialAccountDao extends MongoBaseDao<OfficialAccount> {

}
