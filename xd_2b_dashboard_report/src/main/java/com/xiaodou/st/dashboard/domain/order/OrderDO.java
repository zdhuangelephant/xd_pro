package com.xiaodou.st.dashboard.domain.order;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

@Data
public class OrderDO {
  /* id 主键id */
  @Column(isMajor = true, autoIncrement = true)
  private Long id;
  private Long pilotUnitId;
  private String pilotUnitName;
  /* 报名学生人数 */
  private Integer studentCount;
  /* 报考课次 */
  private Integer applyCount;
  /* orderNumber 订单编号 */
  @Column(canUpdate = false)
  private Long orderNumber;
  /* totalAmount 总金额（元） */
  @Column(canUpdate = false)
  private Double totalAmount;
  /* 缴费状态 -1 已关闭 0：待缴费，1：已缴费 */
  @Column(canUpdate = true)
  private Short status;
  @Column(canUpdate = false)
  private Timestamp orderTime;
  @Column(canUpdate = true)
  private Timestamp payTime;
  @Column(canUpdate = false)
  private Timestamp createTime;
  
  /*折扣率*/
  @Column(canUpdate = false)
  private Double priceRate;
  @Column(canUpdate = false)
  private Integer adminId;
  @Column(canUpdate = false)
  private String adminRealName;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(OrderDO.class, "xd_dashboard_order",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/order/")
        .buildXml();
  }
}
