package com.xiaodou.oms.statemachine.instance.trainSelfPay;

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
 * Date: 2015/1/7
 * Time: 11:03
 *
 * @author Tian.Dong
 */
@States(
    {
        @State(name = "Init"),
        @State(name = "InQueue"),
        @State(name = "HoldingSeat"),
        @State(name = "PaySuccess"),
        @State(name = "PayFailure"),
        @State(name = "Closed"),
        @State(name = "Delivering"),
        @State(name = "Pay12306Success"),
        @State(name = "Delivered")
    }
)

@Transitions(
    {
        //1 fromInitToInQueueOnEnterQueueSuccess
        @Transit(from = "Init", to = "InQueue", on = "EnterQueueSuccess", callMethod = "fromInitToInQueueOnEnterQueueSuccess"),

        //2 fromInitToClosedOnEnterQueueFailure
        @Transit(from = "Init", to = "Closed", on = "EnterQueueFailure", callMethod = "fromInitToClosedOnEnterQueueFailure"),

        //3.fromInQueueToHoldingSeatOnHoldingSeatSuccess
        @Transit(from = "InQueue", to = "HoldingSeat", on = "HoldingSeatSuccess", callMethod = "fromInQueueToHoldingSeatOnHoldingSeatSuccess"),

        //4.fromInQueueToClosedOnHoldingSeatFailure
        @Transit(from = "InQueue", to = "Closed", on = "HoldingSeatFailure", callMethod = "fromInQueueToClosedOnHoldingSeatFailure"),

        //5.fromHoldingSeatToPaySuccessOnPaySuccess
        @Transit(from = "HoldingSeat", to = "PaySuccess", on = "PaySuccess", callMethod = "fromHoldingSeatToPaySuccessOnPaySuccess"),

        //6.fromHoldingSeatToPayFailureOnPayFailure
        @Transit(from = "HoldingSeat", to = "PayFailure", on = "PayFailure", callMethod = "fromHoldingSeatToPayFailureOnPayFailure"),

        //7.fromHoldingSeatToClosedOnCancelByUser
        @Transit(from = "HoldingSeat", to = "Closed", on = "CancelByUser", callMethod = "fromHoldingSeatToClosedOnCancelByUser"),

        //8.fromHoldingSeatToClosedOnPay12306Timeout
        @Transit(from = "HoldingSeat", to = "Closed", on = "Pay12306Timeout", callMethod = "fromHoldingSeatToClosedOnPay12306Timeout"),

        //9.fromPaySuccessToDeliveringOnEnterDeliveringQueue
        @Transit(from = "PaySuccess", to = "Delivering", on = "EnterDeliveringQueue", callMethod = "fromPaySuccessToDeliveringOnEnterDeliveringQueue"),

        //10.fromDeliveringToPay12306SuccessOnPay12306Success
        @Transit(from = "Delivering", to = "Pay12306Success", on = "Pay12306Success", callMethod = "fromDeliveringToPay12306SuccessOnPay12306Success"),

        //11.fromPayFailureToPaySuccessOnPaySuccess
        @Transit(from = "PayFailure", to = "PaySuccess", on = "PaySuccess", callMethod = "fromPayFailureToPaySuccessOnPaySuccess"),

        //12.fromPayFailureToClosedOnCancelByUser
        @Transit(from = "PayFailure", to = "Closed", on = "CancelByUser", callMethod = "fromPayFailureToClosedOnCancelByUser"),

        //13.fromPayFailureToClosedOnPay12306Timeout
        @Transit(from = "PayFailure", to = "Closed", on = "Pay12306Timeout", callMethod = "fromPayFailureToClosedOnPay12306Timeout"),

        //14.fromClosedToClosedOnPaySuccess
        @Transit(from = "Closed", to = "Closed", on = "PaySuccess", callMethod = "fromClosedToClosedOnPaySuccess"),

        //15 fromClosedToClosedOnPayFailure
        @Transit(from = "Closed", to = "Closed", on = "PayFailure", callMethod = "fromClosedToClosedOnPayFailure"),

        //16 fromDeliveringToClosedOnPay12306Failure
        @Transit(from = "Delivering", to = "Closed", on = "Pay12306Failure", callMethod = "fromDeliveringToClosedOnPay12306Failure"),

        //17.fromDeliveringToClosedOnPay12306Timeout
        @Transit(from = "Delivering", to = "Closed", on = "Pay12306Timeout", callMethod = "fromDeliveringToClosedOnPay12306Timeout"),

        //18.fromPayFailureToPayFailureOnPayFailure
        @Transit(from = "PayFailure", to = "PayFailure", on = "PayFailure", callMethod = "fromPayFailureToPayFailureOnPayFailure"),

        //19.fromClosedToClosedOnRefundSuccess
        @Transit(from = "Closed", to = "Closed", on = "RefundSuccess", callMethod = "fromClosedToClosedOnRefundSuccess"),

        //20.fromClosedToClosedOnRefundFailure
        @Transit(from = "Closed", to = "Closed", on = "RefundFailure", callMethod = "fromClosedToClosedOnRefundFailure"),

        //21. fromDeliveredToDeliveredOnTicketRefundSuccess
        @Transit(from = "Delivered", to = "Delivered", on = "TicketRefundSuccess", callMethod = "fromDeliveredToDeliveredOnTicketRefundSuccess"),

        //22. fromDeliveredToDeliveredOnTicketRefundFailure
        @Transit(from = "Delivered", to = "Delivered", on = "TicketRefundFailure", callMethod = "fromDeliveredToDeliveredOnTicketRefundFailure"),

        //23. fromDeliveredToDeliveredOnRefundRequest 客服操作线下退票退款
        @Transit(from = "Delivered", to = "Delivered", on = "RefundRequest", callMethod = "fromDeliveredToDeliveredOnRefundRequest"),

        //24. fromDeliveredToDeliveredOnRefundSuccess
        @Transit(from = "Delivered", to = "Delivered", on = "RefundSuccess", callMethod = "fromDeliveredToDeliveredOnRefundSuccess"),

        //25.fromDeliveredToDeliveredOnRefundFailure
        @Transit(from = "Delivered", to = "Delivered", on = "RefundFailure", callMethod = "fromDeliveredToDeliveredOnRefundFailure"),

        //26.fromPay12306SuccessToDeliveredOnDeliveredSuccessBySystem
        @Transit(from = "Pay12306Success", to = "Delivered", on = "DeliveredSuccessBySystem", callMethod = "fromPay12306SuccessToDeliveredOnDeliveredSuccessBySystem"),

        //27.fromPay12306SuccessToDeliveredOnDeliveredSuccessByMan
        @Transit(from = "Pay12306Success", to = "Delivered", on = "DeliveredSuccessByMan", callMethod = "fromPay12306SuccessToDeliveredOnDeliveredSuccessByMan"),

        //28.fromPay12306SuccessToClosedOnDeliveredFailure
        @Transit(from = "Pay12306Success", to = "Closed", on = "DeliveredFailure", callMethod = "fromPay12306SuccessToClosedOnDeliveredFailure"),

        //29.fromInQueueToClosedOnPay12306Timeout
        @Transit(from = "InQueue", to = "Closed", on = "Pay12306Timeout", callMethod = "fromInQueueToClosedOnPay12306Timeout"),

        //30.fromHoldingSeatToClosedOnCancelByFraud
        @Transit(from = "HoldingSeat", to = "Closed", on = "CancelByFraud", callMethod = "fromHoldingSeatToClosedOnCancelByFraud"),

        //31.fromPayFailureToClosedOnCancelByFraud
        @Transit(from = "PayFailure", to = "Closed", on = "CancelByFraud", callMethod = "fromPayFailureToClosedOnCancelByFraud")

    }
)
@StateMachineParamters(stateType = TrainSelfPayState.class, eventType = TrainSelfPayEvent.class, contextType = Context.class)
public class TrainSelfPayStateMachine extends AbstractUntypedStateMachine {

