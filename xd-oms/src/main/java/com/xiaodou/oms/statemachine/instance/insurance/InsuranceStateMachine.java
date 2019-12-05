package com.xiaodou.oms.statemachine.instance.insurance;

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
import com.xiaodou.oms.util.ErrorsWrapper;
import com.xiaodou.oms.util.OrderLoggerUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * 
 * @Title:InsuranceStateMachine.java
 * 
 * @Description:保险业务的状态机
 * 
 * @author zhaoyang
 * @date July 3, 2014 9:20:35 AM
 * @version V1.0
 */
@States({ 
	    @State(name = "Init"), 
	    @State(name = "PaySuccess"),
		@State(name = "PayFailure"),
		@State(name = "Closed"),
		@State(name = "Delivering"),
		@State(name = "Delivered"),
		@State(name = "DeliveredFailure"),
		@State(name = "Canceling"),
		@State(name = "CancelSuccess"),
		@State(name = "CancelFailure"),
})

@Transitions({
   //支付成功
  @Transit(from = "PaySuccess", to = "Delivering", on = "DeliveringSuccess", callMethod = "fromPaySuccessToDeliveringOnDeliveringSuccess"),
 
  //投保中
  @Transit(from = "Delivering", to = "Delivered", on = "DeliveredSuccess", callMethod = "fromDeliveringToDeliveredOnDeliveredSuccess"),
  @Transit(from = "Delivering", to = "DeliveredFailure", on = "DeliveredFailure", callMethod = "fromDeliveringToDeliveredFailureOnDeliveredFailure"),
  
  //投保失败
  @Transit(from = "DeliveredFailure", to = "Delivering", on = "DeliveringRequestAgain", callMethod = "fromDeliveredFailureToDeliveringOnDeliveringRequestAgain"),
  @Transit(from = "DeliveredFailure", to = "Delivered", on = "ModifiedToDelivered", callMethod = "fromDeliveredFailureToDeliveredOnModifiedToDelivered"),
  @Transit(from = "DeliveredFailure", to = "Closed", on = "RefundRequest", callMethod = "fromDeliveredFailureToClosedOnRefundRequest"),
  
  //退保请求
  @Transit(from = "Delivered", to = "Canceling", on = "CancelingRequest", callMethod = "fromDeliveredToCancelingOnCancelingRequest"),
  
  //退保成功
  @Transit(from = "Canceling", to = "CancelSuccess", on = "CancelSuccess", callMethod = "fromCancelingToCancelSuccessOnCancelSuccess"),
  //退保失败
  @Transit(from = "Canceling", to = "CancelFailure", on = "CancelFailure", callMethod = "fromCancelingToCancelFailureOnCancelFailure"),
  //后台人工退保
  @Transit(from = "Delivered", to = "Canceling", on = "CancelingRequestFromMS", callMethod = "fromDeliveredToCancelingOnCancelingRequestFromMS"),
  
  //后台再次退保
  @Transit(from = "CancelFailure", to = "Canceling", on = "CancelingRequestAgain", callMethod = "fromCancelFailureToCancelingOnCancelingRequestAgain"),
  //后台修改成退保成功
  @Transit(from = "CancelFailure", to = "CancelSuccess", on = "ModifiedToCancelSuccess", callMethod = "fromCancelFailureToCancelSuccessOnModifiedToCancelSuccess"),
  //后台修改成投保成功
  @Transit(from = "CancelFailure", to = "Delivered", on = "ModifiedToDelivered", callMethod = "fromCancelFailureToDeliveredOnModifiedToDelivered"),
  //取消
  @Transit(from = "Closed", to = "Closed", on = "RefundSuccess", callMethod = "fromClosedToClosedOnRefundSuccess"),
  @Transit(from = "Closed", to = "Closed", on = "RefundFailure", callMethod = "fromClosedToClosedOnRefundFailure"),
  
  //退保成功
  @Transit(from = "CancelSuccess", to = "CancelSuccess", on = "RefundSuccess", callMethod = "fromCancelSuccessToCancelSuccessOnRefundSuccess"),
  @Transit(from = "CancelSuccess", to = "CancelSuccess", on = "RefundFailure", callMethod = "fromCancelSuccessToCancelSuccessOnRefundFailure"),
})

