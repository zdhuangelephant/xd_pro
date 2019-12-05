package com.xiaodou.oms.statemachine.instance.train;

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
@States({@State(name = "Init"),
    @State(name = "InQueue"),
    @State(name = "HoldingSeat"),
    @State(name = "PaySuccess"),
    @State(name = "PayFailure"),
    @State(name = "Closed"),
    @State(name = "Delivering"),
    @State(name = "Delivered"),
    @State(name = "WaitingSecondPay"),})
@Transitions({
    // 新单
    @Transit(from = "Init", to = "PaySuccess", on = "PaySuccess", callMethod = "fromInitToPaySuccessOnPaySuccess"),
    @Transit(from = "Init", to = "PayFailure", on = "PayFailure", callMethod = "fromInitToPayFailureOnPayFailure"),
    @Transit(from = "Init", to = "Closed", on = "PayTimeout", callMethod = "fromInitToClosedOnPayTimeout"),
    @Transit(from = "Init", to = "Closed", on = "CancelByUser", callMethod = "fromInitToClosedOnCancelByUser"),
    @Transit(from = "Init", to = "Closed", on = "CancelByFraud", callMethod = "fromInitToClosedOnCancelByFraud"),
    // 取消
    @Transit(from = "Closed", to = "Closed", on = "RefundSuccess", callMethod = "fromClosedToClosedOnRefundSuccess"),
    @Transit(from = "Closed", to = "Closed", on = "RefundFailure", callMethod = "fromClosedToClosedOnRefundFailure"),
    @Transit(from = "Closed", to = "Closed", on = "PaySuccess", callMethod = "fromClosedToClosedOnPaySuccess"),
    @Transit(from = "Closed", to = "Closed", on = "PayFailure", callMethod = "fromClosedToClosedOnPayFailure"),
    @Transit(from = "Closed", to = "Closed", on = "EntryQueueSuccess", callMethod = "fromClosedToClosedOnEntryQueueSuccess"),
    @Transit(from = "Closed", to = "Closed", on = "HoldingSeatSuccess", callMethod = "fromClosedToClosedOnHoldingSeatSuccess"),
    //占座中
    @Transit(from = "InQueue", to = "Closed", on = "EntryQueueFailure", callMethod = "fromInQueueToClosedOnEntryQueueFailure"),
    @Transit(from = "InQueue", to = "Closed", on = "HoldingSeatFailure", callMethod = "fromInQueueToClosedOnHoldingSeatFailure"),
    @Transit(from = "InQueue", to = "HoldingSeat", on = "HoldingSeatSuccess", callMethod = "fromInQueueToHoldingSeatOnHoldingSeatSuccess"),
    //占座成功
    @Transit(from = "HoldingSeat", to = "Closed", on = "PayTimeout", callMethod = "fromHoldingSeatToClosedOnPayTimeout"),
    @Transit(from = "HoldingSeat", to = "Closed", on = "CancelByUser", callMethod = "fromHoldingSeatToClosedOnCancelByUser"),
    @Transit(from = "HoldingSeat", to = "Closed", on = "CancelByFraud", callMethod = "fromHoldingSeatToClosedOnCancelByFraud"),
    @Transit(from = "HoldingSeat", to = "PaySuccess", on = "PaySuccess", callMethod = "fromHoldingSeatToPaySuccessOnPaySuccess"),
    @Transit(from = "HoldingSeat", to = "PayFailure", on = "PayFailure", callMethod = "fromHoldingSeatToPayFailureOnPayFailure"),

    // 支付失败
    @Transit(from = "PayFailure", to = "PaySuccess", on = "PaySuccess", callMethod = "fromPayFailureToPaySuccessOnPaySuccess"),
    @Transit(from = "PayFailure", to = "Closed", on = "PayTimeout", callMethod = "fromPayFailureToClosedOnPayTimeout"),
    @Transit(from = "PayFailure", to = "Closed", on = "CancelByUser", callMethod = "fromPayFailureToClosedOnCancelByUser"),
    @Transit(from = "PayFailure", to = "PayFailure", on = "PayFailure", callMethod = "fromPayFailureToPayFailureOnPayFailure"),
    @Transit(from = "PayFailure", to = "Closed", on = "CancelByFraud", callMethod = "fromPayFailureToClosedOnCancelByFraud"),
    // 支付成功
    @Transit(from = "PaySuccess", to = "Delivering", on = "DeliveringSuccess", callMethod = "fromPaySuccessToDeliveringOnDeliveringSuccess"),
    @Transit(from = "PaySuccess", to = "Closed", on = "DeliveredTimeout", callMethod = "fromPaySuccessToClosedOnDeliveredTimeout"),
    @Transit(from = "PaySuccess", to = "Closed", on = "CancelByUser", callMethod = "fromPaySuccessToClosedOnCancelByUser"),
    // 出票中
    @Transit(from = "Delivering", to = "Delivered", on = "DeliveredSuccess", callMethod = "fromDeliveringToDeliveredOnDeliveredSuccess"),
    @Transit(from = "Delivering", to = "WaitingSecondPay", on = "NeedSencondPay", callMethod = "fromDeliveringToWaitingSecondPayOnWaitingSecondPay"),
    @Transit(from = "Delivering", to = "Closed", on = "DeliveredFailure", callMethod = "fromDeliveringToClosedOnDeliveredFailure"),
    @Transit(from = "Delivering", to = "Closed", on = "DeliveredTimeout", callMethod = "fromDeliveringToClosedOnDeliveredTimeout"),
    @Transit(from = "Delivering", to = "PaySuccess", on = "OrderReturn", callMethod = "fromDeliveringToPaySuccessOnOrderReturn"),
    // 待退差价
    @Transit(from = "WaitingSecondPay", to = "Delivered", on = "SecondPaySuccess", callMethod = "fromWaitingSecondPayToDeliveredOnSecondPaySuccess"),
    @Transit(from = "WaitingSecondPay", to = "WaitingSecondPay", on = "TicketRefundRequest", callMethod = "fromWaitingSecondPayToWaitingSecondPayOnTicketRefundRequest"),
    @Transit(from = "WaitingSecondPay", to = "WaitingSecondPay", on = "TicketRefundSuccess", callMethod = "fromWaitingSecondPayToWaitingSecondPayOnTicketRefundSuccess"),
    @Transit(from = "WaitingSecondPay", to = "WaitingSecondPay", on = "TicketRefundFailure", callMethod = "fromWaitingSecondPayToWaitingSecondPayOnTicketRefundFailure"),
    @Transit(from = "WaitingSecondPay", to = "WaitingSecondPay", on = "RefundRequest", callMethod = "fromWaitingSecondPayToWaitingSecondPayOnRefundRequest"),
    @Transit(from = "WaitingSecondPay", to = "WaitingSecondPay", on = "RefundSuccess", callMethod = "fromWaitingSecondPayToWaitingSecondPayOnRefundSuccess"),
    @Transit(from = "WaitingSecondPay", to = "WaitingSecondPay", on = "RefundFailure", callMethod = "fromWaitingSecondPayToWaitingSecondPayOnRefundFailure"),
    // 出票成功
    @Transit(from = "Delivered", to = "Delivered", on = "TicketRefundRequest", callMethod = "fromDeliveredToDeliveredOnTicketRefundRequest"),
    @Transit(from = "Delivered", to = "Delivered", on = "TicketRefundSuccess", callMethod = "fromDeliveredToDeliveredOnTicketRefundSuccess"),
    @Transit(from = "Delivered", to = "Delivered", on = "TicketRefundFailure", callMethod = "fromDeliveredToDeliveredOnTicketRefundFailure"),
    @Transit(from = "Delivered", to = "Delivered", on = "RefundRequest", callMethod = "fromDeliveredToDeliveredOnRefundRequest"),
    @Transit(from = "Delivered", to = "Delivered", on = "RefundSuccess", callMethod = "fromDeliveredToDeliveredOnRefundSuccess"),
    @Transit(from = "Delivered", to = "Delivered", on = "RefundFailure", callMethod = "fromDeliveredToDeliveredOnRefundFailure"),

})
@StateMachineParamters(stateType = TrainState.class, eventType = TrainEvent.class, contextType = Context.class)
public class TrainStateMachine extends AbstractUntypedStateMachine {
  /**
   * 流执行器
   */
  private static FlowExecutor flowExecutor = SpringWebContextHolder.getBean("flowExecutor");

