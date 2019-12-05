package com.xiaodou.control.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.control.dao.admin.AdminRoleDao;
import com.xiaodou.control.dao.admin.RoleDao;
import com.xiaodou.control.dao.admin.RolePrivilegeDao;
import com.xiaodou.control.model.admin.AdminRole;
import com.xiaodou.control.model.admin.Role;
import com.xiaodou.control.model.admin.RolePrivilege;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 14-9-2.
 */
@Service("adminRoleService")
public class AdminRoleService {
	@Resource
	RolePrivilegeDao rolePrivilegeDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private AdminRoleDao adminRoleDao;

	/**
	 * 添加角色
	 * 
	 * @param role
	 */
	public void addRole(Role role) {
		this.roleDao.addEntity(role);
	}

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	public Boolean deleteRole(Integer roleId) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", roleId);
		Boolean result = this.roleDao.deleteEntityByCond(cond);
		this.deleteRoleAllPrivilege(roleId);
		this.deleteRoleAdminsRelations(roleId);
		return result;
	}

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return
	 */
	public Boolean updateRole(Role role) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", role.getId());
		return this.roleDao.updateEntityByCond(cond, role);
	}

	/**
	 * 根据条件查询角色列表
	 * 
	 * @param cond
	 * @return
	 */
	public List<Role> queryRolesByCond(Map<String, Object> cond) {
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(Role.class));
		return this.roleDao.findEntityListByCond(param, null).getResult();
	}

	/**
	 * 根据id查询角色
	 * 
	 * @param roleId
	 * @return
	 */
	public Role findRoleById(Integer roleId) {
		Role role = new Role();
		role.setId(roleId);
		return this.roleDao.findEntityById(role);
	}

	/**
	 * 删除本角色的全部权限
	 * 
	 * @param roleId
	 */
	public void deleteRoleAllPrivilege(Integer roleId) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("roleId", roleId);
		this.rolePrivilegeDao.deleteEntityByCond(cond);
	}

	/**
	 * 查询某角色的全部权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RolePrivilege> queryRolePrivileges(Integer roleId) {
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("roleId", roleId);
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(RolePrivilege.class));
		return this.rolePrivilegeDao.findEntityListByCond(param, null)
				.getResult();
	}

	/**
	 * 查询某角色下的全部管理员
	 * 
	 * @param roleId
	 */
	public List<AdminRole> queryRoleAdmins(Integer roleId) {
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("roleId", roleId);
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(AdminRole.class));
		return this.adminRoleDao.findEntityListByCond(param, null).getResult();
	}

	/**
	 * 删除角色管理员管理关系
	 * 
	 * @param roleId
	 */
	public void deleteRoleAdminsRelations(Integer roleId) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("roleId", roleId);
		this.adminRoleDao.deleteEntityByCond(cond);
	}

	/**
	 * 添加管理员角色
	 * 
	 * @param adminId
	 * @param roleIds
	 */
	public void addAdminRole(Integer adminId, List<Integer> roleIds) {
		for (Integer roleId : roleIds) {
			AdminRole adminRole = new AdminRole();
			adminRole.setRoleId(roleId);
			adminRole.setAdminId(adminId);
			adminRoleDao.addEntity(adminRole);
		}
	}

	/**
	 * 删除管理员角色
	 * 
	 * @param adminId
	 * @param roleIds
	 * @return
	 */
	public Boolean deleteAdminRole(Integer adminId, List<Integer> roleIds) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("adminId", adminId);
		cond.put("roleIds", roleIds);
		return adminRoleDao.deleteEntityByCond(cond);
	}
	/**
	 * 设置权限
	 * 
	 * @param adminId
	 * @param roleIds
	 * @return
	 */
	public void assigenRole(Integer adminId, String needDeleteId,String needAddId){
		if (StringUtils.isNotBlank(needDeleteId)) {
			String[] needDeleteIdArray = needDeleteId.split(";");
			List<Integer> needDeleteIdList = new ArrayList<>();
			for (String id : needDeleteIdArray) {
				needDeleteIdList.add(Integer.parseInt(id));
			}
			this.deleteAdminRole(adminId, needDeleteIdList);
		}

		if (StringUtils.isNotBlank(needAddId)) {
			String[] needAddIdArray = needAddId.split(";");
			List<Integer> needAddIdList = new ArrayList<>();
			for (String id : needAddIdArray) {
				needAddIdList.add(Integer.parseInt(id));
			}
			this.addAdminRole(adminId, needAddIdList);
		}
     }
}
