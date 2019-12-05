/**
 * @Title: NbapiStateMachine.java
 * @Package com.elong.oms.statemachine.instance.ihotelnbapi
 * @Description: 国际酒店业务的状态机
 * Copyright: Copyright (c) 2014 
 * Email: songbin0819@163.com
 * 
 * @author zwl
 * @date 2015年4月22日 上午9:45:35
 * @version V1.0
 */
package com.xiaodou.oms.statemachine.instance.ihotelnbapi;

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
 * NbapiStateMachine
 * 
 * @Title: NbapiStateMachine
 * @Description: 国际酒店业务的状态机
 * @author zwl
 * @date 2015年4月22日 上午9:45:35
 *
 */

@States({
// 新单
        @State(name = "Init"),
        // 支付成功
        @State(name = "PaySuccess"),
        // 确认成功
        @State(name = "Delivered"),
        // 确认失败
        @State(name = "DeliveredFailure"),
        // 取消中
        @State(name = "Canceling"),
        // 取消
        @State(name = "Closed"),
        // 取消成功
        @State(name = "CancelSuccess"),
        // 取消失败
        @State(name = "CancelFailure"),
        // 已入住
        @State(name = "CheckedIn"),
        // 已结账
        @State(name = "CheckedOut") })
@Transitions({
// 1.确认成功
        @Transit(from = "Init", to = "Delivered", on = "DeliveredSuccess", callMethod = "fromInitToDeliveredOnDeliveredSuccess"),
        // 2.确认失败
        @Transit(from = "Init", to = "DeliveredFailure", on = "DeliveredFailure", callMethod = "fromInitToDeliveredFailureOnDeliveredFailure"),
        // 3.支付成功
        @Transit(from = "Delivered", to = "PaySuccess", on = "PaySuccess", callMethod = "fromDeliveredToPaySuccessOnPaySuccess"),
        // 4.NBAPI取消
        @Transit(from = "PaySuccess", to = "Canceling", on = "CancelByNbapi", callMethod = "fromPaySuccessToCancelingOnCancelByNbapi"),
        // 5.退款成功
        @Transit(from = "CancelSuccess", to = "CancelSuccess", on = "RefundSuccess", callMethod = "fromCancelSuccessToCancelSuccessOnRefundSuccess"),
        // 6.退款失败
        @Transit(from = "CancelSuccess", to = "CancelSuccess", on = "RefundFailure", callMethod = "fromCancelSuccessToCancelSuccessOnRefundFailure"),
        // 7.NBAPI取消
        @Transit(from = "DeliveredFailure", to = "Closed", on = "CancelByNbapi", callMethod = "fromDeliveredFailureToClosedOnCancelByNbapi"),
        // 8.确认超时
        @Transit(from = "DeliveredFailure", to = "Closed", on = "DeliveredTimeout", callMethod = "fromDeliveredFailureToClosedOnDeliveredTimeout"),
        // 9.NBAPI重试
        @Transit(from = "DeliveredFailure", to = "Delivered", on = "RetrySuccess", callMethod = "fromDeliveredFailureToDeliveredOnRetrySuccess"),
        // 10.取消失败
        @Transit(from = "Canceling", to = "CancelFailure", on = "CancelFailure", callMethod = "fromCancelingToCancelFailureOnCancelFailure"),
        // 11.重新取消请求
        @Transit(from = "CancelFailure", to = "Canceling", on = "CancelingRequestAgain", callMethod = "fromCancelFailureToCancelingOnCancelingRequestAgain"),
        // 12.人工调整到取消成功
        @Transit(from = "CancelFailure", to = "CancelSuccess", on = "ModifyToCancelSuccess", callMethod = "fromCancelFailureToCancelSuccessOnModifyToCancelSuccess"),
        // 13.确认失败取消
        @Transit(from = "DeliveredFailure", to = "Closed", on = "CancelingFromFailure", callMethod = "fromDeliveredFailureToClosedOnCancelingFromFailure"),
        // 14.取消成功
        @Transit(from = "Canceling", to = "CancelSuccess", on = "CancelSuccess", callMethod = "fromCancelingToCancelSuccessOnCancelSuccess"),
        // 15.客服强制取消
        @Transit(from = "PaySuccess", to = "CancelSuccess", on = "ForceCancelByMis", callMethod = "fromPaySuccessToCancelSuccessOnForceCancelByMis"),
        // 32.支付成功->已入住 入住成功
        @Transit(from = "PaySuccess", to = "CheckedIn", on = "CheckingInSuccess", callMethod = "fromPaySuccessToCheckedInOnCheckingInSuccess"),
        // 33.已入住->已结账 离店成功
        @Transit(from = "CheckedIn", to = "CheckedOut", on = "CheckingOutSuccess", callMethod = "fromCheckedInToCheckedOutOnCheckingOutSuccess") })
