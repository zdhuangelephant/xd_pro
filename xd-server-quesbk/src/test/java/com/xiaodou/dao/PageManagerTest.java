package com.xiaodou.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.frameworkcache.page.PageManager;
import com.xiaodou.vo.request.BaseListPojo;

public class PageManagerTest extends BaseUnitils {

  @SpringBean("pageManager")
  PageManager pageManager;

  BaseListPojo pojo;

  @Before
  public void initPojo() {
    pojo = new BaseListPojo();
    pojo.setModule("3");
    pojo.setUid("11");
  }

  @Test
  @Ignore
  public void testAddPage1() {
    pageManager.addPage(new PagePojo(pojo));
  }

  /**
   * 页面静态化
   * key 页面唯一key
   * doc 页面内容
   * valiTime 超时时长
   * refresh 是否刷新超时时间
   */
  @Test
  @Ignore
  public void testAddPage2() {
    pageManager.addPage(FastJsonUtil.toJson(pojo), pojo, 100000, false);
  }

  @Test
//  @Ignore
  public void testGetPage() {
    BaseListPojo r = pageManager.getPage(pojo);
    Assert.assertEquals(r.getUid(), pojo.getUid());
  }
}
