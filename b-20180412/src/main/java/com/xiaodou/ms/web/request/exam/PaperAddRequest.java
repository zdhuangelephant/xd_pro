package com.xiaodou.ms.web.request.exam;

import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/8/12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaperAddRequest extends BaseRequest {

  // 产品Id
  private Long productId;

  // 试卷名
  private String examName;

  // 描述
  private String mdesc;

  // 问题
  private String quesIds;

public Long getProductId() {
	return productId;
}

public void setProductId(Long productId) {
	this.productId = productId;
}

public String getExamName() {
	return examName;
}

public void setExamName(String examName) {
	this.examName = examName;
}

public String getMdesc() {
	return mdesc;
}

public void setMdesc(String mdesc) {
	this.mdesc = mdesc;
}

public String getQuesIds() {
	return quesIds;
}

public void setQuesIds(String quesIds) {
	this.quesIds = quesIds;
}


}
