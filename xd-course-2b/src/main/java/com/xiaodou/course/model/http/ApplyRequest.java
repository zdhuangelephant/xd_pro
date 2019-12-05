package com.xiaodou.course.model.http;

import java.util.List;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

import lombok.Data;

public class ApplyRequest extends BaseValidatorPojo {

  private List<ApplyRequestDTO> listApplyRequest;

  public List<ApplyRequestDTO> getListApplyRequest() {
    return listApplyRequest;
  }

  public void setListApplyRequest(List<ApplyRequestDTO> listApplyRequest) {
    this.listApplyRequest = listApplyRequest;
  }

  @Data
  public static class ApplyRequestDTO {
    /**studentId 学生id*/
    private Integer studentId;
    /**userId 业务库用户id*/
    private Long userId;
    /**productId 课程id*/
    private Integer productId;
    private String telephone;
    /**module 课程所属地域*/
    private String module;
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
    
  }
  
}
