package com.xiaodou.common.info;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xiaodou.common.info.model.CommonInfo;
import com.xiaodou.common.info.model.ModuleInfo;
import com.xiaodou.common.info.service.CommonInfoCache;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class CommonInfoUtil {

  private static CommonInfoCache commonInfoCache;

  static {
    ScheduledThreadPoolExecutor _executor = SummerCommonScheduledExecutor.getExecutor();
    _executor.scheduleWithFixedDelay(new SummerScheduledTask(_executor) {
      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("初始化公共信息失败.", t);
      }

      @Override
      public void doMain() {
        initCommonInfoCodeInfoList();
      }
    }, 2, 24 * 60 * 60, TimeUnit.SECONDS);
  }

  private static void init() {
    commonInfoCache = SpringWebContextHolder.getBean(CommonInfoCache.class);
  }

  public static void initCommonInfo() {
    if (null == commonInfoCache) {
      synchronized (CommonInfoUtil.class) {
        if (null == commonInfoCache) {
          init();
        }
      }
    }
  }
  /**
   * 缓存id-CommonInfoInfo/CommonInfoInfo列表
   * 
   * @param merchantCacheKey 合作商缓存KEY
   * @param type type
   */
  public static List<CommonInfo> initCommonInfoCodeInfoList() {
    if (null == commonInfoCache) {
      synchronized (CommonInfoUtil.class) {
        if (null == commonInfoCache) {
          init();
        }
      }
    }
    return commonInfoCache.initCommonInfoCodeInfoList();
  }

  /**
   * 根据id获取CommonInfo实例
   * 
   * @param id 城市主键ID
   * @return CommonInfo
   */
  public static CommonInfo getCommonInfoInfoByCode(String code) {
    if (null == commonInfoCache) {
      synchronized (CommonInfoUtil.class) {
        if (null == commonInfoCache) {
          init();
        }
      }
    }
    return commonInfoCache.getCommonInfoInfoByCode(code);
  }

  /**
   * 获取CommonInfoInfo列表
   * 
   * @return List<CommonInfo>
   */
  public static List<CommonInfo> getCommonInfoList() {
    if (null == commonInfoCache) {
      synchronized (CommonInfoUtil.class) {
        if (null == commonInfoCache) {
          init();
        }
      }
    }
    return commonInfoCache.getCommonInfoList();
  }

  /**
   * 
   * @description 更新公共信息
   * @author 李德洪
   * @Date 2017年8月3日
   * @param code
   * @return
   */
  public static CommonInfo updateCommonInfoInfoByCode(CommonInfo commonInfo) {
    if (null == commonInfoCache) {
      synchronized (CommonInfoUtil.class) {
        if (null == commonInfoCache) {
          init();
        }
      }
    }
    return commonInfoCache.updateCommonInfoInfoByCode(commonInfo);
  }
  
  public static ModuleInfo getDefauleModuleInfo() {
    initCommonInfo();
    return commonInfoCache.getDefauleModuleInfo();
  }
  
  public static ModuleInfo getModuleInfoByModuleName(String moduleName) {
    initCommonInfo();
    return commonInfoCache.getModuleInfoByModuleName(moduleName);
  }
  
}
