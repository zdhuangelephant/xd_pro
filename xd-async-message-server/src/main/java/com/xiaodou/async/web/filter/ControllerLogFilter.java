package com.xiaodou.async.web.filter;

import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.OutInEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

public class ControllerLogFilter {
  private final static Set<String> methodName = Sets.newHashSet();

  // private final boolean switcher = "ON".equals(ConfigUtils.getLogSwitch());

  /**
   * ProcessController切入点
   * 
   * @param joinPoint 切入点
   * @param result 返回结果
   */
  public Object service(JoinPoint joinPoint) {
    long startTime = System.currentTimeMillis();
    Object result = null;
    try {
      // 记录请求信息:方法类/方法名/请求参数
      result = ((ProceedingJoinPoint) joinPoint).proceed();
      return result;
    } catch (Throwable e) {
      result = new ResultInfo(ResultType.SYSFAIL);
      ((ResultInfo) result).appendRetdesc("#" + getErrMsg(e));
      LoggerUtil.error(joinPoint.getSignature().getName(), e);
      return FastJsonUtil.toJson(result);
    } finally {
      LoggerUtil.outInInfo(baseHandlerResponse(joinPoint, result, System.currentTimeMillis()
          - startTime));
    }
  }

  private String getErrMsg(Throwable t) {
    if (StringUtils.isNotBlank(t.getMessage())) return t.getMessage();
    if (null != t.getCause()) return getErrMsg(t.getCause());
    return StringUtils.EMPTY;
  }

  /**
   * 拦截封装日志信息方法
   * 
   * @param joinPoint 切入点
   * @param result 返回结果
   * @param costTime
   * @return 记录日志信息
   */
  private OutInEntity baseHandlerResponse(JoinPoint joinPoint, Object result, long costTime) {
    String _result = StringUtils.EMPTY;
    if (null != result) _result = result.toString();
    OutInEntity entity = new OutInEntity();
    entity.setTargetClass(joinPoint.getTarget().getClass().getName());// 记录请求service类
    entity.setTargetMethod(joinPoint.getSignature().getName());// 记录请求方法名
    if (/* switcher || */!methodName.contains(entity.getTargetMethod())) {
      Object[] args = joinPoint.getArgs();
      if (null != args && args.length > 0) {
        for (Object arg : args) {
          if (arg instanceof BaseValidatorPojo) {
            entity.setParams(FastJsonUtil.toJson(arg));// 记录传入参数
            break;
          }
        }
      }
      entity.setResponseInfo(_result);// 记录方法返回值
    }
    entity.setUseTime(costTime);// 记录方法耗时
    return entity;
  }

}
