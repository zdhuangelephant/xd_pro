package com.xiaodou.userCenter.module.domain.vo;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.request.ModifyMyInfoRequest;

import lombok.Data;

@Data
public class ModuleInfoDTO {

    public ModuleInfoDTO() {}

    public static ModuleInfoDTO getInstance(String headVersion, UserModel user,
            ModifyMyInfoRequest request) {
        String moduleInfo = user.getModuleInfo();
        ModuleInfoDTO dto = new ModuleInfoDTO();
        if (StringUtils.isJsonNotBlank(user.getModuleInfo())) {
            dto = FastJsonUtil.fromJson(moduleInfo, ModuleInfoDTO.class);
        }
        if (StringUtils.isNotBlank(request.getMajor())) {
            dto.setMajor(request.getMajor());
        }
        if (StringUtils.isNotBlank(request.getSign())) {
            dto.setSign(request.getSign());
        }
        if (StringUtils.isNotBlank(request.getMajorId())) {
            dto.setMajorId(request.getMajorId());
        }
        if (StringUtils.isNotBlank(request.getMajorName())) {
            dto.setMajorName(request.getMajorName());
        }
        if (StringUtils.isNotBlank(request.getRegion())) {
            dto.setRegion(request.getRegion());
        }
        if (StringUtils.isNotBlank(request.getRegionName())) {
            dto.setRegionName(request.getRegionName());
        }
        return dto;
    }

    private String major;// 用户已选专业的专业码值
    private String sign; // 签名
    private String medal; // 称号 FastJsonUtil.toJson(StMedal);
    private String picList;// 图片

    /** module 地区 */
    private String region;
    /** moduleName 地区名称 */
    private String regionName;
    /** majorId 专业id */
    private String majorId;
    /** majorName 专业名称 */
    private String majorName;

    @Data
    public static class Medal {
        /** medalId 勋章id */
        private String medalId;
        /** medalName 勋章名称 */
        private String medalName;
        /** medalImg 勋章图片 */
        private String medalImg;
    }

    @Data
    public static class Picture {
        private String pictrue;
    }

}
