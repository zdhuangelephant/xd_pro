package com.xiaodou.userCenter.module.selfTaught.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.info.CommonInfoUtil;
import com.xiaodou.common.info.model.CommonInfo;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.enums.MajorLevel;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel.MajorInfo;
import com.xiaodou.userCenter.model.ProductUtilModel;
import com.xiaodou.userCenter.model.property.BlankNoticeModelProperty;
import com.xiaodou.userCenter.module.domain.vo.RegionVO;
import com.xiaodou.userCenter.module.domain.vo.RegionVO.Major;
import com.xiaodou.userCenter.module.selfTaught.request.StConfigRequest;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse.BlankNotice;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse.CategoryConfigData;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse.ConfigData;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse.RegionConfigData;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigVersionResponse;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigVersionResponse.ConfigVersionData;
import com.xiaodou.userCenter.module.selfTaught.service.facade.IUcenterModelServiceFacade;
import com.xiaodou.userCenter.request.ConfigRequest;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.util.ThirdPlateformProp;

@Service(value = "configService")
public class StConfigService {

    @Resource(name = "stUcenterModelServiceFacade")
    IUcenterModelServiceFacade stUcenterModelServiceFacade;

    private String error_null_code = "-1";
    private String majorList_code = "010";
    private String thirdloginurl_code = "011";
    private String shareplateformurl_code = "012";
    private String blankNotice_code = "013";
    private String androidVersion_code = "014";
    private String iosVersion_code = "016";
    private String androidDownLoadUrl_code = "017";
    private String iosDownLoadUrl_code = "018";
    private String androidVersionDesc_code = "019";
    private String iosVersionDesc_code = "020";
    private String androidMinUsableVersion_code = "021";
    private String iosMinUsableVersion_code = "022";
    private String androidUpdatePackSize_code = "023";
    private String iosUpdatePackSize_code = "024";
    private String isCanRegister_code = "025";
    private String feedback_code = "026";
    private String touristSwitch_code = "027";

    /** IOS_INTERIM_OPEN_VERSION IOS临时开放注册版本 */
    private static final String IOS_INTERIM_OPEN_VERSION = "IOS_INTERIM_OPEN_VERSION";

    public StConfigVersionResponse configVersion(ConfigRequest pojo) {
        StConfigVersionResponse response = new StConfigVersionResponse(ResultType.SUCCESS);
        ConfigVersionData data = new ConfigVersionData();
        data.setThirdlogin(
                CommonInfoUtil.getCommonInfoInfoByCode(thirdloginurl_code).getInfoVersion());
        data.setShareplateform(
                CommonInfoUtil.getCommonInfoInfoByCode(shareplateformurl_code).getInfoVersion());
        data.setBlankNotice(
                CommonInfoUtil.getCommonInfoInfoByCode(blankNotice_code).getInfoVersion());
        data.setMajor(CommonInfoUtil.getCommonInfoInfoByCode(majorList_code).getInfoVersion());
        data.setAndroidVersion(
                CommonInfoUtil.getCommonInfoInfoByCode(androidVersion_code).getInfoVersion());
        data.setIosVersion(
                CommonInfoUtil.getCommonInfoInfoByCode(iosVersion_code).getInfoVersion());
        data.setAndroidDownLoadUrl(
                CommonInfoUtil.getCommonInfoInfoByCode(androidDownLoadUrl_code).getInfoVersion());
        data.setIosDownLoadUrl(
                CommonInfoUtil.getCommonInfoInfoByCode(iosDownLoadUrl_code).getInfoVersion());
        String androidVersionDesc = this.getInfoVersion(androidVersionDesc_code);
        if (!error_null_code.equals(androidVersionDesc)) {
            data.setAndroidVersionDesc(androidVersionDesc);
        }
        String iosVersionDesc = this.getInfoVersion(iosVersionDesc_code);
        if (!error_null_code.equals(iosVersionDesc)) {
            data.setIosVersionDesc(iosVersionDesc);
        }
        String androidMinUsableVersion = this.getInfoVersion(androidMinUsableVersion_code);
        if (!error_null_code.equals(androidMinUsableVersion)) {
            data.setAndroidMinUsableVersion(androidMinUsableVersion);
        }
        String iosMinUsableVersion = this.getInfoVersion(iosMinUsableVersion_code);
        if (!error_null_code.equals(iosMinUsableVersion)) {
            data.setIosMinUsableVersion(iosMinUsableVersion);
        }
        String androidUpdatePackSize = this.getInfoVersion(androidUpdatePackSize_code);
        if (!error_null_code.equals(androidUpdatePackSize)) {
            data.setAndroidUpdatePackSize(androidUpdatePackSize);
        }
        String iosUpdatePackSize = this.getInfoVersion(iosUpdatePackSize_code);
        if (!error_null_code.equals(iosUpdatePackSize)) {
            data.setIosUpdatePackSize(iosUpdatePackSize);
        }
        String isCanRegister = this.getInfoVersion(isCanRegister_code);
        String iosInterimOpenVersion = ConfigProp.getParams(IOS_INTERIM_OPEN_VERSION);
        if ("ios".equals(pojo.getClientType()) && StringUtils.isNotBlank(iosInterimOpenVersion)
                && !"NULL".equals(iosInterimOpenVersion)
                && iosInterimOpenVersion.equals(pojo.getVersion())) {
            data.setIsCanRegister("0");
        } else if (!error_null_code.equals(isCanRegister)) {
            data.setIsCanRegister(isCanRegister);
        }
        String touristSwitch = this.getInfoVersion(touristSwitch_code);
        if (!error_null_code.equals(touristSwitch)) {
            data.setTouristSwitch(touristSwitch);
        }
        response.setData(data);
        return response;
    }

