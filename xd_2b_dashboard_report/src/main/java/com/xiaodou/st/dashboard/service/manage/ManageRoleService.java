package com.xiaodou.st.dashboard.service.manage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.domain.admin.RoleDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;

@Service
public class ManageRoleService {

  @Resource
  IStServiceFacade stServiceFacade;

  public List<RoleDO> listRole(Map<String, Object> inputs) {
    return stServiceFacade.listManageRole(inputs, CommUtil.getAllField(RoleDO.class)).getResult();
  }

  /**
   * 
   * @description 增加角色
   * @author 李德洪
   * @Date 2017年6月26日
   * @param roleDO
   * @return
   */
  public String saveRole(RoleDO roleDO) {
    boolean flag = stServiceFacade.saveRole(roleDO);
    return String.valueOf(flag);
  }

  /**
   * 
   * @description 修改角色信息
   * @author 李德洪
   * @Date 2017年6月26日
   * @param roleDO
   * @return
   */
  public String updateRole(RoleDO roleDO) {
    boolean flag = stServiceFacade.updateRole(roleDO);
    return String.valueOf(flag);
  }

  /**
   * 
   * @description 查詢角色
   * @author 李德洪
   * @Date 2017年4月1日
   * @param unitId
   * @return
   */
  public RoleDO getRole(Integer roleId) {
    return stServiceFacade.getRoleById(roleId);
  }

  /**
   * 
   * @description 刪除角色
   * @author 李德洪
   * @Date 2017年4月1日
   * @param unitId
   * @return
   */
  public String removeRole(Integer roleId) {
    boolean flag = stServiceFacade.removeRole(roleId);
    return String.valueOf(flag);
  }
}