@StateMachineParamters(stateType = IHotelNbapiState.class, eventType = IHotelNbapiEvent.class, contextType = Context.class)
public class IHotelNbapiStateMachine extends AbstractUntypedStateMachine {
    /**
     * 流执行器
     */
    private static FlowExecutor flowExecutor = SpringWebContextHolder.getBean("flowExecutor");

    protected IHotelNbapiStateMachine(ImmutableUntypedState initialState, Map<Object, ImmutableUntypedState> states) {
        super(initialState, states);
    }

    /**
     * 
     * override afterTransitionCausedException
     * <p>
     * Title: afterTransitionCausedException
     * </p>
     * <p>
     * Description:
     * </p>
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
     * 
     * override afterTransitionCompleted
     * <p>
     * Title: afterTransitionCompleted
     * </p>
     * <p>
     * Description:
     * </p>
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
     * 
     * override afterTransitionDeclined
     * <p>
     * Title: afterTransitionDeclined
     * </p>
     * <p>
     * Description:
     * </p>
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
     * 1.新单→确认成功 fromInitToDeliveredOnDeliveredSuccess
     * 
     * @Title: fromInitToDeliveredOnDeliveredSuccess
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromInitToDeliveredOnDeliveredSuccess(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInitToDeliveredOnDeliveredSuccess", context, to.getName());
    }

    /**
     * 2.新单→确认失败 fromInitToDeliveredFailureOnDeliveredFailure
     * 
     * @Title: fromInitToDeliveredFailureOnDeliveredFailure
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromInitToDeliveredFailureOnDeliveredFailure(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromInitToDeliveredFailureOnDeliveredFailure", context, to.getName());
    }

    /**
     * 
     * 3.确认成功→支付成功 fromDeliveredToPaySuccessOnPaySuccess
     * 
     * @Title: fromDeliveredToPaySuccessOnPaySuccess
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromDeliveredToPaySuccessOnPaySuccess(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromDeliveredToPaySuccessOnPaySuccess", context, to.getName());
    }

    /**
     * 
     * 4.支付成功→取消中 fromPaySuccessToCancelingOnCancelByNbapi
     * 
     * @Title: fromPaySuccessToCancelingOnCancelByNbapi
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromPaySuccessToCancelingOnCancelByNbapi(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromPaySuccessToCancelingOnCancelByNbapi", context, to.getName());
    }

    /**
     * 
     * 5.取消成功→取消成功 退款成功 fromCancelSuccessToCancelSuccessOnRefundSuccess
     * 
     * @Title: fromCancelSuccessToCancelSuccessOnRefundSuccess
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromCancelSuccessToCancelSuccessOnRefundSuccess(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromCancelSuccessToCancelSuccessOnRefundSuccess", context, to.getName());
    }

    /**
     * 
     * 6.取消成功→取消成功 退款失败 fromCancelSuccessToCancelSuccessOnRefundFailure
     * 
     * @Title: fromCancelSuccessToCancelSuccessOnRefundFailure
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromCancelSuccessToCancelSuccessOnRefundFailure(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromCancelSuccessToCancelSuccessOnRefundFailure", context, to.getName());
    }

    /**
     * 
     * 7.确认失败→关单 NBAPI取消 fromDeliveredFailureToClosedOnCancelByNbapi
     * 
     * @Title: fromDeliveredFailureToClosedOnCancelByNbapi
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromDeliveredFailureToClosedOnCancelByNbapi(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromDeliveredFailureToClosedOnCancelByNbapi", context, to.getName());
    }

    /**
     * 
     * 8.确认失败→关单 确认超时 fromDeliveredFailureToClosedOnDeliveredTimeout
     * 
     * @Title: fromDeliveredFailureToClosedOnDeliveredTimeout
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromDeliveredFailureToClosedOnDeliveredTimeout(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromDeliveredFailureToClosedOnDeliveredTimeout", context, to.getName());
    }

    /**
     * 
     * 9.确认失败→确认成功 NBAPI重试成功 fromDeliveredFailureToDeliveredOnRetrySuccess
     * 
     * @Title: fromDeliveredFailureToDeliveredOnRetrySuccess
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromDeliveredFailureToDeliveredOnRetrySuccess(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromDeliveredFailureToDeliveredOnRetrySuccess", context, to.getName());
    }

    /**
     * 
     * 10.取消中→取消失败 取消失败 fromCancelingToCancelFailureOnCancelFailure
     * 
     * @Title: fromCancelingToCancelFailureOnCancelFailure
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromCancelingToCancelFailureOnCancelFailure(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromCancelingToCancelFailureOnCancelFailure", context, to.getName());
    }

    /**
     * 
     * 11.取消失败→取消中 重新取消请求 fromCancelFailureToCancelingOnCancelingRequestAgain
     * 
     * @Title: fromCancelFailureToCancelingOnCancelingRequestAgain
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromCancelFailureToCancelingOnCancelingRequestAgain(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromCancelFailureToCancelingOnCancelingRequestAgain", context, to.getName());
    }

    /**
     * 
     * 12.取消失败→取消成功 人工调整到取消成功
     * fromCancelFailureToCancelSuccessOnModifyToCancelSuccess
     * 
     * @Title: fromCancelFailureToCancelSuccessOnModifyToCancelSuccess
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromCancelFailureToCancelSuccessOnModifyToCancelSuccess(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromCancelFailureToCancelSuccessOnModifyToCancelSuccess", context, to.getName());
    }

    /**
     * 
     * 13.确认失败→关单 确认失败取消 fromDeliveredFailureToClosedOnCancelingFromFailure
     * 
     * @Title: fromDeliveredFailureToClosedOnCancelingFromFailure
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromDeliveredFailureToClosedOnCancelingFromFailure(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromDeliveredFailureToClosedOnCancelingFromFailure", context, to.getName());
    }

    /**
     * 
     * 14.取消中→取消成功 取消成功fromCancelingToCancelSuccessOnCancelSuccess
     * 
     * @Title: fromCancelingToCancelSuccessOnCancelSuccess
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromCancelingToCancelSuccessOnCancelSuccess(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromCancelingToCancelSuccessOnCancelSuccess", context, to.getName());
    }

    /**
     * 
     * 15.确认成功→取消成功 客服强制取消fromDeliveredToCancelSuccessOnForceCancelByMis
     *
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromPaySuccessToCancelSuccessOnForceCancelByMis(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
    	flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromPaySuccessToCancelSuccessOnForceCancelByMis", context, to.getName());
    }
    
    /**
     * 
     * 32.支付成功→已入住 入住成功fromPaySuccessToCheckedInOnCheckingInSuccess
     * 
     * @Title: fromPaySuccessToCheckedInOnCheckingInSuccess
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromPaySuccessToCheckedInOnCheckingInSuccess(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromPaySuccessToCheckedInOnCheckingInSuccess", context, to.getName());
    }

    /**
     * 
     * 33.已入住→已结账 离店成功 fromCheckedInToCheckedOutOnCheckingOutSuccess
     * 
     * @Title: fromCheckedInToCheckedOutOnCheckingOutSuccess
     * @Description: TODO
     * @param from
     * @param to
     * @param event
     * @param context
     * @throws Exception
     */
    public void fromCheckedInToCheckedOutOnCheckingOutSuccess(IHotelNbapiState from, IHotelNbapiState to, IHotelNbapiEvent event, Context context) throws Exception {
        flowExecutor.doFlow(context.getCoreParams().getProductLine(), "fromCheckedInToCheckedOutOnCheckingOutSuccess", context, to.getName());
    }
}
