package com.xiaodou.aopagent.filter;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.xiaodou.aopagent.util.TraceWrapper;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.TraceEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * AOP
 * 
 * @author zhouhuan
 * 
 */
public class TraceInterceptor{

    /**
     * 异常通知（方法发生异常执行的代码）
     * 可以访问到异常对象；且可以指定在出现特定异常时执行的代码
     * @param joinPoint
     * @param ex
     */
    public void afterThrowingMethod(JoinPoint joinPoint,Exception ex){
        String methodName = joinPoint.getSignature().getName();
        LoggerUtil.error(methodName, ex);
    }
   
   
    public Object aroundMethod(ProceedingJoinPoint joinPoint){
    	TraceEntity trace = new TraceEntity();
        Object result = null;      
        Long beforeTime=System.currentTimeMillis();
        Long afterTime = 0L;
        String processId = null;
        String parentId = null;
        if(TraceWrapper.getWrapper().getTraceParam().getProcessId()==null){
        	TraceWrapper.getWrapper().getTraceParam().setProcessId("0");
        }
        processId=String.valueOf(Integer.valueOf(TraceWrapper.getWrapper().getTraceParam().getProcessId())+1);
        String myId=UUID.randomUUID().toString();
        parentId=TraceWrapper.getWrapper().getTraceParam().getMyId();
        TraceWrapper.getWrapper().getTraceParam().setParentId(parentId);
        TraceWrapper.getWrapper().getTraceParam().setMyId(myId);
        TraceWrapper.getWrapper().getTraceParam().setProcessId(processId);
        try {    
             if(TraceWrapper.getWrapper().getTraceParam()==null){
            	 return null;
             }      
             result = joinPoint.proceed();
             afterTime=System.currentTimeMillis();       
             this.recordTraceLog(trace,joinPoint, afterTime-beforeTime,processId,myId,parentId,result);    
         } catch (Throwable e) {
        	trace.setErrorMsg(e.toString());
        	afterTime=System.currentTimeMillis();       
        	this.recordTraceLog(trace,joinPoint, afterTime-beforeTime,processId,myId,parentId,result);    
         } 
        return result;
    }
    
    public void recordTraceLog(TraceEntity trace,ProceedingJoinPoint joinPoint,Long executionTime,String processId,String myId,String parentId,Object result){ 	
    	String traceId=TraceWrapper.getWrapper().getTraceParam().getTraceId();
        String projectId=TraceWrapper.getWrapper().getTraceParam().getProjectId();
        String excutePointName =joinPoint.getSignature().getDeclaringTypeName() + "."+ joinPoint.getSignature().getName();
        trace.setExcutePoint(excutePointName);
        trace.setExecutionTime(executionTime.toString());
        trace.setLogName("trace");
        trace.setPersonalityData("");
        trace.setProcessId(processId);
        trace.setProjectId(projectId);
        trace.setTraceId(traceId);
        trace.setMyId(myId);
        trace.setParentId(parentId);  
        Object[] args = joinPoint.getArgs();
        trace.setEntryParameter(getLimitString(FastJsonUtil.toJson(args),500));
        String excuteResult=FastJsonUtil.toJson(result);
        if(excuteResult!=null&&excuteResult.length()>0){
        	trace.setExcuteResult(getLimitString(excuteResult,500));
        }else{
        	trace.setExcuteResult(excuteResult);
        }
        LoggerUtil.traceInfo(trace);
    }
    
    
    
    private String getLimitString(String src, Integer limit) {
        return src.length() > limit + 5 ? src.substring(0, limit) : src;
      }
        
}
