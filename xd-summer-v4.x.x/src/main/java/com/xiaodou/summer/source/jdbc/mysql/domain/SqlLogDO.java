package com.xiaodou.summer.source.jdbc.mysql.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SqlLogDO {

	private String jdbcUrl;
	/** statementId */
	private String statementId;
	/** sql sql语句 */
	private String sql;
	/** executTime 执行时间 ms */
	private Long executTiming;

	private Timestamp createTime;

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Long getExecutTiming() {
		return executTiming;
	}

	public void setExecutTiming(Long executTiming) {
		this.executTiming = executTiming;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
