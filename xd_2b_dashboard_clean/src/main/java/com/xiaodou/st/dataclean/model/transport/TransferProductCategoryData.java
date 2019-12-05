package com.xiaodou.st.dataclean.model.transport;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.st.dataclean.model.domain.raw.TransferProductCategoryData .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 产品专业数据
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferProductCategoryData extends BaseTransferModel {

  // 主键
  @NotEmpty
  private Integer id;

  // 父
  private Integer parentId;

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
  @Column(betweenScope = true)
  private Timestamp createTime;

  // 更新时间
  @Column(betweenScope = true)
  private Timestamp updateTime;

  // 是否为叶节点
  private Integer isLeaf;

  // 分类（专业代码）
  private String typeCode;

  /** classify 专业分类 */
  private Integer classify;

}