@StateMachineParamters(stateType=InsuranceState.class, eventType=InsuranceEvent.class, contextType=Context.class)
public class InsuranceStateMachine extends AbstractUntypedStateMachine {
  /** 流执行器 */
  private static FlowExecutor flowExecutor = SpringWebContextHolder.getBean("flowExecutor");

  protected InsuranceStateMachine(ImmutableUntypedState initialState,
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
   * 支付成功->投保中，收到投保请求触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPaySuccessToDeliveringOnDeliveringSuccess(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToDeliveringOnDeliveringSuccess", context,to.getName());

  }
  
  /**
   * 投保中->投保成功，投保成功时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveringToDeliveredOnDeliveredSuccess(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToDeliveredOnDeliveredSuccess", context,to.getName());

  }
  /**
   * 投保中->投保失败，投保失败时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveringToDeliveredFailureOnDeliveredFailure(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToDeliveredFailureOnDeliveredFailure", context,to.getName());

  }
  
  /**
   * 投保失败->投保中，人工再次投保时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredFailureToDeliveringOnDeliveringRequestAgain(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredFailureToDeliveringOnDeliveringRequestAgain", context,to.getName());

  }
  /**
   * 投保失败->投保成功，人工改状态时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredFailureToDeliveredOnModifiedToDelivered(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredFailureToDeliveredOnModifiedToDelivered", context,to.getName());

  }
  /**
   * 投保失败->取消，人工发起退款时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredFailureToClosedOnRefundRequest(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredFailureToClosedOnRefundRequest", context,to.getName());

  }
  
  /**
   * 投保成功->退保中，收到退保请求时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredToCancelingOnCancelingRequest(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToCancelingOnCancelingRequest", context,to.getName());

  }
  
  /**
   * 投保成功->退保中，后台ms出发请求。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredToCancelingOnCancelingRequestFromMS(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToCancelingOnCancelingRequestFromMS", context,to.getName());
  }
  
  
  /**
   * 退保中->退保成功，退保成功时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelingToCancelSuccessOnCancelSuccess(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelingToCancelSuccessOnCancelSuccess", context,to.getName());

  }
  /**
   * 退保中->退保失败，退保失败时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelingToCancelFailureOnCancelFailure(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelingToCancelFailureOnCancelFailure", context,to.getName());

  }
  
  /**
   * 退保失败->退保中，再次发起退保请求时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelFailureToCancelingOnCancelingRequestAgain(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelFailureToCancelingOnCancelingRequestAgain", context,to.getName());

  }
  /**
   * 退保失败->退保成功，人工改状态时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelFailureToCancelSuccessOnModifiedToCancelSuccess(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelFailureToCancelSuccessOnModifiedToCancelSuccess", context,to.getName());

  }
  /**
   * 退保失败->投保成功，人工改状态时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelFailureToDeliveredOnModifiedToDelivered(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelFailureToDeliveredOnModifiedToDelivered", context,to.getName());

  }
  /**
   * 取消->取消，payment通知退款成功时触发。
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
   * 取消->取消，payment通知退款失败时触发。
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
  /**
   * 退保成功-退保成功，payment通知退款成功时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelSuccessToCancelSuccessOnRefundSuccess(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelSuccessToCancelSuccessOnRefundSuccess", context,to.getName());

  }
  
  /**
   * 退保成功-退保成功，payment通知退款失败时触发。
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelSuccessToCancelSuccessOnRefundFailure(InsuranceState from, InsuranceState to, InsuranceEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelSuccessToCancelSuccessOnRefundFailure", context,to.getName());

  }
}
