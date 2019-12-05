package com.xiaodou.autotest.web.model.datasource;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * @name @see com.xiaodou.autotest.web.model.dataSource.DataSourceData.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年8月29日
 * @description 数据源参数项
 * @version 1.0
 */
public class DataSourceData {
	/** id  */
	@GeneralField
	@Column(canUpdate = true, sortBy = false, isMajor = true)
	private String id;
	/** alias 别名 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String alias;
	/** driverUrl 地址 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String driverUrl;
	/** userName 用户名 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String userName;
	/** password 密码 */
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String password;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDriverUrl() {
		return driverUrl;
	}

	public void setDriverUrl(String driverUrl) {
		this.driverUrl = driverUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(DataSourceData.class, "data_source_data",
				"E:/work3/autotest/src/main/resources/conf/mybatis/")
				.buildXml();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
