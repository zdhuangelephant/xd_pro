package com.xiaodou.control.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.control.dao.admin.PrivilegeDao;
import com.xiaodou.control.dao.admin.RolePrivilegeDao;
import com.xiaodou.control.model.admin.Privilege;
import com.xiaodou.control.model.admin.Role;
import com.xiaodou.control.model.admin.RolePrivilege;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 14-9-2.
 * <p/>
 * 权限service
 */
@Service("adminPrivilegeService")
public class AdminPrivilegeService {

	/**
	 * 权限DAO
	 */
	@Resource
	PrivilegeDao privilegeDao;

	/**
	 * 角色权限DAO
	 */
	@Resource
	RolePrivilegeDao rolePrivilegeDao;

	/**
	 * 角色service
	 */
	@Resource
	AdminRoleService adminRoleService;

	/**
	 * 管理员service
	 */
	@Resource
	AdminService adminService;

	public AdminRoleService getAdminRoleService() {
		return adminRoleService;
	}

	public void setAdminRoleService(AdminRoleService adminRoleService) {
		this.adminRoleService = adminRoleService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * 获取管理员的所有权限
	 * 
	 * @param adminId
	 * @return
	 */
	public List<Privilege> queryAdminAllRolePrivilege(Integer adminId) {

		// 获取所有角色
		List<Role> roles = adminService.queryAdminRoles(adminId);
		List<Integer> roleIds = new ArrayList<>();
		Boolean isSuperMan = false;
		for (Role role : roles) {
			roleIds.add(role.getId());
			if (role.getId().equals(1)) {
				isSuperMan = true;
				break;
			}
		}

		if (isSuperMan) {
			return this.queryAllPrivileges();
		}

		// 获取角色的权限列表
		List<Privilege> rolePrivileges = new ArrayList<>();
		Map<String, Object> cond = new HashMap<>();
		cond.put("roleIds", roleIds);
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(Privilege.class));
		Page<RolePrivilege> privilegeList = rolePrivilegeDao.findEntityListByCond(param, null);
		List<Integer> rolePrivilegeIds = new ArrayList<>();
		for (RolePrivilege privilege : privilegeList.getResult()) {
			rolePrivilegeIds.add(privilege.getPrivilegeId());
		}
		if (rolePrivilegeIds.size() > 0) {
			rolePrivileges = this.queryPrivilegeByIds(rolePrivilegeIds);
		}

		return rolePrivileges;
	}

	/**
	 * 根据账号获取所有权限
	 * 
	 * @param adminId
	 * @return
	 */
	public List<Privilege> queryAllPrivilegeByAdmin(List<Integer> roleIds) {
		List<Privilege> privileges = new ArrayList<>();
		if (roleIds.size() > 0) {
			Map<String, Object> cond = new HashMap<>();
			cond.put("roleIds", roleIds);
			IQueryParam param = new QueryParam();
			 param.addInputs(cond);
			 param.addOutputs(CommUtil.getAllField(Privilege.class));
			Page<RolePrivilege> privilegeList = rolePrivilegeDao.findEntityListByCond(param, null);
			Map<Integer, RolePrivilege> privilegeMap = new HashMap<>();
			List<Integer> privilegeIds = new ArrayList<>();
			for (RolePrivilege privilege : privilegeList.getResult()) {
				if (!privilegeMap.containsKey(privilege.getPrivilegeId())) {
					privilegeMap.put(privilege.getPrivilegeId(), privilege);
					privilegeIds.add(privilege.getPrivilegeId());
				}
			}
			if (privilegeIds.size() > 0) {
				privileges = this.queryShowPrivilegeByIds(privilegeIds);
			}
		}
		List<Integer> ids = new ArrayList<Integer>();
		for (Privilege privilege : privileges) {
			ids.add(privilege.getId());
		}
		List<Privilege> privilegeList = this.queryPrivilegeByIds(ids);
		return privilegeList;
	}

	/**
	 * 新增权限
	 * 
	 * @param adminPrivilege
	 * @return
	 */
	public Privilege addPrivilege(Privilege adminPrivilege) {
		Privilege privilege = privilegeDao.addEntity(adminPrivilege);
		return privilege;
	}

	/**
	 * 添加角色权限
	 * 
	 * @param roleId
	 * @param priIds
	 */
	public void addRolePrivilege(Integer roleId, List<Integer> priIds) {
		for (Integer priId : priIds) {
			RolePrivilege rolePrivilege = new RolePrivilege();
			rolePrivilege.setRoleId(roleId);
			rolePrivilege.setPrivilegeId(priId);
			rolePrivilegeDao.addEntity(rolePrivilege);
		}
	}

