package com.xiaodou.oms.statemachine.instance.businsurance;

import java.util.Map;

import org.squirrelframework.foundation.exception.TransitionException;
import org.squirrelframework.foundation.fsm.ImmutableUntypedState;
import org.squirrelframework.foundation.fsm.annotation.State;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParamters;
import org.squirrelframework.foundation.fsm.annotation.States;
import org.squirrelframework.foundation.fsm.annotation.Transit;
import org.squirrelframework.foundation.fsm.annotation.Transitions;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.exception.UnknownTransitionException;
import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.FlowExecutor;
import com.xiaodou.oms.statemachine.instance.insurance.InsuranceEvent;
import com.xiaodou.oms.statemachine.instance.insurance.InsuranceState;
import com.xiaodou.oms.util.ErrorsWrapper;
import com.xiaodou.oms.util.OrderLoggerUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * 
 *@Description: 保险业务的状态机
 *@author: zhenpu.hou
 *@date : 2015年5月11日 下午2:19:57
 */
@States({ 
    @State(name = "Init"),//  0  新单
    @State(name = "PaySuccess"),//1 支付成功
		@State(name = "PayFailure"),//-1  支付失败
		@State(name = "Closed"),//5   取消
		@State(name = "DeliveringSuccess"),//6  投保成功
		@State(name = "Delivered"),//2   出保成功
		@State(name = "DeliveredFailure"),//-2   出保失败
})

@Transitions({
   //投保成功
  @Transit(from = "PaySuccess", to = "DeliveringSuccess", on = "DeliveringSuccess", callMethod = "fromPaySuccessToDeliveringSuccessOnDeliveringSuccess"),//13
  
  //支付成功
  @Transit(from = "DeliveredFailure", to = "PaySuccess", on = "DeliveringRequestAgain", callMethod = "fromDeliveredFailureToPaySuccessOnDeliveringRequestAgain"),//19
  
  //出保成功
  @Transit(from = "PaySuccess", to = "Delivered", on = "DeliveredSuccess", callMethod = "fromPaySuccessToDeliveredOnDeliveredSuccess"),//15
  @Transit(from = "DeliveringSuccess", to = "Delivered", on = "DeliveredSuccess", callMethod = "fromDeliveringSuccessToDeliveredOnDeliveredSuccess"),//14
  @Transit(from = "DeliveredFailure", to = "Delivered", on = "ModifiedToDelivered", callMethod = "fromDeliveredFailureToDeliveredOnModifiedToDelivered"),//17
  
  //出保失败
  @Transit(from = "DeliveringSuccess", to = "DeliveredFailure", on = "DeliveredFailure", callMethod = "fromDeliveringSuccessToDeliveredFailureOnDeliveredFailure"),//16 
  @Transit(from = "PaySuccess", to = "DeliveredFailure", on = "DeliveredFailure", callMethod = "fromPaySuccessToDeliveredFailureOnDeliveredFailure"),//18

  //取消
  @Transit(from = "DeliveredFailure", to = "Closed", on = "RefundRequest", callMethod = "fromDeliveredFailureToClosedOnRefundRequest"),//20
  @Transit(from = "Closed", to = "Closed", on = "RefundSuccess", callMethod = "fromClosedToClosedOnRefundSuccess"),//23
  @Transit(from = "Closed", to = "Closed", on = "RefundFailure", callMethod = "fromClosedToClosedOnRefundFailure"),//24
})

@StateMachineParamters(stateType=BusInsuranceState.class, eventType=BusInsuranceEvent.class, contextType=Context.class)
public class BusInsuranceStateMachine extends AbstractUntypedStateMachine {
  /** 流执行器 */
  private static FlowExecutor flowExecutor = SpringWebContextHolder.getBean("flowExecutor");

  protected BusInsuranceStateMachine(ImmutableUntypedState initialState,
      Map<Object, ImmutableUntypedState> states) {
    super(initialState, states);
  }

