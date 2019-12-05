package com.xiaodou.oms.statemachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.service.facade.OrderServiceFacade;

/**
 * @Title:StateMachineFactory.java
 * @Description:状态机工厂类
 * @author zhaoyang
 * @date June 18, 2014 8:33:35 AM
 * @version V1.0
 */
public class StateMachineFactory {

  @Resource
  OrderServiceFacade orderServiceFacade;

  /** 状态机实例列表 */
  public List<String> stateMachineInstanceList = new ArrayList<String>();

  public List<String> getStateMachineInstanceList() {
    return stateMachineInstanceList;
  }

  public void setStateMachineInstanceList(List<String> stateMachineInstanceList) {
    this.stateMachineInstanceList = stateMachineInstanceList;
  }

  /** 状态机builder缓存 */
  public Map<String, UntypedStateMachineBuilder> builderMap =
      new HashMap<String, UntypedStateMachineBuilder>();

  public UntypedStateMachine getStateMachine(String statemachineInstance, String stateInstance,
      Context context) throws Exception {

    UntypedStateMachineBuilder builder = builderMap.get(statemachineInstance);
    Integer instanceStatus = null;
    if (context.getCoreParams().getOrderId() != null) {
      Order dbOrder = queryOrderDetail(context.getCoreParams().getOrderId());
      context.getShares().put("dbOrder", dbOrder);
      instanceStatus = dbOrder.getOrderStatus();
    } else if (context.getCoreParams().getGorderId() != null) {
      Gorder dbGorder = queryGorderDetail(context.getCoreParams().getGorderId());
      context.getShares().put("dbGorder", dbGorder);
      instanceStatus = dbGorder.getGorderStatus();
    } else {
      throw new Exception("初始化状态机失败，gorderId、orderId都为空");
    }
    Enum state = StateEventConvertor.getOrderState(instanceStatus, stateInstance);
    return builder.newStateMachine(state);

  }

  /**
   * 实例化builder缓存
   * 
   * @throws Exception
   */
  public void initMethod() throws Exception {

    for (String statemachine : stateMachineInstanceList) {
      statemachine = statemachine.trim();
      Class clazz = Class.forName(statemachine);
      UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(clazz);
      builderMap.put(statemachine, builder);
    }
  }

  /**
   * 查询库里的订单数据
   * 
   * @param orderId
   * @return
   * @throws Exception
   */
  private Order queryOrderDetail(String orderId) throws Exception {
    Map output = new HashMap();
    output.put("id", "1");
    output.put("gorderId", "1");
    output.put("orderStatus", "1");
    output.put("buyAccountId", "1");
    output.put("payAmount", "1");
    output.put("orderAmount", "1");
    output.put("productType", "1");
    output.put("merchantId", "1");
    output.put("merchantOrderId", "1");
    output.put("orderTime", "1");
    output.put("updateTime", "1");
    output.put("merchantName", "1");
    Map input = new HashMap();
    input.put("id", orderId);
    input.put("forUpdate", 1);

    return orderServiceFacade.queryOrderDetail(input, output);

  }

  /**
   * 查询库里的订单数据
   * 
   * @param gorderId
   * @return
   * @throws Exception
   */
  private Gorder queryGorderDetail(String gorderId) throws Exception {
    Map output = new HashMap();
    output.put("id", "1");
    output.put("gorderStatus", "1");
    Map input = new HashMap();
    input.put("id", gorderId);
    input.put("forUpdate", 1);
    return orderServiceFacade.queryGorderDetail(input, output);
  }

}