	/**
	 * 删除角色权限
	 * 
	 * @param roleId
	 * @param priIds
	 * @return
	 */
	public Boolean deleteRolePrivilege(Integer roleId, List<Integer> priIds) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("roleId", roleId);
		cond.put("privilegeIds", priIds);
		return rolePrivilegeDao.deleteEntityByCond(cond);
	}

	/**
	 * 删除权限
	 * 
	 * @param groupType
	 *            服务组号
	 * @return
	 */
	public Boolean deletePrivilege(String groupType) {
		// 删除本权限
		Map<String, Object> cond = new HashMap<>();
		cond.put("groupType", groupType);
		boolean result = privilegeDao.deleteEntityByCond(cond);
		Privilege privilege = this.queryPrivilegesByGroupId(groupType);
		this.deletePrivilegeRole(privilege.getId());
		return result;
	}

	/**
	 * 根据Id查找权限
	 * 
	 * @param ids
	 * @return
	 */
	public List<Privilege> queryPrivilegeByIds(List<Integer> ids) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("ids", ids);
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(Privilege.class));
		Page<Privilege> adminPrivilegePage = privilegeDao.findEntityListByCond(param, null);
		return adminPrivilegePage.getResult();
	}

	/**
	 * 查询所有可显示
	 * 
	 * @param ids
	 * @return
	 */
	public List<Privilege> queryShowPrivilegeByIds(List<Integer> ids) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("ids", ids);
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(Privilege.class));
		Page<Privilege> adminPrivilegePage = privilegeDao.findEntityListByCond(param, null);
		return adminPrivilegePage.getResult();
	}

	/**
	 * 根据Url查询
	 * 
	 * @param url
	 * @return
	 */
	public Privilege queryPrivilegesByGroupId(String groupType) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("groupType", groupType);
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(Privilege.class));
		Page<Privilege> adminPrivilegePage = privilegeDao.findEntityListByCond(param, null);
		if (adminPrivilegePage.getResult().size() == 0) {
			return null;
		} else {
			return adminPrivilegePage.getResult().get(0);
		}
	}

	/**
	 * 获取全部权限
	 * 
	 * @return
	 */
	public List<Privilege> queryAllPrivileges() {
		IQueryParam param = new QueryParam();
		 param.addOutputs(CommUtil.getAllField(Privilege.class));
		Page<Privilege> result = privilegeDao.findEntityListByCond(param, null);
		return result.getResult();
	}

	/**
	 * 获取全部权限
	 * 
	 * @return
	 */
	public List<Privilege> queryPrivilegeById(String groupType) {
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("groupType", groupType);
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(Privilege.class));
		Page<Privilege> result = privilegeDao.findEntityListByCond(param, null);
		return result.getResult();
	}

	/**
	 * 获取全部可见权限
	 * 
	 * @return
	 */
	public List<Privilege> queryAllDisplayPrivileges() {
		IQueryParam param = new QueryParam();
		 param.addOutputs(CommUtil.getAllField(Privilege.class));
		Page<Privilege> result = privilegeDao.findEntityListByCond(param, null);
		return result.getResult();
	}

	/**
	 * 根据权限号查询
	 * 
	 * @param privilegeId
	 * @return
	 */
	public Privilege findPrivilegeById(Integer privilegeId) {
		Privilege privilege = new Privilege();
		privilege.setId(privilegeId);
		return privilegeDao.findEntityById(privilege);
	}

	/**
	 * 更新权限信息
	 * 
	 * @param privilege
	 * @return
	 */
	public Boolean updatePrivilege(Privilege privilege) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("groupType", privilege.getGroupType());
		privilegeDao.updateEntityByCond(cond, privilege);
		return true;
	}

	/**
	 * 删除掉某权限的全部关系
	 * 
	 * @param priId
	 */
	public void deletePrivilegeRole(Integer priId) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", priId);
		this.rolePrivilegeDao.deleteEntityByCond(cond);
	}

	/**
	 * 查询某权限的全部角色
	 * 
	 * @param priId
	 * @return
	 */
	public List<Role> queryRolesByPrivilege(Integer priId) {
		Map<String, Object> cond = new HashMap<String, Object>();
		List<Role> roles = new ArrayList<Role>();
		cond.put("privilegeId", priId);
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(RolePrivilege.class));
		Page<RolePrivilege> AdminRolePrivileges = this.rolePrivilegeDao
				.findEntityListByCond(param, null);
		for (RolePrivilege rolePrivilege : AdminRolePrivileges.getResult()) {
			roles.add(adminRoleService.findRoleById(rolePrivilege.getRoleId()));
		}
		return roles;
	}
	
	/**
	 * 设置权限
	 * 
	 * @return
	 */
	public void setPrivilege(Integer roleId, String needDeleteId,
			String needAddId){
		if (StringUtils.isNotBlank(needDeleteId)) {
			String[] needDeleteIdArray = needDeleteId.split(";");
			if (needDeleteIdArray != null && needDeleteIdArray.length > 0) {
				List<Integer> needDeleteIdList = new ArrayList<>();
				for (String id : needDeleteIdArray) {
					needDeleteIdList.add(Integer.parseInt(id));
				}
				this.deleteRolePrivilege(roleId,
						needDeleteIdList);
			}
		}

		if (StringUtils.isNotBlank(needAddId)) {
			String[] needAddIdArray = needAddId.split(";");
			if (needAddIdArray != null && needAddIdArray.length > 0) {
				List<Integer> needAddIdList = new ArrayList<>();
				for (String id : needAddIdArray) {
					needAddIdList.add(Integer.parseInt(id));
				}
				this.addRolePrivilege(roleId, needAddIdList);
			}
		}
	}

}
