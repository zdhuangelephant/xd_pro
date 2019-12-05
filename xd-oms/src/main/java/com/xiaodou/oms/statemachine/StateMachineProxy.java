package com.xiaodou.oms.statemachine;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.squirrelframework.foundation.fsm.UntypedStateMachine;

import com.xiaodou.oms.statemachine.engine.StateMachineContext;
import com.xiaodou.oms.statemachine.engine.instance.StateMachineProductLineConf;


/**
 * @Title:StateMachineProxy.java
 * @Description:状态机代理类，根据一定的策略，找到相应的状态机执行。是外界调用状态机的唯一入口。
 * @author zhaoyang
 * @date June 18, 2014 8:33:35 AM
 * @version V1.0
 */
public class StateMachineProxy {

  @Resource
  StateMachineFactory stateMachineFactory;

  /**
   * 事件触发方法
   * 
   * @param context
   * @throws Exception
   */
  @SuppressWarnings("rawtypes")
  public void fire(Context context) throws Exception {
    
    UntypedStateMachine stateMachine = null;
    try{
    Map<String, String> instanceMap = getInstance(context);

    stateMachine =
        stateMachineFactory.getStateMachine(instanceMap.get("stateMachineInstance"),
            instanceMap.get("stateInstance"), context);
    stateMachine.start(context);

    Enum eventEnum =
        StateEventConvertor.getOrderEvent(context.getCoreParams().getEvent(),
            instanceMap.get("eventInstance"));
    stateMachine.fire(eventEnum, context);
    
    }finally {
      context = null;
      stateMachine = null;
    }
  }

  
  /**
   * 根据策略找到相应的状态机实例
   * 
   * @param context
   * @return
   */
  private Map<String, String> getInstance(Context context) throws Exception{

    StateMachineProductLineConf conf =
        StateMachineContext.getConf().getStateMachineProductLineConfMap()
            .get(context.getCoreParams().getProductLine());
    if(conf == null){
      throw new Exception("状态机配置找不到，productLine="+context.getCoreParams().getProductLine());
    }
    Map<String, String> instanceMap = new HashMap<String, String>();

    instanceMap.put("stateMachineInstance", conf.getStateMachineInstance());
    instanceMap.put("stateInstance", conf.getStateInstance());
    instanceMap.put("eventInstance", conf.getEventInstance());

    return instanceMap;

  }


}
