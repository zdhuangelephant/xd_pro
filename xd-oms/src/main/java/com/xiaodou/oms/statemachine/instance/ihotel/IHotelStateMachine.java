package com.xiaodou.oms.statemachine.instance.ihotel;

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
 * @author guanguo.gao
 * @version V1.0
 * @Title:IHotelStateMachine.java
 * @Description:国际酒店业务的状态机
 * @date Oct 24, 2014 15:24:35 PM
 */
@States({
    //新单
    @State(name = "Init"),
    //支付成功
    @State(name = "PaySuccess"), 
    //支付失败
    @State(name = "PayFailure"),
    //取消
    @State(name = "Closed"), 
    //配单成功
    @State(name = "Delivering"),
    //确认成功
    @State(name = "Delivered"),
    //确认失败
    @State(name = "DeliveredFailure"), 
    //确认成功有差价
    @State(name = "WaitingSecondPay"),
    //取消中
    @State(name = "Canceling"),
    //取消成功
    @State(name = "CancelSuccess"),
    //取消失败
    @State(name = "CancelFailure"),
    //入住成功
    @State(name = "CheckedIn"),
    //结账成功
    @State(name = "CheckedOut")
    })
@Transitions({
    //1.支付成功
    @Transit(from = "Init", to = "PaySuccess", on = "PaySuccess", callMethod = 
       "fromInitToPaySuccessOnPaySuccess"),
    //2.支付失败
    @Transit(from = "Init", to = "PayFailure", on = "PayFailure", callMethod = 
       "fromInitToPayFailureOnPayFailure"),
    //3.支付超时
    @Transit(from = "Init", to = "Closed", on = "PayTimeout", callMethod =
        "fromInitToClosedOnPayTimeout"),
    //4.用户取消
    @Transit(from = "Init", to = "Closed", on = "CancelByUser", callMethod =
        "fromInitToClosedOnCancelByUser"),
    //5.支付成功
    @Transit(from = "Closed", to = "Closed", on = "PaySuccess", callMethod =
        "fromClosedToClosedOnPaySuccess"),
    //6.支付失败
    @Transit(from = "Closed", to = "Closed", on = "PayFailure", callMethod =
        "fromClosedToClosedOnPayFailure"),
    //7.退款成功
    @Transit(from = "Closed", to = "Closed", on = "RefundSuccess", callMethod =
        "fromClosedToClosedOnRefundSuccess"),
    //8.退款失败
    @Transit(from = "Closed", to = "Closed", on = "RefundFailure", callMethod =
        "fromClosedToClosedOnRefundFailure"),
    //9.支付超时
    @Transit(from = "PayFailure", to = "Closed", on = "PayTimeout", callMethod =
        "fromPayFailureToClosedOnPayTimeout"),
    //10.用户取消
    @Transit(from = "PayFailure", to = "Closed", on = "CancelByUser", callMethod =
        "fromPayFailureToClosedOnCancelByUser"),
    //11.支付成功
    @Transit(from = "PayFailure", to = "PaySuccess", on = "PaySuccess", callMethod =
        "fromPayFailureToPaySuccessOnPaySuccess"),
    //12.支付失败
    @Transit(from = "PayFailure", to = "PayFailure", on = "PayFailure", callMethod =
        "fromPayFailureToPayFailureOnPayFailure"),
    //13.用户取消
    @Transit(from = "PaySuccess", to = "Delivered", on = "CancelByUser", callMethod =
        "fromPaySuccessToClosedOnCancelByUser"),
    //14.配单成功
    @Transit(from = "PaySuccess", to = "Delivering", on = "DeliveringSuccess", callMethod =
        "fromPaySuccessToDeliveringOnDeliveringSuccess"),
    //15.确认成功
    @Transit(from = "Delivering", to = "Delivered", on = "DeliveredSuccess", callMethod = 
         "fromDeliveringToDeliveredOnDeliveredSuccess"),
    //16.确认成功有差价
    @Transit(from = "Delivering", to = "WaitingSecondPay", on = "WaitingSecondPay", callMethod =
        "fromDeliveringToWaitingSecondPayOnWaitingSecondPay"),
    //17.确认超时
    @Transit(from = "Delivering", to = "DeliveredFailure", on = "DeliveredTimeout", callMethod =
        "fromDeliveringToDeliveredFailureOnDeliveredTimeout"),
    //18.确认失败
    @Transit(from = "Delivering", to = "DeliveredFailure", on = "DeliveredFailure", callMethod =
        "fromDeliveringToDeliveredFailureOnDeliveredFailure"),
    //19.客服确认成功
    @Transit(from = "DeliveredFailure", to = "Delivered", on = "ModifyToSuccess", callMethod =
        "fromDeliveredFailureToDeliveredOnModifyToSuccess"),
    //20.重试请求
    @Transit(from = "DeliveredFailure", to = "Delivering", on = "DeliveringAgain", callMethod =
        "fromDeliveredFailureToDeliveringOnDeliveringAgain"),
    //21.退还差价成功
    @Transit(from = "WaitingSecondPay", to = "Delivered", on = "SecondPaySuccess", callMethod =
        "fromWaitingSecondPayToDeliveredOnSecondPaySuccess"),
    //22.客服确认取消
    @Transit(from = "DeliveredFailure", to = "Closed", on = "CancelingFromFailure", callMethod =
        "fromDeliveredFailureToClosedOnCancelingFromFailure"),
    //23.取消请求
    @Transit(from = "WaitingSecondPay", to = "Canceling", on = "CancelingRequest", callMethod =
        "fromWaitingSecondPayToCancelingOnCancelingRequest"),
    //24.取消请求
    @Transit(from = "Delivered", to = "Canceling", on = "CancelByUser", callMethod =
        "fromDeliveredToCancelingOnCancelByUser"),
    //25.取消成功
    @Transit(from = "Canceling", to = "CancelSuccess", on = "CancelSuccess", callMethod =
        "fromCancelingToCancelSuccessOnCancelSuccess"),
    //26.取消失败
    @Transit(from = "Canceling", to = "CancelFailure", on = "CancelFailure", callMethod =
        "fromCancelingToCancelFailureOnCancelFailure"),
    //27.退款成功
    @Transit(from = "CancelSuccess", to = "CancelSuccess", on = "RefundSuccess", callMethod =
        "fromCancelSuccessToCancelSuccessOnRefundSuccess"),
    //28.退款失败
    @Transit(from = "CancelSuccess", to = "CancelSuccess", on = "RefundFailure", callMethod =
         "fromCancelSuccessToCancelSuccessOnRefundFailure"),
    //29.重新取消请求
    @Transit(from = "CancelFailure", to = "Canceling", on = "CancelingRequestAgain", callMethod =
        "fromCancelFailureToCancelingOnCancelingRequestAgain"),
    //30.人工调整到取消成功
    @Transit(from = "CancelFailure", to = "CancelSuccess", on = "ModifyToCancelSuccess", callMethod =
        "fromCancelFailureToCancelSuccessOnModifyToCancelSuccess"), 
    //31.人工调整到确认成功
    @Transit(from = "CancelFailure", to = "Delivered", on = "ModifyToDelivered", callMethod =
        "fromCancelFailureToDeliveredOnModifyToDelivered"),
    //32.确认成功->入住成功 入住成功
    @Transit(from = "Delivered", to = "CheckedIn", on = "CheckingInSuccess", callMethod =
        "fromDeliveredToCheckedInOnCheckingInSuccess"), 
    //33.已入住->已结账 结账成功
    @Transit(from = "CheckedIn", to = "CheckedOut", on = "CheckingOutSuccess", callMethod =
        "fromCheckedInToCheckedOutOnCheckingOutSuccess"),
   //34. 确认失败->取消中 取消
   @Transit(from = "DeliveredFailure", to = "Canceling", on = "CancelByUser", callMethod =
        "fromDeliveredFailureToCancelingOnCancelByUser"),
   //35. 新单->取消 风控拒绝
   @Transit(from = "Init", to = "Closed", on = "CancelByFraud", callMethod =
        "fromInitToClosedOnCancelByFraud"),
   //36. 新单->新单 非工作时间风控可疑
    @Transit(from = "Init", to = "Init", on = "OutworkDoubtByFraud", callMethod =
        "fromInitToInitOnOutworkDoubtByFraud"),
   //37. 支付失败->取消 风控拒绝
    @Transit(from = "PayFailure", to = "Closed", on = "CancelByFraud", callMethod =
        "fromPayFailureToClosedOnCancelByFraud"),
   //38. 支付失败->支付失败 非工作时间风控可疑
    @Transit(from = "PayFailure", to = "PayFailure", on = "OutworkDoubtByFraud", callMethod =
        "fromPayFailureToPayFailureOnOutworkDoubtByFraud"),
   //39. 确认成功 -> 取消成功 客服强制取消
   @Transit(from = "Delivered", to = "CancelSuccess", on = "ForceCancelByMis", callMethod = 
	"fromDeliveredToCancelSuccessOnForceCancelByMis")
})
@StateMachineParamters(stateType = IHotelState.class, eventType = IHotelEvent.class, contextType = Context.class)
public class IHotelStateMachine extends AbstractUntypedStateMachine {
  /**
   * 流执行器
   */
  private static FlowExecutor flowExecutor = SpringWebContextHolder.getBean("flowExecutor");