    public String getInfoVersion(String code) {
        String version = error_null_code;
        CommonInfo commonInfo = CommonInfoUtil.getCommonInfoInfoByCode(code);
        if (null != commonInfo) version = commonInfo.getInfoVersion();
        return version;
    }

    public StConfigResponse config(StConfigRequest request, String headVersion) {
        StConfigResponse response = new StConfigResponse(ResultType.SUCCESS);
        ConfigData data = new ConfigData();
        if ("1".equals(request.getMajorList())) {
            if (!headVersion.equals("149")) {
            	data = new RegionConfigData();
                List<RegionVO> list = this.listRegionVO();
                ((RegionConfigData) data).setRegionList(list);
                ((RegionConfigData) data).setRegionCount(String.valueOf(list.size()));
            } else {
            	data = new CategoryConfigData();
                Map<String, List<ProductCategoryUtilModel>> majorMap = Maps.newHashMap();
                initMajorList(majorMap);
                if(CollectionUtils.isEmpty(majorMap)) {
                    ((CategoryConfigData) data).setBenkeList(majorMap.get("benkeList"));
                    ((CategoryConfigData) data).setZhuankeList(majorMap.get("zhuankeList"));
                }
            }
        }
        if ("1".equals(request.getThirdlogin())) {
            data.setThirdlogin(ThirdPlateformProp.getThirdLogin());
        }
        if ("1".equals(request.getShareplateform())) {
            data.setShareplateform(ThirdPlateformProp.getSharePlateform());
        }
        if ("1".equals(request.getBlankNotice())) {
            BlankNoticeResponse blankNoticeResponse = initBlankNotice();
            if (null != blankNoticeResponse) {
                data.setBlankNotice(new BlankNotice().initBlankNotice(blankNoticeResponse));
            }
        }
        if ("1".equals(request.getFeedbackCategory())) {
            String feedbackCategorys =
                    CommonInfoUtil.getCommonInfoInfoByCode(feedback_code).getInfoVersion();
            if (StringUtils.isNotBlank(feedbackCategorys)) {
                List<String> feedbackCategoryList = FastJsonUtil.fromJsons(feedbackCategorys,
                        new TypeReference<List<String>>() {});
                data.setFeedbackCategoryList(feedbackCategoryList);
            }
        }
        response.setData(data);
        return response;
    }

    private BlankNoticeResponse initBlankNotice() {
        BlankNoticeResponse response = new BlankNoticeResponse(ResultType.SUCCESS);
        try {
            List<BlankNoticeModel> blackNoticeList = stUcenterModelServiceFacade
                    .queryBlackNoticeList(null, BlankNoticeModelProperty.getAllInfo());
            if (CollectionUtils.isEmpty(blackNoticeList)) {
                return null;
            }
            return response.setBlankNoticeResponse(blackNoticeList.get(0));
        } catch (Exception e) {
            LoggerUtil.error("查询首页弹出页数据异常", e);
            return new BlankNoticeResponse(ResultType.SYSFAIL);
        }
    }

