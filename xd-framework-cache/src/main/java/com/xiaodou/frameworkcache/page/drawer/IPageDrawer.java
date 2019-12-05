package com.xiaodou.frameworkcache.page.drawer;

/**
 * @name @see com.xiaodou.frameworkcache.page.drawer.IPageDrawer.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月7日
 * @description 页面渲染器
 * @version 1.0
 * @param <T> 参数实体
 * @param <R> 页面实体
 */
public interface IPageDrawer<T, R> {

  /**
   * 获取唯一键
   * 
   * @return 唯一键
   */
  public String getKey();

  /**
   * 获取页面实体
   * 
   * @return 页面实体
   */
  public R getPage();

  /**
   * 获取有效时间
   * 
   * @return 有效时长
   */
  public int getValidityTime();

  /**
   * 获取刷新开关
   * 
   * @return 刷新开关
   */
  public boolean getRefreshLimit();

}
