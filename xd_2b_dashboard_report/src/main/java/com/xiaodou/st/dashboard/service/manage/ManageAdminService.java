package com.xiaodou.st.dashboard.service.manage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.domain.admin.AdminDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class ManageAdminService {
  @Resource
  IStServiceFacade stServiceFacade;

  public List<AdminDO> listManageAdmin(AdminDO adminDO) {
    Map<String, Object> inputs = Maps.newHashMap();
    INPUTS: {
      if (null == adminDO) break INPUTS;
      if (null != adminDO.getRole()) inputs.put("role", adminDO.getRole());
      if (null != adminDO.getUnitId()) inputs.put("unitId", adminDO.getUnitId());
      if (StringUtils.isNotBlank(adminDO.getUserName()))
        inputs.put("userNameLike", adminDO.getUserName());
      if (StringUtils.isNotBlank(adminDO.getRealName()))
        inputs.put("realNameLike", adminDO.getRealName());
      if (StringUtils.isNotBlank(adminDO.getEmail())) inputs.put("emailLike", adminDO.getEmail());
      if (null != adminDO.getTelephone()) inputs.put("telephoneLike", adminDO.getTelephone());
    }
    Page<AdminDO> page =
        stServiceFacade.listManageAdmin(inputs, CommUtil.getAllField(AdminDO.class));
    if (null == page) return null;
    return page.getResult();
  }

}
