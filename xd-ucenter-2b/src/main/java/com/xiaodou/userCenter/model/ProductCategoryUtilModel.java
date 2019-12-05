package com.xiaodou.userCenter.model;

import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;
@Data
public class ProductCategoryUtilModel {

  /**id 主键*/
  @BaseField
  @GeneralField
  private Integer id;

  // 展示状态(1、显示2、不显示)
  @GeneralField
  private Integer showStatus;

  // 名称
  @GeneralField
  private String name;

  /** pictureUrl 专业封面 */
  @GeneralField
  private String pictureUrl;

  /**module */
  @GeneralField
  private String module;

  // 分类（专业代码）
  @GeneralField
  private String typeCode;

  // 模块名
  @GeneralField
  private String moduleName;

  /* 专业层次(1：本科，2：专科) */
  @GeneralField
  private String majorLevel;

  /* 专业下课程数目 */
  @GeneralField
  private String courseCount;
  
  @GeneralField
  private String majorInfo;
  
  /**chiefAcademy 主考院校*/
  private String chiefAcademy;

  @Data
  public static class MajorInfo {
    private String createTime;// 创建记录时间
    private String degree;// 学位（eg:文学学士）
    private String detail;
    private String examSchool;// majorLevel;主考院校
    private String majorLevel;// 专业层次(eg:专科)
  }
}
