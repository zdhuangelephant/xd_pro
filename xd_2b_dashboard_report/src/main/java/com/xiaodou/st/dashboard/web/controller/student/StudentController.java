package com.xiaodou.st.dashboard.web.controller.student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.PhoneUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.grade.ClassDO;
// import com.xiaodou.st.dashboard.domain.inneralarm.DcFailMsgDTO;
// import com.xiaodou.st.dashboard.domain.inneralarm.InnerAlarmHookDTO;
// import com.xiaodou.st.dashboard.domain.inneralarm.InnerAlarmHookDTO.FailTypeEnum;
import com.xiaodou.st.dashboard.domain.product.RawDataProductCategoryDO;
import com.xiaodou.st.dashboard.domain.student.ImportStudentDTO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.apply.ApplyService;
import com.xiaodou.st.dashboard.service.grade.ClassService;
import com.xiaodou.st.dashboard.service.product.ProductService;
import com.xiaodou.st.dashboard.service.student.StudentService;
import com.xiaodou.st.dashboard.web.controller.BaseController;
import com.xiaodou.summer.dao.pagination.Page;

@Controller("studentController")
@RequestMapping("/student")
public class StudentController extends BaseController {

  @Resource
  StudentService studentService;
  @Resource
  ClassService classService;
  @Resource
  ProductService productService;
  @Resource
  ApplyService applyService;

