package com.xiaodou.mongodb.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.mongodb.model.Test;
import com.xiaodou.summer.dao.mongo.MongoDbBaseDao;
/**
 * 
 * 
 * 
 * @Title: 
 * @Description: TODO serverGroupDao层
 * @author zhouhuan
 * @date 2016/7/25
 * 
 */

@Repository("testDao")
public class TestDao extends MongoDbBaseDao<Test> {
  /**
   * 增加日志
   *@author zhouhuan
   *@Time 2016-8-2
   */
  public void addGroup(Test s){
    this.save(s);
  }

}
