package com.xiaodou.st.dashboard.service.student;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.PhoneUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.grade.ClassDO;
import com.xiaodou.st.dashboard.domain.student.ImportStudentDTO;
import com.xiaodou.st.dashboard.domain.student.StudentBaseUserDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.domain.student.StudentMisDO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.grade.ClassService;
import com.xiaodou.st.dashboard.service.message.QueueService;
import com.xiaodou.st.dashboard.service.order.OrderService;
import com.xiaodou.st.dashboard.util.ExcelUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.enums.Gender;

@Service
public class StudentService extends BaseDashboardService {
  @Resource
  IStServiceFacade stServiceFacade;
  @Resource
  OrderService orderService;
  @Resource
  ClassService classService;
  @Resource
  QueueService queueService;

  public Page<StudentDO> listStudent(StudentDO studentDO, Page<StudentDO> pg) {
    Map<String, Object> inputs = Maps.newHashMap();
    AdminUser adminUser = super.getAdminUser();
    if (null == adminUser.getUnitId()) return null;
    inputs.put("pilotUnitId", adminUser.getUnitId());
    INPUTS: {
      if (null == studentDO) break INPUTS;
      if (null != studentDO.getClassId() && studentDO.getClassId() != -1)
        inputs.put("classId", studentDO.getClassId());
      if (StringUtils.isNotBlank(studentDO.getRealName()))
        inputs.put("realNameLike", studentDO.getRealName());
      if (StringUtils.isNotBlank(studentDO.getAdmissionCardCode()))
        inputs.put("admissionCardCodeLike", studentDO.getAdmissionCardCode());
      if (StringUtils.isNotBlank(studentDO.getTelephone()))
        inputs.put("telephoneLike", studentDO.getTelephone());
    }
    Page<StudentDO> page =
        stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), pg);
    return page;
  }

  // 修改学生手机号码：不按照第三级单位查询学生
  public Page<StudentDO> listStudentInfo(StudentDO studentDO, Page<StudentDO> pg) {
    Map<String, Object> inputs = Maps.newHashMap();
    INPUTS: {
      if (null == studentDO) break INPUTS;
      if (null != studentDO.getClassId() && studentDO.getClassId() != -1)
        inputs.put("classId", studentDO.getClassId());
      if (StringUtils.isNotBlank(studentDO.getRealName()))
        inputs.put("realNameLike", studentDO.getRealName());
      if (StringUtils.isNotBlank(studentDO.getAdmissionCardCode()))
        inputs.put("admissionCardCodeLike", studentDO.getAdmissionCardCode());
      if (StringUtils.isNotBlank(studentDO.getTelephone()))
        inputs.put("telephoneLike", studentDO.getTelephone());
    }
    Page<StudentDO> page =
        stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), pg);
    return page;
  }

  public Page<StudentDO> findStudentCountListGByPilotUnit() {
    return stServiceFacade.findStudentCountListGByPilotUnit();
  }

  public Page<StudentDO> listStudentByClassId(Long classId) {
    Map<String, Object> inputs = Maps.newHashMap();
    AdminUser adminUser = super.getAdminUser();
    if (null == adminUser.getUnitId()) return null;
    inputs.put("pilotUnitId", adminUser.getUnitId());
    inputs.put("classId", classId);
    Page<StudentDO> page =
        stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), null);
    return page;
  }

  public List<String> listTelePhoneAndAdmissionCardCode() {
    List<String> list = Lists.newArrayList();
    Page<StudentDO> page =
        stServiceFacade.listStudent(null, CommUtil.getAllField(StudentDO.class), null);
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return list;
    return Lists.transform(page.getResult(), new Function<StudentDO, String>() {
      @Override
      public String apply(StudentDO input) {
        if (null == input) return null;
        return input.getTelephone();
      }
    });
  }

  // public void listAdmissionCardCode(Set<String> phoneSet, Set<String> admissionCardSet) {
  // Assert.notNull(phoneSet, "phoneSet can't be null.");
  // Assert.notNull(admissionCardSet, "admissionCardSet can't be null.");
  // Page<StudentDO> page =
  // stServiceFacade.listStudent(null, CommUtil.getAllField(StudentDO.class), null);
  // if (null == page || null == page.getResult() || page.getResult().isEmpty()) return;
  // for (StudentDO student : page.getResult()) {
  // if (null == student) continue;
  // phoneSet.add(student.getTelephone());
  // admissionCardSet.add(student.getAdmissionCardCode());
  // }
  // }

  public void packageMap(Map<String, StudentDO> phoneMap, Map<String, StudentDO> admissionCardMap) {
    Assert.notNull(phoneMap, "phoneMap can't be null.");
    Assert.notNull(admissionCardMap, "admissionCardMap can't be null.");
    Page<StudentDO> page =
        stServiceFacade.listStudent(null, CommUtil.getAllField(StudentDO.class), null);
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return;
    for (StudentDO student : page.getResult()) {
      if (null == student) continue;
      phoneMap.put(student.getTelephone(), student);
      admissionCardMap.put(student.getAdmissionCardCode(), student);
    }
  }

  /**
   * 增加学生(提前做判断处理)
   */
  public String saveStudent(StudentDO studentDO) {
    AdminUser adminUser = super.getAdminUser();
    // Map<String, Object> inputs = Maps.newHashMap();
    // if (null == adminUser.getUnitId()) return "请退出重新登录";
    // inputs.put("telephone", studentDO.getTelephone());
    // Page<StudentDO> pg =
    // stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), null);
    // if (null != pg && null != pg.getResult() && !pg.getResult().isEmpty()) return "手机号已经存在！";
    studentDO.setPilotUnitId(adminUser.getUnitId());
    studentDO.setPilotUnitName(adminUser.getUnitName());
    studentDO.setAdminId(adminUser.getUserId());
    studentDO.setAdminName(adminUser.getRealName());
    studentDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    Long classId = studentDO.getClassId();
    if (null == classId) {
      List<ClassDO> classList = classService.listClass();
      for (ClassDO cl : classList) {
        if (cl.getClassName().equals(studentDO.getClassName())) classId = cl.getId();
      }
    }
    studentDO.setClassId(classId);
    boolean flag = stServiceFacade.saveStudent(studentDO);
    if (!flag) return "添加学生失败！";
    // 修改班级学生数
    queueService.pushUpdateClassStudentCount(Lists.newArrayList(classId));
    return String.valueOf(flag);
  }

  public Integer saveStudentList(List<StudentDO> sdoList, Long classId) {
    Map<String, Object> column = Maps.newHashMap();
    column.put("classId", 1);
    column.put("className", 1);
    column.put("realName", 1);
    column.put("gender", 1);
    column.put("telephone", 1);
    column.put("admissionCardCode", 1);
    column.put("pilotUnitId", 1);
    column.put("pilotUnitName", 1);
    column.put("adminName", 1);
    column.put("adminId", 1);
    column.put("createTime", 1);
    int count = 0;
    try {
      count = stServiceFacade.saveStudentList(sdoList, column);
    } catch (Exception e) {
      LoggerUtil.error("批量注册学生失败！", e);
    }
    // 修改班级学生数
    if (count > 0) queueService.pushUpdateClassStudentCount(Lists.newArrayList(classId));
    return count;
  }


  /**
   * 修改学生信息
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param studentId
   * @return
   */
  public String updateStudent(StudentDO studentDO) {
    AdminUser adminUser = super.getAdminUser();
    if (null == adminUser.getUnitId()) return "请退出重新登录！";
    StudentDO sdo = stServiceFacade.getStudent(studentDO.getId());
    Map<String, StudentDO> phoneMap = Maps.newHashMap();
    Map<String, StudentDO> admissionCardMap = Maps.newHashMap();
    this.packageMap(phoneMap, admissionCardMap);
    StringBuffer msg = new StringBuffer(200);
    boolean flag = true;
    // 检测手机号
    if (StringUtils.isBlank(studentDO.getTelephone())) {
      msg.append(Constants.ERROR_PHONENUM_EMPTY);
      flag = flag && false;
    } else {
      if (PhoneUtil.validatePhone(studentDO.getTelephone())) {
        if (phoneMap.containsKey(studentDO.getTelephone())
            && !sdo.getTelephone().equals(studentDO.getTelephone())) {
          msg.append(Constants.ERROR_PHONENUM_EXIST + "，");
          flag = flag && false;
        } else if (!sdo.getTelephone().equals(studentDO.getTelephone())
            && Constants.FAIL_REGISTER == sdo.getStudentStatus()) {
          studentDO.setStudentStatus(Constants.NOT_REGISTER);
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
      } else if (admissionCardMap.containsKey(studentDO.getAdmissionCardCode())
          && !sdo.getAdmissionCardCode().equals(studentDO.getAdmissionCardCode())) {
        msg.append(Constants.ERROR_CARD_EXIST + "，");
        flag = flag && false;
      }
    }
    if (!flag) return msg.toString();

    flag = stServiceFacade.updateStudent(studentDO);
    if (!flag) return "更新学生失败！";
    // 修改班级学生数
    if (null != sdo && null != studentDO.getClassId()
        && !sdo.getClassId().equals(studentDO.getClassId())) {
      Page<StudentDO> page = this.listStudentByClassId(studentDO.getClassId());
      ClassDO classDO = new ClassDO();
      classDO.setId(studentDO.getClassId());
      classDO.setStudentCount(null != page ? page.getTotalCount() : 0);
      if (!stServiceFacade.updateClass(classDO))
        LoggerUtil.error("修改学生时，修改班级学生数失败！", new Exception());

      Page<StudentDO> page1 = this.listStudentByClassId(sdo.getClassId());
      ClassDO classDO1 = new ClassDO();
      classDO1.setId(sdo.getClassId());
      classDO1.setStudentCount(null != page1 ? page1.getTotalCount() : 0);
      if (!stServiceFacade.updateClass(classDO1))
        LoggerUtil.error("修改学生时，修改班级学生数失败！", new Exception());
    }
    queueService.pushUpdateApplyByStudent(studentDO);
    return String.valueOf(flag);
  }

  /**
   * 查詢学生
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param studentId
   * @return
   */
  public StudentDO getStudent(Integer studentId) {
    return stServiceFacade.getStudent(studentId);
  }

  public List<StudentDO> listStudent(List<String> studentIdList) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("idList", studentIdList);
    Page<StudentDO> studentPage =
        stServiceFacade.listStudent(input, CommUtil.getAllField(StudentDO.class), null);
    if (null == studentPage || null == studentPage.getResult() || studentPage.getResult().isEmpty())
      return null;
    return studentPage.getResult();
  }

  /**
   * 刪除学生
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param studentId
   * @return
   */
  public String removeStudent(Integer studentId, Long classId) {
    if (null == studentId || null == classId) return String.valueOf(false);
    boolean flag1 = stServiceFacade.removeStudent(studentId);
    // 修改班级学生数
    boolean flag2 = updateClassStudentCount(classId);
    return String.valueOf(flag1 && flag2);
  }

  private boolean updateClassStudentCount(Long classId) {
    // 修改班级学生数
    Page<StudentDO> page = this.listStudentByClassId(classId);
    ClassDO classDO = new ClassDO();
    classDO.setId(classId);
    if (null != page)
      classDO.setStudentCount(page.getTotalCount());
    else
      classDO.setStudentCount(0l);
    boolean flag2 = stServiceFacade.updateClass(classDO);
    return flag2;
  }

  public String removeStudentByClassId(Long classId) {
    if (null == classId) return String.valueOf(false);
    Map<String, Object> input = Maps.newHashMap();
    input.put("classId", classId);
    Integer removeCount = stServiceFacade.removeStudentByCond(input);
    // 修改班级学生数
    ClassDO classDO = new ClassDO();
    classDO.setId(classId);
    classDO.setStudentCount(0l);
    boolean flag2 = stServiceFacade.updateClass(classDO);
    if (!flag2) return String.valueOf(flag2);
    return String.valueOf(removeCount);
  }

  public void downloadExcel(String sheetName, String fileName, List<ImportStudentDTO> list,
      HttpServletResponse response) {
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    // fieldMap.put("className", "班级");
    fieldMap.put("realName", "姓名");
    fieldMap.put("gender", "性别（只填男女）");
    fieldMap.put("telephone", "手机号（11位）");
    fieldMap.put("admissionCardCode", "准考证号（12位，可为空）");
    fieldMap.put("msg", "");
    try {
      ExcelUtil.listToExcel(list, fieldMap, sheetName, response, fileName);
    } catch (Exception e) {
      LoggerUtil.error("导出excel异常！", e);
    }
  }

  public String detectionExcelUrl(String url) {
    String msg = "";
    int verifyCount = 0;
    List<ImportStudentDTO> studentList = this.readExcel(url);
    if (null == studentList) return "表格中的必选列列不可缺少，查看是否缺少列。";
    if (studentList.isEmpty())
      return "共检测到学生信息0条，其中有效学生信息<span id='verifyCount'>0</span>条，错误学生信息<span id='errorCount'>0</span>条。<br/>请上传正常格式与数据正常的excel！";
    Integer totalCount = studentList.size();
    Integer errorCount = 0;
    Map<String, Integer> phoneCountMap = Maps.newHashMap();
    Map<String, Integer> admissionCardCountMap = Maps.newHashMap();
    packageCountMap(studentList, phoneCountMap, admissionCardCountMap);
    Map<String, StudentDO> phoneMap = Maps.newHashMap();
    Map<String, StudentDO> admissionCardMap = Maps.newHashMap();
    packageMap(phoneMap, admissionCardMap);
    for (ImportStudentDTO isdto : studentList) {
      boolean flag =
          this.detectionResult(isdto, phoneMap, admissionCardMap, phoneCountMap,
              admissionCardCountMap);
      if (!flag) errorCount++;
    }
    verifyCount = totalCount - errorCount;
    if (errorCount == 0) {
      msg =
          "共检测到学生信息" + totalCount + "条，其中有效学生信息<span id='verifyCount'>" + verifyCount
              + "</span>条，错误学生信息<span id='errorCount'>" + errorCount + "</span>条。";
      return msg;
    }
    msg =
        "共检测到学生信息" + totalCount + "条，其中有效学生信息<span id='verifyCount'>" + verifyCount
            + "</span>条，错误学生信息<span id='errorCount'>" + errorCount + "</span>条。错误数据已为您标出，可直接下载";
    return msg;
  }


  private boolean detectionResult(ImportStudentDTO isdto, Map<String, StudentDO> phoneMap,
      Map<String, StudentDO> admissionCardMap, Map<String, Integer> phoneCountMap,
      Map<String, Integer> admissionCardCountMap) {
    boolean flag = true;
    // 检测姓名
    if (StringUtils.isBlank(isdto.getRealName())) {
      flag = false;
    }
    // 检测性别
    if (StringUtils.isBlank(isdto.getGender())) {
      flag = false;
    } else {
      boolean genderFlag = false;
      for (Gender gender : Gender.values()) {
        genderFlag = isdto.getGender().equals(gender.getName()) || genderFlag;
      }
      if (!genderFlag) {
        flag = false;
      }
    }
    // 检测手机号
    if (StringUtils.isBlank(isdto.getTelephone())) {
      flag = false;
    } else {
      if (PhoneUtil.validatePhone(isdto.getTelephone())) {
        if (phoneMap.containsKey(isdto.getTelephone())) flag = false;
        if (phoneCountMap.get(isdto.getTelephone()) > 1) flag = false;
      } else {
        flag = false;
      }
    }
    // 检测准考证号
    if (StringUtils.isNotBlank(isdto.getAdmissionCardCode())) {
      if (!PhoneUtil.validateNumber(isdto.getAdmissionCardCode(), 12)
          || admissionCardMap.containsKey(isdto.getAdmissionCardCode())
          || admissionCardCountMap.get(isdto.getAdmissionCardCode()) > 1) {
        flag = false;
      }
    }
    return flag;
  }


  /**
   * 生成Excel
   */
  public List<ImportStudentDTO> createErrorExcel(String url) {
    List<ImportStudentDTO> studentList = this.readExcel(url);
    Map<String, Integer> phoneCountMap = Maps.newHashMap();
    Map<String, Integer> admissionCardCountMap = Maps.newHashMap();
    packageCountMap(studentList, phoneCountMap, admissionCardCountMap);
    if (null == studentList) return Lists.newArrayList();
    Map<String, StudentDO> phoneMap = Maps.newHashMap();
    Map<String, StudentDO> admissionCardMap = Maps.newHashMap();
    packageMap(phoneMap, admissionCardMap);
    for (ImportStudentDTO isdto : studentList) {
      StringBuffer msg = new StringBuffer(200);
      // 检测姓名
      if (StringUtils.isBlank(isdto.getRealName())) {
        msg.append(Constants.ERROR_NAME_EMPTY);
      }
      // 检测性别
      if (StringUtils.isBlank(isdto.getGender())) {
        msg.append(Constants.ERROR_GENDER_EMPTY);
      } else {
        boolean genderFlag = false;
        for (Gender gender : Gender.values()) {
          genderFlag = isdto.getGender().equals(gender.getName()) || genderFlag;
        }
        if (!genderFlag) msg.append(Constants.ERROR_GENDER_ERROR);
      }
      // 检测手机号
      if (StringUtils.isBlank(isdto.getTelephone())) {
        msg.append(Constants.ERROR_PHONENUM_EMPTY);
      } else {
        if (PhoneUtil.validatePhone(isdto.getTelephone())) {
          if (phoneMap.containsKey(isdto.getTelephone())) {
            msg.append(Constants.ERROR_PHONENUM_EXIST + "("
                + phoneMap.get(isdto.getTelephone()).getRealName() + "，"
                + phoneMap.get(isdto.getTelephone()).getAdmissionCardCode() + ")，");
          } else {
            if (phoneCountMap.get(isdto.getTelephone()) > 1) {
              msg.append(Constants.ERROR_PHONENUM_EXIST_EXCEL + "，");
            }
          }
        } else {
          msg.append(Constants.ERROR_PHONENUM_ERROR + "(应为11位有效手机号)，");
        }
      }
      // 检测准考证号
      if (StringUtils.isNotBlank(isdto.getAdmissionCardCode())) {
        if (!PhoneUtil.validateNumber(isdto.getAdmissionCardCode(), 12))
          msg.append(Constants.ERROR_CARD_ERROR + "(为12位有效数字)，");
        else {
          if (admissionCardMap.containsKey(isdto.getAdmissionCardCode())) {
            msg.append(Constants.ERROR_CARD_EXIST + "("
                + admissionCardMap.get(isdto.getAdmissionCardCode()).getRealName() + "，"
                + admissionCardMap.get(isdto.getAdmissionCardCode()).getTelephone() + ")，");

          } else {
            if (admissionCardCountMap.get(isdto.getAdmissionCardCode()) > 1) {
              msg.append(Constants.ERROR_CARD_ERROR_EXCEL + "，");
            }
          }
        }
      }
      isdto.setMsg(msg.toString());
    }
    return studentList;
  }

  private void packageCountMap(List<ImportStudentDTO> studentList,
      Map<String, Integer> phoneCountMap, Map<String, Integer> admissionCardCountMap) {
    for (ImportStudentDTO isdto : studentList) {
      String telephone = isdto.getTelephone();
      String admissionCard = isdto.getAdmissionCardCode();
      if (phoneCountMap.containsKey(telephone))
        phoneCountMap.put(telephone, phoneCountMap.get(telephone) + 1);
      else
        phoneCountMap.put(telephone, 1);
      if (admissionCardCountMap.containsKey(admissionCard))
        admissionCardCountMap.put(admissionCard, admissionCardCountMap.get(admissionCard) + 1);
      else
        admissionCardCountMap.put(admissionCard, 1);
    }
  }

  /**
   * 读取Excel->ImportStudentDTO
   */
  public List<ImportStudentDTO> readExcelOld(String urlString) {
    List<ImportStudentDTO> lineList = Lists.newArrayList();
    try {
      // String fileName = "head.xls";
      URL url = new URL(urlString);
      URLConnection conn = url.openConnection();
      InputStream is = conn.getInputStream();
      Workbook book = Workbook.getWorkbook(is);
      // 获得第一个工作表对象
      Sheet sheet = book.getSheet(0);
      // j:列，i:行
      // msg在不读取
      Field[] fields = ImportStudentDTO.class.getDeclaredFields();
      int col = fields.length - 2;
      for (int j = 0; j <= col; j++) {
        Cell cell = sheet.getCell(j, 0);
        String result = cell.getContents();
        if (StringUtils.isEmpty(result)) return null;
      }
      for (int i = 1; i <= sheet.getRows() - 1; i++) {
        String rowStr = "{";
        for (int j = 0; j <= col; j++) {
          // System.out.println(j + ":" + i);
          Cell cell = sheet.getCell(j, i);
          if (null == cell) break;
          String result = cell.getContents();
          // System.out.print(result + ";");
          if (StringUtils.isNotBlank(result))
            rowStr += String.format("%s:\"%s\",", fields[j].getName(), result.trim());
        }
        if (!"{".equals(rowStr)) {
          rowStr = rowStr.substring(0, rowStr.lastIndexOf(",")) + "}";
          // System.out.println(rowStr);
          ImportStudentDTO headTest = FastJsonUtil.fromJson(rowStr, ImportStudentDTO.class);
          lineList.add(headTest);
        }
      }
      book.close();
    } catch (Exception e) {
      LoggerUtil.error("excel检测异常！", e);
    }
    return lineList;
  }

  public List<ImportStudentDTO> readExcel(String urlString) {
    List<ImportStudentDTO> lineList = Lists.newArrayList();
    try {
      String excelType = urlString.substring(urlString.lastIndexOf(".") + 1);
      URL url = new URL(urlString);
      URLConnection conn = url.openConnection();
      InputStream is = conn.getInputStream();
      // InputStream is = new FileInputStream("D:/公司/小逗网络/excel/批量导入学生成绩.xlsx");
      if (Constants.FILE_TYPE_XLS.equals(excelType)) {
        lineList = this.readXLSFile(is);
      } else if (Constants.FILE_TYPE_XLSX.equals(excelType)) {
        lineList = this.readXLSXFile(is);
      }
    } catch (Exception e) {
      LoggerUtil.error("excel检测异常！", e);
    }
    return lineList;
  }

  @SuppressWarnings("rawtypes")
  public List<ImportStudentDTO> readXLSFile(InputStream is) throws IllegalArgumentException,
      IllegalAccessException, IOException {
    Map<String, Integer> studentCondMap = Maps.newHashMap();
    studentCondMap.put("姓名", 0);
    studentCondMap.put("性别（只填男女）", 0);
    studentCondMap.put("手机号（11位）", 0);
    studentCondMap.put("准考证号（12位，可为空）", 0);
    List<ImportStudentDTO> lineList = Lists.newArrayList();
    HSSFWorkbook wb = new HSSFWorkbook(is);
    // 获得第一个工作表对象
    HSSFSheet sheet = wb.getSheetAt(0);
    HSSFRow row;
    HSSFCell cell;
    Iterator rows = sheet.rowIterator();
    Field[] fields = ImportStudentDTO.class.getDeclaredFields();
    while (rows.hasNext()) {
      row = (HSSFRow) rows.next();
      Iterator cells = row.cellIterator();
      ImportStudentDTO idto = new ImportStudentDTO();
      // 每列
      while (cells.hasNext()) {
        String result = StringUtils.EMPTY;
        cell = (HSSFCell) cells.next();
        if (cell.getCellNum() > fields.length - 2) break;
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
          result = cell.getStringCellValue().trim();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
          double number = cell.getNumericCellValue();
          DecimalFormat df = new DecimalFormat("0");
          result = df.format(number);
        } else {
          // U Can Handel Boolean, Formula, Errors
        }
        if (row.getRowNum() == sheet.getFirstRowNum()) {
          if (studentCondMap.containsKey(result)) {
            studentCondMap.put(result, studentCondMap.get(result) + 1);
            if (studentCondMap.get(result) > 1) {
              throw new RuntimeException(Constants.PROMPT_HEADERERROR_MSG);
            }
          } else {
            throw new RuntimeException(Constants.PROMPT_HEADERERROR_MSG);
          }
        } else if (row.getRowNum() > sheet.getFirstRowNum()) {
          // System.out.println(cell.getCellNum() + ".." + fields[cell.getCellNum()].getName()
          // + ".." + result);
          fields[cell.getCellNum()].set(idto, result);
        }
      }
      // if (row.getRowNum() != sheet.getFirstRowNum()
      // && !StringUtils.isBlank(idto.getAdmissionCardCode())) {
      // lineList.add(idto);
      // }
      if (row.getRowNum() != sheet.getFirstRowNum()) {
        lineList.add(idto);
      }
    }
    return lineList;
  }

  @SuppressWarnings("rawtypes")
  public List<ImportStudentDTO> readXLSXFile(InputStream is) throws IllegalArgumentException,
      IllegalAccessException, IOException {
    Map<String, Integer> studentCondMap = Maps.newHashMap();
    studentCondMap.put("姓名", 0);
    studentCondMap.put("性别（只填男女）", 0);
    studentCondMap.put("手机号（11位）", 0);
    studentCondMap.put("准考证号（12位，可为空）", 0);
    List<ImportStudentDTO> lineList = Lists.newArrayList();
    XSSFWorkbook wb = new XSSFWorkbook(is);
    // 获得第一个工作表对象
    XSSFSheet sheet = wb.getSheetAt(0);
    XSSFRow row;
    XSSFCell cell;
    Iterator rows = sheet.rowIterator();
    Field[] fields = ImportStudentDTO.class.getDeclaredFields();
    while (rows.hasNext()) {
      row = (XSSFRow) rows.next();
      Iterator cells = row.cellIterator();
      ImportStudentDTO idto = new ImportStudentDTO();
      // 每列
      while (cells.hasNext()) {
        String result = StringUtils.EMPTY;
        cell = (XSSFCell) cells.next();
        if (cell.getColumnIndex() > fields.length - 2) break;
        if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
          result = cell.getStringCellValue().trim();
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
          double number = cell.getNumericCellValue();
          DecimalFormat df = new DecimalFormat("0");
          result = df.format(number);
        } else {
          // U Can Handel Boolean, Formula, Errors
        }
        if (row.getRowNum() == sheet.getFirstRowNum()) {
          if (studentCondMap.containsKey(result)) {
            studentCondMap.put(result, studentCondMap.get(result) + 1);
            if (studentCondMap.get(result) > 1) {
              throw new RuntimeException(Constants.PROMPT_HEADERERROR_MSG);
            }
          } else {
            throw new RuntimeException(Constants.PROMPT_HEADERERROR_MSG);
          }
        } else if (row.getRowNum() > sheet.getFirstRowNum()) {
          // System.out.println(cell.getCellNum() + ".." + fields[cell.getCellNum()].getName()
          // + ".." + result);
          fields[cell.getColumnIndex()].set(idto, result);
        }
      }
      // if (row.getRowNum() != sheet.getFirstRowNum()
      // && !StringUtils.isAllBlank(idto.getAdmissionCardCode())){
      // lineList.add(idto);
      // }
      if (row.getRowNum() != sheet.getFirstRowNum()) {
        lineList.add(idto);
      }
    }
    return lineList;
  }

  @SuppressWarnings({"deprecation"})
  public String importStudent(String url, Long classId, String className) {
    LoggerUtil.alarmInfo("start----" + System.currentTimeMillis());
    List<StudentDO> sdoList = Lists.newArrayList();
    List<ImportStudentDTO> studentList = this.readExcel(url);
    LoggerUtil.alarmInfo("readExcel end----" + System.currentTimeMillis());
    Map<String, Integer> phoneCountMap = Maps.newHashMap();
    Map<String, Integer> admissionCardCountMap = Maps.newHashMap();
    packageCountMap(studentList, phoneCountMap, admissionCardCountMap);
    LoggerUtil.alarmInfo("packageCountMap end----" + System.currentTimeMillis());
    Integer count = 0;
    if (null == studentList) return count.toString();
    Map<String, StudentDO> phoneMap = Maps.newHashMap();
    Map<String, StudentDO> admissionCardMap = Maps.newHashMap();
    packageMap(phoneMap, admissionCardMap);
    AdminUser adminUser = super.getAdminUser();
    LoggerUtil.alarmInfo("packageMap end----" + System.currentTimeMillis());
    for (ImportStudentDTO isdto : studentList) {
      StudentDO studentDO = new StudentDO();
      studentDO.setPilotUnitId(adminUser.getUnitId());
      studentDO.setPilotUnitName(adminUser.getUnitName());
      studentDO.setAdminId(adminUser.getUserId());
      studentDO.setAdminName(adminUser.getRealName());
      studentDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
      studentDO.setClassId(classId);
      studentDO.setClassName(className);;
      if (StringUtils.isNotBlank(isdto.getAdmissionCardCode()))
        studentDO.setAdmissionCardCode(isdto.getAdmissionCardCode());
      if (!this.detectionResult(isdto, phoneMap, admissionCardMap, phoneCountMap,
          admissionCardCountMap)) continue;
      String gender = "1";
      switch (isdto.getGender()) {
        case Constants.MAN:
          gender = Gender.MAN.getCode() + "";
          break;
        case Constants.WOMAN:
          gender = Gender.WOMAM.getCode() + "";
          break;
        default:
          break;
      }
      studentDO.setGender(gender);
      studentDO.setRealName(isdto.getRealName());
      studentDO.setTelephone(isdto.getTelephone());
      sdoList.add(studentDO);
    }
    LoggerUtil.alarmInfo("for循环组装list end----" + System.currentTimeMillis());
    count = this.saveStudentList(sdoList, classId);
    LoggerUtil.alarmInfo("批量插入  end----" + System.currentTimeMillis());
    return count.toString();
  }


  /**
   * 修改学生手机号码
   */
  public String updateStudentTelephone(StudentDO studentDO) {
    Map<String, Object> inputs = Maps.newHashMap();
    INPUTS: {
      if (null == studentDO) break INPUTS;
      // 新手机号判断
      if (StringUtils.isNotBlank(studentDO.getRegTelephone()))
        inputs.put("telephone", studentDO.getRegTelephone());
    }
    Page<StudentDO> page =
        stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), null);
    if (null != page && null != page.getResult() && !page.getResult().isEmpty()) {
      return "新手机号[" + studentDO.getRegTelephone() + "]已经被云测评别的学生占用！";
    }
    StudentBaseUserDO studentBaseUserDO = new StudentBaseUserDO();
    studentBaseUserDO.setModule(Constants.MODULE);
    studentBaseUserDO.setTelephone(studentDO.getRegTelephone());
    List<StudentBaseUserDO> list = stServiceFacade.findTelByTelephoneAndModule(studentBaseUserDO);
    if (null != list && !list.isEmpty()) {
      return "新手机号[" + studentDO.getRegTelephone() + "]已经被业务别的学生占用！";
    }
    boolean flag = stServiceFacade.updateStudentTelephone(studentDO);
    return String.valueOf(flag);
  }


  /**
   * 清除学生头像数据，初始化上传头像状态
   */
  public String resetStudentPortrait(StudentDO studentDO) {
    StudentMisDO studentMisDO = new StudentMisDO();
    if(null == studentDO.getUserId()){
      return "无业务用户ID！";
    }
    studentMisDO.setUserId(studentDO.getUserId());
    StudentMisDO studentMisDOValue = stServiceFacade.findUserOfficialInfoByUserId(studentMisDO);
    if (studentMisDOValue != null && StringUtils.isNotBlank(studentMisDOValue.getOfficialInfo())) {
      JSONObject json = (JSONObject) JSONObject.parse(studentMisDOValue.getOfficialInfo());
      if (json.containsKey("officialStatus")) {
        json.put("officialStatus", "0");
      }
      json.remove("benchmarkFace");
      studentMisDOValue.setOfficialInfo(json.toJSONString());
      boolean flag1 = stServiceFacade.updateUserOfficialInfoByUserId(studentMisDOValue);
      if (!flag1) {
        return "修改MIS中用户头像信息失败！";
      }
    }
    boolean flag2 = stServiceFacade.resetStudentPortrait(studentDO);
    return String.valueOf(flag2);
  }

}
