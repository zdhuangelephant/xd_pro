package com.xiaodou.ms.vo.mq;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.vo.mq.AddProductEvent.TransferProductData;

/**
 * @name AddProductEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 添加产品事件
 * @version 1.0
 */
public class AddProductEvent extends DCCoreEvent<TransferProductData> {
  private static final String EVENT_NAME = "addProduct";

  public AddProductEvent() {
    setEventName(EVENT_NAME);
    // setModule(XdmsConstant.MODULE);
  }

  /**
   * @name @see com.xiaodou.vo.mq.TransferProductData.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年3月31日
   * @description 传输参数类
   * @version 1.0
   */
  @Data
  public static class TransferProductData {
    public TransferProductData(ProductModel model) {
      this.id = model.getId();
      this.name = model.getName();
      this.briefInfo = model.getBriefInfo();
      this.detail = model.getDetail();
      this.imageUrl = model.getImageUrl();
      this.currApplyCount = model.getCurrApplyCount();
      this.totalApplyCount = model.getTotalApplyCount();
      this.beginApplyTime = model.getBeginApplyTime();
      this.endApplyTime = model.getEndApplyTime();
      this.originalAmount = model.getOriginalAmount();
      this.payAmount = model.getPayAmount();
      this.createTime = model.getCreateTime();
      this.updateTime = model.getUpdateTime();
      this.misc = model.getMisc();
      this.showStatus = model.getShowStatus();
      this.praiseCount = model.getPraiseCount();
      this.questionBankSetting = model.getQuestionBankSetting();
      this.resourceSubject = model.getResourceSubject();
      this.shareUrl = model.getShareUrl();
      this.courseCode = model.getCourseCode();
      this.examDate = model.getExamDate();
      this.module = model.getModule();
      this.ruleId = model.getRuleId();
    }

    public TransferProductData(Long id, String module) {
      this.id = id.longValue();
      this.module = module;
    }

    // 主键Id
    private Long id;
    // 产品名
    private String name;
    // 简介
    private String briefInfo;
    // 详情
    private String detail;
    // 图片地址
    private String imageUrl;
    // 当前报名人数
    private Integer currApplyCount;
    // 报名人数上限
    private Integer totalApplyCount;
    // 开始申请时间
    private Timestamp beginApplyTime;
    // 申请结束时间
    private Timestamp endApplyTime;
    // 原价
    private BigDecimal originalAmount;
    // 优惠价
    private BigDecimal payAmount;
    // 创建时间
    private Timestamp createTime;
    // 更新时间
    private Timestamp updateTime;
    // misc 杂项
    private String misc;
    // 是否显示
    private Integer showStatus;
    // 点赞数
    private Integer praiseCount;
    // 题库设置
    private String questionBankSetting;
    // 资源产品ID
    private Long resourceSubject;
    /** shareUrl 分享url */
    private String shareUrl;
    /** courseCode 课程码 */
    private String courseCode;
    /** examDate 考期 */
    private String examDate;
    // zwj 新增字段
    /** 地域编码 */
    @GeneralField
    private String module;
    /** 计分规则Id */
    @GeneralField
    private String ruleId;
  }
}
