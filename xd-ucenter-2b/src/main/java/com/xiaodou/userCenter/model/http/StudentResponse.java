package com.xiaodou.userCenter.model.http;

import java.util.List;

import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class StudentResponse extends ResponseBase{

  public StudentResponse(ResultType type) {
    super(type);
  }
  private List<StudentResponseDTO> listStudentResponse;

  @Data
  public static class StudentResponseDTO {

    private Integer studentId;// 学生id
    private Long userId;// 业务系统id
    private Short studentStatus;// 学生状态 0、未注册，1、注册成功，2、注册失败
    
    private String telephone;

  }

}
