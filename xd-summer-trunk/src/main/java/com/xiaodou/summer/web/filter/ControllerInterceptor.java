package com.xiaodou.summer.web.filter;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.HttpMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.EmojiUtil;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.ActionEntity;
import com.xiaodou.common.util.log.model.BatchProcessEntity;
import com.xiaodou.common.util.log.model.OutInEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.util.ReqInfoWrapper;
import com.xiaodou.summer.validator.IValidator;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name ControllerInterceptor CopyRright (c) 2015 by <a
 *       href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月3日
 * @description 记录访问请求日志及req参数格式封装解析
 * @version 1.0
 */
public class ControllerInterceptor {

  private ICacheService cacheService = new DefaultCacheService();

  public ICacheService getCacheService() {
    return cacheService;
  }

  public void setCacheService(ICacheService cacheService) {
    this.cacheService = cacheService;
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
      object = new ResultInfo(ResultType.SYSFAIL);
      ((ResultInfo) object).appendRetdesc("#" + getErrMsg(e));
      LoggerUtil.error(joinPoint.getSignature().getName(), e);
      return FastJsonUtil.toJson(object);
    } finally {
      outIn.setResponseInfo(FastJsonUtil.toJson(object));
      Long time = System.currentTimeMillis() - startTime;
      outIn.setUseTime(time);// 记录方法耗时
      LoggerUtil.batchProcessInfo(outIn);
      ActionEntity action = new ActionEntity();
      String excutePointName =
          joinPoint.getSignature().getDeclaringTypeName() + "."
              + joinPoint.getSignature().getName();
      action.setExcutePoint(excutePointName);
      action.setLogName("out_in");
      action.setActionInfo(outIn);
      action.setHasError(hasError);
      action.setUseTime(time.toString());
      action.setErrorMessage(errorMessage);
      LoggerUtil.actionInfo(action);
    }
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

  public Object service(JoinPoint joinPoint) {
    long startTime = System.currentTimeMillis();
    Object object = null;
    Boolean hasError = false;
    String errorMessage = StringUtils.EMPTY;
    OutInEntity outIn = new OutInEntity();
    AtomicBoolean isCache = new AtomicBoolean(false);
    try {
      // 记录请求信息:方法类/方法名/请求参数
      Errors error = setOutInInfo(outIn, joinPoint, isCache);
      if (null != error && error.hasErrors()) {
        ResultInfo response = new ResultInfo(ResultType.VALFAIL);
        response.appendRetdesc("#" + getErrMsg(error));
        return FastJsonUtil.toJson(response);
      }
      // 如果使用缓存，先尝试从缓存获取
      if (isCache.get()) {
        object =
            cacheService.getCacheInfo(outIn.getTargetClass(), outIn.getTargetMethod(), outIn
                .getParams().toString());
        // 从缓存获取失败,则执行逻辑并写入缓存
        if (null == object) {
          object = ((ProceedingJoinPoint) joinPoint).proceed();
          cacheService.cacheInfo(outIn.getTargetClass(), outIn.getTargetMethod(), outIn.getParams()
              .toString(), object);
        }
        return object;
      }
      object = ((ProceedingJoinPoint) joinPoint).proceed();
      if (object instanceof String) object = EmojiUtil.resolveToEmojiFromByte(object.toString());
      return object;
    } catch (Throwable e) {
      object = new ResultInfo(ResultType.SYSFAIL);
      ((ResultInfo) object).appendRetdesc("#" + getErrMsg(e));
      LoggerUtil.error(joinPoint.getSignature().getName(), e);
      return FastJsonUtil.toJson(object);
    } finally {
      String result = StringUtils.EMPTY;
      if (object == null) {} else if (object instanceof String || object instanceof Integer
          || object instanceof Character || object instanceof Short || object instanceof Long
          || object instanceof Double || object instanceof Float || object instanceof Boolean
          || object instanceof Byte) {
        result = object.toString();

      } else if (object instanceof ResultInfo)
        result = ((ResultInfo) object).toString0();
      else
        result = FastJsonUtil.toJson(object);
      Long time = System.currentTimeMillis() - startTime;
      outIn.setResponseInfo(result);
      outIn.setUseTime(time);// 记录方法耗时
      ActionEntity action = new ActionEntity();
      String excutePointName =
          joinPoint.getSignature().getDeclaringTypeName() + "."
              + joinPoint.getSignature().getName();
      action.setExcutePoint(excutePointName);
      action.setLogName("out_in");
      action.setHasError(hasError);
      action.setUseTime(time.toString());
      action.setErrorMessage(errorMessage);
      LoggerUtil.actionInfo(action);
      outIn.setResponseInfo(getLimitString(result, 1000));
      LoggerUtil.outInInfo(outIn);
    }
  }

  private Errors setOutInInfo(OutInEntity outIn, JoinPoint joinPoint, AtomicBoolean isCache)
      throws IllegalArgumentException, IllegalAccessException {
    Errors error = null;
    outIn.setTargetClass(joinPoint.getTarget().getClass().getName());// 记录请求类
    outIn.setTargetMethod(joinPoint.getSignature().getName());// 记录请求方法
    Object[] args = joinPoint.getArgs();
    // 1 判断是否使用缓存
    isCache.set(cacheService.isCached(outIn.getTargetClass(), outIn.getTargetMethod()));

    final StringBuilder _params = new StringBuilder(500).append("{");
    if (null != args && args.length > 0) {
      for (int i = 0; i < args.length; i++) {
        if (args[i] instanceof IValidator) {
          if (needBuildParam()) {
            HttpServletRequest req = ReqInfoWrapper.getWrapper().getReq();
            String json = req.getParameter("req");
            json = EmojiUtil.resolveToByteFromEmoji(json);
            if (StringUtils.isJsonNotBlank(json)) {
              if (HttpMethod.GET.name().equals(req.getMethod())) json = getDecodeValue(json);
              args[i] = FastJsonUtil.fromJson(json, args[i].getClass());
            }
          }
          error = ((IValidator) args[i]).validate();
        }
        if (null != args[i] && !(args[i] instanceof ServletRequest)
            && !(args[i] instanceof ServletResponse))
          _params.append(JSON.toJSONString(args[i])).append(",");
      }
      setParamer(args, joinPoint, "methodInvocation", "arguments");
    }
    String param = _params.replace(_params.length() - 1, _params.length(), "}").toString();
    outIn.setParams(getLimitString(param, 1000));
    return error;
  }

