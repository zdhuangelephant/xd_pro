package com.xiaodou.dao;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dao.product.QuesbkExamPaperDao;
import com.xiaodou.domain.product.QuesbkExamPaper;

public class QuesbkExamPaperDaoTest extends BaseUnitils {
  @SpringBean("quesbkExamPaperDao")
  QuesbkExamPaperDao quesbkExamPaperDao;

  @Ignore
  public void deleteByPrimaryKey() {
    try {
      System.out.println("deleteByPrimaryKey");
      System.out.println(quesbkExamPaperDao
          .deleteByPrimaryKey("0092a554-2abd-4219-8e5f-c9458c1c3848"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Ignore
  public void insert() {
    System.out.println("insert");
    try {
      QuesbkExamPaper paper = new QuesbkExamPaper();
      String uuid = UUID.randomUUID().toString();
      System.out.println(uuid);
      paper.setId(uuid);
      paper.setExamTypeId(6L);
      paper.setMdesc("");
      paper.setStatus("");
      paper.setCourseId(123456L);
      System.out.println(JSON.toJSONString(paper));
      quesbkExamPaperDao.insert(paper);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Ignore
  public void insertSelective() {
    System.out.println("insertSelective");
    try {
      QuesbkExamPaper paper = new QuesbkExamPaper();
      String uuid = UUID.randomUUID().toString();
      System.out.println(uuid);
      paper.setId(uuid);
      paper.setExamTypeId(6L);
      paper.setMdesc("");
      paper.setStatus("");
      paper.setCourseId(123456L);
      System.out.println(JSON.toJSONString(paper));
      quesbkExamPaperDao.insert(paper);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void selectByPrimaryKey() {
    System.out.println("selectByPrimaryKey");
    try {
      System.out.println(quesbkExamPaperDao
          .selectByPrimaryKey("b36117fd-8328-4c40-8a81-f9f8e0018225"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Ignore
  public void updateByPrimaryKeySelective() {
    System.out.println("updateByPrimaryKeySelective");
    try {
      QuesbkExamPaper record = new QuesbkExamPaper();
      record.setId("b36117fd-8328-4c40-8a81-f9f8e0018225");
      record.setExamTypeId(10L);
      quesbkExamPaperDao.updateByPrimaryKeySelective(record);
      System.out.println(JSON.toJSONString(record));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Ignore
  public void updateByPrimaryKey() {
    System.out.println("updateByPrimaryKey");
    try {
      QuesbkExamPaper record = new QuesbkExamPaper();
      record.setId("b36117fd-8328-4c40-8a81-f9f8e0018225");
      record.setExamTypeId(20L);
      quesbkExamPaperDao.updateByPrimaryKey(record);
      System.out.println(JSON.toJSONString(record));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void selectBySubjectIdAndExamType() {
    System.out.println("selectBySubjectIdAndExamType");
    try {

      List<QuesbkExamPaper> page = quesbkExamPaperDao.selectBySubjectIdAndExamType("31842372", "6");
      System.out.println(FastJsonUtil.toJson(page));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void queryTodayExamQuesList() {
    System.out.println("queryTodayExamPaperList");
    try {
      HashMap<String, Object> cond = Maps.newHashMap();
      cond.put("uid", 94);
      cond.put("courseIdList", Lists.newArrayList("825110421"));
      System.out.println(JSON.toJSONString(cond));
      List<String> page = quesbkExamPaperDao.queryTodayExamQuesList(cond);
      System.out.println(FastJsonUtil.toJson(page));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void queryTotalExamPaperList() {
    System.out.println("queryTotalExamPaperList");
    try {
      HashMap<String, Object> cond = Maps.newHashMap();
      cond.put("uid", 94);
      cond.put("courseIdList", Lists.newArrayList("825110421"));
      System.out.println(JSON.toJSONString(cond));
      List<String> page = quesbkExamPaperDao.queryTotalExamPaperList(cond);
      System.out.println(FastJsonUtil.toJson(page));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
