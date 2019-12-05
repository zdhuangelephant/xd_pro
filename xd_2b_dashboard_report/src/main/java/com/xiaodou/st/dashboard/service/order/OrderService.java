package com.xiaodou.st.dashboard.service.order;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.order.OrderDO;
import com.xiaodou.st.dashboard.domain.order.OrderDTO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.grade.ClassService;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class OrderService extends BaseDashboardService {
  @Resource
  IStServiceFacade stServiceFacade;
  @Resource
  ClassService classService;

  public List<OrderDO> listOrder(OrderDTO orderDTO) {
    Map<String, Object> inputs = Maps.newHashMap();
    AdminUser adminUser = super.getAdminUser();
    if (null == adminUser.getUnitId()) return null;
    inputs.put("pilotUnitId", adminUser.getUnitId());
    INPUTS: {
      if (null == orderDTO) break INPUTS;
      if (null != orderDTO.getStatus()) inputs.put("status", orderDTO.getStatus());
      if (null != orderDTO.getOrderNumber()) inputs.put("orderNumber", orderDTO.getOrderNumber());
      if (StringUtils.isNotBlank(orderDTO.getOrderTime()))
        inputs.put("orderTime", orderDTO.getOrderTime());
      if (StringUtils.isNotBlank(orderDTO.getPayTime()))
        inputs.put("payTime", orderDTO.getPayTime());
      if (null != adminUser.getChildRole()
          && Constants.POILT_UNIT_CHILD_ROLE.equals(adminUser.getChildRole())) {
        // 班級权限人，只能看到自己下的订单
        inputs.put("adminId", adminUser.getId());
      }
    }
    Page<OrderDO> page = stServiceFacade.listOrder(inputs, CommUtil.getAllField(OrderDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  /**
   * 
   * @description 查看订单详情
   * @author 李德洪
   * @Date 2017年4月5日
   * @param orderNumber
   * @return
   */
  public OrderDO getOrder(Long orderNumber) {
    Map<String, Object> inputs = Maps.newHashMap();
    AdminUser adminUser = super.getAdminUser();
    if (null == adminUser.getUnitId()) return null;
    inputs.put("pilotUnitId", adminUser.getUnitId());
    if (null == orderNumber) return null;
    inputs.put("orderNumber", orderNumber);
    Page<OrderDO> page = stServiceFacade.listOrder(inputs, CommUtil.getAllField(OrderDO.class));
    if (null == page || page.getResult().isEmpty()) return null;
    return page.getResult().get(0);
  }

  /**
   * 增加订单
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param orderDO
   * @return
   */
  /*
   * public Long saveOrder(String applyIds, Integer studentCount, Integer applyCount,Double
   * totalAmount) { if(StringUtils.isBlank(applyIds) || null == studentCount ||null == applyCount)
   * return null; OrderDO orderDO = new OrderDO(); AdminUser adminUser = super.getAdminUser();
   * orderDO.setPilotUnitId(adminUser.getUnitId());
   * orderDO.setPilotUnitName(adminUser.getUnitName()); Long orderNumber =
   * System.currentTimeMillis(); orderDO.setOrderNumber(orderNumber);
   * orderDO.setStudentCount(studentCount); orderDO.setApplyCount(applyCount); //Double
   * productPriceRate = Double.valueOf(StaticInfoProp.productPriceRate());
   * orderDO.setTotalAmount(totalAmount); orderDO.setOrderTime(new
   * Timestamp(System.currentTimeMillis())); orderDO.setCreateTime(new
   * Timestamp(System.currentTimeMillis())); orderDO.setAdminId(adminUser.getId());
   * orderDO.setAdminRealName(adminUser.getRealName()); orderDO.setApplyCount(applyCount);
   * orderDO.setPriceRate(adminUser.getPriceRate()); stServiceFacade.saveOrder(orderDO); //if
   * (!flag) return flag; Map<String, Object> inputs = Maps.newHashMap(); List<String> idList =
   * Arrays.asList(applyIds.split(",")); inputs.put("idList", idList); //inputs.put("applyIds",
   * applyIds); ApplyDO applyDO = new ApplyDO(); applyDO.setOrderStatus(Constants.WAITINGPAYMENT);
   * applyDO.setOrderNumber(orderNumber); stServiceFacade.updateApplyByCond(inputs, applyDO); return
   * orderNumber; }
   */
  public Long saveOrder(List<Long> applyIds, Integer studentCount, Integer applyCount,
      Double totalAmount) {
    if (null == applyIds || applyIds.isEmpty() || null == studentCount || null == applyCount)
      return null;
    OrderDO orderDO = new OrderDO();
    AdminUser adminUser = super.getAdminUser();
    orderDO.setPilotUnitId(adminUser.getUnitId());
    orderDO.setPilotUnitName(adminUser.getUnitName());
    Long orderNumber = System.currentTimeMillis();
    orderDO.setOrderNumber(orderNumber);
    orderDO.setStudentCount(studentCount);
    orderDO.setApplyCount(applyCount);
    // Double productPriceRate = Double.valueOf(StaticInfoProp.productPriceRate());
    orderDO.setTotalAmount(totalAmount);
    orderDO.setOrderTime(new Timestamp(System.currentTimeMillis()));
    orderDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    orderDO.setApplyCount(applyCount);
    orderDO.setPriceRate(adminUser.getPriceRate());
    orderDO.setAdminId(adminUser.getId());
    orderDO.setAdminRealName(adminUser.getRealName());
    // orderDO.setClassId(classId);
    stServiceFacade.saveOrder(orderDO);
    // if (!flag) return flag;
    Map<String, Object> inputs = Maps.newHashMap();
    // List<String> idList = Arrays.asList(applyIds.split(","));
    inputs.put("idList", applyIds);
    // inputs.put("applyIds", applyIds);
    ApplyDO applyDO = new ApplyDO();
    applyDO.setOrderStatus(Constants.WAITINGPAYMENT);
    applyDO.setOrderNumber(orderNumber);
    stServiceFacade.updateApplyByCond(inputs, applyDO);
    return orderNumber;
  }

  /**
   * 修改订单状态
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param inputs
   * @param orderId
   * @return
   */
  public boolean updateOrder(OrderDO orderDO) {
    return stServiceFacade.updateOrder(orderDO);
  }

  public boolean closeOrder(Long orderId, Long orderNumber) {
    boolean flag = false;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("orderNumber", orderNumber);
    ApplyDO applyDO = new ApplyDO();
    applyDO.setOrderStatus(Constants.NOTPAYMENT);
    flag = stServiceFacade.updateApplyByCond(inputs, applyDO);
    OrderDO ordeDO = new OrderDO();
    ordeDO.setId(orderId);
    ordeDO.setStatus(Constants.CLOSEPAYMENT);
    ordeDO.setOrderNumber(0l);
    flag = stServiceFacade.updateOrder(ordeDO);
    return flag;
  }


  public boolean updateOrder(Long orderNumber, Short payStatus) {
    boolean flag = false;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("orderNumber", orderNumber);
    ApplyDO applyDO = new ApplyDO();
    applyDO.setOrderStatus(payStatus);
    flag = stServiceFacade.updateApplyByCond(inputs, applyDO);
    OrderDO orderDO = new OrderDO();
    orderDO.setStatus(payStatus);
    orderDO.setPayTime(new Timestamp(System.currentTimeMillis()));
    flag = stServiceFacade.updateOrderByCond(inputs, orderDO);
    return flag;
  }

  /**
   * 刪除订单
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param inputs
   * @return
   */
  public boolean removeOrder(Long orderId) {
    boolean flag = false;
    flag = stServiceFacade.removeOrder(orderId);
    return flag;
  }
}
