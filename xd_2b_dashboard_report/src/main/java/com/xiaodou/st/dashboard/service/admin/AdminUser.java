package com.xiaodou.st.dashboard.service.admin;

import java.sql.Timestamp;
import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.xiaodou.st.dashboard.domain.admin.AdminDO;



/**
 * 
 * @name AdminUser CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月29日
 * @description TODO
 * @version 1.0
 */
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper=false)
@Data
public class AdminUser extends User {

  private Integer id;
  private String salt;

  private String realName;

  private String portrait;

  private String email;

  private Long telephone;

  private Integer userId;

  private String lastLoginIp;

  private Timestamp lastLoginTime;

  private Long unitId;

  private String unitName;
  private Short role;
  private String unitPortrait;
  private Double priceRate;
  
  private Short childRole;
  private String type;


  public AdminUser(AdminDO admin, Collection<? extends GrantedAuthority> authorities) {
    super(admin.getUserName(), admin.getPassword(), authorities);
    this.salt = admin.getSalt();
    this.realName = admin.getRealName();
    this.telephone = admin.getTelephone();
    this.userId = admin.getId();
    this.email = admin.getEmail();
    this.lastLoginIp = admin.getLastLoginIp();
    this.lastLoginTime = admin.getLastLoginTime();
    this.unitId = admin.getUnitId();
    this.role = admin.getRole();
    this.unitName = admin.getUnitName();
    this.portrait = admin.getPortrait();
    this.unitPortrait = admin.getUnitPortrait();
    this.priceRate = admin.getPriceRate();
    this.id = admin.getId();
    this.childRole = admin.getChildRole();
    this.type = admin.getType();
  }

  public AdminUser(AdminDO admin, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {
    super(admin.getUserName(), admin.getPassword(), enabled, accountNonExpired,
        credentialsNonExpired, accountNonLocked, authorities);
    this.salt = admin.getSalt();
    this.realName = admin.getRealName();
    this.telephone = admin.getTelephone();
    this.userId = admin.getId();
    this.email = admin.getEmail();
    this.lastLoginIp = admin.getLastLoginIp();
    this.lastLoginTime = admin.getLastLoginTime();
    this.unitId = admin.getUnitId();
    this.role = admin.getRole();
    this.unitName = admin.getUnitName();
    this.portrait = admin.getPortrait();
    this.unitPortrait = admin.getUnitPortrait();
    this.priceRate = admin.getPriceRate();
    this.id= admin.getId();
    this.childRole = admin.getChildRole();
    this.type = admin.getType();
  }

}
