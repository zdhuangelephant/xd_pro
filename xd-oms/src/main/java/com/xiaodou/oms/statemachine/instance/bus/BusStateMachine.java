package com.xiaodou.oms.statemachine.instance.bus;

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
 * 汽车票状态机配置(二期支付落地)
 * @author bing.cheng
 *
 */

@States({
  @State(name = "Init"),                 //0 新单
  @State(name = "HoldSeatSuccess"),      //6 占座成功
  @State(name = "Closed"),               //5 取消
  @State(name = "PaySuccess"),         //1 支付成功
  @State(name = "PayFailure"),         //-1 支付失败
  @State(name = "HoldTicketIng"),         //10 出票中
  @State(name = "HoldTicketSuccess")    //2 出票成功
})

@Transitions({
  //新单->占座成功、取消
  @Transit(from = "Init", to = "HoldSeatSuccess",   on = "HoldSeatSuccess",    callMethod = "fromInitToHoldSeatSuccessOnHoldSeatSuccess"),   //1
  @Transit(from = "Init", to = "Closed",            on = "HoldSeatFailure",    callMethod = "fromInitToClosedOnHoldSeatFailure"),            //2

  //占座成功-> 取消
  @Transit(from = "HoldSeatSuccess", to = "Closed", on = "PayTimeout",    callMethod = "fromHoldSeatSuccessToClosedOnPayTimeout"),    //3
  @Transit(from = "Init",            to = "Closed", on = "PayTimeout",    callMethod = "fromInitToClosedOnPayTimeout"),            //23
  @Transit(from = "HoldSeatSuccess", to = "Closed", on = "CancelByUser",  callMethod = "fromHoldSeatSuccessToClosedOnCancelByUser"),  //4
  @Transit(from = "HoldSeatSuccess", to = "Closed", on = "CancelByFraud", callMethod = "fromHoldSeatSuccessToClosedOnCancelByFraud"), //5
  
  //占座成功-> 支付成功、支付失败
  @Transit(from = "HoldSeatSuccess", to = "PaySuccess", on = "PaySuccess", callMethod = "fromHoldSeatSuccessToPaySuccessOnPaySuccess"), //6
  @Transit(from = "HoldSeatSuccess", to = "PayFailure", on = "PayFailure", callMethod = "fromHoldSeatSuccessToPayFailureOnPayFailure"), //7
  
  //支付失败-> 支付失败、取消
  @Transit(from = "PayFailure", 	 to = "PaySuccess", on = "PaySuccess",    callMethod = "fromPayFailureToPaySuccessOnPaySuccess"), //8
  @Transit(from = "PayFailure", 	 to = "PayFailure", on = "PayFailure",    callMethod = "fromPayFailureToPayFailureOnPayFailure"), //9
  @Transit(from = "PayFailure", 	 to = "Closed", 	on = "PayTimeout",    callMethod = "fromPayFailureToClosedOnPayTimeout"), //10
  @Transit(from = "PayFailure", 	 to = "Closed", 	on = "CancelByUser",  callMethod = "fromPayFailureToClosedOnCancelByUser"), //11
  @Transit(from = "PayFailure", 	 to = "Closed", 	on = "CancelByFraud", callMethod = "fromPayFailureToClosedOnCancelByFraud"), //12
  
  //支付成功-> 取消、出票中、出票成功
  @Transit(from = "PaySuccess",   to = "Closed", 			 on = "NotifyTicketFailure", callMethod = "fromPaySuccessToClosedOnNotifyTicketFailure"), //13
  @Transit(from = "PaySuccess",   to = "HoldTicketIng", 	 on = "HoldTicketIng", 		 callMethod = "fromPaySuccessToHoldTicketIngOnHoldTicketIng"), //14
  @Transit(from = "PaySuccess",   to = "HoldTicketSuccess",  on = "HoldTicketSuccess",  callMethod = "fromPaySuccessToHoldTicketSuccessOnHoldTicketSuccess"), //15
  
  //出票中->出票成功、取消
  @Transit(from = "HoldTicketIng", to = "HoldTicketSuccess",  on = "HoldTicketSuccess", callMethod = "fromHoldTicketIngToHoldTicketSuccessOnHoldTicketSuccess"), //16
  @Transit(from = "HoldTicketIng", to = "Closed",  			  on = "HoldTicketFailure", callMethod = "fromHoldTicketIngToClosedOnHoldTicketFailure"), //17
  @Transit(from = "PaySuccess",    to = "Closed",             on = "HoldTicketFailure", callMethod = "fromPaySuccessToClosedOnHoldTicketFailure"),//22
  
  //取消
  @Transit(from = "Closed", to = "Closed", on = "PaySuccess", callMethod = "fromClosedToClosedOnPaySuccess"),//18
  @Transit(from = "Closed", to = "Closed", on = "PayFailure", callMethod = "fromClosedToClosedOnPayFailure"),//19
  @Transit(from = "Closed", to = "Closed", on = "RefundSuccess", callMethod = "fromClosedToClosedOnRefundSuccess"), //20
  @Transit(from = "Closed", to = "Closed", on = "RefundFailure", callMethod = "fromClosedToClosedOnRefundFailure") //21
  
})

@StateMachineParamters(stateType = BusState.class, eventType = BusEvent.class, contextType = Context.class)
public class BusStateMachine extends AbstractUntypedStateMachine {

  //执行器
  private static FlowExecutor flowExecutor = SpringWebContextHolder.getBean("flowExecutor");

