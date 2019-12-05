package com.xiaodou.common.city;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xiaodou.common.city.model.City;
import com.xiaodou.common.city.service.CityCache;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class CityUtil {

  private static CityCache cityCache;

  static {
    ScheduledThreadPoolExecutor _executor = SummerCommonScheduledExecutor.getExecutor();
    _executor.scheduleWithFixedDelay(new SummerScheduledTask(_executor) {
      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("初始化城市信息失败.", t);
      }

      @Override
      public void doMain() {
        initCityCodeInfoList();
      }
    }, 2, 24 * 60 * 60, TimeUnit.SECONDS);
  }

  private static void init() {
    cityCache = SpringWebContextHolder.getBean(CityCache.class);
  }

  /**
   * 缓存id-CityInfo/CityInfo列表
   * 
   * @param merchantCacheKey 合作商缓存KEY
   * @param type type
   */
  public static List<City> initCityCodeInfoList() {
    if (null == cityCache) {
      synchronized (CityUtil.class) {
        if (null == cityCache) {
          init();
        }
      }
    }
    return cityCache.initCityCodeInfoList();
  }

  /**
   * 根据id获取City实例
   * 
   * @param id 城市主键ID
   * @return City
   */
  public static City getCityInfoByCode(Integer id) {
    if (null == cityCache) {
      synchronized (CityUtil.class) {
        if (null == cityCache) {
          init();
        }
      }
    }
    return cityCache.getCityInfoById(id);
  }

  /**
   * 获取CityInfo列表
   * 
   * @return List<City>
   */
  public static List<City> getCityList() {
    if (null == cityCache) {
      synchronized (CityUtil.class) {
        if (null == cityCache) {
          init();
        }
      }
    }
    return cityCache.getCityList();
  }

}
