package com.xiaodou.login.service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.login.dao.AdminDao;
import com.xiaodou.login.model.Admin;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouhuan on 17.08.15.
 */
public class AdminService {

	private AdminDao adminDao;

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
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

}
