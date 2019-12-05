package com.xiaodou.userCenter.module.selfTaught.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.info.CommonInfoUtil;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.enums.MajorLevel;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel.MajorInfo;
import com.xiaodou.userCenter.model.ProductUtilModel;
import com.xiaodou.userCenter.model.property.BlankNoticeModelProperty;
import com.xiaodou.userCenter.module.selfTaught.request.StConfigRequest;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse.ConfigData;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigVersionResponse;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigVersionResponse.ConfigVersionData;
import com.xiaodou.userCenter.module.selfTaught.service.facade.IUcenterModelServiceFacade;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.service.IConfigService;
import com.xiaodou.userCenter.util.ThirdPlateformProp;

public class StConfigService
    implements
      IConfigService<StConfigRequest, StConfigVersionResponse, StConfigResponse> {
  IUcenterModelServiceFacade stUcenterModelServiceFacade;

  private String majorList_code = "010";
  private String thirdloginurl_code = "011";
  private String shareplateformurl_code = "012";
  private String blankNotice_code = "013";
  private String androidDownload_Code = "015";
  private String iosDownload_Code = "016";

  @Override
  public StConfigVersionResponse configVersion(BaseRequest pojo) {
    String module = pojo.getModule();
    StConfigVersionResponse response = new StConfigVersionResponse(ResultType.SUCCESS);
    ConfigVersionData data = new ConfigVersionData();
    data.setThirdlogin(CommonInfoUtil.getCommonInfoInfoByCode(thirdloginurl_code, module)
        .getInfoVersion());
    data.setShareplateform(CommonInfoUtil.getCommonInfoInfoByCode(shareplateformurl_code, module)
        .getInfoVersion());
    data.setBlankNotice(CommonInfoUtil.getCommonInfoInfoByCode(blankNotice_code, module)
        .getInfoVersion());
    data.setMajor(CommonInfoUtil.getCommonInfoInfoByCode(majorList_code, module).getInfoVersion());
    data.setAndroidDownloadUrl(CommonInfoUtil.getCommonInfoInfoByCode(androidDownload_Code, module)
        .getInfoVersion());
    data.setIosDownloadUrl(CommonInfoUtil.getCommonInfoInfoByCode(iosDownload_Code, module)
        .getInfoVersion());
    response.setData(data);
    return response;
  }

  @Override
  public StConfigResponse config(StConfigRequest request) {
    StConfigResponse response = new StConfigResponse(ResultType.SUCCESS);
    ConfigData data = new ConfigData();
    if ("1".equals(request.getThirdlogin()))
      data.setThirdlogin(ThirdPlateformProp.getThirdLogin());
    if ("1".equals(request.getShareplateform()))
      data.setShareplateform(ThirdPlateformProp.getSharePlateform());
    if ("1".equals(request.getBlankNotice())) {
      BlankNoticeResponse blankNoticeResponse = initBlankNotice(request.getModule());
      data.setBlankNotice(blankNoticeResponse);
    }
    if ("1".equals(request.getMajorList())) {
      Map<String, List<ProductCategoryUtilModel>> majorMap = initMajorList(request.getModule());
      data.setBenkeList(majorMap.get("benkeList"));
      data.setZhuankeList(majorMap.get("zhuankeList"));
    }
    response.setData(data);
    return response;
  }

  private BlankNoticeResponse initBlankNotice(String module) {
    BlankNoticeResponse response = new BlankNoticeResponse(ResultType.SUCCESS);
    response.setType((short) 2);
    try {
      Map<String, Object> queryCond = Maps.newHashMap();
      queryCond.put("module", module);
      if (null == stUcenterModelServiceFacade) {
        stUcenterModelServiceFacade = SpringWebContextHolder.getBean("stUcenterModelServiceFacade");
      }
      List<BlankNoticeModel> blackNoticeList =
          stUcenterModelServiceFacade.queryBlackNoticeList(queryCond,
              BlankNoticeModelProperty.getAllInfo());
      if (null == blackNoticeList || blackNoticeList.size() <= 0) {
        // return new BlankNoticeResponse(UcenterResType.NoFoundNotice);
        return response;
      }
      if (blackNoticeList.size() > 1) return response;
      // return new BlankNoticeResponse(UcenterResType.MoreThanOneData);
      return response.setBlankNoticeResponse(blackNoticeList.get(0));
    } catch (Exception e) {
      LoggerUtil.error("查询首页弹出页数据异常", e);
      return new BlankNoticeResponse(ResultType.SYSFAIL);
    }
  }

  private Map<String, List<ProductCategoryUtilModel>> initMajorList(String module) {
    Map<String, List<ProductCategoryUtilModel>> majorMap = Maps.newHashMap();
    try {
      Map<String, Object> queryCond = Maps.newHashMap();
      queryCond.put("module", module);
      if (null == stUcenterModelServiceFacade)
        stUcenterModelServiceFacade = SpringWebContextHolder.getBean("stUcenterModelServiceFacade");
      // 1、查询模块所有专业
      List<ProductCategoryUtilModel> benkeList = Lists.newArrayList();
      List<ProductCategoryUtilModel> zhuankeList = Lists.newArrayList();
      Map<String, Object> input = new HashMap<String, Object>();
      input.put("module", module);
      Map<String, Object> output = new HashMap<String, Object>();
      CommUtil.getGeneralField(output, ProductCategoryUtilModel.class);
      List<ProductCategoryUtilModel> list =
          stUcenterModelServiceFacade.queryProductCategoryList(input, output);
      for (ProductCategoryUtilModel productCategoryModel : list) {

        Map<String, Object> _cond = new HashMap<>();
        _cond.put("module", module);
        _cond.put("productCategoryId", productCategoryModel.getId());
        _cond.put("showStatus", 1);
        _cond.put("addModuleCourse", module);// 包括新手课程
        Map<String, Object> _output = new HashMap<>();
        CommUtil.getGeneralField(_output, ProductUtilModel.class);
        Page<ProductUtilModel> page =
            stUcenterModelServiceFacade.queryCascadeProductByCond(_cond, _output);
        if (null != page && null != page.getResult()) {
          productCategoryModel.setCourseCount(String.valueOf(page.getResult().size()));
        }
        if (StringUtils.isNotBlank(productCategoryModel.getMajorInfo())) {
          MajorInfo majorInfo =
              FastJsonUtil.fromJson(productCategoryModel.getMajorInfo(), MajorInfo.class);
          if (MajorLevel.benke.getValue().equals(majorInfo.getMajorLevel())) {
            productCategoryModel.setMajorLevel(MajorLevel.benke.getCode());
            benkeList.add(productCategoryModel);
          }
          if (MajorLevel.zhuanke.getValue().equals(majorInfo.getMajorLevel())) {
            productCategoryModel.setMajorLevel(MajorLevel.zhuanke.getCode());
            zhuankeList.add(productCategoryModel);
          }
        }


      }
      majorMap.put("benkeList", benkeList);
      majorMap.put("zhuankeList", zhuankeList);
      return majorMap;
    } catch (Exception e) {
      LoggerUtil.error("查询专业时数据异常", e);
      return null;
    }
  }

}
