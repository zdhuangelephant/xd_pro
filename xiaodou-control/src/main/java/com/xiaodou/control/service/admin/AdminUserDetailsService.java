package com.xiaodou.control.service.admin;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import com.xiaodou.control.model.admin.Admin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zyp on 14-9-3.
 */
public class AdminUserDetailsService implements UserDetailsManager {

	private AdminService adminService;

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * 根据用户名查询
	 * 
	 * @param userName
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		Admin admin = adminService.findAdminByUserName(userName);
		AdminUser user = null;
		if (admin == null) {
			throw new UsernameNotFoundException(userName);
		}

		Collection<GrantedAuthority> grantedAuthorities = this
				.getGrantedAuthoritys(admin);
		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		user = new AdminUser(admin, enables, accountNonExpired,
				credentialsNonExpired, accountNonLocked, grantedAuthorities);
		return user;
	}

	/**
	 * 获取用户的权限集合
	 * 
	 * @param admin
	 * @return
	 */
	private Set<GrantedAuthority> getGrantedAuthoritys(Admin admin) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		return authSet;
	}

	/**
	 * 创建用户
	 * 
	 * @param user
	 */
	@Override
	public void createUser(UserDetails user) {

	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	@Override
	public void updateUser(UserDetails user) {

	}

	/**
	 * 删除用户
	 * 
	 * @param username
	 */
	@Override
	public void deleteUser(String username) {

	}

	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 */
	@Override
	public void changePassword(String oldPassword, String newPassword) {

	}

	/**
	 * 用户是否存在
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public boolean userExists(String username) {
		return false;
	}
}
