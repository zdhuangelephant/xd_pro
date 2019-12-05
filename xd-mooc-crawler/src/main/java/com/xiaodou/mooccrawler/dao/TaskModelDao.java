package com.xiaodou.mooccrawler.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.mooccrawler.domain.TaskModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

@Repository("taskModelDao")
public class TaskModelDao extends MongoBaseDao<TaskModel> {

}
