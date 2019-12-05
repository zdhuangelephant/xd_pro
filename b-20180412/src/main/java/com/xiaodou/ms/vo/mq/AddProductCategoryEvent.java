package com.xiaodou.ms.vo.mq;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.ms.model.product.ProductCategoryModel;
import com.xiaodou.ms.vo.mq.AddProductCategoryEvent.TransferProductCategoryData;

/**
 * @name AddProductCategoryEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 添加产品专业事件
 * @version 1.0
 */
public class AddProductCategoryEvent extends DCCoreEvent<TransferProductCategoryData> {
  private static final String EVENT_NAME = "addProductCategory";

  public AddProductCategoryEvent() {
    setEventName(EVENT_NAME);
    // setModule(XdmsConstant.MODULE);
  }

  /**
   * @name @see com.xiaodou.vo.mq.TransferProductCategoryData.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年3月31日
   * @description 传输参数类
   * @version 1.0
   */
  @Data
  public static class TransferProductCategoryData {
    public TransferProductCategoryData(Long id, String module) {
      this.id = id;
      this.module = module;
    }

    public TransferProductCategoryData(ProductCategoryModel model) {
      this.id = model.getId();
      this.parentId = model.getParentId();
      this.childId = model.getChildId();
      this.showStatus = model.getShowStatus();
      this.allParentId = model.getAllParentId();
      this.allChildId = model.getAllChildId();
      this.level = model.getLevel();
      this.name = model.getName();
      this.courseCategoryType = model.getCourseCategoryType();
      this.detail = model.getDetail();
      this.misc = model.getMisc();
      this.createTime = model.getCreateTime();
      this.updateTime = model.getUpdateTime();
      this.isLeaf = model.getIsLeaf();
      this.module = model.getModule();
      this.typeCode = model.getTypeCode();
      this.classify = model.getClassify();
      this.isCooperation = model.getIsCooperation();
      this.isSync = model.getIsSync();
      this.isBuy = model.getIsBuy();
      this.courseCount = model.getCourseCount();
      this.chiefAcademy = model.getChiefAcademy();
    }

    // 主键
    private Long id;
    // 父
    private Long parentId;
    // 子
    private String childId;
    // 展示状态
    private Integer showStatus;
    // 所有父id
    private String allParentId;
    // 所有子id
    private String allChildId;
    // 所在层级(从1级开始，最好不要超过3级)
    private Integer level;
    // 名称
    private String name;
    // 分类类型
    private Integer courseCategoryType;
    // 描述
    private String detail;
    private String misc;
    // 创建时间
    private Timestamp createTime;
    // 更新时间
    private Timestamp updateTime;
    // 是否为叶节点
    private Integer isLeaf;
    // app模块
    private String module;
    // 分类（专业代码）
    private String typeCode;
    /** classify 专业分类 */
    private Integer classify;


    // zwj新加字段
    private Integer isCooperation; // '是否为合作专业：1表示是，0表示否',
    private Integer isSync;// '是否为同步云测评：1表示是，0表示否',
    private Integer isBuy;// '是否为可以购买：1表示是，0表示否',
    private Integer courseCount;// '课程数量',

    private String chiefAcademy;// '主考院校',

  }
}