  protected IHotelStateMachine(ImmutableUntypedState initialState,
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
  protected void afterTransitionDeclined( Object from, Object event,  Object context) {  
     ErrorsWrapper.getWrapper().setValue(new UnknownTransitionException("状态机找不到对应的Transition,from="+from+",event="+event));                               
  }


  /**
   * 1.新单->支付成功，payment通知支付成功时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToPaySuccessOnPaySuccess(IHotelState from, IHotelState to, IHotelEvent event,
                                               Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInitToPaySuccessOnPaySuccess", context, to.getName());
  }

  /**
   * 2.新单->支付失败，payment通知支付失败时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromInitToPayFailureOnPayFailure(IHotelState from, IHotelState to, IHotelEvent event,
                                               Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInitToPayFailureOnPayFailure", context, to.getName());
  }

  /**
   * 3.新单->支付超时，关单批处理触发（支付超时）。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromInitToClosedOnPayTimeout(IHotelState from, IHotelState to, IHotelEvent event,
                                           Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInitToClosedOnPayTimeout",
        context, to.getName());
  }

  /**
   * 4.新单->取消，用户触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromInitToClosedOnCancelByUser(IHotelState from, IHotelState to, IHotelEvent event,
                                             Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInitToClosedOnCancelByUser",
        context, to.getName());
  }

  /**
   * 5.取消->取消，payment通知支付成功时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromClosedToClosedOnPaySuccess(IHotelState from, IHotelState to, IHotelEvent event,
                                                Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromClosedToClosedOnPaySuccess", context, to.getName());
  }

  /**
   * 6.取消->取消，payment通知支付失败触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromClosedToClosedOnPayFailure(IHotelState from, IHotelState to, IHotelEvent event,
                                                Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromClosedToClosedOnPayFailure", context, to.getName());
  }

  /**
   * 7.取消->取消，payment通知退款成功时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromClosedToClosedOnRefundSuccess(IHotelState from, IHotelState to, IHotelEvent event,
                                             Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromClosedToClosedOnRefundSuccess",
        context, to.getName());
  }

  /**
   * 8.取消->取消，payment通知退款失败时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromClosedToClosedOnRefundFailure(IHotelState from, IHotelState to, IHotelEvent event,
                                             Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromClosedToClosedOnRefundFailure",
        context, to.getName());
  }
  
  /**
   * 9.支付失败->取消，关单批处理触发（支付超时）。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPayFailureToClosedOnPayTimeout(IHotelState from, IHotelState to, IHotelEvent event, 
                                               Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToClosedOnPayTimeout", context, to.getName());
  }
  
  /**
   * 10.支付失败->取消，用户触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPayFailureToClosedOnCancelByUser(IHotelState from, IHotelState to, IHotelEvent
                                              event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToClosedOnCancelByUser", context, to.getName());
  }

  /**
   * 11.支付失败->支付成功，payment通知支付成功时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPayFailureToPaySuccessOnPaySuccess(IHotelState from, IHotelState to, IHotelEvent
                                              event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToPaySuccessOnPaySuccess", context, to.getName());
  }

  /**
   * 12.支付失败->支付失败，payment通知支付失败时触发。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPayFailureToPayFailureOnPayFailure(IHotelState from, IHotelState to, IHotelEvent
                                              event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPayFailureToPayFailureOnPayFailure", context, to.getName());
  }
  
  /**
   * 13. 支付成功 -> 取消， 用户取消
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPaySuccessToClosedOnCancelByUser(IHotelState from,IHotelState to, 
                                              IHotelEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToClosedOnCancelByUser", context, to.getName());
  }
  
  /**
   * 14. 支付成功 -> 确认中, 配单成功
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromPaySuccessToDeliveringOnDeliveringSuccess(IHotelState from,IHotelState to, 
                                                      IHotelEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromPaySuccessToDeliveringOnDeliveringSuccess", context, to.getName());
  }

  /**
   * 15.确认中->确认成功，确认成功
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveringToDeliveredOnDeliveredSuccess(IHotelState from, IHotelState to,
                                                    IHotelEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToDeliveredOnDeliveredSuccess", context, to.getName());
  }
  
  /**
   * 16. 确认中 -> 确认成功有差价, 确认成功有差价
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveringToWaitingSecondPayOnWaitingSecondPay(IHotelState from, IHotelState
      to, IHotelEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToWaitingSecondPayOnWaitingSecondPay", context, to.getName());
  }

  /**
   * 17.确认中->确认失败，确认超时。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveringToDeliveredFailureOnDeliveredTimeout(IHotelState from, IHotelState to, IHotelEvent
                                                          event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToDeliveredFailureOnDeliveredTimeout", context, to.getName());
  }

  /**
   * 18.确认中->确认失败，订单供应商确认失败。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveringToDeliveredFailureOnDeliveredFailure(IHotelState from, IHotelState to,
                                                        IHotelEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveringToDeliveredFailureOnDeliveredFailure", context, to.getName());
  }

 
  /**
   * 19. 确认失败 -> 确认成功, 客服修改成功
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveredFailureToDeliveredOnModifyToSuccess(IHotelState from, IHotelState to, IHotelEvent
                                                  event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredFailureToDeliveredOnModifyToSuccess", context, to.getName());
  }
  
  /**
   * 20. 确认失败 -> 确认中, 再次重试请求
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveredFailureToDeliveringOnDeliveringAgain(IHotelState from,IHotelState to, IHotelEvent event, 
                                                  Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredFailureToDeliveringOnDeliveringAgain", context, to.getName());
  }

  /**
   * 21. 确认成功有差价 -> 确认成功, 退还差价成功
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromWaitingSecondPayToDeliveredOnSecondPaySuccess(IHotelState from, IHotelState to, IHotelEvent event, 
                                                  Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromWaitingSecondPayToDeliveredOnSecondPaySuccess", context, to.getName());
  }
  
  /**
   * 22. 确认失败 -> 取消, 客服确认取消
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveredFailureToClosedOnCancelingFromFailure(IHotelState from, IHotelState to, IHotelEvent event, 
                                                      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredFailureToClosedOnCancelingFromFailure", context, to.getName());
  }
  
  /**
   * 23.确认成功有差价->取消中，取消请求。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromWaitingSecondPayToCancelingOnCancelingRequest(IHotelState from, IHotelState to, IHotelEvent event, 
                                                  Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromWaitingSecondPayToCancelingOnCancelingRequest", context, to.getName());
  }
  
  /**
   * 24.确认成功->确认中，取消请求。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromDeliveredToCancelingOnCancelByUser(IHotelState from, IHotelState to, IHotelEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToCancelingOnCancelByUser", context, to.getName());
  }
  
  /**
   * 25. 取消中->取消成功，取消成功。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */

  public void fromCancelingToCancelSuccessOnCancelSuccess(IHotelState from, IHotelState to,
                                                          IHotelEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelingToCancelSuccessOnCancelSuccess", context, to.getName());
  }
  
  /**
   * 26. 取消中  -> 取消失败，取消失败。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelingToCancelFailureOnCancelFailure(IHotelState from, IHotelState to, IHotelEvent
                                                  event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelingToCancelFailureOnCancelFailure", context, to.getName());
  }
  
  /**
   * 27. 取消成功 -> 取消成功，退款成功。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelSuccessToCancelSuccessOnRefundSuccess(IHotelState from, IHotelState to, IHotelEvent
                                                  event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelSuccessToCancelSuccessOnRefundSuccess", context, to.getName());
  }
  
  /**
   * 28. 取消成功  -> 取消成功，退款失败。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelSuccessToCancelSuccessOnRefundFailure(IHotelState from, IHotelState to, IHotelEvent
                                                  event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelSuccessToCancelSuccessOnRefundFailure", context, to.getName());
  }
  
  /**
   * 29. 取消失败  -> 取消中，再次取消请求。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelFailureToCancelingOnCancelingRequestAgain(IHotelState from, IHotelState to, IHotelEvent
                                                  event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelFailureToCancelingOnCancelingRequestAgain", context, to.getName());
  }
  
  /**
   * 30. 取消失败  -> 取消成功，人工调整到取消成功。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelFailureToCancelSuccessOnModifyToCancelSuccess(IHotelState from, IHotelState to, IHotelEvent
                                                  event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelFailureToCancelSuccessOnModifyToCancelSuccess", context, to.getName());
  }
  
  /**
   * 31. 取消失败  -> 确认成功，人工调整成确认成功。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCancelFailureToDeliveredOnModifyToDelivered(IHotelState from, IHotelState to, IHotelEvent
                                                  event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCancelFailureToDeliveredOnModifyToDelivered", context, to.getName());
  }

  
  /**
   * 32. 确认成功  -> 已入住，入住成功
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredToCheckedInOnCheckingInSuccess(IHotelState from, IHotelState to, IHotelEvent
                                                  event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredToCheckedInOnCheckingInSuccess", context, to.getName());
  }
  
  /**
   * 33. 已入住  -> 已结账，结账成功。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromCheckedInToCheckedOutOnCheckingOutSuccess(IHotelState from, IHotelState to, IHotelEvent
                                                  event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromCheckedInToCheckedOutOnCheckingOutSuccess", context, to.getName());
  }
  
  /**
   * 33. 已入住  -> 已结账，结账成功。
   *
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredFailureToCancelingOnCancelByUser(IHotelState from, IHotelState to, 
	  IHotelEvent event, Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromDeliveredFailureToCancelingOnCancelByUser", context, to.getName());
  }
  
  /**
   * 35. 新单->取消，风控拒绝触发。
   * 
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToClosedOnCancelByFraud(IHotelState from, IHotelState to, IHotelEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInitToClosedOnCancelByFraud", context, to.getName());

  }
  
  /**
   * 36. 新单->新单，非工作时间风控可疑。
   * 
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToInitOnOutworkDoubtByFraud(IHotelState from, IHotelState to, IHotelEvent event,
      Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),
        "fromInitToInitOnOutworkDoubtByFraud", context, to.getName());

  }
  
  /**
   * 37. 支付失败->取消，风控拒绝触发。
   * 
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPayFailureToClosedOnCancelByFraud(IHotelState from, IHotelState to,
          IHotelEvent event, Context context) throws Exception {
      flowExecutor.doFlow(context.getCoreParams().getProductLine(),
            "fromPayFailureToClosedOnCancelByFraud", context, to.getName());

  }
  
  /**
   * 38. 支付失败->支付失败，非工作时间风控可疑
   * 
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPayFailureToPayFailureOnOutworkDoubtByFraud(IHotelState from, IHotelState to,
          IHotelEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(),
            "fromPayFailureToPayFailureOnOutworkDoubtByFraud", context, to.getName());
      }
  
  /**
   * 39. 确认成功 -> 取消成功，客服强制取消
   * 
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromDeliveredToCancelSuccessOnForceCancelByMis(IHotelState from, IHotelState to,
          IHotelEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(),
            "fromDeliveredToCancelSuccessOnForceCancelByMis", context, to.getName());
      }


}
