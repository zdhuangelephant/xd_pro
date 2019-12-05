package com.xiaodou.course.model.http;

import java.util.List;

import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;

public class ApplyResponse extends ResponseBase {

  public ApplyResponse(ResultType type) {
    super(type);
  }

  private List<ApplyResponseDTO> listApplyResponse;

  public List<ApplyResponseDTO> getListApplyResponse() {
    return listApplyResponse;
  }

  public void setListApplyResponse(List<ApplyResponseDTO> listApplyResponse) {
    this.listApplyResponse = listApplyResponse;
  }

  @Data
  public static class ApplyResponseDTO {

    private Integer studentId;// 学生id
    private Integer productId;//
    private Short applyStatus;
    
    private String telephone;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Short getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Short applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
    
  }
  
}