  protected boolean needBuildParam() {
    return true;
  }

  private String getLimitString(String src, Integer limit) {
    return src.length() > limit + 5 ? src.substring(0, limit) : src;
  }

  private String getErrMsg(Throwable t) {
    if (StringUtils.isNotBlank(t.getMessage())) return t.getMessage();
    if (null != t.getCause()) return getErrMsg(t.getCause());
    return StringUtils.EMPTY;
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

  private String getDecodeValue(String paramValue) {
    try {
      return URLDecoder.decode(paramValue, "UTF-8");
    } catch (Exception e) {
      return paramValue;
    }
  }

  private void setParamer(Object value, Object joinPoint, String... fieldName)
      throws IllegalArgumentException, IllegalAccessException {
    Class<?> pointClass = joinPoint.getClass();
    if (!pointClass.equals(Object.class)) {
      setParamer(value, joinPoint, pointClass, fieldName);
    }
  }

  private void setParamer(Object value, Object joinPoint, Class<?> clazz, String... fieldName)
      throws IllegalArgumentException, IllegalAccessException {
    boolean isFind = false;
    for (Field field : clazz.getDeclaredFields()) {
      if (fieldName[0].equals(field.getName())) {
        isFind = true;
        field.setAccessible(true);
        if (fieldName.length > 1) {
          setParamer(value, field.get(joinPoint),
              Arrays.copyOfRange(fieldName, 1, fieldName.length));
        } else {

          field.set(joinPoint, value);
        }
      }
    }
    if (!isFind) {
      setParamer(value, joinPoint, clazz.getSuperclass(), fieldName);
    }
  }

  /**
   * @name CacheService CopyRright (c) 2015 by <a
   *       href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月3日
   * @description 缓存服务类
   * @version 1.0
   */
  public interface ICacheService {

    public static final String seperator = ":";

    /**
     * 缓存对象,根据业务需要自己实现
     * 
     * @param targetClass 目标类
     * @param targetMethod 目标方法
     * @param params 参数
     * @param object 待缓存对象
     */
    public void cacheInfo(String targetClass, String targetMethod, String params, Object object);

    /**
     * 获取缓存对象,根据业务需要自己实现
     * 
     * @param targetClass 目标类
     * @param targetMethod 目标方法
     * @param params 参数
     * @return 缓存对象
     */
    public Object getCacheInfo(String targetClass, String targetMethod, String params);

    /**
     * 判断是否使用了缓存,根据业务需要自己实现
     * 
     * @param targetClass
     * @param targetMethod
     * @return
     */
    public boolean isCached(String targetClass, String targetMethod);
  }

  /**
   * @name DefaultCacheService CopyRright (c) 2015 by <a
   *       href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月3日
   * @description 缓存服务默认空实现
   * @version 1.0
   */
  private class DefaultCacheService implements ICacheService {

    private CacheInfoProp prop = new CacheInfoProp();

    private String _prefix = prop.autoCache.getProperties("xiaodou.autocache.prefix");

    @Override
    public void cacheInfo(String targetClass, String targetMethod, String params, Object object) {
      String md5;
      try {
        md5 = CommUtil.HEXAndMd5(params);
      } catch (Exception e) {
        md5 = StringUtils.EMPTY;
      }
      String key = getKey(targetClass, targetMethod, md5);
      JedisUtil.addStringToJedis(key, object.toString(), getCacheTime(targetClass, targetMethod));
    }

    @Override
    public Object getCacheInfo(String targetClass, String targetMethod, String params) {
      String md5;
      try {
        md5 = CommUtil.HEXAndMd5(params);
      } catch (Exception e) {
        md5 = StringUtils.EMPTY;
      }
      return JedisUtil.getStringFromJedis(getKey(targetClass, targetMethod, md5));
    }

    @Override
    public boolean isCached(String targetClass, String targetMethod) {
      return "ON".equals(prop.autoCache.getProperties("xiaodou.autocache.switch"))
          && getCacheTime(targetClass, targetMethod) > 0;
    }

    private String getKey(String... prefixs) {
      StringBuilder keyBuilder = new StringBuilder(200).append(_prefix);
      for (String prefix : prefixs) {
        keyBuilder.append(prefix).append(ICacheService.seperator);
      }
      return keyBuilder.substring(0, keyBuilder.length() - 1).toString();
    }

    private Integer getCacheTime(String targetClass, String targetMethod) {
      return getCacheTime(getKey(targetClass, targetMethod));
    }

    private Integer getCacheTime(String key) {
      return prop.autoCache.getPropertiesInt(key);
    }

  }

  /**
   * @name @see com.xiaodou.summer.web.filter.CacheInfoProp.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月20日
   * @description 自动缓存配置文件
   * @version 1.0
   */
  private class CacheInfoProp {

    /**
     * 配置文件
     */
    private FileUtil autoCache = FileUtil.getInstance("/conf/custom/env/auto_cache.properties");

  }
}