    private Map<String, List<ProductCategoryUtilModel>> initMajorList(Map<String, List<ProductCategoryUtilModel>> majorMap) {
        try {
            // 1、查询模块所有专业
            List<ProductCategoryUtilModel> benkeList = Lists.newArrayList();
            List<ProductCategoryUtilModel> zhuankeList = Lists.newArrayList();
            Map<String, Object> input = new HashMap<String, Object>();
            // 默认查询北京
            input.put("module", "2");
            input.put("showStatus", 1);
            List<ProductCategoryUtilModel> list =
                    stUcenterModelServiceFacade.queryProductCategoryList(input,
                            CommUtil.getAllField(ProductCategoryUtilModel.class));


            // 无法保证数据courseCount的正确性，还是先这样写吧。。
            Map<Integer, Integer> tempMap = Maps.newHashMap();
            Map<String, Object> _cond = new HashMap<>();
            _cond.put("module", "2");
            _cond.put("showStatus", 1);
            _cond.put("applyTime", 1);// 在课程有效期内
            Page<ProductUtilModel> page =
                    stUcenterModelServiceFacade.queryCascadeProductByCond(_cond, CommUtil.getAllField(ProductUtilModel.class));
            int initCount = 0;
            if (!CollectionUtils.isEmpty(page.getResult())) {
                //加入新手课程
                for (ProductUtilModel temp : page.getResult()) {
                    if(temp.getProductCategoryId() == null) {
                        if(temp.getModuleCourse().equals(UcenterModelConstant.MODULE)) {
                            initCount = 1;
                        }
                    }
                }
                for (ProductUtilModel temp : page.getResult()) {
                    if(temp.getProductCategoryId() == null) {
                        continue;
                    }
                    if (tempMap.containsKey(temp.getProductCategoryId())) {
                        tempMap.put(temp.getProductCategoryId(),
                                tempMap.get(temp.getProductCategoryId()) + 1);
                    } else {
                        tempMap.put(temp.getProductCategoryId(), initCount + 1 );
                    }
                }
            }
        
            
            for (ProductCategoryUtilModel productCategoryModel : list) {
                int courseCount = tempMap.get(productCategoryModel.getId()) != null
                        ? tempMap.get(productCategoryModel.getId())
                        : initCount;
                productCategoryModel.setCourseCount(String.valueOf(courseCount));

                if (StringUtils.isNotBlank(productCategoryModel.getMajorInfo())) {
                    MajorInfo majorInfo = FastJsonUtil.fromJson(productCategoryModel.getMajorInfo(),
                            MajorInfo.class);
                    switch (MajorLevel.getByValue(majorInfo.getMajorLevel())) {
                        case benke:
                            productCategoryModel.setMajorLevel(MajorLevel.benke.getCode());
                            benkeList.add(productCategoryModel);
                            break;
                        case zhuanke:
                            productCategoryModel.setMajorLevel(MajorLevel.zhuanke.getCode());
                            zhuankeList.add(productCategoryModel);
                            break;
                        default:
                            break;
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


    public List<RegionVO> listRegionVO() {
        List<RegionVO> list = Lists.newArrayList();
        Map<String, Object> input = Maps.newHashMap();
        input.put("showStatus", 1);
        Map<String, Object> output = CommUtil.getAllField(ProductCategoryUtilModel.class);
        output.remove("majorInfo");
        List<ProductCategoryUtilModel> ls =
                stUcenterModelServiceFacade.listProductCategoryJoinModuleInfo(input, output);
        Map<String, List<Major>> tempMap = Maps.newHashMap();
        List<Major> majorList = null;
        Major major = null;
        RegionVO regionVO = null;
        for (ProductCategoryUtilModel cat : ls) {
            major = new Major(cat.getId().toString(), cat.getTypeCode(), cat.getName(),
                cat.getCourseCount(), cat.getPictureUrl(), cat.getChiefAcademy());
            if (tempMap.containsKey(cat.getModule())) {
                tempMap.get(cat.getModule()).add(major);
            } else {
                majorList = Lists.newArrayList(major);
                tempMap.put(cat.getModule(), majorList);
                regionVO = new RegionVO(cat.getModule(), cat.getModuleName(), majorList);
                list.add(regionVO);
            }
        }
        return list;
    }
}