  //初始化
  protected BusStateMachine(ImmutableUntypedState initialState, Map<Object, ImmutableUntypedState> states) {
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
    LoggerUtil.error(
      "statemachine from " + from + " to " + to + " on " + event + " failure orderId="
        + orderId, e1.getTargetException());
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
    OrderLoggerUtil.orderInfo(
      "statemachine from " + from + " to " + to + " on " + event + " completed orderId=" + orderId);
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
   * 新单->占座成功 1
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToHoldSeatSuccessOnHoldSeatSuccess(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromInitToHoldSeatSuccessOnHoldSeatSuccess", context, to.getName());
  }

  
  /**
   * 新单->占座失败取消 2
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToClosedOnHoldSeatFailure(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromInitToClosedOnHoldSeatFailure", context, to.getName());
  }

  /**
   * 占座成功->取消(支付超时) 3
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldSeatSuccessToClosedOnPayTimeout(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromHoldSeatSuccessToClosedOnPayTimeout", context, to.getName());
  }
  
  
  /**
   * 待支付->取消(支付超时) 23
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromInitToClosedOnPayTimeout(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromInitToClosedOnPayTimeout", context, to.getName());
  }
  
  /**
   * 占座成功->取消(用户取消) 4
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldSeatSuccessToClosedOnCancelByUser(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromHoldSeatSuccessToClosedOnCancelByUser", context, to.getName());
  }
  
  /**
   * 占座成功->取消(风控取消) 5
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldSeatSuccessToClosedOnCancelByFraud(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromHoldSeatSuccessToClosedOnCancelByFraud", context, to.getName());
  }

  /**
   * 占座成功-> 支付成功 6
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldSeatSuccessToPaySuccessOnPaySuccess(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromHoldSeatSuccessToPaySuccessOnPaySuccess", context, to.getName());
  }
  
  /**
   * 占座成功-> 支付失败 7
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldSeatSuccessToPayFailureOnPayFailure(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromHoldSeatSuccessToPayFailureOnPayFailure", context, to.getName());
  }
  
  /**
   * 支付失败-> 支付成功 8
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPayFailureToPaySuccessOnPaySuccess(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromPayFailureToPaySuccessOnPaySuccess", context, to.getName());
  }
  
  /**
   * 支付失败-> 支付失败 9
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPayFailureToPayFailureOnPayFailure(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromPayFailureToPayFailureOnPayFailure", context, to.getName());
  }
  
  /**
   * 支付失败-> 取消（支付超时） 10
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPayFailureToClosedOnCancelByUser(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromPayFailureToClosedOnCancelByUser", context, to.getName());
  }
  
  /**
   * 支付失败-> 取消（用户取消） 11
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPayFailureToClosedOnPayTimeout(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromPayFailureToClosedOnPayTimeout", context, to.getName());
  }
  
  /**
   * 支付失败-> 取消（风控取消） 12
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPayFailureToClosedOnCancelByFraud(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromPayFailureToClosedOnCancelByFraud", context, to.getName());
  }
  
  /**
   * 支付成功-> 取消（通知失败） 13
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPaySuccessToClosedOnNotifyTicketFailure(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromPaySuccessToClosedOnNotifyTicketFailure", context, to.getName());
  }
  
  /**
   * 支付成功-> 出票中 14
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPaySuccessToHoldTicketIngOnHoldTicketIng(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromPaySuccessToHoldTicketIngOnHoldTicketIng", context, to.getName());
  }
  
  /**
   * 支付成功-> 出票成功 15
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPaySuccessToHoldTicketSuccessOnHoldTicketSuccess(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromPaySuccessToHoldTicketSuccessOnHoldTicketSuccess", context, to.getName());
  }
  
  /**
   * 出票中-> 出票成功 16
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldTicketIngToHoldTicketSuccessOnHoldTicketSuccess(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromHoldTicketIngToHoldTicketSuccessOnHoldTicketSuccess", context, to.getName());
  } 
  
  /**
   * 出票中-> 出票失败 17
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromHoldTicketIngToClosedOnHoldTicketFailure(BusState from, BusState to, BusEvent event,Context context) throws Exception {
    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromHoldTicketIngToClosedOnHoldTicketFailure", context, to.getName());
  } 
  
  /**
   * 支付成功 18
   * 取消->取消
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromClosedToClosedOnPaySuccess(BusState from, BusState to, BusEvent event,Context context) throws Exception {
	    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromClosedToClosedOnPaySuccess", context, to.getName());
  } 

  /**
   * 支付失败 19
   * 取消->取消
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromClosedToClosedOnPayFailure(BusState from, BusState to, BusEvent event,Context context) throws Exception {
	    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromClosedToClosedOnPayFailure", context, to.getName());
  } 
 
  /**
   * 退款成功 20
   * 取消->取消
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromClosedToClosedOnRefundSuccess(BusState from, BusState to, BusEvent event,Context context) throws Exception {
	    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromClosedToClosedOnRefundSuccess", context, to.getName());
  } 
  
  /**
   * 退款失败 21
   * 取消->取消
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromClosedToClosedOnRefundFailure(BusState from, BusState to, BusEvent event,Context context) throws Exception {
	    flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromClosedToClosedOnRefundFailure", context, to.getName());
  } 
  
  /**
   * 出票失败 22
   * 支付成功-> 取消（出票失败）
   * @param from
   * @param to
   * @param event
   * @param context
   * @throws Exception
   */
  public void fromPaySuccessToClosedOnHoldTicketFailure(BusState from, BusState to, BusEvent event,Context context) throws Exception {
	  flowExecutor.doFlow(context.getCoreParams().getProductLine(),"fromPaySuccessToClosedOnHoldTicketFailure", context, to.getName());
  } 
  
}
