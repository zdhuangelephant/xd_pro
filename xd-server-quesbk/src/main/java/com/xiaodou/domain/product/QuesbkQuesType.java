package com.xiaodou.domain.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class QuesbkQuesType extends BaseEntity {
  
  @Column(isMajor = true, betweenScope = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
  private Long id;

  private String typeName;

  private String status;

  private String mdesc;

  private String misc;

  private Short answerType;

  public void setTypeName(String typeName) {
    this.typeName = typeName == null ? null : typeName.trim();
  }

  public void setStatus(String status) {
    this.status = status == null ? null : status.trim();
  }


  public void setMdesc(String mdesc) {
    this.mdesc = mdesc == null ? null : mdesc.trim();
  }

  public void setMisc(String misc) {
    this.misc = misc == null ? null : misc.trim();
  }

  
  public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Short getAnswerType() {
	return answerType;
}

public void setAnswerType(Short answerType) {
	this.answerType = answerType;
}

public String getTypeName() {
	return typeName;
}

public String getStatus() {
	return status;
}

public String getMdesc() {
	return mdesc;
}

public String getMisc() {
	return misc;
}

public static void main(String[] args) {
    MybatisXmlTool.getInstance(QuesbkQuesType.class, "xd_quesbk_ques_type",
        "F:/xdworks/xd-server-quesbk-b20180102/src/main/resources/conf/mybatis/product/")
        .buildXml();;
  }
}
