package com.xiaodou.st.dashboard.service.score;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.math.NumberUtils;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.PhoneUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductCategoryDO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductDO;
import com.xiaodou.st.dashboard.domain.score.ImportScoreDTO;
import com.xiaodou.st.dashboard.domain.score.LearnRecordDO;
import com.xiaodou.st.dashboard.domain.score.LearnRecordDTO;
import com.xiaodou.st.dashboard.domain.score.ScoreDO;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.apply.ApplyService;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.product.ProductService;
import com.xiaodou.st.dashboard.service.unit.UnitService;
import com.xiaodou.st.dashboard.util.ExcelUtil;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class ScoreService extends BaseDashboardService {
  @Resource
  IStServiceFacade stServiceFacade;

  @Resource
  ProductService productService;

  @Resource
  UnitService unitService;

  @Resource
  ApplyService applyService;

  public Page<ScoreDO> listScore(ScoreDO scoreDO, Page<ScoreDO> pg) {
    Map<String, Object> inputs = Maps.newHashMap();
    INPUTS: {
      if (null == scoreDO) break INPUTS;
      if (StringUtils.isNotBlank(scoreDO.getExamDate()))
        inputs.put("examDate", scoreDO.getExamDate());
      if (null != scoreDO.getCatId()) inputs.put("catId", scoreDO.getCatId());
      if (null != scoreDO.getProductId()) inputs.put("productId", scoreDO.getProductId());
      if (null != scoreDO.getPilotUnitId()) inputs.put("pilotUnitId", scoreDO.getPilotUnitId());
      if (null != scoreDO.getClassId()) inputs.put("classId", scoreDO.getClassId());
      if (null != scoreDO.getStudentId()) inputs.put("studentId", scoreDO.getStudentId());
      if (StringUtils.isNotBlank(scoreDO.getStudentName()))
        inputs.put("studentNameLike", scoreDO.getStudentName());
      if (StringUtils.isNotBlank(scoreDO.getAdmissionCardCode()))
        inputs.put("admissionCardCodeLike", scoreDO.getAdmissionCardCode());
      if (StringUtils.isNotBlank(scoreDO.getTelephone()))
        inputs.put("telephoneLike", scoreDO.getTelephone());
    }
    Map<String, Object> outputs = CommUtil.getAllField(ScoreDO.class);
    outputs.remove("coefficient");
    return stServiceFacade.listScore(inputs, outputs, pg);
  }

  public void exportScoreList(List<ScoreDO> list, HttpServletResponse response, String fileName) {
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    AdminUser adminUser = super.getAdminUser();
    if (adminUser.getRole().equals(Constants.POILT_UNIT_ROLE)) {
      fieldMap.put("className", "班级名称");
    }
    fieldMap.put("studentName", "姓名");
    fieldMap.put("admissionCardCode", "准考证号");
    fieldMap.put("catName", "专业");
    fieldMap.put("productName", "课程名称");
    fieldMap.put("endTime", "结课时间");
    fieldMap.put("score", "成绩");
    if (list != null && list.size() > 0) {
      for (ScoreDO vo : list) {
        if (vo == null) continue;
        if (null == vo.getScore())
          vo.setScore(0D);
        else
          vo.setScore(Double.valueOf(Constants.DECIMAL_FORMAT.format(vo.getScore())));
      }
    }
    try {
      ExcelUtil.listToExcel(list, fieldMap, "成绩汇总表", response, fileName);
    } catch (Exception e) {
      LoggerUtil.error("导出excel异常！", e);
    }
  }

  public void exportTransferScoreList(List<ScoreDO> list, HttpServletResponse response,
      String fileName) {
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    AdminUser adminUser = super.getAdminUser();
    if (adminUser.getRole().equals(Constants.POILT_UNIT_ROLE)) {
      fieldMap.put("className", "班级名称");
    }
    fieldMap.put("studentName", "姓名");
    fieldMap.put("admissionCardCode", "准考证号");
    fieldMap.put("catName", "专业");
    fieldMap.put("productName", "课程名称");
    fieldMap.put("score", "学生端成绩");
    fieldMap.put("discountScore", "线上成绩");
    fieldMap.put("dailyScore", "线下成绩");
    fieldMap.put("reportFinalScore", "综合成绩");
    if (list != null && list.size() > 0) {
      for (ScoreDO vo : list) {
        if (null == vo) {
          continue;
        }
        if (StringUtils.isNotBlank(vo.getStudentName())) {
          vo.setStudentName(vo.getStudentName().trim());
        }
        if (null == vo.getScore() || null == vo.getCoefficient()) {
          vo.setDiscountScore(0D);
        } else {
          vo.setDiscountScore(Double.valueOf(Constants.DECIMAL_FORMAT.format(vo.getScore()
              * vo.getCoefficient())));
        }
        if (null == vo.getDailyScore()) {
          vo.setDailyScore(0D);
          vo.setReportFinalScore(Double.valueOf(Constants.DECIMAL_FORMAT.format(vo
              .getDiscountScore() + 0D)));
        } else {
          vo.setDailyScore(Double.valueOf(Constants.DECIMAL_FORMAT.format(vo.getDailyScore())));
          vo.setReportFinalScore(Double.valueOf(Constants.DECIMAL_FORMAT.format(vo
              .getDiscountScore() + vo.getDailyScore())));
        }
        if (null == vo.getCoefficient())
          vo.setCoefficient(0D);
        else
          vo.setCoefficient(Double.valueOf(Constants.DECIMAL_FORMAT.format(vo.getCoefficient())));
        if (null == vo.getScore())
          vo.setScore(0D);
        else
          vo.setScore(Double.valueOf(Constants.DECIMAL_FORMAT.format(vo.getScore())));
      }
    }
    try {
      ExcelUtil.listToExcel(list, fieldMap, "成绩汇总表", response, fileName);
    } catch (Exception e) {
      LoggerUtil.error("导出excel异常！", e);
    }
  }

  public List<LearnRecordDO> listLearnRecord(LearnRecordDTO learnRecordDTO) {
    Map<String, Object> inputs = Maps.newHashMap();
    if (null == learnRecordDTO || null == learnRecordDTO.getProductId()
        || null == learnRecordDTO.getStudentId()) return null;
    inputs.put("productId", learnRecordDTO.getProductId());
    inputs.put("studentId", learnRecordDTO.getStudentId());
    if (null != learnRecordDTO.getLearnType())
      inputs.put("learnType", learnRecordDTO.getLearnType());
    if (StringUtils.isNotBlank(learnRecordDTO.getRecordTime())) {
      inputs.put("recordTime", learnRecordDTO.getRecordTime());
    }
    Page<LearnRecordDO> page =
        stServiceFacade.listLearnRecord(inputs, CommUtil.getAllField(LearnRecordDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  public ScoreDO listScoreByCond(Long productId, Long studentId) {
    Map<String, Object> inputs = Maps.newHashMap();
    if (null == productId || null == studentId) return null;
    inputs.put("productId", productId);
    inputs.put("studentId", studentId);
    Map<String, Object> outputs = CommUtil.getAllField(ScoreDO.class);
    outputs.remove("coefficient");
    Page<ScoreDO> page = stServiceFacade.listScore(inputs, outputs, null);
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return null;
    return page.getResult().get(0);
  }

  public ScoreDO getScoreById(Long id) {
    return stServiceFacade.getScoreById(id);
  }

  public void downloadExcel(String sheetName, String fileName, List<ImportScoreDTO> list,
      HttpServletResponse response) {
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    fieldMap.put("studentName", "姓名");
    fieldMap.put("admissionCardCode", "准考证号");
    fieldMap.put("catCode", "专业代码");
    fieldMap.put("catName", "专业名称");
    fieldMap.put("productCode", "课程代码");
    fieldMap.put("productName", "课程名称");
    fieldMap.put("discountScore", "线上成绩");
    fieldMap.put("dailyScore", "线下成绩");
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
    List<ImportScoreDTO> scoreList = this.readExcel(url);
    if (null == scoreList) return Constants.PROMPT_HEADERERROR_MSG;
    if (scoreList.isEmpty())
      return "共检测到成绩信息0条，其中有效信息<span id='verifyCount'>0</span>条，错误成绩信息<span id='errorCount'>0</span>条。<br/>请上传正常格式与数据正常的excel！";
    Integer totalCount = scoreList.size();
    Integer errorCount = 0;

    Map<String, StudentDO> admissionCardMap = Maps.newHashMap();
    packageMap(admissionCardMap);
    Map<Long, UnitDO> packageUnitMap = Maps.newHashMap();
    packageUnitMap(packageUnitMap);
    HashMap<String, String> majorCodes = Maps.newHashMap();
    wrapMajorCodes(majorCodes);
    HashMap<String, String> productCodes = Maps.newHashMap();
    wrapProductCodes(productCodes);
    HashMap<String, Integer> productFullScore = Maps.newHashMap();
    wrapProductFullScore(productFullScore);
    // Map<String, ApplyDO> successApplyMap = Maps.newHashMap();
    // packageSuccessApplyMap(successApplyMap);
    Map<String, ScoreDO> scoreMap = Maps.newHashMap();
    packageScoreMap(scoreMap);

    Map<String, AtomicInteger> existsDataMap = Maps.newHashMap();
    for (ImportScoreDTO isdto : scoreList) {
      if (null == isdto) continue;
      String key =
          isdto.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + isdto.getProductCode();
      AtomicInteger ai = new AtomicInteger(1);
      if (existsDataMap.containsKey(key)) ai.incrementAndGet();
      existsDataMap.put(key, ai);
    }
    for (ImportScoreDTO isdto : scoreList) {
      if (null == isdto) continue;
      boolean flag = false;
      String key =
          isdto.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + isdto.getProductCode();
      if (existsDataMap.get(key).get() == 1) {
        flag =
            this.detectionResult(isdto, majorCodes, admissionCardMap, productCodes,
                productFullScore, packageUnitMap, scoreMap, super.getAdminUser());
      }
      if (!flag) errorCount++;
    }
    verifyCount = totalCount - errorCount;
    if (errorCount == 0) {
      msg =
          "共检测到成绩信息<span id='totalCount'>" + totalCount
              + "</span>条，其中有效成绩信息<span id='verifyCount'>" + verifyCount
              + "</span>条，错误成绩信息<span id='errorCount'>" + errorCount + "</span>条。";
      return msg;
    }
    msg =
        "共检测到成绩信息<span id='totalCount'>" + totalCount + "</span>条，其中有效成绩信息<span id='verifyCount'>"
            + verifyCount + "</span>条，错误成绩信息<span id='errorCount'>" + errorCount
            + "</span>条。错误数据已为您标出，可直接下载";
    return msg;
  }

  /**
   * 读取Excel->ImportStudentDTO
   */
  public List<ImportScoreDTO> readExcelOld(String urlString) {
    Map<String, Integer> scoreCondMap = Maps.newHashMap();
    scoreCondMap.put("姓名", 0);
    scoreCondMap.put("准考证号", 0);
    scoreCondMap.put("专业代码", 0);
    scoreCondMap.put("专业名称", 0);
    scoreCondMap.put("课程代码", 0);
    scoreCondMap.put("课程名称", 0);
    scoreCondMap.put("平时成绩", 0);
    List<ImportScoreDTO> lineList = Lists.newArrayList();
    try {
      // URL url = new URL(urlString);
      // URLConnection conn = url.openConnection();
      // InputStream is = conn.getInputStream();
      InputStream is = new FileInputStream("D:/公司/小逗网络/excel/template20171023085206.xls");
      Workbook book = Workbook.getWorkbook(is);
      // 获得第一个工作表对象
      Sheet sheet = book.getSheet(0);
      // j:列，i:行
      // msg在不读取
      Field[] fields = ImportScoreDTO.class.getDeclaredFields();
      int col = fields.length - 2;
      for (int j = 0; j <= col; j++) {
        Cell cell = sheet.getCell(j, 0);
        String result = cell.getContents();
        if (StringUtils.isEmpty(result)) return null;
        if (scoreCondMap.containsKey(result)) {
          scoreCondMap.put(result.trim(), scoreCondMap.get(result) + 1);
        }
      }
      for (String key : scoreCondMap.keySet()) {
        if (scoreCondMap.get(key) != 1) {
          throw new RuntimeException(Constants.PROMPT_HEADERERROR_MSG);
        }
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
          ImportScoreDTO headTest = FastJsonUtil.fromJson(rowStr, ImportScoreDTO.class);
          lineList.add(headTest);
        }
      }
      book.close();
    } catch (Exception e) {
      LoggerUtil.error("excel检测异常！", e);
    }
    return lineList;
  }


  // public static void main(String[] args) {
  // // System.out.println(ImportScoreDTO.class.getDeclaredFields().length);
  // ScoreService service = new ScoreService();
  // List<ImportScoreDTO> list = service.readExcel("aa.xlsx");
  // for (ImportScoreDTO a : list) {
  // System.out.println(a.toString());
  // }
  // }
  public List<ImportScoreDTO> readExcel(String urlString) {
    List<ImportScoreDTO> lineList = Lists.newArrayList();
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
  public List<ImportScoreDTO> readXLSFile(InputStream is) throws IllegalArgumentException,
      IllegalAccessException, IOException {
    Map<String, Integer> scoreCondMap = Maps.newHashMap();
    scoreCondMap.put("姓名", 0);
    scoreCondMap.put("准考证号", 0);
    scoreCondMap.put("专业代码", 0);
    scoreCondMap.put("专业名称", 0);
    scoreCondMap.put("课程代码", 0);
    scoreCondMap.put("课程名称", 0);
    scoreCondMap.put("线上成绩", 0);
    scoreCondMap.put("线下成绩", 0);
    List<ImportScoreDTO> lineList = Lists.newArrayList();
    HSSFWorkbook wb = new HSSFWorkbook(is);
    // 获得第一个工作表对象
    HSSFSheet sheet = wb.getSheetAt(0);
    HSSFRow row;
    HSSFCell cell;
    Iterator rows = sheet.rowIterator();
    Field[] fields = ImportScoreDTO.class.getDeclaredFields();
    while (rows.hasNext()) {
      row = (HSSFRow) rows.next();
      Iterator cells = row.cellIterator();
      ImportScoreDTO idto = new ImportScoreDTO();
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
          if (cell.getCellNum() == 1) result = df.format(number);
          if (cell.getCellNum() == 6 || cell.getCellNum() == 7)
            result = Constants.SCORE_FORMAT.format(number);
        } else {
          // U Can Handel Boolean, Formula, Errors
        }
        if (row.getRowNum() == sheet.getFirstRowNum()) {
          if (scoreCondMap.containsKey(result)) {
            scoreCondMap.put(result, scoreCondMap.get(result) + 1);
            if (scoreCondMap.get(result) > 1) {
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
      if (row.getRowNum() != sheet.getFirstRowNum()
          && !StringUtils.isAllBlank(idto.getAdmissionCardCode(), idto.getCatCode(),
              idto.getCatName(), idto.getDailyScore(), idto.getProductCode(),
              idto.getProductName(), idto.getStudentName())) lineList.add(idto);
    }
    return lineList;
  }

  @SuppressWarnings("rawtypes")
  public List<ImportScoreDTO> readXLSXFile(InputStream is) throws IllegalArgumentException,
      IllegalAccessException, IOException {
    Map<String, Integer> scoreCondMap = Maps.newHashMap();
    scoreCondMap.put("姓名", 0);
    scoreCondMap.put("准考证号", 0);
    scoreCondMap.put("专业代码", 0);
    scoreCondMap.put("专业名称", 0);
    scoreCondMap.put("课程代码", 0);
    scoreCondMap.put("课程名称", 0);
    scoreCondMap.put("线上成绩", 0);
    scoreCondMap.put("线下成绩", 0);
    List<ImportScoreDTO> lineList = Lists.newArrayList();
    XSSFWorkbook wb = new XSSFWorkbook(is);
    // 获得第一个工作表对象
    XSSFSheet sheet = wb.getSheetAt(0);
    XSSFRow row;
    XSSFCell cell;
    Iterator rows = sheet.rowIterator();
    Field[] fields = ImportScoreDTO.class.getDeclaredFields();
    while (rows.hasNext()) {
      row = (XSSFRow) rows.next();
      Iterator cells = row.cellIterator();
      ImportScoreDTO idto = new ImportScoreDTO();
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
          if (cell.getColumnIndex() == 1) result = df.format(number);
          if (cell.getColumnIndex() == 6 || cell.getColumnIndex() == 7)
            result = Constants.SCORE_FORMAT.format(number);
        } else {
          // U Can Handel Boolean, Formula, Errors
        }
        if (row.getRowNum() == sheet.getFirstRowNum()) {
          if (scoreCondMap.containsKey(result)) {
            scoreCondMap.put(result, scoreCondMap.get(result) + 1);
            if (scoreCondMap.get(result) > 1) {
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
      if (row.getRowNum() != sheet.getFirstRowNum()
          && !StringUtils.isAllBlank(idto.getAdmissionCardCode(), idto.getCatCode(),
              idto.getCatName(), idto.getDailyScore(), idto.getProductCode(),
              idto.getProductName(), idto.getStudentName())) lineList.add(idto);
    }
    return lineList;
  }

  @SuppressWarnings({"deprecation"})
  public String importScore(String url) {
    LoggerUtil.alarmInfo("start----" + System.currentTimeMillis());
    List<ImportScoreDTO> scoreList = this.readExcel(url);
    Integer count = 0;
    if (null == scoreList || scoreList.size() <= 0) return count.toString();

    HashMap<String, String> majorCodes = Maps.newHashMap();
    wrapMajorCodes(majorCodes);
    HashMap<String, String> productCodes = Maps.newHashMap();
    wrapProductCodes(productCodes);
    HashMap<String, Integer> productFullScore = Maps.newHashMap();
    wrapProductFullScore(productFullScore);
    Map<String, StudentDO> admissionCardMap = Maps.newHashMap();
    packageMap(admissionCardMap);
    Map<Long, UnitDO> packageUnitMap = Maps.newHashMap();
    packageUnitMap(packageUnitMap);
    // Map<String, ApplyDO> successApplyMap = Maps.newHashMap();
    // packageSuccessApplyMap(successApplyMap);
    Map<String, ScoreDO> scoreMap = Maps.newHashMap();
    packageScoreMap(scoreMap);
    // 更新操作
    AdminUser adminUser = super.getAdminUser();
    LoggerUtil.alarmInfo("packageMap end----" + System.currentTimeMillis());
    Map<String, AtomicInteger> existsDataMap = Maps.newHashMap();
    for (ImportScoreDTO isdto : scoreList) {
      if (null == isdto) continue;
      String key =
          isdto.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + isdto.getProductCode();
      AtomicInteger ai = new AtomicInteger(1);
      if (existsDataMap.containsKey(key)) ai.incrementAndGet();
      existsDataMap.put(key, ai);
    }
    for (ImportScoreDTO isdto : scoreList) {
      if (null == isdto) continue;
      String key =
          isdto.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + isdto.getProductCode();
      if (existsDataMap.get(key).get() != 1) continue;
      if (!this.detectionResult(isdto, majorCodes, admissionCardMap, productCodes,
          productFullScore, packageUnitMap, scoreMap, super.getAdminUser())) continue;
      // 根据已有的条件进行查询

      List<ScoreDO> pageScoreList = this.findScoreByCond(isdto);
      if (pageScoreList != null && pageScoreList.size() > 0) {
        // ScoreDO targetDO = pageScoreList.get(0);
        for (ScoreDO targetDO : pageScoreList) {
          if (targetDO != null) {
            ScoreDO vo = new ScoreDO();
            vo.setId(targetDO.getId());
            String df = Constants.DECIMAL_FORMAT.format(Double.valueOf(isdto.getDailyScore()));
            vo.setDailyScore(Double.valueOf(df));
            vo.setDailyScoreOperator(adminUser.getUserId());
            vo.setDailyScoreOperateTime(new Timestamp(System.currentTimeMillis()));
            stServiceFacade.updateScoreByDailyScore(vo);
          }
        }
      } else {}
      count++;
    }
    LoggerUtil.alarmInfo("for循环组装list end----" + System.currentTimeMillis());
    /**
     * 批量更新操作
     */
    // count = this.saveStudentList(sdoList, classId);
    LoggerUtil.alarmInfo("批量更新  end----" + System.currentTimeMillis());
    return count.toString();
  }

  public void packageMap(Map<String, StudentDO> admissionCardMap) {
    Assert.notNull(admissionCardMap, "admissionCardMap can't be null.");
    Page<StudentDO> page =
        stServiceFacade.listStudent(null, CommUtil.getAllField(StudentDO.class), null);
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return;
    for (StudentDO student : page.getResult()) {
      if (null == student) continue;
      student.setRealName(student.getRealName().trim());
      admissionCardMap.put(student.getAdmissionCardCode(), student);
    }
  }

  public void packageUnitMap(Map<Long, UnitDO> unitMap) {
    Assert.notNull(unitMap, "unitMap can't be null.");
    List<UnitDO> list = unitService.listPilotUnit();
    if (null == list || list.isEmpty()) return;
    for (UnitDO udo : list) {
      if (null == udo) continue;
      unitMap.put(udo.getId(), udo);
    }
  }

  public void packageScoreMap(Map<String, ScoreDO> scoreMap) {
    Assert.notNull(scoreMap, "scoreMap can't be null.");
    // Map<String, Object> inputs = Maps.newHashMap();
    Page<ScoreDO> page =
    // stServiceFacade.listScoreNoCommon(inputs, CommUtil.getAllField(ScoreDO.class), null);
        this.listScore(null, null);
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return;
    for (ScoreDO sdo : page.getResult()) {
      if (null == sdo) continue;
      sdo.setStudentName(sdo.getStudentName().trim());
      String key =
          sdo.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + sdo.getProductCode()
              + Constants.UNIQUE_SEPEATEOR + sdo.getUnitId();
      scoreMap.put(key, sdo);
    }
  }

  /**
   * @deprecated
   * @description TODO
   * @author 李德洪
   * @Date 2017年10月30日
   * @param successApplyMap
   */
  public void packageSuccessApplyMap1(Map<String, ApplyDO> successApplyMap) {
    Assert.notNull(successApplyMap, "successApplyMap can't be null.");
    ApplyDO applyDO = new ApplyDO();
    applyDO.setOrderStatus(Constants.ALREADYPAYMENT);
    applyDO.setApplyStatus(Constants.HAS_APPLY);
    List<ApplyDO> adoList = applyService.listApply(applyDO);
    HashMap<Integer, String> majorIds = Maps.newHashMap();
    wrapMajorIds(majorIds);
    if (null == adoList || adoList.isEmpty()) return;
    for (ApplyDO ado : adoList) {
      if (null == ado) continue;
      String key =
          ado.getStudentName().trim() + Constants.UNIQUE_SEPEATEOR
              + ado.getAdmissionCardCode().trim() + Constants.UNIQUE_SEPEATEOR
              + majorIds.get(ado.getCatId()).trim() + Constants.UNIQUE_SEPEATEOR
              + ado.getCatName().trim() + Constants.UNIQUE_SEPEATEOR + ado.getProductCode().trim()
              + Constants.UNIQUE_SEPEATEOR + ado.getProductName().trim();
      successApplyMap.put(key, ado);
    }
  }

  private boolean detectionResult(ImportScoreDTO isdto, Map<String, String> majorCodes,
      Map<String, StudentDO> admissionCardMap, Map<String, String> productCodes,
      Map<String, Integer> productFullScore, Map<Long, UnitDO> packageUnitMap,
      Map<String, ScoreDO> scoreMap, AdminUser adminUser) {
    // boolean flag = true;
    // 检测姓名
    if (StringUtils.isBlank(isdto.getStudentName())) {
      return false;
    }
    // 检测准考证号
    if (StringUtils.isNotBlank(isdto.getAdmissionCardCode())) {
      boolean f = PhoneUtil.validateNumber(isdto.getAdmissionCardCode(), 12);
      boolean g = admissionCardMap.containsKey(isdto.getAdmissionCardCode());
      if (!f || !g) {
        return false;
      }
    } else {
      return false;
    }
    // 检测学生所在第三级单位与当前单位是否一致
    StudentDO sdo = admissionCardMap.get(isdto.getAdmissionCardCode());
    if (null == sdo || !packageUnitMap.containsKey(sdo.getPilotUnitId())) {
      return false;
    }
    if (sdo.getPilotUnitId() != adminUser.getUnitId()) {
      return false;
    }
    // 校验专业代码/专业名称
    if (StringUtils.isNotBlank(isdto.getCatCode())) {
      if (!majorCodes.containsKey(isdto.getCatCode())) {
        return false;
      } else {
        // 校验专业名称
        if (StringUtils.isNotBlank(isdto.getCatName())) {
          if (!majorCodes.containsValue(isdto.getCatName())
              || !majorCodes.get(isdto.getCatCode()).equals(isdto.getCatName())) {
            return false;
          }
        } else {
          return false;
        }
      }
    } else {
      return false;
    }

    // 校验课程代码和课程名称
    if (StringUtils.isNotBlank(isdto.getProductCode())) {
      if (!productCodes.containsKey(isdto.getProductCode())) {
        return false;
      } else {
        // 校验课程名称
        if (StringUtils.isNotBlank(isdto.getProductName())) {
          if (!productCodes.containsValue(isdto.getProductName())
              || !productCodes.get(isdto.getProductCode()).equals(isdto.getProductName())) {
            return false;
          }
        } else {
          return false;
        }
      }
    } else {
      return false;
    }

    // 校验平时成绩
    if (StringUtils.isBlank(isdto.getDailyScore())) {
      return false;
    } else {
      Integer maxScore = productFullScore.get(isdto.getProductCode());
      if (NumberUtils.isNumber(isdto.getDailyScore())) {
        double tmpDailyScore = Double.valueOf(isdto.getDailyScore());
        if (null == maxScore || tmpDailyScore > maxScore || tmpDailyScore < 0) return false;
      } else
        return false;
    }

    // 根据现有的条件取查询
    String scoreKey =
        isdto.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + isdto.getProductCode()
            + Constants.UNIQUE_SEPEATEOR + adminUser.getUnitId();
    if (!scoreMap.containsKey(scoreKey)) {
      return false;
    }
    // List<ScoreDO> pageScoreList = this.findScoreByCond(isdto);
    // if (pageScoreList == null || pageScoreList.size() <= 0) {
    // return false;
    // }
    return true;
  }

  /**
   * 生成Excel
   */
  public List<ImportScoreDTO> createErrorExcel(String url) {
    AdminUser adminUser = super.getAdminUser();
    List<ImportScoreDTO> scoreList = this.readExcel(url);
    if (null == scoreList) return Lists.newArrayList();
    // 查出所有scoreDO的数据
    HashMap<String, String> majorCodes = Maps.newHashMap();
    wrapMajorCodes(majorCodes);
    HashMap<String, String> productCodes = Maps.newHashMap();
    wrapProductCodes(productCodes);
    HashMap<String, Integer> productFullScore = Maps.newHashMap();
    wrapProductFullScore(productFullScore);
    Map<String, StudentDO> admissionCardMap = Maps.newHashMap();
    packageMap(admissionCardMap);
    Map<Long, UnitDO> packageUnitMap = Maps.newHashMap();
    packageUnitMap(packageUnitMap);
    // Map<String, ApplyDO> successApplyMap = Maps.newHashMap();
    // packageSuccessApplyMap(successApplyMap);
    Map<String, ScoreDO> scoreMap = Maps.newHashMap();
    packageScoreMap(scoreMap);
    Map<String, AtomicInteger> existsDataMap = Maps.newHashMap();
    for (ImportScoreDTO isdto : scoreList) {
      if (null == isdto) continue;
      String key =
          isdto.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + isdto.getProductCode();
      AtomicInteger ai = new AtomicInteger(1);
      if (existsDataMap.containsKey(key)) ai.incrementAndGet();
      existsDataMap.put(key, ai);
    }
    for (ImportScoreDTO isdto : scoreList) {
      // StringBuffer msg = new StringBuffer(100);
      if (null == isdto) continue;
      String key =
          isdto.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + isdto.getProductCode();
      if (existsDataMap.get(key).get() != 1) {
        // msg.append(Constants.PROMPT_SCORE_REPEATED);
        isdto.setMsg(Constants.PROMPT_SCORE_REPEATED);
        continue;
      }
      // 检测姓名
      if (StringUtils.isBlank(isdto.getStudentName())) {
        // msg.append(Constants.ERROR_NAME_EMPTY);
        isdto.setMsg(Constants.ERROR_NAME_EMPTY);
        continue;
      }
      // 检测准考证号
      if (StringUtils.isNotBlank(isdto.getAdmissionCardCode())) {
        if (!PhoneUtil.validateNumber(isdto.getAdmissionCardCode(), 12)) {
          // msg.append(Constants.ERROR_CARD_ERROR);
          isdto.setMsg(Constants.ERROR_CARD_ERROR);
          continue;
        } else if (!admissionCardMap.containsKey(isdto.getAdmissionCardCode())) {
          // msg.append(Constants.PROMPT_SCORE_ERROR_CARD_NOTEXISTS);
          isdto.setMsg(Constants.PROMPT_SCORE_ERROR_CARD_NOTEXISTS);
          continue;
        }
      } else {
        // msg.append(Constants.PROMPT_SCORE_ERROR_CARD_EMPTY);
        isdto.setMsg(Constants.PROMPT_SCORE_ERROR_CARD_EMPTY);
        continue;
      }
      // 检测学生所在第三级单位与当前单位是否一致
      StudentDO sdo = admissionCardMap.get(isdto.getAdmissionCardCode());
      if (null == sdo || !packageUnitMap.containsKey(sdo.getPilotUnitId())) {
        isdto.setMsg(Constants.PROMPT_SCORE_ERROR_POILT_UNIT_NOTEXISTS);
        continue;
      }
      if (sdo.getPilotUnitId() != adminUser.getUnitId()) {
        isdto.setMsg(Constants.PROMPT_SCORE_ERROR_POILT_UNIT_MATCH);
        continue;
      }
      // 检测专业代码和专业名称
      if (StringUtils.isNotBlank(isdto.getCatCode())) {
        if (!majorCodes.containsKey(isdto.getCatCode())) {
          // msg.append(Constants.PROMPT_SCORE_ERROR_CATCODE_ERROR);
          isdto.setMsg(Constants.PROMPT_SCORE_ERROR_CATCODE_ERROR);
          continue;
        } else {
          if (StringUtils.isNotBlank(isdto.getCatName())) {
            if (!majorCodes.get(isdto.getCatCode()).equals(isdto.getCatName())) {
              isdto.setMsg(Constants.PROMPT_SCORE_ERROR_CATCODE_CATNAME_MISMATCH);
              continue;
            }
          } else {
            isdto.setMsg(Constants.PROMPT_SCORE_ERROR_CATNAME_EMPTY);
            continue;
          }
        }
      } else {
        isdto.setMsg(Constants.PROMPT_SCORE_ERROR_CATCODE_EMPTY);
        continue;
      }

      // 检测课程代码和课程名称
      if (StringUtils.isNotBlank(isdto.getProductCode())) {
        if (StringUtils.isNotBlank(isdto.getProductName())) {
          if (!productCodes.containsKey(isdto.getProductCode())) {
            isdto.setMsg(Constants.PROMPT_SCORE_ERROR_PRODUCT_NOTEXISTS);
            continue;
          } else {
            if (!productCodes.get(isdto.getProductCode()).equals(isdto.getProductName())) {
              isdto.setMsg(Constants.PROMPT_SCORE_ERROR_PRODUCT_PRODUCTNAME_MISMATCH);
              continue;
            }
          }
        } else {
          isdto.setMsg(Constants.PROMPT_SCORE_ERROR_PRODUCTNAME_EMPTY);
          continue;
        }

      } else {
        isdto.setMsg(Constants.PROMPT_SCORE_ERROR_PRODUCT_EMPTY);
        continue;
      }

      // 检测平时成绩
      if (StringUtils.isBlank(isdto.getDailyScore())) {
        isdto.setMsg(Constants.PROMPT_SCORE_ERROR_DAILYSCORE_EMPTY);
        continue;
      } else {
        Integer maxScore = productFullScore.get(isdto.getProductCode());
        if (NumberUtils.isNumber(isdto.getDailyScore())) {
          double tmpDailyScore = Double.valueOf(isdto.getDailyScore());
          if (null == maxScore || tmpDailyScore > maxScore || tmpDailyScore < 0) {
            isdto.setMsg(String.format(Constants.PROMPT_SCORE_ERROR_DAILYSCORE_EXCEEDS, maxScore));
            continue;
          }
        } else {
          isdto.setMsg(Constants.PROMPT_SCORE_ERROR_DAILYSCORE_ERROR);
          continue;
        }
      }
      String scoreKey =
          isdto.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + isdto.getProductCode()
              + Constants.UNIQUE_SEPEATEOR + adminUser.getUnitId();
      if (!scoreMap.containsKey(scoreKey)) {
        isdto.setMsg(Constants.PROMPT_SCORE_INVALIDE_PRIVILAGES);
        continue;
      }

      // 根据现有的条件取查询
      // List<ScoreDO> pageScoreList = this.findScoreByCond(isdto);
      // if (pageScoreList == null || pageScoreList.size() <= 0) {
      // isdto.setMsg(Constants.PROMPT_SCORE_INVALIDE_PRIVILAGES);
      // continue;
      // }
      // isdto.setMsg(msg.toString());
    }
    return scoreList;
  }

  private List<ScoreDO> findAllScoreList() {
    Map<String, Object> outputs = Maps.newHashMap();
    outputs.put("id", 1);
    outputs.put("studentId", 1);
    outputs.put("studentName", 1);
    outputs.put("admissionCardCode", 1);
    outputs.put("catCode", 1);
    outputs.put("catName", 1);
    outputs.put("productId", 1);
    outputs.put("productCode", 1);
    outputs.put("productName", 1);
    outputs.put("pilotUnitId", 1);
    outputs.put("pilotUnitName", 1);
    Page<ScoreDO> pageList = stServiceFacade.listScore(null, outputs, null);
    return pageList != null ? pageList.getResult() : null;
  }

  /**
   * @deprecated
   * @description TODO
   * @author 李德洪
   * @Date 2017年10月25日
   * @param allScoreMap
   */
  private void wrapAllScoreList(HashMap<String, ScoreDO> allScoreMap) {
    Assert.notNull(allScoreMap, "allScoreMap can't be null.");
    Map<String, Object> outputs = Maps.newHashMap();
    outputs.put("courseCode", "");
    outputs.put("dailyFullScore", "");
    List<ScoreDO> list = findAllScoreList();
    if (null != list && !list.isEmpty()) {
      for (ScoreDO sdo : list) {
        if (sdo == null) continue;
        sdo.setStudentName(sdo.getStudentName().trim());
        allScoreMap.put(sdo.getPilotUnitId() + sdo.getAdmissionCardCode(), sdo);
      }
    }
  }

  private void wrapProductFullScore(HashMap<String, Integer> productFullScore) {
    Assert.notNull(productFullScore, "productFullScore can't be null.");
    Map<String, Object> outputs = Maps.newHashMap();
    outputs.put("courseCode", "");
    outputs.put("dailyFullScore", "");
    Page<RawDataProductDO> listsPages = stServiceFacade.listRawDataProduct(null, outputs);
    if (listsPages != null && listsPages.getResult() != null && listsPages.getResult().size() > 0) {
      for (RawDataProductDO rdp : listsPages.getResult()) {
        if (rdp == null) continue;
        productFullScore.put(rdp.getCourseCode(), rdp.getDailyFullScore());
      }
    }
  }

  private void wrapProductCodes(HashMap<String, String> productCodes) {
    StaticInfoDO staticInfo = stServiceFacade.staticInfo();
    HashMap<String, Object> input = Maps.newHashMap();
    if (null != staticInfo) {
      input.put("examDate", staticInfo.getExamDate());
    }

    Assert.notNull(productCodes, "productCodes can't be null.");
    Map<String, Object> outputs = Maps.newHashMap();
    outputs.put("courseCode", "");
    outputs.put("name", "");
    Page<RawDataProductDO> listsPages = stServiceFacade.listRawDataProduct(input, outputs);
    if (listsPages != null && listsPages.getResult() != null && listsPages.getResult().size() > 0) {
      for (RawDataProductDO rdp : listsPages.getResult()) {
        if (rdp == null) continue;
        productCodes.put(rdp.getCourseCode(), rdp.getName());
      }
    }
  }


  private void wrapMajorCodes(HashMap<String, String> majorCodes) {
    Assert.notNull(majorCodes, "majorCodes can't be null.");
    List<RawDataProductCategoryDO> productList = productService.listCategory();
    if (productList != null) {
      for (RawDataProductCategoryDO rawDataProductCategoryDO : productList) {
        if (rawDataProductCategoryDO == null) continue;
        majorCodes.put(rawDataProductCategoryDO.getTypeCode(), rawDataProductCategoryDO.getName());
      }
    }
  }

  private void wrapMajorIds(HashMap<Integer, String> majorIds) {
    Assert.notNull(majorIds, "majorIds can't be null.");
    List<RawDataProductCategoryDO> productList = productService.listCategory();
    if (productList != null) {
      for (RawDataProductCategoryDO rawDataProductCategoryDO : productList) {
        if (rawDataProductCategoryDO == null) continue;
        majorIds.put(rawDataProductCategoryDO.getId(), rawDataProductCategoryDO.getTypeCode());
      }
    }
  }

  private List<ScoreDO> findScoreByCond(ImportScoreDTO isdto) {
    Map<String, Object> inputs = Maps.newHashMap();
    ScoreDO cond = new ScoreDO();
    cond.setPilotUnitId(getAdminUser().getUnitId());
    cond.setStudentName(isdto.getStudentName());
    cond.setAdmissionCardCode(isdto.getAdmissionCardCode());
    cond.setCatCode(isdto.getCatCode());
    cond.setCatName(isdto.getCatName());
    cond.setProductCode(isdto.getProductCode());
    cond.setProductName(isdto.getProductName());
    CommUtil.transferFromVO2Map(inputs, cond);
    List<ScoreDO> pageScoreList = this.findScoreByCond(inputs, null);
    return pageScoreList;
  }

  private List<ScoreDO> findScoreByCond(Map<String, Object> inputs, Page<ScoreDO> pg) {
    Map<String, Object> outputs = Maps.newHashMap();
    outputs.put("id", 1);
    outputs.put("studentId", 1);
    outputs.put("studentName", 1);
    outputs.put("admissionCardCode", 1);
    outputs.put("catCode", 1);
    outputs.put("catName", 1);
    outputs.put("productId", 1);
    outputs.put("productCode", 1);
    outputs.put("productName", 1);
    outputs.put("pilotUnitId", 1);
    outputs.put("pilotUnitName", 1);

    Page<ScoreDO> page = stServiceFacade.findPureScoreListByCond(inputs, outputs, null);
    return page == null ? null : (page.getResult() == null ? null : page.getResult());

  }

  public Page<ScoreDO> listScoreJoinProduct(ScoreDO scoreDO, Page<ScoreDO> pg) {
    Map<String, Object> inputs = Maps.newHashMap();
    INPUTS: {
      if (null == scoreDO) break INPUTS;
      if (StringUtils.isNotBlank(scoreDO.getExamDate()))
        inputs.put("examDate", scoreDO.getExamDate());
      if (null != scoreDO.getCatId()) inputs.put("catId", scoreDO.getCatId());
      if (null != scoreDO.getProductId()) inputs.put("productId", scoreDO.getProductId());
      if (null != scoreDO.getPilotUnitId()) inputs.put("pilotUnitId", scoreDO.getPilotUnitId());
      if (null != scoreDO.getClassId()) inputs.put("classId", scoreDO.getClassId());
      if (StringUtils.isNotBlank(scoreDO.getStudentName()))
        inputs.put("studentNameLike", scoreDO.getStudentName());
      if (StringUtils.isNotBlank(scoreDO.getAdmissionCardCode()))
        inputs.put("admissionCardCodeLike", scoreDO.getAdmissionCardCode());
      if (StringUtils.isNotBlank(scoreDO.getTelephone()))
        inputs.put("telephoneLike", scoreDO.getTelephone());
    }
    // Map<String, Object> outputs = Maps.newHashMap();
    // outputs.put("id", 1);
    // outputs.put("pilotUnitName", 1);
    // outputs.put("studentId", 1);
    // outputs.put("studentName", 1);
    // outputs.put("admissionCardCode", 1);
    // outputs.put("catName", 1);
    // outputs.put("productId", 1);
    // outputs.put("productName", 1);
    // outputs.put("endTime", 1);
    // outputs.put("score", 1);
    // outputs.put("className", 1);
    //
    // outputs.put("dailyScore", 1);
    // outputs.put("coefficient", 1);
    //
    return stServiceFacade
        .findScoreListJoinProduct(inputs, CommUtil.getAllField(ScoreDO.class), pg);
  }

  /**
   * @deprecated
   * @description TODO
   * @author 李德洪
   * @Date 2017年10月31日
   * @return
   */
  public List<ImportScoreDTO> listScoreByApply1() {
    List<ImportScoreDTO> list = Lists.newArrayList();
    ApplyDO applyDO = new ApplyDO();
    applyDO.setOrderStatus(Constants.ALREADYPAYMENT);
    applyDO.setApplyStatus(Constants.HAS_APPLY);
    List<ApplyDO> adoList = applyService.listApply(applyDO);
    HashMap<Integer, String> majorIds = Maps.newHashMap();
    wrapMajorIds(majorIds);
    for (ApplyDO ado : adoList) {
      ImportScoreDTO idto = new ImportScoreDTO();
      idto.setStudentName(ado.getStudentName());
      idto.setAdmissionCardCode(ado.getAdmissionCardCode());
      idto.setCatCode(majorIds.get(ado.getCatId()));
      idto.setCatName(ado.getCatName());
      idto.setProductCode(ado.getProductCode());
      idto.setProductName(ado.getProductName());
      list.add(idto);
    }
    return list;
  }

  public List<ImportScoreDTO> listImportScore() {
    Page<ScoreDO> page = this.listScoreJoinProduct(null, null);
    List<ImportScoreDTO> list = Lists.newArrayList();
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return null;
    for (ScoreDO isdo : page.getResult()) {
      ImportScoreDTO idto = new ImportScoreDTO();
      idto.setStudentName(isdo.getStudentName());
      idto.setAdmissionCardCode(isdo.getAdmissionCardCode());
      idto.setCatCode(isdo.getCatCode());
      idto.setCatName(isdo.getCatName());
      idto.setProductCode(isdo.getProductCode());
      idto.setProductName(isdo.getProductName());
      String discountScore = "0";
      if (isdo.getScore() != null && isdo.getCoefficient() != null) {
        discountScore = Constants.DECIMAL_FORMAT.format(isdo.getScore() * isdo.getCoefficient());
      }
      idto.setDiscountScore(discountScore);
      list.add(idto);
    }
    return list;
  }

}
