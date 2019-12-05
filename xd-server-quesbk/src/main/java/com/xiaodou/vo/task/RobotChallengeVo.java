package com.xiaodou.vo.task;

import com.xiaodou.domain.product.ChallengeRobot;
import com.xiaodou.vo.request.ExamDetailPojo;

public class RobotChallengeVo {

  public RobotChallengeVo() {}

  private ExamDetailPojo examDetailPojo;

  private ChallengeRobot robot;

  public ExamDetailPojo getExamDetailPojo() {
    return examDetailPojo;
  }

  public void setExamDetailPojo(ExamDetailPojo examDetailPojo) {
    this.examDetailPojo = examDetailPojo;
  }

  public ChallengeRobot getRobot() {
    return robot;
  }

  public void setRobot(ChallengeRobot robot) {
    this.robot = robot;
  }

}
