package com.xiaodou.autotest.web.service.jsfunction;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.autotest.core.funclib.FuncLibFactory;
import com.xiaodou.autotest.web.constants.Cons;
import com.xiaodou.autotest.web.dao.jsfunction.JSFunctionDao;
import com.xiaodou.autotest.web.model.jsfunction.JSFunction;
import com.xiaodou.autotest.web.request.JsFunctionRequest;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("jSFunctionService")
public class JSFunctionService {
  
  @Resource
  JSFunctionDao jSFunctionDao;

  public JSFunction insertJSFunction(JsFunctionRequest request) {
    JSFunction func = new JSFunction();
    func.setCreateTime(new Timestamp(System.currentTimeMillis()));
    func.setFunSignature(request.getFunSignature());
    func.setFunctionBody(request.getFunctionBody());
    func.setUid(request.getUid());
    return jSFunctionDao.addEntity(func);
  }

  public Boolean updateDataSource(JsFunctionRequest request) {
    JSFunction func = new JSFunction();
    func.setFunSignature(request.getFunSignature());
    func.setFunctionBody(request.getFunctionBody());
    func.setId(request.getId());
    registJsFunctions(request);
    return jSFunctionDao.updateEntityById(func);
    
  }

  public Boolean removeJSFunctions(Long id) {
    JSFunction func = new JSFunction();
    func.setId(id);
    return jSFunctionDao.deleteEntityById(func);
    
  }

  public void registJsFunctions(JsFunctionRequest request) {
    if(StringUtils.isNotBlank(request.getFunSignature()) && 
        StringUtils.isNotBlank(request.getFunctionBody())) {
      FuncLibFactory.registScript(String.format(Cons.SCRIPT, request.getFunSignature(), request.getFunctionBody()));
    }
  }

  public List<JSFunction> findListByCond() {
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(JSFunction.class));
    Page<JSFunction> page = jSFunctionDao.findEntityListByCond(param, null); // findEntityListByCond
    return (null == page || page.getResult().size() == 0) ? null : page.getResult();
  }

  public JSFunction findEntityById(Long id) {
    JSFunction fun = new JSFunction();
    fun.setId(id);
    return jSFunctionDao.findEntityById(fun);
  }
}
