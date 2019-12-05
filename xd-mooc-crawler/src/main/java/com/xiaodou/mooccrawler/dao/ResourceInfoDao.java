package com.xiaodou.mooccrawler.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.mooccrawler.domain.ResourceInfo;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

@Repository("resourceInfoDao")
public class ResourceInfoDao extends MongoBaseDao<ResourceInfo> {

}
