package com.xiaodou.oms.statemachine.instance.train12306;

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
 * @author zhaoyang
 * @version V1.0
 * @Title:TrainStateMachine.java
 * @Description:火车票业务的状态机
 * @date June 16, 2014 3:24:35 PM
 */

@States(
  {
    @State(name = "Init"),
    @State(name = "Closed"),
    @State(name = "HoldingSeat"),
    @State(name = "Complete"),
    @State(name = "InQueue")
  }
)

@Transitions(
  {
    @Transit(from = "Init", to = "InQueue", on = "EnterQueueSuccess", callMethod = "fromInitToInQueueOnEnterQueueSuccess"),
    @Transit(from = "Init", to = "Closed", on = "EnterQueueFailure", callMethod = "fromInitToClosedOnEnterQueueFailure"),
    @Transit(from = "InQueue", to = "Closed", on = "HoldingSeatFailure", callMethod = "fromInQueueToClosedOnHoldingSeatFailure"),
    @Transit(from = "InQueue", to = "HoldingSeat", on = "HoldingSeatSuccess", callMethod = "fromInQueueToHoldingSeatOnHoldingSeatSuccess"),
    @Transit(from = "HoldingSeat", to = "Closed", on = "SynWithPayTimeout", callMethod = "fromHoldingSeatToClosedOnSynWithPayTimeout"),
    @Transit(from = "HoldingSeat", to = "Complete", on = "SynWithComplete", callMethod = "fromHoldingSeatToCompleteOnSynWithComplete"),
    @Transit(from = "HoldingSeat", to = "Closed", on = "CancelByUser", callMethod = "fromHoldingSeatToClosedOnCancelByUser"),
    @Transit(from = "Complete", to = "Complete", on = "RefundTicket", callMethod = "fromCompleteToCompleteOnRefundTicket"),
    @Transit(from = "Init" , to = "HoldingSeat" , on = "HoldingSeatSuccessFromInit", callMethod = "fromInitToHoldingSeatOnHoldingSeatSuccessFromInit")
  }
)

@StateMachineParamters(stateType = Train12306State.class, eventType = Train12306Event.class, contextType = Context.class)
public class Train12306StateMachine extends AbstractUntypedStateMachine {

  //执行器
  private static FlowExecutor flowExecutor = SpringWebContextHolder.getBean("flowExecutor");

  //初始化
  protected Train12306StateMachine(ImmutableUntypedState initialState,Map<Object, ImmutableUntypedState> states) {
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
  public void afterTransitionCausedException(Exception e, Object from, Object to, Object event,Object context) {
    TransitionException e1 = (TransitionException) e;
    String orderId = ((Context) context).getCoreParams().getOrderId();
    LoggerUtil.error("statemachine from " + from + " to " + to + " on " + event + " failure orderId=" + orderId, e1.getTargetException());
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
    OrderLoggerUtil.orderInfo("statemachine from " + from + " to " + to + " on " + event + " completed orderId=" + orderId);
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
    ErrorsWrapper.getWrapper().setValue(new UnknownTransitionException("状态机找不到对应的Transition,from=" + from + ",event=" + event));
  }

  /**
   * 进入队列成功
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToInQueueOnEnterQueueSuccess(Train12306State from, Train12306State to, Train12306Event event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromInitToInQueueOnEnterQueueSuccess", context, to.getName());
  }

  /**
   * 重初始状态到占座成功
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToHoldingSeatOnHoldingSeatSuccessFromInit(Train12306State from, Train12306State to, Train12306Event event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromInitToHoldingSeatOnHoldingSeatSuccessFromInit", context, to.getName());
  }

  /**
   * 进入队列失败
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToClosedOnEnterQueueFailure(Train12306State from, Train12306State to, Train12306Event event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromInitToClosedOnEnterQueueFailure", context, to.getName());
  }

  /**
   * 占座失败
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInQueueToClosedOnHoldingSeatFailure(Train12306State from, Train12306State to, Train12306Event event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromInQueueToClosedOnHoldingSeatFailure", context, to.getName());
  }

  /**
   * 占座成功
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInQueueToHoldingSeatOnHoldingSeatSuccess(Train12306State from, Train12306State to, Train12306Event event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromInQueueToHoldingSeatOnHoldingSeatSuccess", context, to.getName());
  }

  /**
   * 状态同步未支付关单
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldingSeatToClosedOnSynWithPayTimeout(Train12306State from, Train12306State to, Train12306Event event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromHoldingSeatToClosedOnSynWithPayTimeout", context, to.getName());
  }

  /**
   * 状态同步支付完成
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldingSeatToCompleteOnSynWithComplete(Train12306State from, Train12306State to, Train12306Event event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromHoldingSeatToCompleteOnSynWithComplete", context, to.getName());
  }

  /**
   * 用户取消订单
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldingSeatToClosedOnCancelByUser(Train12306State from, Train12306State to, Train12306Event event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromHoldingSeatToClosedOnCancelByUser", context, to.getName());
  }

  /**
   * 用户退票
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCompleteToCompleteOnRefundTicket(Train12306State from, Train12306State to, Train12306Event event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromCompleteToCompleteOnRefundTicket", context, to.getName());
  }


}
