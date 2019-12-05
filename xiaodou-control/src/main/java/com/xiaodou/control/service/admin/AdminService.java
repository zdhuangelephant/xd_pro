package com.xiaodou.control.service.admin;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.control.dao.admin.AdminDao;
import com.xiaodou.control.dao.admin.AdminRoleDao;
import com.xiaodou.control.model.admin.Admin;
import com.xiaodou.control.model.admin.AdminRole;
import com.xiaodou.control.model.admin.Role;
import com.xiaodou.control.util.RandomUtils;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Created by zyp on 14-9-2.
 */
public class AdminService {

	private AdminDao adminDao;
	@Resource
	AdminRoleDao adminRoleDao;
	@Resource
	AdminRoleService adminRoleService;

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	/**
	 * 增加管理员
	 * 
	 * @param admin
	 */
	public void addAdmin(Admin admin) {
		String salt = RandomUtils.generateString(6);
		admin.setSalt(salt);
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		String password = md5PasswordEncoder.encodePassword(
				admin.getPassword(), salt);
		admin.setPassword(password);
		this.adminDao.addEntity(admin);
	}

	/**
	 * 查询管理员
	 * 
	 * @param cond
	 */
	public List<Admin> queryAdmin(Map<String, Object> cond) {
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(Admin.class));
		return this.adminDao.findEntityListByCond(param, null).getResult();
	}

	/**
	 * 查询管理员
	 * 
	 * @param adminId
	 */
	public Admin findAdminById(Integer adminId) {
		Admin admin = new Admin();
		admin.setId(adminId);
		return this.adminDao.findEntityById(admin);
	}

	/**
	 * 根据用户名查找用户
	 * 
	 * @param userName
	 * @return
	 */
	public Admin findAdminByUserName(String userName) {
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("userName", userName);
		List<Admin> admins = this.queryAdmin(cond);
		if (admins.size() == 0) {
			return null;
		} else {
			return admins.get(0);
		}
	}

	/**
	 * 验证密码
	 * 
	 * @param adminId
	 * @param password
	 * @return
	 */
	public Boolean validatePassword(Integer adminId, String password) {
		Admin admin = this.findAdminById(adminId);
		if (admin == null) {
			return false;
		} else {
			Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
			if (md5PasswordEncoder.encodePassword(password, admin.getSalt())
					.equals(admin.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 查询管理员的权限列表
	 * 
	 * @param adminId
	 * @return
	 */
	public List<Role> queryAdminRoles(Integer adminId) {
		List<Role> roles = new ArrayList<Role>();
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("adminId", adminId);
		IQueryParam param = new QueryParam();
		 param.addInputs(cond);
		 param.addOutputs(CommUtil.getAllField(Role.class));
		Page<AdminRole> adminAdminRoles = this.adminRoleDao
				.findEntityListByCond(param, null);
		for (AdminRole adminRole : adminAdminRoles.getResult()) {
			roles.add(this.adminRoleService.findRoleById(adminRole.getRoleId()));
		}
		return roles;
	}

	/**
	 * 删除管理员
	 * 
	 * @param adminId
	 */
	public Boolean deleteAdmin(Integer adminId) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", adminId);
		Boolean result = this.adminDao.deleteEntityByCond(cond);
		this.deleteAdminRolesReltion(adminId);
		return result;
	}

	/**
	 * 按照管理员Id删除关系
	 * 
	 * @param adminId
	 */
	public void deleteAdminRolesReltion(Integer adminId) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("adminId", adminId);
		adminRoleDao.deleteEntityByCond(cond);
	}

	/**
	 * 更新管理员
	 * 
	 * @param admin
	 */
	public Boolean updateAdmin(Admin admin) {
		// 判断密码是否为空
		if (StringUtils.isNotBlank(admin.getPassword())) {
			String salt = RandomUtils.generateString(6);
			admin.setSalt(salt);
			Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
			String password = md5PasswordEncoder.encodePassword(
					admin.getPassword(), salt);
			admin.setPassword(password);
		} else {
			admin.setSalt(null);
		}
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", admin.getId());
		return this.adminDao.updateEntityByCond(cond, admin);
	}

}
