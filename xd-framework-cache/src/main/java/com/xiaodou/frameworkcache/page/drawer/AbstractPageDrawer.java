package com.xiaodou.frameworkcache.page.drawer;

import lombok.Data;

import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.frameworkcache.page.drawer.AbstractPageDrawer.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月7日
 * @description 页面渲染器-概要实现
 * @version 1.0
 * @param <T>
 * @param <R>
 */
@Data
public abstract class AbstractPageDrawer<T, R> implements IPageDrawer<T, R> {

  /** pojo 参数类 */
  private T pojo;

  public AbstractPageDrawer(T pojo) {
    this.pojo = pojo;
  }

  @Override
  public String getKey() {
    if (null != pojo) {
      return FastJsonUtil.toJson(pojo);
    }
    throw new IllegalArgumentException("The pojo is Empty.");
  }

}