  /**
   * 统一的异常捕获。
   * @param e
   * @param from
   * @param to
   * @param event
   * @param context
   */
  @Override
  public void afterTransitionCausedException(Exception e, Object from, Object to, Object event,
      Object context) {
    TransitionException e1 = (TransitionException) e;
    String orderId = ((Context) context).getCoreParams().getOrderId();
    LoggerUtil.error("statemachine from " + from + " to " + to + " on " + event
        + " failure orderId=" + orderId, e1.getTargetException());
    throw e1;

  }
  /**
   * Transition成功执行完后触发。
   * @param from
   * @param to
   * @param event
   * @param context
   */
  @Override
  public void afterTransitionCompleted(Object from, Object to, Object event, Object context) {
    String orderId = ((Context) context).getCoreParams().getOrderId();
    OrderLoggerUtil.orderInfo("statemachine from " + from + " to " + to + " on " + event
        + " completed orderId=" + orderId);
  }
  
  /**
   * 状态机找不到对应的Transition时抛异常。
   *
   * @param from
   * @param event
   * @param context
   */
  @Override
  protected void afterTransitionDeclined( Object from, Object event,  Object context) {  
     ErrorsWrapper.getWrapper().setValue(new UnknownTransitionException("状态机找不到对应的Transition,from="+from+",event="+event));                               
  }

  /**
   * 支付成功->投保成功，收到投保请求触发。  13
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPaySuccessToDeliveringSuccessOnDeliveringSuccess(BusInsuranceState from, BusInsuranceState to, BusInsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToDeliveringSuccessOnDeliveringSuccess", context,to.getName());
  }
  
  /**
   * 投保成功->出保成功，出保成功时触发。  14
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveringSuccessToDeliveredOnDeliveredSuccess(BusInsuranceState from, BusInsuranceState to, BusInsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringSuccessToDeliveredOnDeliveredSuccess", context,to.getName());
  }
  
  /**
   * 支付成功->出保成功，出保成功时触发。  15
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPaySuccessToDeliveredOnDeliveredSuccess(BusInsuranceState from, BusInsuranceState to, BusInsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToDeliveredOnDeliveredSuccess", context,to.getName());
  }
  
  /**
   * 投保成功->出保失败，出保失败时触发。 16
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveringSuccessToDeliveredFailureOnDeliveredFailure(BusInsuranceState from, BusInsuranceState to, BusInsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringSuccessToDeliveredFailureOnDeliveredFailure", context,to.getName());
  }
  
  /**
   * 支付成功->出保失败，出保失败时触发。 18
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPaySuccessToDeliveredFailureOnDeliveredFailure(BusInsuranceState from, BusInsuranceState to, BusInsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToDeliveredFailureOnDeliveredFailure", context,to.getName());
  }
  
  /**
   * 出保失败->支付成功，人工再次投保时触发。  19
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredFailureToPaySuccessOnDeliveringRequestAgain(BusInsuranceState from, BusInsuranceState to, BusInsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredFailureToPaySuccessOnDeliveringRequestAgain", context,to.getName());
  }
  
  /**
   * 出保失败->出保成功，人工改状态时触发。  17
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredFailureToDeliveredOnModifiedToDelivered(BusInsuranceState from, BusInsuranceState to, BusInsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredFailureToDeliveredOnModifiedToDelivered", context,to.getName());
  }
  
  /**
   * 出保失败->取消，人工发起退款时触发。  20
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredFailureToClosedOnRefundRequest(BusInsuranceState from, BusInsuranceState to, BusInsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredFailureToClosedOnRefundRequest", context,to.getName());
  }
  
  /**
   * 取消->取消，payment通知退款成功时触发。  23
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromClosedToClosedOnRefundSuccess(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromClosedToClosedOnRefundSuccess", context,to.getName());

  }
  
  /**
   * 取消->取消，payment通知退款失败时触发。24
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromClosedToClosedOnRefundFailure(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromClosedToClosedOnRefundFailure", context,to.getName());

  }
  
  
}
