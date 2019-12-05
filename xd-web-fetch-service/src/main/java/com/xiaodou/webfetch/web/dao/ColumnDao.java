package com.xiaodou.webfetch.web.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.summer.dao.mongo.MongoBaseDao;
import com.xiaodou.webfetch.web.domain.Column;

@Repository("columnDao")
public class ColumnDao extends MongoBaseDao<Column> {

}