  @RequestMapping("/student_list")
  public ModelAndView studentList(StudentDO studentDO) {
    ModelAndView mv = new ModelAndView("/student/studentList");
    AdminUser adminUser = super.getAdminUser();
    mv.addObject("adminUser", adminUser);
    Page<StudentDO> pg = new Page<StudentDO>();
    Integer pageNo = studentDO.getPageNo();
    if (pageNo == null) {
      pageNo = 1;
      studentDO.setPageNo(pageNo);
    }
    pg.setPageNo(pageNo);
    Integer pageSize = studentDO.getPageSize();
    if (null == pageSize) pageSize = 10;
    pg.setPageSize(pageSize);
    mv.addObject("pageSize", pageSize);
    mv.addObject("studentDO", studentDO);
    List<ClassDO> listClass = classService.listClass();
    if (CollectionUtils.isEmpty(listClass)) {
      mv.addObject("pageCount", 0);
      mv.addObject("totalCount", 0);
      mv.addObject("pageNo", 1);
      // InnerAlarmHookDTO hook =
      // new InnerAlarmHookDTO(FailTypeEnum.REPORT_BUSINESS_DATA_FAIL, DcFailMsgDTO.classIsNull(
      // "studentList").getMessage());
      // LoggerUtil.alarmInfo(hook);
      return mv;
    }
    mv.addObject("listClass", listClass);
    List<RawDataProductCategoryDO> rawDataProductCatList = productService.listCategory();
    mv.addObject("listCategory", rawDataProductCatList);
    // if (CollectionUtils.isEmpty(rawDataProductCatList)) {
    // InnerAlarmHookDTO hook =
    // new InnerAlarmHookDTO(FailTypeEnum.REPORT_BUSINESS_DATA_FAIL, DcFailMsgDTO.productIsNull(
    // "studentList").getMessage());
    // LoggerUtil.alarmInfo(hook);
    // }
    if (null != adminUser.getChildRole()
        && Constants.POILT_UNIT_CHILD_ROLE.equals(adminUser.getChildRole())) {
      if (null != studentDO.getClassId()) {
        studentDO.setClassId(studentDO.getClassId());
      } else {
        if (null != listClass && !listClass.isEmpty())
          studentDO.setClassId(listClass.get(0).getId());
      }
    }
    Page<StudentDO> page = studentService.listStudent(studentDO, pg);
    if (null == page || CollectionUtils.isEmpty(page.getResult())) {
      mv.addObject("pageCount", 0);
      mv.addObject("totalCount", 0);
      mv.addObject("pageNo", 1);
      return mv;
    }
    mv.addObject("pageCount", page.getTotalPage());
    mv.addObject("totalCount", page.getTotalCount());
    mv.addObject("pageNo", page.getPageNo());
    List<StudentDO> listStudent = page.getResult();
    if (null == listStudent || listStudent.size() < 1) {
      return mv;
    }
    List<Integer> studentIdList = Lists.newArrayList();
    Map<Integer, List<ApplyDO>> applyMap = Maps.newHashMap();
    for (StudentDO sdo : listStudent) {
      studentIdList.add(sdo.getId());
    }
    List<ApplyDO> hasApplyList = applyService.listApplyBySidList(studentIdList);
    if (null != hasApplyList && !hasApplyList.isEmpty()) {
      for (ApplyDO apply : hasApplyList)
        if (null != apply && null != apply.getStudentId()) {
          if (!applyMap.containsKey(apply.getStudentId())) {
            ArrayList<ApplyDO> applyDoList = Lists.newArrayList();
            applyMap.put(apply.getStudentId(), applyDoList);
          }
          applyMap.get(apply.getStudentId()).add(apply);
        }
    }
    for (StudentDO sdo : listStudent) {
      Short deleteStatus = Constants.CANDELETESTUDENT;// 可以删除
      List<ApplyDO> applyList = applyMap.get(sdo.getId());
      if (null != applyList && applyList.size() > 0) {
        sdo.setApplyList(applyList);
        // deleteStatus = Constants.CANNOTDELETESTUDENT;
      }
      if (null != sdo.getUserId()) {
        deleteStatus = Constants.CANNOTDELETESTUDENT;
      }
      sdo.setDeleteStatus(deleteStatus);
      sdo.setRegTelephone(sdo.getTelephone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
    }
    mv.addObject("listStudent", listStudent);
    return mv;
  }

  @RequestMapping("/save_student")
  @ResponseBody()
  public String saveStudent(StudentDO studentDO) {
    ClassDO cdo = classService.getClass(studentDO.getClassId());
    studentDO.setClassName(cdo.getClassName());
    Map<String, StudentDO> phoneMap = Maps.newHashMap();
    Map<String, StudentDO> admissionCardMap = Maps.newHashMap();
    studentService.packageMap(phoneMap, admissionCardMap);
    StringBuffer msg = new StringBuffer(200);
    boolean flag = true;
    // 检测手机号
    if (StringUtils.isBlank(studentDO.getTelephone())) {
      msg.append(Constants.ERROR_PHONENUM_EMPTY);
      flag = flag && false;
    } else {
      if (PhoneUtil.validatePhone(studentDO.getTelephone())) {
        if (phoneMap.containsKey(studentDO.getTelephone())) {
          msg.append(Constants.ERROR_PHONENUM_EXIST + "，");
          flag = flag && false;
        }
      } else {
        msg.append(Constants.ERROR_PHONENUM_ERROR + "(应为11位有效手机号)，");
        flag = flag && false;
      }
    }
    // 检测准考证号
    if (StringUtils.isNotBlank(studentDO.getAdmissionCardCode())) {
      if (!PhoneUtil.validateNumber(studentDO.getAdmissionCardCode(), 12)) {
        msg.append(Constants.ERROR_CARD_ERROR + "(为12位有效数字)，");
        flag = flag && false;
      } else if (admissionCardMap.containsKey(studentDO.getAdmissionCardCode())) {
        msg.append(Constants.ERROR_CARD_EXIST + "，");
        flag = flag && false;
      }
    }
    if (!flag) return msg.toString();
    return studentService.saveStudent(studentDO);
  }

  @RequestMapping("/update_student")
  @ResponseBody()
  public String updateStudent(StudentDO studentDO) {
    AdminUser adminUser = super.getAdminUser();
    studentDO.setPilotUnitId(adminUser.getUnitId());
    studentDO.setPilotUnitName(adminUser.getUnitName());
    studentDO.setAdminId(adminUser.getUserId());
    studentDO.setAdminName(adminUser.getRealName());
    if (null != studentDO && null != studentDO.getClassId()) {
      ClassDO cdo = classService.getClass(studentDO.getClassId());
      studentDO.setClassName(cdo.getClassName());
    }
    if (StringUtils.isBlank(studentDO.getAdmissionCardCode())) {
      studentDO.setAdmissionCardCode(null);// 防止被修改
    }
    return studentService.updateStudent(studentDO);
  }

  @RequestMapping("/get_student")
  @ResponseBody()
  public String getStudent(Integer studentId) {
    return FastJsonUtil.toJson(studentService.getStudent(studentId));
  }

  @RequestMapping("/remove_student")
  @ResponseBody()
  public String removeStudent(Integer studentId, Long classId) {
    return studentService.removeStudent(studentId, classId);
  }

  @RequestMapping("/remove_student_by_classId")
  @ResponseBody()
  public String removeStudentByClassId(Long classId) {
    ClassDO classDO = classService.getClass(classId);
    if (classDO.getStudentCount() > 0) {
      ApplyDO applyDO = new ApplyDO();
      applyDO.setClassId(classDO.getId());
      List<ApplyDO> applyList = applyService.listApply(applyDO);
      if (null == applyList || applyList.isEmpty()) {
        classDO.setBatchDel(Constants.CAN_BATCH_DEL);
        return studentService.removeStudentByClassId(classId);
      }
    }
    return "CANOT_BATCH_DEL";
  }

  @RequestMapping("/download_excel")
  public void downloadExcel(HttpServletResponse response) {
    List<ImportStudentDTO> list = Lists.newArrayList();
    ImportStudentDTO idto = new ImportStudentDTO();
    // idto.setClassName("网络一班");
    idto.setRealName("李旭");
    idto.setGender("女");
    idto.setTelephone("18500001111");
    idto.setAdmissionCardCode("010423151245");
    idto.setMsg("示例");
    list.add(idto);
    String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
    studentService.downloadExcel("示例", "template" + dateFileName, list, response);
  }

  @RequestMapping("/detection_excel_url")
  @ResponseBody()
  public String detectionExcelUrl(String url) {
    return studentService.detectionExcelUrl(url);
  }

  @RequestMapping("/create_error_excel")
  public void createErrorExcel(String url, HttpServletResponse response) {
    List<ImportStudentDTO> errorExcelList = studentService.createErrorExcel(url);
    String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
    studentService.downloadExcel("检测", "detection" + dateFileName, errorExcelList, response);
  }

  @RequestMapping("/import_student")
  @ResponseBody()
  public String importStudent(String url, Long classId, String className) {
    return studentService.importStudent(url, classId, className);
  }

  // 修改学生手机号码，重置头像
  @RequestMapping("/student_info")
  public ModelAndView studentInfo(StudentDO studentDO) {
    ModelAndView mv = new ModelAndView("/student/studentInfo");
    mv.addObject("studentDO", studentDO);
    if (null == studentDO) return mv;
    if (StringUtils.isBlank(studentDO.getTelephone())
        && StringUtils.isBlank(studentDO.getAdmissionCardCode())) return mv;
    if (StringUtils.isNotBlank(studentDO.getTelephone())
        && !PhoneUtil.validatePhone(studentDO.getTelephone())) return mv;
    if (StringUtils.isNotBlank(studentDO.getAdmissionCardCode())
        && !PhoneUtil.validateNumber(studentDO.getAdmissionCardCode(), 12)) return mv;
    Page<StudentDO> page = studentService.listStudentInfo(studentDO, null);
    if (null != page && null != page.getResult() && !page.getResult().isEmpty()) {
      mv.addObject("student", page.getResult().get(0));
    }
    return mv;
  }

  @RequestMapping("/update_student_telephone")
  @ResponseBody()
  public String updateStudentTelephone(StudentDO studentDO) {
    return studentService.updateStudentTelephone(studentDO);
  }

  @RequestMapping("/reset_student_portrait")
  @ResponseBody()
  public String resetStudentPortrait(StudentDO studentDO) {
    return studentService.resetStudentPortrait(studentDO);
  }
}
