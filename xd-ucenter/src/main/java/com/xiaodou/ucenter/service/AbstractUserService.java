package com.xiaodou.ucenter.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.ucenter.domain.model.BaseUserDO;
import com.xiaodou.ucenter.service.facade.IUcenterServiceFacade;

public class AbstractUserService {

    @Resource
    IUcenterServiceFacade ucenterServiceFacade;


    public List<BaseUserDO> getUserByTel(String telephone) {
        Map<String, Object> cond = Maps.newHashMap();
        cond.put("telephone", telephone);
        return ucenterServiceFacade.listUser(cond, CommUtil.getAllField(BaseUserDO.class));
    }

    public List<BaseUserDO> getUserByXid(String xdUniqueId) {
        Map<String, Object> cond = Maps.newHashMap();
        cond.put("xdUniqueId", xdUniqueId);
        return ucenterServiceFacade.listUser(cond, CommUtil.getAllField(BaseUserDO.class));
    }

    public List<BaseUserDO> listUserByXids(List<String> xdUniqueIdList) {
        Map<String, Object> cond = Maps.newHashMap();
        if (xdUniqueIdList.size() == 1) {
            cond.put("xdUniqueId", xdUniqueIdList.get(0));
        } else {
            cond.put("xdUniqueIdList", xdUniqueIdList);
        }
        return ucenterServiceFacade.listUser(cond, CommUtil.getAllField(BaseUserDO.class));
    }

    public List<BaseUserDO> listAllUser() {
        return ucenterServiceFacade.listUser(null, CommUtil.getAllField(BaseUserDO.class));
    }
}