  /**
   * 流执行器
   */
  private static FlowExecutor flowExecutor = SpringWebContextHolder.getBean("flowExecutor");

  protected TrainSelfPayStateMachine(ImmutableUntypedState initialState, Map<Object, ImmutableUntypedState> states) {
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
  public void afterTransitionCausedException(Exception e, Object from, Object to, Object event, Object context) {
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

  public void fromInitToInQueueOnEnterQueueSuccess(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                   Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInitToInQueueOnEnterQueueSuccess", context, to.getName());

  }

  public void fromInitToClosedOnEnterQueueFailure(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                  Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInitToClosedOnEnterQueueFailure", context, to.getName());

  }

  public void fromInQueueToHoldingSeatOnHoldingSeatSuccess(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                           Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInQueueToHoldingSeatOnHoldingSeatSuccess", context, to.getName());

  }

  public void fromInQueueToClosedOnHoldingSeatFailure(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInQueueToClosedOnHoldingSeatFailure", context, to.getName());

  }

  public void fromInQueueToClosedOnPay12306Timeout(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInQueueToClosedOnPay12306Timeout", context, to.getName());

  }

  public void fromHoldingSeatToPaySuccessOnPaySuccess(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromHoldingSeatToPaySuccessOnPaySuccess", context, to.getName());

  }

  public void fromHoldingSeatToPayFailureOnPayFailure(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromHoldingSeatToPayFailureOnPayFailure", context, to.getName());

  }

  public void fromHoldingSeatToClosedOnCancelByUser(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                    Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromHoldingSeatToClosedOnCancelByUser", context, to.getName());

  }

  public void fromHoldingSeatToClosedOnPay12306Timeout(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                       Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromHoldingSeatToClosedOnPay12306Timeout", context, to.getName());

  }

  public void fromPaySuccessToDeliveringOnEnterDeliveringQueue(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                               Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToDeliveringOnEnterDeliveringQueue", context, to.getName());

  }

  public void fromDeliveringToPay12306SuccessOnPay12306Success(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                          Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToPay12306SuccessOnPay12306Success", context, to.getName());

  }

  public void fromPayFailureToPaySuccessOnPaySuccess(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                     Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToPaySuccessOnPaySuccess", context, to.getName());

  }

  public void fromPayFailureToClosedOnCancelByUser(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                   Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToClosedOnCancelByUser", context, to.getName());

  }

  public void fromPayFailureToClosedOnPay12306Timeout(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToClosedOnPay12306Timeout", context, to.getName());

  }

  public void fromPaySuccessToClosedOnCancelByUser(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                   Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToClosedOnCancelByUser", context, to.getName());

  }

  public void fromPaySuccessToClosedOnPay12306Timeout(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToClosedOnPay12306Timeout", context, to.getName());

  }

  public void fromDeliveringToClosedOnPay12306Failure(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                       Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToClosedOnPay12306Failure", context, to.getName());

  }

  public void fromDeliveringToClosedOnPay12306Timeout(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToClosedOnPay12306Timeout", context, to.getName());

  }

  public void fromPayFailureToPayFailureOnPayFailure(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                     Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToPayFailureOnPayFailure", context, to.getName());

  }

  public void fromClosedToClosedOnPaySuccess(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                             Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromClosedToClosedOnPaySuccess", context, to.getName());

  }

  public void fromClosedToClosedOnPayFailure(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                             Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromClosedToClosedOnPayFailure", context, to.getName());

  }

  public void fromClosedToClosedOnRefundSuccess(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromClosedToClosedOnRefundSuccess", context, to.getName());

  }

  public void fromClosedToClosedOnRefundFailure(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromClosedToClosedOnRefundFailure", context, to.getName());

  }


  public void fromDeliveredToDeliveredOnTicketRefundSuccess(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                            Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnTicketRefundSuccess", context, to.getName());

  }

  public void fromDeliveredToDeliveredOnTicketRefundFailure(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                            Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnTicketRefundFailure", context, to.getName());

  }

  public void fromDeliveredToDeliveredOnRefundRequest(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnRefundRequest", context, to.getName());

  }

  public void fromDeliveredToDeliveredOnRefundSuccess(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnRefundSuccess", context, to.getName());

  }

  public void fromDeliveredToDeliveredOnRefundFailure(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToDeliveredOnRefundFailure", context, to.getName());

  }

  public void fromPay12306SuccessToDeliveredOnDeliveredSuccessBySystem(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPay12306SuccessToDeliveredOnDeliveredSuccessBySystem", context, to.getName());

  }

  public void fromPay12306SuccessToDeliveredOnDeliveredSuccessByMan(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                                       Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPay12306SuccessToDeliveredOnDeliveredSuccessByMan", context, to.getName());

  }

  public void fromPay12306SuccessToClosedOnDeliveredFailure(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                                       Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPay12306SuccessToClosedOnDeliveredFailure", context, to.getName());

  }

  public void fromHoldingSeatToClosedOnCancelByFraud(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                            Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromHoldingSeatToClosedOnCancelByFraud", context, to.getName());

  }

  public void fromPayFailureToClosedOnCancelByFraud(TrainSelfPayState from, TrainSelfPayState to, TrainSelfPayEvent event,
                                                            Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToClosedOnCancelByFraud", context, to.getName());

  }
}
