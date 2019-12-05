package com.xiaodou.oms.statemachine;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.xiaodou.oms.exception.PreCheckFailException;
import com.xiaodou.oms.statemachine.engine.StateMachineContext;
import com.xiaodou.oms.statemachine.engine.instance.StateMachineProductLineConf;
import com.xiaodou.oms.statemachine.engine.instance.TransitionConf;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.IApiProxy;
import com.xiaodou.oms.util.ErrorsWrapper;
import com.xiaodou.oms.util.OrderLoggerUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.oms.util.model.FlowExcutorEntity;


/**
 * <p>
 * Transition流式处理器
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月8日
 */
public class FlowExecutor {

  /**
   * 流执行器
   */
  private static FlowExecutor flowExecutor = null;

  private void init(){
    flowExecutor = SpringWebContextHolder.getBean("flowExecutor");
  }

  private FlowExecutor getFlowExecutor(){
    if(null == flowExecutor){
      synchronized (FlowExecutor.class){
        if(null == flowExecutor)
          init();
      }
    }
    return flowExecutor;
  }
  /**
   * 流式处理方法
   * 
   * @param productLine 所属业务线
   * @param transitionName transition名
   * @param context 上下文
   * @param toState 目标状态
   * @throws Throwable
   */
  public void doFlow(String productLine, String transitionName, Context context, Integer toState)
      throws Exception {
    getFlowExecutor().doApi(getFlowExecutor().doMain(productLine, transitionName, context, toState)
        .getMessage(), context);// 发rabbit-MQ异步消息
  }

  @SuppressWarnings("unchecked")
  public TransitionConf doMain(String productLine, String transitionName, Context context,
      Integer toState) throws Exception {
    FlowExcutorEntity logentity =
        new FlowExcutorEntity(productLine, transitionName, context, toState);
    long start = System.currentTimeMillis();
    try {
      context.getShares().put("toState", toState);// 将toState写入缓存
      TransitionConf transitionConf = getTransitionConf(productLine, transitionName);
      boolean flag = preCheck(transitionConf.getPreCheckList(), context);// 前置检查
      if (flag) {
        doApi(transitionConf.getDoList(), context);// 执行API
        doApi(transitionConf.getRecordMessage(), context);// 记录消息
      }
      logentity.setResult("success");
      return transitionConf;
    } catch (Throwable ei) {
      Throwable e =
          (ei instanceof InvocationTargetException) ? ((InvocationTargetException) ei)
              .getTargetException() : ei;
      logentity.setResult("fail");
      logentity.setErrmsg(e.getMessage());
      ErrorsWrapper.getWrapper().setValue(e);
      throw ei;
    } finally {
      logentity.setCostTime(System.currentTimeMillis() - start);// 记录耗时
      OrderLoggerUtil.flowExcutorInfo(logentity);
    }
  }

  /**
   * 执行前置检查API
   * 
   * @param preCheckList 前置检查API列表
   * @param context 上下文信息
   * @return 是否通过前置检查
   * @throws Exception
   */
  public boolean preCheck(List<IApiProxy> preCheckList, Context context) throws Exception {
    for (IApiProxy endpoint : preCheckList) {
      String result = endpoint.invoke(context).toString();
      if (!(result.indexOf("\"retcode\":0") > 0)) {
        throw new PreCheckFailException(endpoint.getApi().getName() + result);
      }
    }
    return true;
  }

  /**
   * 执行API列表
   * 
   * @param apiList api列表
   * @param context 上下文信息
   * @throws Exception
   */
  public void doApi(List<IApiProxy> apiList, Context context) throws Exception {
    for (IApiProxy endpoint : apiList) {
      endpoint.invoke(context);
    }
  }

  private static TransitionConf getTransitionConf(String productLine, String transitionName) {

    StateMachineProductLineConf stateMachineProductLineConf =
        StateMachineContext.getConf().getStateMachineProductLineConfMap().get(productLine);
    Map<String, TransitionConf> transitionConfMap =
        stateMachineProductLineConf.getTransitionConfMap();
    return transitionConfMap.get(transitionName);

  }

}
