package com.xiaodou.common.util.log.model;

import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @ClassName: BatchProcessEntity
 * 
 * @Description: 批处理对象
 * 
 * @author Guanguo.Gao 增加字段
 * 
 * @date 2014年7月24日 下午5:04:59
 * 
 * @version V1.0
 */
public class BatchProcessEntity extends BaseEntity {

    /** 目标类 **/
    private String targetClass;

    /** 目标方法 **/
    private String targetMethod;
    
    /** 入参 **/
    private String params;
    
    /** 方法相应结果 **/
    private String responseInfo;
    
    /** 耗时 ms**/
    private long useTime;
    
    /** 内容 **/
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo;
    }

    public String getUseTime() {
        return useTime + "ms";
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }
    
    @Override
    public String toString()
    {
        return FastJsonUtil.toJson(this);
    }
}
