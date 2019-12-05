package com.xiaodou.userCenter.model;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.enums.Gender;

public class UserModel {
    /**
     * 主键id
     */
    @GeneralField
    @BaseField
    private Long id;

    /**
     * token初始时间
     */
    @BaseField
    private Timestamp tokenTime;

    /**
     * token值
     */
    @GeneralField
    @BaseField
    private String token;

    /**
     * 创建时间
     */
    @BaseField
    private Timestamp createTime;

    /**
     * 用过的设备号列表,设备号之间以逗号分隔
     */
    @BaseField
    private String usedDeviceId;

    /**
     * 用户年龄
     */
    @BaseField
    private Integer age;

    /**
     * 用户地址
     */
    @BaseField
    private String address;

    /**
     * 最新device ip
     */
    @BaseField
    private String latestDeviceIp;

    /**
     * 昵称
     */
    @BaseField
    private String nickName;

    /**
     * 性别
     */
    @BaseField
    private Integer gender;

    /**
     * 头像URL地址
     */
    @BaseField
    private String portrait;

    /**
     * 所属模块信息
     */
    @BaseField
    private String moduleInfo;

    @BaseField
    private Integer credit; // 积分
    @BaseField
    private Integer bePraiseNum;// 被点赞数
    @BaseField
    private Integer userType;// 用户类型 1 普通用户 2 导入用户
    @BaseField
    private String officialInfo;// 后台官方导入数据（不可修改）

    @BaseField
    private Long learnDays;// 学习天数
    @BaseField
    private Long loginDays;// 登录天数
    /**
     * 小逗自生产账号（唯一）
     */
    @BaseField
    private String xdUniqueId;
    @BaseField
    private String packageTag;

    public String getPackageTag() {
        return packageTag;
    }

    public void setPackageTag(String packageTag) {
        this.packageTag = packageTag;
    }

    public String getXdUniqueId() {
        return xdUniqueId;
    }

    public void setXdUniqueId(String xdUniqueId) {
        this.xdUniqueId = xdUniqueId;
    }

    public Long getLearnDays() {
        return learnDays;
    }

    public void setLearnDays(Long learnDays) {
        this.learnDays = learnDays;
    }

    public Long getLoginDays() {
        return loginDays;
    }

    public void setLoginDays(Long loginDays) {
        this.loginDays = loginDays;
    }

    public Integer getGenderCode() {
        return gender;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = "".equals(gender) ? null : gender;
    }

    public void setGender(String gender) {
        if (StringUtils.isBlank(gender)) return;
        this.gender = Gender.getByCode(gender).getCode();
    }

    public String getLatestDeviceIp() {
        return latestDeviceIp;
    }

    public void setLatestDeviceIp(String latestDeviceIp) {
        this.latestDeviceIp = latestDeviceIp;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = ("".equals(nickName)) ? null : nickName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Timestamp getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(Timestamp tokenTime) {
        this.tokenTime = tokenTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getUsedDeviceId() {
        return usedDeviceId;
    }

    public void setUsedDeviceId(String usedDeviceId) {
        this.usedDeviceId = usedDeviceId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = "".equals(address) ? null : address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(String moduleInfo) {
        this.moduleInfo = moduleInfo;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getBePraiseNum() {
        return bePraiseNum;
    }

    public void setBePraiseNum(Integer bePraiseNum) {
        this.bePraiseNum = bePraiseNum;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getOfficialInfo() {
        return officialInfo;
    }

    public void setOfficialInfo(String officialInfo) {
        this.officialInfo = officialInfo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
