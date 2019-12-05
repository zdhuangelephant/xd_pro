package com.xiaodou.mooccrawler.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.mooccrawler.domain.ChapterInfo;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

@Repository("chapterInfoDao")
public class ChapterInfoDao extends MongoBaseDao<ChapterInfo> {

}
