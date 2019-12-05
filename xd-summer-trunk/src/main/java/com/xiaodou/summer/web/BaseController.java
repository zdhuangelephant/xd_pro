package com.xiaodou.summer.web;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 基础Controller,封装一些controller用到的基础方法
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-2-10
 */
public abstract class BaseController {
  /**
   * 参数异常提示信息
   * 
   * @param errors 错误
   * @return 错误提示
   */
  public static String getErrMsg(Errors errors) {
    List<ObjectError> errs = errors.getAllErrors();
    StringBuffer errInfo = new StringBuffer(200);
    for (ObjectError err : errs) {
      errInfo.append(err.getDefaultMessage());
    }
    return errInfo.toString();
  }

  /**
   * 提取Controller方法主干
   * 
   * @param pojo 参数Pojo
   * @param serviceHandler 业务方法Handler
   * @return resultInfoVO
   * @throws Exception
   */
  public <T extends BaseValidatorPojo> String doMain(T pojo, ServiceHandler<T> serviceHandler)
      throws Exception {
    try{
      if(null == pojo){
        return FastJsonUtil.toJson(new ResultInfo(ResultType.VALFAIL));
      }
      Errors errors = pojo.validate();
      ResultInfo res = null;
      if (errors.hasErrors()) {
        res = new ResultInfo(ResultType.VALFAIL);
        res.appendRetdesc(getErrMsg(errors));
      } else {
        res = serviceHandler.doService(pojo);
      }
      return FastJsonUtil.toJson(res);
    } catch (Throwable e){
      LoggerUtil.error("[Oms][请求失败]",e);
      throw e;
    }
  }

}
