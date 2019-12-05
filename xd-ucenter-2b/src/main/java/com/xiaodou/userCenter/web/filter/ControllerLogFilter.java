package com.xiaodou.userCenter.web.filter;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.ActionEntity;
import com.xiaodou.common.util.log.model.BatchProcessEntity;
import com.xiaodou.common.util.log.model.OutInEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.IValidator;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.enums.LoginPar;
import com.xiaodou.userCenter.util.SaltUtil;

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
        Object result = StringUtils.EMPTY;
        Boolean hasError = false;
        String errorMessage = StringUtils.EMPTY;
        OutInEntity outIn = new OutInEntity();
        Errors error = baseHandlerResponse(outIn, joinPoint);
        if (null != error && error.hasErrors()) {
            ResultInfo response = new ResultInfo(ResultType.VALFAIL);
            response.appendRetdesc("#" + getErrMsg(error));
            return FastJsonUtil.toJson(response);
        }
        try {
            // 记录请求信息:方法类/方法名/请求参数
            result = ((ProceedingJoinPoint) joinPoint).proceed();
            return result;
        } catch (Throwable e) {
            hasError = true;
            errorMessage = e.getMessage();
            result = new ResultInfo(ResultType.SYSFAIL);
            ((ResultInfo) result).appendRetdesc("#" + getErrMsg(e));
            LoggerUtil.error(joinPoint.getSignature().getName(), e);
            return FastJsonUtil.toJson(result);
        } finally {
            outIn.setUseTime(System.currentTimeMillis() - startTime);
            outIn.setResponseInfo(result.toString());
            LoggerUtil.outInInfo(outIn);
            ActionEntity action = new ActionEntity();
            String excutePointName = joinPoint.getSignature().getDeclaringTypeName() + "."
                    + joinPoint.getSignature().getName();
            action.setExcutePoint(excutePointName);
            action.setLogName("out_in");
            action.setActionInfo(result);
            action.setHasError(hasError);
            action.setErrorMessage(errorMessage);
            LoggerUtil.actionInfo(action);
        }
    }

    public Object batch(JoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        Object object = null;
        Boolean hasError = false;
        String errorMessage = StringUtils.EMPTY;
        BatchProcessEntity outIn = new BatchProcessEntity();
        try {
            // 记录请求信息:方法类/方法名/请求参数
            Errors error = batchInfo(outIn, joinPoint);
            if (null != error && error.hasErrors()) {
                ResultInfo response = new ResultInfo(ResultType.VALFAIL);
                response.appendRetdesc("#" + getErrMsg(error));
                return FastJsonUtil.toJson(response);
            }
            return ((ProceedingJoinPoint) joinPoint).proceed();
        } catch (Throwable e) {
            hasError = true;
            errorMessage = e.getMessage();
            object = new ResultInfo(ResultType.SYSFAIL);
            ((ResultInfo) object).appendRetdesc("#" + getErrMsg(e));
            LoggerUtil.error(joinPoint.getSignature().getName(), e);
            return FastJsonUtil.toJson(object);
        } finally {
            outIn.setResponseInfo(FastJsonUtil.toJson(object));
            outIn.setUseTime(System.currentTimeMillis() - startTime);// 记录方法耗时
            LoggerUtil.batchProcessInfo(outIn);
            ActionEntity action = new ActionEntity();
            String excutePointName = joinPoint.getSignature().getDeclaringTypeName() + "."
                    + joinPoint.getSignature().getName();
            action.setExcutePoint(excutePointName);
            action.setLogName("out_in");
            action.setActionInfo(object);
            action.setHasError(hasError);
            action.setErrorMessage(errorMessage);
            LoggerUtil.actionInfo(action);
        }
    }

    private String getErrMsg(Throwable t) {
        if (StringUtils.isNotBlank(t.getMessage())) return t.getMessage();
        if (null != t.getCause()) return getErrMsg(t.getCause());
        return StringUtils.EMPTY;
    }

    private Errors batchInfo(BatchProcessEntity outIn, JoinPoint joinPoint) {
        Errors error = null;
        outIn.setTargetClass(joinPoint.getTarget().getClass().getName());// 记录请求类
        outIn.setTargetMethod(joinPoint.getSignature().getName());// 记录请求方法
        Object[] args = joinPoint.getArgs();
        outIn.setParams(FastJsonUtil.toJson(args));
        if (null != args && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof IValidator) {
                    error = ((IValidator) args[i]).validate();
                }
            }
        }
        return error;
    }

    /**
     * 参数异常提示信息
     * 
     * @param errors 错误
     * @return 错误提示
     */
    public String getErrMsg(Errors errors) {
        List<ObjectError> errs = errors.getAllErrors();
        StringBuffer errInfo = new StringBuffer();
        for (ObjectError err : errs) {
            errInfo.append(err.getDefaultMessage());
        }
        return errInfo.toString();
    }

    /**
     * 拦截封装日志信息方法
     * 
     * @param joinPoint 切入点
     * @param result 返回结果
     * @param costTime
     * @return 记录日志信息
     */
    private Errors baseHandlerResponse(OutInEntity outin, JoinPoint joinPoint) {
        Errors error = null;
        outin.setTargetClass(joinPoint.getTarget().getClass().getName());// 记录请求service类
        outin.setTargetMethod(joinPoint.getSignature().getName());// 记录请求方法名
        if (!methodName.contains(outin.getTargetMethod())) {
            Object[] args = joinPoint.getArgs();
            if (null != args && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    // 这里的参数记录需要重构
                    // TODO
                    if (args[i] instanceof ServletRequest) {
                        outin.setParams(getReqParam((HttpServletRequest) args[i]));// 记录传入参数
                        break;
                    }
                    if (args[i] instanceof IValidator) {
                        error = ((IValidator) args[i]).validate();
                        outin.setParams(FastJsonUtil.toJson(args[i]));// 记录传入参数
                        break;
                    }
                }
            }
        }
        return error;
    }

    public final String getReqParam(HttpServletRequest request) {
        String deviceId = request.getHeader(LoginPar.deviceId.toString());
        String clientIp = request.getHeader(LoginPar.clientIp.toString());
        String sessionToken = request.getHeader(LoginPar.sessionToken.toString());
        String module = request.getHeader(LoginPar.module.toString());
        String version = request.getHeader(LoginPar.version.toString());
        String clientType = request.getHeader(LoginPar.clientType.toString());
        return String.format(
                "{RequestPojo:{deviceId:%s,clientIp:%s,sessionToken:%s,module:%s,version:%s,clientType:%s}}",
                SaltUtil.saltInfo(deviceId), clientIp, SaltUtil.saltInfo(sessionToken), module,
                version, clientType);
    }

}
