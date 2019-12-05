package com.xiaodou.st.dashboard.domain.http;

import java.util.List;

import lombok.Data;

import com.xiaodou.summer.vo.out.ResultType;

public class StudentResponse extends ResponseBase{

  public StudentResponse(){};
  
  public StudentResponse(ResultType type) {
    super(type);
  }

  private List<StudentResponseDTO> listStudentResponse;

  public List<StudentResponseDTO> getListStudentResponse() {
    return listStudentResponse;
  }

  public void setListStudentResponse(List<StudentResponseDTO> listStudentResponse) {
    this.listStudentResponse = listStudentResponse;
  }

  @Data
  public static class StudentResponseDTO {

    private Integer studentId;// 学生id
    private Long userId;// 业务系统id
    private Short studentStatus;// 学生状态 0、未注册，1、注册成功，2、注册失败，已经存在该课程，3、注册异常，4、成功导入
    
    /***********/
    private String telephone;
  }

  
  @Data
  public static class StudentQueueMessageDTO{
    private List<StudentResponseDTO> listStudentResponseDTO;
    private String syncId;
  }
}
