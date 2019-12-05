package com.xiaodou.forum.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.forum.dao.forum.CascadeQueryForumDao;
import com.xiaodou.forum.model.forum.CommentForumModel;

public class CasacdeQueryForumDaoTest extends BaseUnitils{
  
  @SpringBean("cascadeQueryForumDao")
  CascadeQueryForumDao cascadeQueryForumDao;
  
  @SuppressWarnings("rawtypes")
  private Map getOutput(){
    Map<String, Object> output=new HashMap<String, Object>();
    output.put("id", "1");
    output.put("forumId", "1");
    output.put("replyId", "1");
    output.put("targeId", "1");
    output.put("praiseNumber", "1");
    output.put("tag", "1");
    output.put("createTime", "1");
    output.put("nickName", "1");
    output.put("content", "1");
    output.put("targeContent", "1");
    output.put("publisherId", "1");
    output.put("title", "1");
    return output;
  }
  
//  @Test
  @SuppressWarnings("unchecked")
  public void findList(){
    Map<String, Object> cond=new HashMap<String, Object>();
    Map<String, Object> input=new HashMap<String, Object>();
    Map<String, Object> sort=new HashMap<String, Object>();
    Map<String, Object> output;
    output=getOutput();
    cond.put("input", input);
    cond.put("sort", sort);
    cond.put("output", output);
    List<CommentForumModel> list = new ArrayList<CommentForumModel>();
    list = cascadeQueryForumDao.queryCommentInForumListByCondNoPage(cond);
    System.out.println(JSON.toJSONString(list));
  }
  
  @Test
  public void findDetail(){
    System.out.println("CasacdeQueryForumDaoTest is running...");
  }

}
