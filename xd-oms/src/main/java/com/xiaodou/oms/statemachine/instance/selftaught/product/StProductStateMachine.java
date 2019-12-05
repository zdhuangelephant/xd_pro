package com.xiaodou.oms.statemachine.instance.selftaught.product;

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

@States({@State(name = "Init"), @State(name = "PaySuccess"), @State(name = "Closed"),
    @State(name = "Delivered"),})
@Transitions({
    // 新单
    @Transit(from = "Init", to = "PaySuccess", on = "PaySuccess", callMethod = "fromInitToPaySuccessOnPaySuccess"),
    @Transit(from = "Init", to = "Closed", on = "PayFailure", callMethod = "fromInitToClosedOnPayFailure"),
    @Transit(from = "Init", to = "Closed", on = "PayTimeout", callMethod = "fromInitToClosedOnPayTimeout"),
    // 支付成功
    @Transit(from = "PaySuccess", to = "Delivered", on = "DeliveredSuccess", callMethod = "fromPaySuccessToDeliveredOnDeliveredSuccess"),})
@StateMachineParamters(stateType = StProductState.class, eventType = StProductEvent.class, contextType = Context.class)
public class StProductStateMachine extends AbstractUntypedStateMachine {
  /**
   * 流执行器
   */
  private static FlowExecutor flowExecutor = SpringWebContextHolder.getBean("flowExecutor");

  protected StProductStateMachine(ImmutableUntypedState initialState,
      Map<Object, ImmutableUntypedState> states) {
    super(initialState, states);
  }

  /**
   * 统一的异常捕获。
   * 
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
   * 
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
  protected void afterTransitionDeclined(Object from, Object event, Object context) {
    ErrorsWrapper.getWrapper().setValue(
        new UnknownTransitionException("状态机找不到对应的Transition,from=" + from + ",event=" + event));

  }

  /**
   * 新单->支付成功 支付成功
   * 
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToPaySuccessOnPaySuccess(StProductState from, StProductState to, StProductEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInitToPaySuccessOnPaySuccess", context, to.getName());

  }

  /**
   * 新单->关闭 支付失败
   * 
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToClosedOnPayFailure(StProductState from, StProductState to, StProductEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInitToClosedOnPayFailure",
        context, to.getName());

  }

  /**
   * 新单->关闭 支付超时
   * 
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToClosedOnPayTimeout(StProductState from, StProductState to, StProductEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInitToClosedOnPayTimeout",
        context, to.getName());

  }

  /**
   * 支付成功->购买成功 购买成功
   * 
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPaySuccessToDeliveredOnDeliveredSuccess(StProductState from, StProductState to,
      StProductEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToDeliveredOnDeliveredSuccess", context, to.getName());

  }

}