  protected TrainStateMachine(ImmutableUntypedState initialState,
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
   * 新单->支付成功，payment通知支付成功时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToPaySuccessOnPaySuccess(TrainState from, TrainState to, TrainEvent event,
                                               Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInitToPaySuccessOnPaySuccess", context, to.getName());

  }

  /**
   * 新单->支付失败，payment通知支付失败时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromInitToPayFailureOnPayFailure(TrainState from, TrainState to, TrainEvent event,
                                               Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInitToPayFailureOnPayFailure", context, to.getName());

  }

  /**
   * 新单->支付超时，关单批处理触发（支付超时）。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromInitToClosedOnPayTimeout(TrainState from, TrainState to, TrainEvent event,
                                           Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInitToClosedOnPayTimeout",
        context, to.getName());

  }

  /**
   * 新单->取消，用户触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromInitToClosedOnCancelByUser(TrainState from, TrainState to, TrainEvent event,
                                             Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInitToClosedOnCancelByUser",
        context, to.getName());

  }

  /**
   * 新单->取消，红包冻结失败触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromInitToClosedOnCancelByFrozenFail(TrainState from, TrainState to, TrainEvent event,
                                                   Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInitToClosedOnCancelByFrozenFail",
        context, to.getName());

  }

  /**
   * 新单->取消，风控拒绝触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToClosedOnCancelByFraud(TrainState from, TrainState to, TrainEvent event,
                                              Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInitToClosedOnCancelByFraud", context, to.getName());

  }

  /**
   * 取消->取消，payment通知退款成功时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromClosedToClosedOnRefundSuccess(TrainState from, TrainState to, TrainEvent event,
                                                Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromClosedToClosedOnRefundSuccess", context, to.getName());

  }

  /**
   * 取消->取消，payment通知退款失败时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromClosedToClosedOnRefundFailure(TrainState from, TrainState to, TrainEvent event,
                                                Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromClosedToClosedOnRefundFailure", context, to.getName());

  }

  /**
   * 取消->取消，payment通知支付成功时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromClosedToClosedOnPaySuccess(TrainState from, TrainState to, TrainEvent event,
                                             Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromClosedToClosedOnPaySuccess",
        context, to.getName());

  }

  /**
   * 取消->取消，payment通知支付失败时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromClosedToClosedOnPayFailure(TrainState from, TrainState to, TrainEvent event,
                                             Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromClosedToClosedOnPayFailure",
        context, to.getName());

  }


  /**
   * 取消->取消 进入占座队列成功
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromClosedToClosedOnEntryQueueSuccess(TrainState from, TrainState to, TrainEvent event,
                                                    Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromClosedToClosedOnEntryQueueSuccess",
        context, to.getName());

  }

  /**
   * 取消->取消 占座成功
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromClosedToClosedOnHoldingSeatSuccess(TrainState from, TrainState to, TrainEvent event,
                                                     Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromClosedToClosedOnHoldingSeatSuccess",
        context, to.getName());

  }

  /**
   * 占座中->取消 进入占座队列失败
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInQueueToClosedOnEntryQueueFailure(TrainState from, TrainState to, TrainEvent event,
                                                     Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInQueueToClosedOnEntryQueueFailure",
        context, to.getName());

  }

  /**
   * 占座中->取消 占座失败
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInQueueToClosedOnHoldingSeatFailure(TrainState from, TrainState to, TrainEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInQueueToClosedOnHoldingSeatFailure",
        context, to.getName());

  }

  /**
   * 占座中->占座成功 占座成功
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInQueueToHoldingSeatOnHoldingSeatSuccess(TrainState from, TrainState to, TrainEvent event,
                                                           Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInQueueToHoldingSeatOnHoldingSeatSuccess",
        context, to.getName());

  }

  /**
   * 占座成功->取消 支付超时
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldingSeatToClosedOnPayTimeout(TrainState from, TrainState to, TrainEvent event,
                                                  Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromHoldingSeatToClosedOnPayTimeout",
        context, to.getName());

  }

  /**
   * 占座成功->取消 用户取消
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldingSeatToClosedOnCancelByUser(TrainState from, TrainState to, TrainEvent event,
                                                    Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromHoldingSeatToClosedOnCancelByUser",
        context, to.getName());

  }

  /**
   * 占座成功->取消 风控拒绝
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldingSeatToClosedOnCancelByFraud(TrainState from, TrainState to, TrainEvent event,
                                                     Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromHoldingSeatToClosedOnCancelByFraud",
        context, to.getName());

  }

  /**
   * 占座成功->支付成功 支付成功
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldingSeatToPaySuccessOnPaySuccess(TrainState from, TrainState to, TrainEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromHoldingSeatToPaySuccessOnPaySuccess",
        context, to.getName());

  }

  /**
   * 占座成功->支付失败 支付失败
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldingSeatToPayFailureOnPayFailure(TrainState from, TrainState to, TrainEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromHoldingSeatToPayFailureOnPayFailure",
        context, to.getName());

  }


  /**
   * 支付失败->支付成功，payment通知支付成功时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPayFailureToPaySuccessOnPaySuccess(TrainState from, TrainState to,
                                                     TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToPaySuccessOnPaySuccess", context, to.getName());

  }

  /**
   * 支付失败->取消，关单批处理触发（支付超时）。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPayFailureToClosedOnPayTimeout(TrainState from, TrainState to, TrainEvent event,
                                                 Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToClosedOnPayTimeout", context, to.getName());

  }

  /**
   * 支付失败->取消，用户触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPayFailureToClosedOnCancelByUser(TrainState from, TrainState to,
                                                   TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToClosedOnCancelByUser", context, to.getName());

  }


  public void fromPayFailureToClosedOnCancelByFraud(TrainState from, TrainState to,
                                                    TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToClosedOnCancelByFraud", context, to.getName());

  }


  /**
   * 支付失败->支付失败，payment通知支付失败时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPayFailureToPayFailureOnPayFailure(TrainState from, TrainState to,
                                                     TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToPayFailureOnPayFailure", context, to.getName());

  }

  /**
   * 支付成功->出票中，订单成功分配到供应商后触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPaySuccessToDeliveringOnDeliveringSuccess(TrainState from, TrainState to,
                                                            TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToDeliveringOnDeliveringSuccess", context, to.getName());

  }

  /**
   * 支付成功->取消，用户触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPaySuccessToClosedOnCancelByUser(TrainState from, TrainState to,
                                                   TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToClosedOnCancelByUser", context, to.getName());

  }

  /**
   * 支付成功->取消，关单批处理触发（配单超时）。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPaySuccessToClosedOnDeliveredTimeout(TrainState from, TrainState to,
                                                       TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToClosedOnDeliveredTimeout", context, to.getName());

  }

  /**
   * 出票中->出票成功，出票成功时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveringToDeliveredOnDeliveredSuccess(TrainState from, TrainState to,
                                                          TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToDeliveredOnDeliveredSuccess", context, to.getName());

  }

  /**
   * 出票中->出票成功待退差价，出票成功待退差价时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveringToWaitingSecondPayOnWaitingSecondPay(TrainState from, TrainState to,
                                                                 TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToWaitingSecondPayOnWaitingSecondPay", context, to.getName());

  }

  /**
   * 出票中->取消，出票失败时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveringToClosedOnDeliveredFailure(TrainState from, TrainState to,
                                                       TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToClosedOnDeliveredFailure", context, to.getName());

  }

  /**
   * 出票中->取消，关单批处理触发（出票超时）。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveringToClosedOnDeliveredTimeout(TrainState from, TrainState to,
                                                       TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToClosedOnDeliveredTimeout", context, to.getName());

  }

  /**
   * 出票中->支付成功，订单返库触发
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveringToPaySuccessOnOrderReturn(TrainState from, TrainState to,
                                                      TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToPaySuccessOnOrderReturn", context, to.getName());

  }

  /**
   * 出票成功待退差价->出票成功，payment通知退款成功时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromWaitingSecondPayToDeliveredOnSecondPaySuccess(TrainState from, TrainState to,
                                                                TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromWaitingSecondPayToDeliveredOnSecondPaySuccess", context, to.getName());

  }

  /**
   * 出票成功待退差价->出票成功待退差价，用户发起的退票请求触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromWaitingSecondPayToWaitingSecondPayOnTicketRefundRequest(TrainState from,
                                                                          TrainState to, TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromWaitingSecondPayToWaitingSecondPayOnTicketRefundRequest", context, to.getName());

  }

  /**
   * 出票成功待退差价->出票成功待退差价，退票成功触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromWaitingSecondPayToWaitingSecondPayOnTicketRefundSuccess(TrainState from,
                                                                          TrainState to, TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromWaitingSecondPayToWaitingSecondPayOnTicketRefundSuccess", context, to.getName());

  }

  /**
   * 出票成功待退差价->出票成功待退差价，退票失败触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromWaitingSecondPayToWaitingSecondPayOnTicketRefundFailure(TrainState from,
                                                                          TrainState to, TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromWaitingSecondPayToWaitingSecondPayOnTicketRefundFailure", context, to.getName());

  }

  /**
   * 出票成功待退差价->出票成功待退差价，退款请求触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromWaitingSecondPayToWaitingSecondPayOnRefundRequest(TrainState from, TrainState to,
                                                                    TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromWaitingSecondPayToWaitingSecondPayOnRefundRequest", context, to.getName());

  }

  /**
   * 出票成功待退差价->出票成功待退差价，退款成功触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromWaitingSecondPayToWaitingSecondPayOnRefundSuccess(TrainState from, TrainState to,
                                                                    TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromWaitingSecondPayToWaitingSecondPayOnRefundSuccess", context, to.getName());

  }

  /**
   * 出票成功待退差价->出票成功待退差价，退款失败触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromWaitingSecondPayToWaitingSecondPayOnRefundFailure(TrainState from, TrainState to,
                                                                    TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromWaitingSecondPayToWaitingSecondPayOnRefundFailure", context, to.getName());

  }

  /**
   * 出票成功->出票成功，用户发起的退票请求触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveredToDeliveredOnTicketRefundRequest(TrainState from, TrainState to,
                                                            TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnTicketRefundRequest", context, to.getName());

  }

  /**
   * 出票成功->出票成功，退票成功触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveredToDeliveredOnTicketRefundSuccess(TrainState from, TrainState to,
                                                            TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnTicketRefundSuccess", context, to.getName());

  }

  /**
   * 出票成功->出票成功，退票失败触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredToDeliveredOnTicketRefundFailure(TrainState from, TrainState to,
                                                            TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnTicketRefundFailure", context, to.getName());

  }

  /**
   * 出票成功->出票成功，退款请求触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */


  public void fromDeliveredToDeliveredOnRefundRequest(TrainState from, TrainState to,
                                                      TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnRefundRequest", context, to.getName());

  }

  /**
   * 出票成功->出票成功，退款成功触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveredToDeliveredOnRefundSuccess(TrainState from, TrainState to,
                                                      TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnRefundSuccess", context, to.getName());

  }

  /**
   * 出票成功->出票成功，退款失败触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveredToDeliveredOnRefundFailure(TrainState from, TrainState to,
                                                      TrainEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnRefundFailure", context, to.getName());

  }

}
