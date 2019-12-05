package com.xiaodou.frameworkcache.page;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.frameworkcache.page.dao.PageInfoDao;
import com.xiaodou.frameworkcache.page.drawer.IPageDrawer;
import com.xiaodou.frameworkcache.page.model.PageInfo;
import com.xiaodou.summer.dao.mongo.enums.Scope;
import com.xiaodou.summer.dao.mongo.param.MongoFieldParam;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.DeleteParam;
import com.xiaodou.summer.dao.param.IDeleteParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

@Service
public class PageManager {

  @Resource
  PageInfoDao pageInfoDao;
  private Integer initialDelay = 0;
  private Integer crontDelay = 30;

  @PostConstruct
  public void construct() {
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        startCront();
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("页面静态化定时删除异常.", t);
      }
    }, initialDelay, crontDelay, TimeUnit.SECONDS);
  }

  /**
   * 删除过期页面
   */
  private void startCront() {
    IDeleteParam param = new DeleteParam();
    Long now = System.currentTimeMillis();
    MongoFieldParam limit = new MongoFieldParam(now, Scope.LT);
    param.addInput("limitTime", limit);
    pageInfoDao.deleteEntityByCond(param);
  }

  /**
   * 添加缓存页面
   * 
   * @param key 缓存键
   * @param pageInfo 缓存页面值
   * @param validityTime 过期时间
   * @param refreshLimit 针对已有缓存是否刷新过期时间值
   */
  public <T> void addPage(String key, T pageInfo, int validityTime, boolean refreshLimit) {
    addPage(key, pageInfo, validityTime, refreshLimit, TimeUnit.MILLISECONDS);
  }

  /**
   * 添加缓存页面
   * 
   * @param key 缓存键
   * @param pageInfo 缓存页面值
   * @param validityTime 过期时间
   * @param refreshLimit 针对已有缓存是否刷新过期时间值
   * @param timeUnit 过期时间单元
   */
  public <T> void addPage(String key, T pageInfo, int validityTime, boolean refreshLimit,
      TimeUnit timeUnit) {
    Assert.isTrue(null != pageInfo && StringUtils.isNotBlank(key));
    if (null == timeUnit) {
      timeUnit = TimeUnit.MILLISECONDS;
    }
    IQueryParam param = new QueryParam();
    param.addInput("key", key);
    param.addOutputs(PageInfo.class);
    Page<PageInfo> infoPage = pageInfoDao.findEntityListByCond(param, null);
    long currentTimeMillis = System.currentTimeMillis();
    if (null != infoPage && null != infoPage.getResult() && !infoPage.getResult().isEmpty()) {
      PageInfo info = infoPage.getResult().get(0);
      info.setPageInfo(FastJsonUtil.toJson(pageInfo));
      if (refreshLimit) {
        Long lValidityTime = TimeUnit.MILLISECONDS.convert(validityTime, timeUnit);
        info.setValidityTime(lValidityTime);
        Long newLimitTime = currentTimeMillis + lValidityTime;
        info.setLimitTime(newLimitTime);
        info.setSLimitTime(new Timestamp(newLimitTime).toString());
      }
      pageInfoDao.updateEntityById(info);
    } else {
      PageInfo info = new PageInfo();
      info.setKey(key);
      info.setPageClassType(pageInfo.getClass().getName());
      info.setPageInfo(FastJsonUtil.toJson(pageInfo));
      info.setCreateTime(currentTimeMillis);
      info.setSCreateTime(new Timestamp(currentTimeMillis).toString());
      info.setValidityTime(new Long(validityTime));
      Long newLimitTime = currentTimeMillis + validityTime;
      info.setLimitTime(newLimitTime);
      info.setSLimitTime(new Timestamp(newLimitTime).toString());
      pageInfoDao.addEntity(info);
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T getPage(String key) {
    try {
      Assert.isTrue(StringUtils.isNotBlank(key));
      IQueryParam param = new QueryParam();
      param.addInput("key", key);
      param.addOutputs(PageInfo.class);
      Page<PageInfo> infoPage = pageInfoDao.findEntityListByCond(param, null);
      if (null != infoPage && null != infoPage.getResult() && !infoPage.getResult().isEmpty()) {
        PageInfo info = infoPage.getResult().get(0);
        String sPageInfo = info.getPageInfo();
        String pageClassType = info.getPageClassType();
        Class<?> type = Class.forName(pageClassType);
        if (StringUtils.isJsonNotBlank(sPageInfo)) {
          return (T) FastJsonUtil.fromJson(sPageInfo, type);
        }
      }
      return null;
    } catch (ClassNotFoundException e) {
      LoggerUtil.error("获取页面异常.key=" + key, e);
      return null;
    }
  }

  public <T, R> R getPage(T pojo) {
    return getPage(FastJsonUtil.toJson(pojo));
  }

  public <D extends IPageDrawer<?, ?>> void addPage(D drawer) {
    addPage(drawer.getKey(), drawer.getPage(), drawer.getValidityTime(), drawer.getRefreshLimit());
  }
}
