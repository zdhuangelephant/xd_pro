package com.xiaodou.oms.statemachine.instance.ihotelinsurance;

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
 * @Description: <p>国际酒店-保险<p>
 * 
 * @author <mailto:guanguo.gao@corp.elong.com>Guanguo.Gao</mailto>
 * @date 2014年10月23日 下午6:00:11
 * @version <b>1.0</b>
 */

@States({
    //0 新单
    @State(name = "Init"), 
    //1 支付成功
    @State(name = "PaySuccess"),
    //-1 支付失败
    @State(name = "PayFailure"),
    //5 取消
    @State(name = "Closed"),
    //5 投保中
    @State(name = "Delivering"),
    //2 投保成功
    @State(name = "Delivered"),
    //-2 投保失败
    @State(name = "DeliveredFailure") 
    })

@Transitions({
    //1.支付成功 -> 投保中    投保请求
    @Transit(from = "PaySuccess", to = "Delivering", on = "DeliveringSuccess", callMethod = "fromPaySuccessToDeliveringOnDeliveringSuccess"),
    //2.投保中 -> 投保成功    投保成功
    @Transit(from = "Delivering", to = "Delivered", on = "DeliveredSuccess", callMethod = "fromDeliveringToDeliveredOnDeliveredSuccess"),
    //3.投保中 -> 投保失败     投保失败
    @Transit(from = "Delivering", to = "DeliveredFailure", on = "DeliveredFailure", callMethod = "fromDeliveringToDeliveredFailureOnDeliveredFailure"),
    //4.投保失败 -> 投保中  再次请求投保
    @Transit(from = "DeliveredFailure", to = "PaySuccess", on = "DeliveringRequestAgain", callMethod = "fromDeliveredFailureToPaySuccessOnDeliveringRequestAgain"),
    //5.投保失败 -> 投保成功 修改成成功
    @Transit(from = "DeliveredFailure", to = "Delivered", on = "ModifiedToDelivered", callMethod = "fromDeliveredFailureToDeliveredOnModifiedToDelivered"),
    //6.投保失败 -> 取消 退款请求
    @Transit(from = "DeliveredFailure", to = "Closed", on = "RefundRequest", callMethod = "fromDeliveredFailureToClosedOnRefundRequest"),
    //7.取消 -> 取消 退款成功
    @Transit(from = "Closed", to = "Closed", on = "RefundSuccess", callMethod = "fromClosedToClosedOnRefundSuccess"),
    //8.取消 -> 取消 退款失败
    @Transit(from = "Closed", to = "Closed", on = "RefundFailure", callMethod = "fromClosedToClosedOnRefundFailure")
    })

@StateMachineParamters(stateType = IHotelInsuranceState.class, eventType = IHotelInsuranceEvent.class, contextType = Context.class)
public class IHotelInsuranceStateMachine extends AbstractUntypedStateMachine {

    /** 流执行器 */
    private static FlowExecutor flowExecutor = SpringWebContextHolder.getBean("flowExecutor");

    protected IHotelInsuranceStateMachine(ImmutableUntypedState initialState,
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
     * 1.支付成功->投保中，收到投保请求触发。
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromPaySuccessToDeliveringOnDeliveringSuccess(IHotelInsuranceState from, IHotelInsuranceState to, IHotelInsuranceEvent event,
        Context context) throws Exception {
      flowExecutor.doFlow(context.getCoreParams().getProductLine(),
          "fromPaySuccessToDeliveringOnDeliveringSuccess", context,to.getName());
    }
    
    /**
     * 2.投保中->投保成功，投保成功时触发。
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromDeliveringToDeliveredOnDeliveredSuccess(IHotelInsuranceState from, IHotelInsuranceState to, IHotelInsuranceEvent event,
        Context context) throws Exception {
      flowExecutor.doFlow(context.getCoreParams().getProductLine(),
          "fromDeliveringToDeliveredOnDeliveredSuccess", context,to.getName());
    }
    
    /**
     * 3.投保中->投保失败，投保失败时触发。
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromDeliveringToDeliveredFailureOnDeliveredFailure(IHotelInsuranceState from, IHotelInsuranceState to, IHotelInsuranceEvent event,
        Context context) throws Exception {
      flowExecutor.doFlow(context.getCoreParams().getProductLine(),
          "fromDeliveringToDeliveredFailureOnDeliveredFailure", context,to.getName());
    }
    
    /**
     * 4.投保失败->支付成功，人工再次投保时触发。
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromDeliveredFailureToPaySuccessOnDeliveringRequestAgain(IHotelInsuranceState from, IHotelInsuranceState to, IHotelInsuranceEvent event,
        Context context) throws Exception {
      flowExecutor.doFlow(context.getCoreParams().getProductLine(),
          "fromDeliveredFailureToPaySuccessOnDeliveringRequestAgain", context,to.getName());
    }
    
    /**
     * 5.投保失败->投保成功，人工改状态时触发。
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromDeliveredFailureToDeliveredOnModifiedToDelivered(IHotelInsuranceState from, IHotelInsuranceState to, IHotelInsuranceEvent event,
        Context context) throws Exception {
      flowExecutor.doFlow(context.getCoreParams().getProductLine(),
          "fromDeliveredFailureToDeliveredOnModifiedToDelivered", context,to.getName());
    }
    
    /**
     * 6.投保失败->取消，人工发起退款时触发。
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromDeliveredFailureToClosedOnRefundRequest(IHotelInsuranceState from, IHotelInsuranceState to, IHotelInsuranceEvent event,
        Context context) throws Exception {
      flowExecutor.doFlow(context.getCoreParams().getProductLine(),
          "fromDeliveredFailureToClosedOnRefundRequest", context,to.getName());
    }
   
    /**
     * 7.取消->取消，payment通知退款成功时触发。
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromClosedToClosedOnRefundSuccess(IHotelInsuranceState from, IHotelInsuranceState to, IHotelInsuranceEvent event,
        Context context) throws Exception {
      flowExecutor.doFlow(context.getCoreParams().getProductLine(),
          "fromClosedToClosedOnRefundSuccess", context,to.getName());
    }
    
    /**
     * 8.取消->取消，payment通知退款失败时触发。
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromClosedToClosedOnRefundFailure(IHotelInsuranceState from, IHotelInsuranceState to, IHotelInsuranceEvent event,
        Context context) throws Exception {
      flowExecutor.doFlow(context.getCoreParams().getProductLine(),
          "fromClosedToClosedOnRefundFailure", context,to.getName());
    }

}
