package com.xiaodou.oms.statemachine.engine.model.api.proxy;

import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.engine.model.api.BaseAPI;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.extension.ICondition;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.extension.IException;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.extension.ITimeOut;

/**
 * <p>API抽象代理</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月19日
 */
public interface IApiProxy {
  
    /**
     * 反射调用API方法
     * @param context 上下文
     * @return 
     * @throws Exception 异常
     */
    public Object invoke(Context context) throws Exception;
    
    /**
     * 获取超时拓展点
     * @return 超时拓展点
     */
    public Class<? extends ITimeOut> getOnTimeOut();

    /**
     * 设置超时拓展点
     * @param onTimeOut 超时拓展点
     * @throws ClassNotFoundException 拓展点不存在
     */
    public void setOnTimeOut(String onTimeOut) throws ClassNotFoundException;

    /**
     * 获取异常拓展点
     * @return 异常拓展点
     */
    public Class<? extends IException> getOnException();

    /**
     * 设置异常拓展点
     * @param onException 异常拓展点
     * @throws ClassNotFoundException 拓展点不存在
     */
    public void setOnException(String onException) throws ClassNotFoundException;

    /**
     * 获取条件拓展点
     * @return 条件拓展点
     */
    public Class<? extends ICondition> getOnCondition();

    /**
     * 设置条件拓展点
     * @param onConditon 条件拓展点
     * @throws ClassNotFoundException 条件点不存在
     */
    public void setOnCondition(String onConditon) throws ClassNotFoundException;

    
    /**
     * 获取API
     * @return API
     */
    public <T extends BaseAPI> T getApi();

}
