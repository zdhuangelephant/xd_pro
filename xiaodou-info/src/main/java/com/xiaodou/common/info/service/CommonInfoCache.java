package com.xiaodou.common.info.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.info.constant.Constant;
import com.xiaodou.common.info.dao.CommonInfoDao;
import com.xiaodou.common.info.dao.ModuleInfoDao;
import com.xiaodou.common.info.model.CommonInfo;
import com.xiaodou.common.info.model.ModuleInfo;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * 城市信息缓存类
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-13
 */
@Service("commonInfoCache")
public class CommonInfoCache {

  @Resource
  CommonInfoDao commonInfoDao;
  @Resource
  ModuleInfoDao moduleInfoDao;

  /**
   * 缓存id-CommonInfoInfo/CommonInfoInfo列表
   */
  public List<CommonInfo> initCommonInfoCodeInfoList() {
    List<CommonInfo> commonInfoList = Lists.newArrayList();
     IQueryParam param = new QueryParam();
     param.addOutputs(CommUtil.getAllField(CommonInfo.class));
     Page<CommonInfo> commonInfoPage = commonInfoDao.findEntityListByCond(param, null);
     commonInfoList = commonInfoPage.getResult();
    try {
      if (CollectionUtils.isEmpty(commonInfoList)) {
        // TODO 报警
      } else {
        for (CommonInfo commonInfo : commonInfoList) {
          JedisUtil.addStringToJedis(
              Constant.CACHE_INFO + Constant.SEPRATOR + commonInfo.getInfoCode(),
              commonInfo.toString(), 48 * 60 * 60);
        }
        JedisUtil.addStringToJedis(Constant.CACHE_INFO, FastJsonUtil.toJson(commonInfoList),
            48 * 60 * 60);
      }
    } catch (Exception e) {
      LoggerUtil.error("Err : initCommonInfoCodeInfoList failed.", e);
    }
    return commonInfoList;
  }

  /**
   * 缓存id-CommonInfoInfo
   * 
   * @param id
   */
  public CommonInfo initCommonInfoCodeInfo(String infoCode) {
    CommonInfo commonInfo = new CommonInfo();
    commonInfo.setInfoCode(infoCode);
    commonInfo = commonInfoDao.findEntityByInfoCode(commonInfo);
    try {
      if (commonInfo == null) {
        // TODO 报警
      } else {
        JedisUtil.addStringToJedis(Constant.CACHE_INFO + Constant.SEPRATOR + infoCode,
            commonInfo.toString(), 48 * 60 * 60);
      }
    } catch (Exception e) {
      LoggerUtil.error("Err : initCommonInfoCodeInfo failed.", e);
    }
    return commonInfo;
  }

  /**
   * 根据CommonInfoCode获取CommonInfo实例
   * 
   * @param commonInfoCode 城市码
   * @return CommonInfo
   */
  public CommonInfo getCommonInfoInfoByCode(String infoCode) {
    String sCommonInfo =
        JedisUtil.getStringFromJedis(Constant.CACHE_INFO + Constant.SEPRATOR + infoCode);
    if (StringUtils.isJsonNotBlank(sCommonInfo)) {
      return FastJsonUtil.fromJson(sCommonInfo, CommonInfo.class);
    }
    return initCommonInfoCodeInfo(infoCode);
  }

  /**
   * 获取CommonInfoInfo列表
   * 
   * @return List<CommonInfo>
   */
  public List<CommonInfo> getCommonInfoList() {
    String sCommonInfoLst = JedisUtil.getStringFromJedis(Constant.CACHE_INFO);
    if (StringUtils.isJsonNotBlank(sCommonInfoLst)) {
      return FastJsonUtil.fromJsons(sCommonInfoLst, new TypeReference<List<CommonInfo>>() {});
    }
    return initCommonInfoCodeInfoList();
  }

  /**
   * 缓存id-CommonInfoInfo
   * 
   * @param id
   */
  public CommonInfo updateCommonInfoInfoByCode(CommonInfo commonInfo) {
    if (null == commonInfo) return null;
    String infoCode = commonInfo.getInfoCode();
    if (StringUtils.isBlank(infoCode)) {
      return null;
    }
    commonInfo.setInfoCode(infoCode);
    boolean flag = commonInfoDao.updateEntityByInfoCode(commonInfo);
    if (!flag) {
      return null;
    }
    try {
      JedisUtil.addStringToJedis(Constant.CACHE_INFO + Constant.SEPRATOR + infoCode,
          commonInfo.toString(), 48 * 60 * 60);
    } catch (Exception e) {
      LoggerUtil.error("Err : initCommonInfoCodeInfo failed.", e);
    }
    return commonInfo;
  }

  public ModuleInfo getDefauleModuleInfo() {
    IQueryParam param = new QueryParam();
    param.addInput("isFirstChoice", Constant.isFirstChoice);
    param.addOutputs(CommUtil.getAllField(ModuleInfo.class));
    Page<ModuleInfo> page = moduleInfoDao.findEntityListByCond(param, null);
    if(!CollectionUtils.isEmpty(page.getResult())) {
      return page.getResult().get(0);
    }
    return null;
  }

  public ModuleInfo getModuleInfoByModuleName(String moduleName) {
    String moduleInfo =
        JedisUtil.getStringFromJedis(Constant.CACHE_INFO + Constant.SEPRATOR + moduleName);
    if (StringUtils.isJsonNotBlank(moduleInfo)) {
      return FastJsonUtil.fromJson(moduleInfo, ModuleInfo.class);
    }
    IQueryParam param = new QueryParam();
    param.addInput("moduleName", moduleName);
    param.addOutputs(CommUtil.getAllField(ModuleInfo.class));
    Page<ModuleInfo> page = moduleInfoDao.findEntityListByCond(param, null);
    if(!CollectionUtils.isEmpty(page.getResult())) {
      return page.getResult().get(0);
    }
    return null;
  }
}
