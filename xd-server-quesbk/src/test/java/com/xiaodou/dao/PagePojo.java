package com.xiaodou.dao;

import com.xiaodou.frameworkcache.page.drawer.AbstractPageDrawer;
import com.xiaodou.vo.request.BaseListPojo;

public class PagePojo extends AbstractPageDrawer<BaseListPojo, BaseListPojo> {

  public PagePojo(BaseListPojo pojo) {
    super(pojo);
  }


  @Override
  public BaseListPojo getPage() {
    return getPojo();
  }

  @Override
  public boolean getRefreshLimit() {
    return true;
  }

  @Override
  public int getValidityTime() {
    return 100000;
  }

}
