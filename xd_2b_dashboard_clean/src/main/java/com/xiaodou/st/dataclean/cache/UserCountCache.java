//package com.xiaodou.st.dataclean.cache;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.google.common.collect.Maps;
//import com.xiaodou.common.cache.local.DynamicTimingLocalCache;
//import com.xiaodou.common.util.StringUtils;
//import com.xiaodou.common.util.log.LoggerUtil;
//import com.xiaodou.st.dataclean.constant.Constant;
//import com.xiaodou.st.dataclean.model.domain.dashboard.Unit;
//import com.xiaodou.st.dataclean.model.domain.raw.RawDataProductCategoryModel;
//import com.xiaodou.st.dataclean.prop.CacheTimeProp;
//import com.xiaodou.st.dataclean.service.common.CommonService;
//import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
//import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
//import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;
//
///**
// * @name @see com.xiaodou.service.cache.QuesbkCache.java
// * @CopyRright (c) 2015 by XiaoDou NetWork Technology
// * 
// * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
// * @date 2015年8月8日
// * @description 产品列表缓存
// * @version 1.0
// */
//@Service("userCountCache")
//public class UserCountCache {
//
//  @Resource
//  CommonService commonService;
//  @Resource
//  DashBoardServiceFacade dashBoardServiceFacade;
//
//  /** initialDelay 初始调度任务时机 */
//  private Integer initialDelay = 0;
//
//  /** userCount 刷新延迟 */
//  private Integer userCountDelay = Integer.parseInt(CacheTimeProp
//      .getParams("dasboard.usercount.cache.time"));
//  /** flag 刷新任务标志位 */
//  private AtomicBoolean flag = new AtomicBoolean(false);
//
//  /**
//   * 刷新
//   * 
//   * @param moduleId 模块ID
//   * @param typeCode 类型码
//   */
//  @PostConstruct
//  public void init() {
//    if (flag.compareAndSet(false, true)) {
//      SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
//        @Override
//        public void doMain() {
//          processTaughtUnitStudentCount();
//          processChiefUnitUserCount();
//          processPilotUnitStudentCount();
//          processCatPilotUnitStudentCount();
//        }
//
//        /**
//         * 处理自考办学生人数
//         */
//        private void processTaughtUnitStudentCount() {
//          setTaughtUnitStudentCount(commonService.getTaughtUnitStudentCount());
//        }
//
//        /**
//         * 处理主考院校学生人数
//         * 
//         * @param product 产品
//         */
//        private void processChiefUnitUserCount() {
//          List<Unit> unitList = dashBoardServiceFacade.queryChiefUnit().getResult();
//          for (Unit u : unitList) {
//            setUnitStundetCount(commonService.unitStundetCount(u.getId().toString()), u.getId()
//                .toString());
//          }
//        }
//
//        /**
//         * 处理试点单位学生人数
//         * 
//         * @param product 产品
//         */
//        private void processPilotUnitStudentCount() {
//          List<Unit> unitList = dashBoardServiceFacade.queryPilotUnit().getResult();
//          for (Unit u : unitList) {
//            setPilotUnitStudentCount(commonService.getPilotUnitStudentCount(u.getId().toString()),
//                u.getId().toString());
//          }
//        }
//
//        /**
//         * 处理试点单位学生人数
//         * 
//         * @param product 产品
//         */
//        private void processCatPilotUnitStudentCount() {
//          Map<String, Object> input = Maps.newHashMap();
//          List<Unit> unitList = dashBoardServiceFacade.queryPilotUnit().getResult();
//          List<RawDataProductCategoryModel> list =
//              dashBoardServiceFacade.getRawDataProductCategoryByCond(input);
//          for (Unit u : unitList) {
//            for (RawDataProductCategoryModel r : list) {
//              setCatPilotUnitStudentCount(
//                  commonService.getStudentCountByCond(u.getId().toString(), r.getId().toString()),
//                  u.getId().toString(), r.getId().toString());
//            }
//          }
//        }
//
//        @Override
//        public void onException(Throwable t) {
//          LoggerUtil.error("刷新学生人数缓存异常.", t);
//        }
//      }, initialDelay, userCountDelay, TimeUnit.SECONDS);
//    }
//  }
//
//  /**
//   * 获取自考办学生人数
//   * 
//   * @param productId 产品ID
//   * @return
//   */
//  public Integer getTaughtUnitStudentCount() {
//    String cacheKey = Constant.TAUGHT;
//    Object count = DynamicTimingLocalCache.getCache(cacheKey);
//    if (null != count) {
//      return (Integer) count;
//    }
//    return null;
//  }
//
//  /**
//   * 存储自考办学生人数
//   * 
//   */
//  public void setTaughtUnitStudentCount(Integer count) {
//    String cacheKey = Constant.TAUGHT;
//    DynamicTimingLocalCache.cache(cacheKey, count, userCountDelay);
//  }
//
//  /**
//   * 获取主考院校学生人数
//   * 
//   */
//  public Integer getUnitStundetCount(String chiefUnitId) {
//    if (StringUtils.isBlank(chiefUnitId) || null == chiefUnitId) return 0;
//    String cacheKey = String.format(Constant.CHIEF, chiefUnitId);
//    Object count = DynamicTimingLocalCache.getCache(cacheKey);
//    if (null != count) {
//      return (Integer) count;
//    }
//    return null;
//  }
//
//  /**
//   * 存储主考院校学生人数
//   * 
//   */
//  public void setUnitStundetCount(Integer count, String chiefUnitId) {
//    String cacheKey = String.format(Constant.CHIEF, chiefUnitId);
//    DynamicTimingLocalCache.cache(cacheKey, count, userCountDelay);
//  }
//
//
//
//  /**
//   * 获取试点单位学生数
//   * 
//   * @param module 模块ID
//   * @param typeCode 类型码
//   * @return 产品列表
//   */
//  public Integer getPilotUnitStudentCount(String pilotUnitId) {
//    if (StringUtils.isBlank(pilotUnitId) || null == pilotUnitId) return 0;
//    String cacheKey = String.format(Constant.POILT, pilotUnitId);
//    Object count = DynamicTimingLocalCache.getCache(cacheKey);
//    if (null != count) {
//      return (Integer) count;
//    }
//    return null;
//  }
//
//  /**
//   * 存储试点单位学生人数
//   * 
//   */
//  public void setPilotUnitStudentCount(Integer count, String pilotUnitId) {
//    String cacheKey = String.format(Constant.POILT, pilotUnitId);
//    DynamicTimingLocalCache.cache(cacheKey, count, userCountDelay);
//  }
//
//
//  /**
//   * 获取试点单位-专业学生数
//   * 
//   * @param module 模块ID
//   * @param typeCode 类型码
//   * @return 产品列表
//   */
//  public Integer getCatPilotUnitStudentCount(String pilotUnitId, String catId) {
//    if (StringUtils.isBlank(pilotUnitId) || null == pilotUnitId
//        || (StringUtils.isBlank(catId) || null == catId)) return 0;
//    String cacheKey = String.format(Constant.POILTCAT, pilotUnitId, catId);
//    Object count = DynamicTimingLocalCache.getCache(cacheKey);
//    if (null != count) {
//      return (Integer) count;
//    }
//    return null;
//  }
//
//  /**
//   * 获取试点单位-专业学生数
//   * 
//   */
//  public void setCatPilotUnitStudentCount(Integer count, String pilotUnitId, String catId) {
//    String cacheKey = String.format(Constant.POILTCAT, pilotUnitId, catId);
//    DynamicTimingLocalCache.cache(cacheKey, count, userCountDelay);
//  }
//
//
//
//}
