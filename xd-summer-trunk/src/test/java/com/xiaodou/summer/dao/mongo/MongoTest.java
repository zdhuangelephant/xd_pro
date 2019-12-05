package com.xiaodou.summer.dao.mongo;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.BaseSpringTest;
import com.xiaodou.summer.dao.mongo.dao.TestModel1Dao;
import com.xiaodou.summer.dao.mongo.enums.Scope;
import com.xiaodou.summer.dao.mongo.model.TestModel1;
import com.xiaodou.summer.dao.mongo.param.MongoFieldParam;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.DeleteParam;
import com.xiaodou.summer.dao.param.IDeleteParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

public class MongoTest extends BaseSpringTest {

  @Resource
  TestModel1Dao testModel1Dao;

  @Before
  public void initData() {
    {
      TestModel1 model = new TestModel1();
      String string = UUID.randomUUID().toString();
      System.out.println(string);
      model.setTid(string);
      model.setName(string);
      testModel1Dao.addEntity(model);
    }
    {
      TestModel1 mode2 = new TestModel1();
      String string = UUID.randomUUID().toString();
      System.out.println(string);
      mode2.setTid(string);
      mode2.setAge(20);
      testModel1Dao.addEntity(mode2);
    }
  }

  @Test
  public void testQuery() {
    IQueryParam param = new QueryParam();
    param.addInput("name", new MongoFieldParam(true, Scope.EXIST));
    Page<TestModel1> modelPage = testModel1Dao.findEntityListByCond(param, null);
    Assert.assertTrue("存在条件查询异常", modelPage != null && modelPage.getTotalCount() == 1);
    System.out.println(FastJsonUtil.toJson(modelPage.getResult().get(0)));
  }

  @After
  public void clearData() {
    IDeleteParam param = new DeleteParam();
    param.addInput("tid", new MongoFieldParam(true, Scope.EXIST));
    testModel1Dao.deleteEntityByCond(param);
  }
}
