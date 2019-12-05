package com.xiaodou.st.dashboard.service.manage;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyCountDO;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.staticinfo.ApplyCollectVO;
import com.xiaodou.st.dashboard.domain.staticinfo.StudentCollectVO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.exception.ExcelException;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.util.ExcelUtil;
import com.xiaodou.st.dashboard.util.StandardProp;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class ManageApplyCollectService {
  @Resource
  IStServiceFacade stServiceFacade;

  public List<ApplyCollectVO> listApplyCollectVO() {
    List<ApplyCollectVO> resultList = Lists.newArrayList();
    List<String> groups = Lists.newArrayList();
    groups.add("pilotUnitId");
    Page<ApplyCountDO> applyPage =
        stServiceFacade.groupCatApply(null, CommUtil.getAllField(ApplyCountDO.class), groups);
    if (CollectionUtils.isEmpty(applyPage.getResult())) {
      return resultList;
    }
    Map<Long, List<ApplyDO>> tempMap = wrapPilotUnitMapFromApply();
    Integer totalApplySCPlus = 0;
    Integer totalApplyACPlus = 0;
    Integer notPaymentACPlus = 0;
    Integer waitingPaymentACPlus = 0;
    Integer alreadyPaymentACPlus = 0;
    Integer applyACPlus = 0;
    Integer businessApplySuccessACPlus = 0;
    Integer businessApplyAlreadyACPlus = 0;
    Map<Long, ApplyCollectVO> pilotUnitMap = Maps.newHashMap();
    ApplyCollectVO vo;
    for (ApplyCountDO acdo : applyPage.getResult()) {
      vo = new ApplyCollectVO();
      vo.setPilotUnitId(acdo.getPilotUnitId());
      vo.setPilotUnitName(acdo.getPilotUnitName());
      vo.setTotalApplySC(acdo.getStudentNum());
      vo.setTotalApplyAC(acdo.getApplyCount());
      if (tempMap.containsKey(acdo.getPilotUnitId())) {
        Integer notPaymentAC = 0;
        Integer waitingPaymentAC = 0;
        Integer alreadyPaymentAC = 0;
        Integer applyAC = 0;
        Integer businessApplySuccessAC = 0;
        Integer businessApplyAlreadyAC = 0;
        if (!CollectionUtils.isEmpty(tempMap.get(acdo.getPilotUnitId()))) {
          for (ApplyDO ado : tempMap.get(acdo.getPilotUnitId())) {
            switch (ado.getOrderStatus().shortValue()) {
              case Constants.NOTPAYMENT:
                notPaymentAC++;
                break;
              case Constants.WAITINGPAYMENT:
                waitingPaymentAC++;
                break;
              case Constants.ALREADYPAYMENT:
                alreadyPaymentAC++;
                break;
              default:
                break;
            }
            switch (ado.getApplyStatus().shortValue()) {
              case Constants.APPLY_SUCCESS:
                applyAC++;
                break;
              case Constants.BUSINESS_APPLY_SUCCESS:
                businessApplySuccessAC++;
                break;
              case Constants.BUSINESS_APPLY_ALREADY:
                businessApplyAlreadyAC++;
                break;
              default:
                break;
            }
          }
        }
        vo.setNotPaymentAC(notPaymentAC);
        vo.setWaitingPaymentAC(waitingPaymentAC);
        vo.setAlreadyPaymentAC(alreadyPaymentAC);
        vo.setApplyAC(applyAC);
        vo.setBusinessApplySuccessAC(businessApplySuccessAC);
        vo.setBusinessApplyAlreadyAC(businessApplyAlreadyAC);

        notPaymentACPlus += notPaymentAC;
        waitingPaymentACPlus += waitingPaymentAC;
        alreadyPaymentACPlus += alreadyPaymentAC;
        applyACPlus += applyAC;
        businessApplySuccessACPlus += businessApplySuccessAC;
        businessApplyAlreadyACPlus += businessApplyAlreadyAC;
      }
      totalApplySCPlus += acdo.getStudentNum();
      totalApplyACPlus += acdo.getApplyCount();
      pilotUnitMap.put(acdo.getPilotUnitId(), vo);
    }

    resultList = Lists.newArrayList(pilotUnitMap.values());
    vo = new ApplyCollectVO();
    vo.setPilotUnitName("总计");
    vo.setTotalApplySC(totalApplySCPlus);
    vo.setTotalApplyAC(totalApplyACPlus);
    vo.setNotPaymentAC(notPaymentACPlus);
    vo.setWaitingPaymentAC(waitingPaymentACPlus);
    vo.setAlreadyPaymentAC(alreadyPaymentACPlus);
    vo.setApplyAC(applyACPlus);
    vo.setBusinessApplySuccessAC(businessApplySuccessACPlus);
    vo.setBusinessApplyAlreadyAC(businessApplyAlreadyACPlus);
    resultList.add(0, vo);
    return resultList;
  }

  private Map<Long, List<ApplyDO>> wrapPilotUnitMapFromApply() {
    Map<Long, List<ApplyDO>> map = Maps.newHashMap();
    Page<ApplyDO> page = stServiceFacade.listApply(null, CommUtil.getAllField(ApplyDO.class), null);
    for (ApplyDO ado : page.getResult()) {
      if (!map.containsKey(ado.getPilotUnitId())) {
        map.put(ado.getPilotUnitId(), Lists.newArrayList(ado));
      } else {
        map.get(ado.getPilotUnitId()).add(ado);
      }
    }
    return map;
  }

  private Map<Long, List<StudentDO>> wrapPilotUnitMapFromStudent() {
    Map<Long, List<StudentDO>> map = Maps.newHashMap();
    Page<StudentDO> page =
        stServiceFacade.listStudent(null, CommUtil.getAllField(StudentDO.class), null);
    for (StudentDO ado : page.getResult()) {
      if (!map.containsKey(ado.getPilotUnitId())) {
        map.put(ado.getPilotUnitId(), Lists.newArrayList(ado));
      } else {
        map.get(ado.getPilotUnitId()).add(ado);
      }
    }
    return map;
  }

  public List<StudentCollectVO> listStudentCollectVO() {
    List<StudentCollectVO> resultList = Lists.newArrayList();
    Map<Long, List<StudentDO>> tempMap = wrapPilotUnitMapFromStudent();
    Iterator<Entry<Long, List<StudentDO>>> iterator = tempMap.entrySet().iterator();
    Integer notRegisterSCPlus = 0;
    Integer successRegisterSCPlus = 0;
    Integer failRegisterSCPlus = 0;
    Integer errorRegisterSCPlus = 0;
    Integer successImportSCPlus = 0;
    Integer totalSCPlus = 0;
    Map<Long, StudentCollectVO> pilotUnitMap = Maps.newHashMap();
    StudentCollectVO vo;
    while (iterator.hasNext()) {
      vo = new StudentCollectVO();
      Integer notRegisterSC = 0;
      Integer successRegisterSC = 0;
      Integer failRegisterSC = 0;
      Integer errorRegisterSC = 0;
      Integer successImportSC = 0;
      Integer totalSC = 0;
      Entry<Long, List<StudentDO>> entry = iterator.next();
      List<StudentDO> list = entry.getValue();
      vo.setPilotUnitId(entry.getKey());
      vo.setPilotUnitName(list.get(0).getPilotUnitName());
      for (StudentDO sdo : list) {
        switch (sdo.getStudentStatus().shortValue()) {
          case Constants.NOT_REGISTER:
            notRegisterSC++;
            break;
          case Constants.SUCCESS_REGISTER:
            successRegisterSC++;
            break;
          case Constants.FAIL_REGISTER:
            failRegisterSC++;
            break;
          case Constants.ERROR_REGISTER:
            errorRegisterSC++;
            break;
          case Constants.SUCCESS_IMPORT:
            successImportSC++;
            break;
          default:
            break;
        }
        totalSC++;
      }
      vo.setNotRegisterSC(notRegisterSC);
      vo.setSuccessRegisterSC(successRegisterSC);
      vo.setFailRegisterSC(failRegisterSC);
      vo.setErrorRegisterSC(errorRegisterSC);
      vo.setSuccessImportSC(successImportSC);
      vo.setTotalSC(totalSC);

      notRegisterSCPlus += notRegisterSC;
      successRegisterSCPlus += successRegisterSC;
      failRegisterSCPlus += failRegisterSC;
      errorRegisterSCPlus += errorRegisterSC;
      successImportSCPlus += successImportSC;
      totalSCPlus += totalSC;

      pilotUnitMap.put(entry.getKey(), vo);
    }

    resultList = Lists.newArrayList(pilotUnitMap.values());
    vo = new StudentCollectVO();
    vo.setPilotUnitName("总计");
    vo.setNotRegisterSC(notRegisterSCPlus);
    vo.setSuccessRegisterSC(successRegisterSCPlus);
    vo.setFailRegisterSC(failRegisterSCPlus);
    vo.setErrorRegisterSC(errorRegisterSCPlus);
    vo.setSuccessImportSC(successImportSCPlus);
    vo.setTotalSC(totalSCPlus);
    resultList.add(0, vo);
    return resultList;
  }

  @Data
  class StudentCollectDTO {
    /* 第三级单位名称 */
    private String pilotUnitName;
    /* 班级名称 */
    private String className;
    /* realName 姓名 */
    private String realName;
    /* gender 性别 */
    private String gender;
    /* telephone 手机号 */
    private String telephone;
    /* admissionCardCode 准考证号 */
    private String admissionCardCode = StringUtils.EMPTY;
    /* 学生状态 0、未注册，1、注册成功，2、注册失败，已经存在该学生3、注册异常，4、成功导入 */
    private String studentStatus;
  }

  enum TempEnum {
    MAN("gender-1", "男"), WOMAN("gender-2", "女"),

    NOT_REGISTER("studentStatus-0", "未注册"), SUCCESS_REGISTER("studentStatus-1", "注册成功"), FAIL_REGISTER(
        "studentStatus-2", "注册失败"), ERROR_REGISTER("studentStatus-3", "注册异常"), SUCCESS_IMPORT(
        "studentStatus-4", "成功导入"),

    WAITINGPAYMENT("orderStatus-0", "待缴费"), ALREADYPAYMENT("orderStatus-1", "已缴费"), NOTPAYMENT(
        "orderStatus-2", "未缴费"),

    APPLY_SUCCESS("applyStatus-0", "后台报名完成"), BUSINESS_APPLY_SUCCESS("applyStatus-1", "业务系统报名成功"), BUSINESS_APPLY_ALREADY(
        "applyStatus-2", "已经购买该课程"), BUSINESS_APPLY_ERROR("applyStatus-3", "报名异常");
    private String code;
    private String msg;

    private TempEnum(String code, String msg) {
      this.code = code;
      this.msg = msg;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }

    private static Map<String, String> map = Maps.newHashMap();
    static {
      for (TempEnum genderEnum : TempEnum.values()) {
        map.put(genderEnum.getCode(), genderEnum.getMsg());
      }
    }

    public static String getMsgByTypeAndCode(String type, String code) {
      return map.get(String.format("%s-%s", type, code));
    }

  }

  public void downloadStudentCollectVOExcel(String sheetName, String fileName, Long pilotUnitId,
      Short studentStatus, HttpServletResponse response) throws ExcelException {
    Map<String, Object> input = Maps.newHashMap();
    if (null != pilotUnitId) {
      input.put("pilotUnitId", pilotUnitId);
    }
    if (null != studentStatus) {
      input.put("studentStatus", studentStatus);
    }
    Page<StudentDO> page =
        stServiceFacade.listStudent(input, CommUtil.getAllField(StudentDO.class), null);
    Assert.notNull(page.getResult(), "list is not null!");
    List<StudentCollectDTO> list = Lists.newArrayList();
    StudentCollectDTO scdto;
    for (StudentDO sdo : page.getResult()) {
      scdto = new StudentCollectDTO();
      scdto.setAdmissionCardCode(sdo.getAdmissionCardCode());
      scdto.setClassName(sdo.getClassName());
      scdto.setGender(TempEnum.getMsgByTypeAndCode("gender", sdo.getGender()));
      scdto.setPilotUnitName(sdo.getPilotUnitName());
      scdto.setRealName(sdo.getRealName());
      scdto.setStudentStatus(TempEnum.getMsgByTypeAndCode("studentStatus", sdo.getStudentStatus()
          .toString()));
      scdto.setTelephone(sdo.getTelephone());
      list.add(scdto);
    }
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    fieldMap.put("pilotUnitName", StandardProp.pilotUnitName() + "名称");
    fieldMap.put("className", "班级名称");
    fieldMap.put("realName", "姓名");
    fieldMap.put("gender", "性别");
    fieldMap.put("telephone", "手机号");
    fieldMap.put("admissionCardCode", "准考证号");
    fieldMap.put("studentStatus", "学生账号状态");
    ExcelUtil.listToExcel(list, fieldMap, sheetName, response, fileName);
  }

  @Data
  class ApplyCollectDTO {
    /* 第三级单位名称 */
    private String pilotUnitName;
    /* 管理教师姓名 */
    private String adminName;
    /* 所在班级名称 */
    private String className;
    private String studentName;
    private String telephone;
    /* 准考证号 */
    private String admissionCardCode;
    /* catName 专业名称 */
    private String catName;
    /* courseName 所选课程 */
    private String productName;
    /* orderNumber 订单号 */
    private Long orderNumber;
    /* 订单状态0：待缴费，1：已缴费，2未缴费 */
    private String orderStatus;
    /* 报名状态（0、后台报名完成1、业务系统报名成功2、已经购买该课程3、报名异常） */
    private String applyStatus;
  }

  public void downloadApplyCollectVOExcel(String sheetName, String fileName, Long pilotUnitId,
      Short orderStatus, Short applyStatus, HttpServletResponse response) throws ExcelException {
    Map<String, Object> input = Maps.newHashMap();
    if (null != pilotUnitId) {
      input.put("pilotUnitId", pilotUnitId);
    }
    if (null != orderStatus) {
      input.put("orderStatus", orderStatus);
    }
    if (null != applyStatus) {
      input.put("applyStatus", applyStatus);
    }
    Page<ApplyDO> page =
        stServiceFacade.listApply(input, CommUtil.getAllField(ApplyDO.class), null);
    Assert.notNull(page.getResult(), "list is not null!");
    List<ApplyCollectDTO> list = Lists.newArrayList();
    ApplyCollectDTO acdto;
    for (ApplyDO ado : page.getResult()) {
      acdto = new ApplyCollectDTO();
      acdto.setPilotUnitName(ado.getPilotUnitName());
      acdto.setAdminName(ado.getAdminName());
      acdto.setClassName(ado.getClassName());
      acdto.setStudentName(ado.getStudentName());
      acdto.setTelephone(ado.getTelephone());
      acdto.setAdmissionCardCode(ado.getAdmissionCardCode());
      acdto.setCatName(ado.getCatName());
      acdto.setProductName(ado.getProductName());
      acdto.setOrderNumber(ado.getOrderNumber());
      acdto.setOrderStatus(TempEnum.getMsgByTypeAndCode("orderStatus", ado.getOrderStatus()
          .toString()));
      acdto.setApplyStatus(TempEnum.getMsgByTypeAndCode("applyStatus", ado.getApplyStatus()
          .toString()));
      list.add(acdto);
    }
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    fieldMap.put("pilotUnitName", StandardProp.pilotUnitName() + "名称");
    fieldMap.put("adminName", "管理教师姓名");
    fieldMap.put("className", "班级名称");
    fieldMap.put("studentName", "姓名");
    fieldMap.put("telephone", "手机号");
    fieldMap.put("admissionCardCode", "准考证号");
    fieldMap.put("catName", "专业名称");
    fieldMap.put("productName", "课程名称");
    fieldMap.put("orderNumber", "订单号");
    fieldMap.put("orderStatus", "订单状态");
    fieldMap.put("applyStatus", "报名状态");
    ExcelUtil.listToExcel(list, fieldMap, sheetName, response, fileName);
  }

}
