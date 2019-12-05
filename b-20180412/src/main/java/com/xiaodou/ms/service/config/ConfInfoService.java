package com.xiaodou.ms.service.config;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.info.CommonInfoUtil;
import com.xiaodou.common.info.model.CommonInfo;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.ms.web.request.config.ConfInfoRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.config.ConfInfoResponse;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.common.info.dao.CommonInfoDao;

@Service("confInfoService")
public class ConfInfoService {

  @Resource
	CommonInfoDao confInfoDao;

  public Boolean delete(Integer id) {
    CommonInfo info = new CommonInfo();
    info.setId(id);
    return confInfoDao.deleteEntityById(info);
  }

  public List<CommonInfo> listConfInfo(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(CommUtil.getAllField(CommonInfo.class));
    queryParam.addSort("infoCode", Sort.DESC);
    return confInfoDao.findEntityListByCond(queryParam, null).getResult();
  }

  public CommonInfo findConfInfoById(Integer id) {
    CommonInfo info = new CommonInfo();
    info.setId(id);
    return confInfoDao.findEntityById(info);
  }

  public ConfInfoResponse edit(ConfInfoRequest request) {
    ConfInfoResponse response = new ConfInfoResponse(ResultType.SUCCESS);
    CommonInfo model = request.initModel();
    // boolean res = confInfoDao.updateEntityById(model);
    CommonInfo commonInfo = CommonInfoUtil.updateCommonInfoInfoByCode(model);
    if (null == commonInfo) return new ConfInfoResponse(ResultType.VALID_FAIL);
    return response;
  }

}
