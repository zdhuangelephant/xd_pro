package com.xiaodou.ms.service.exam;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.common.ResourceType;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionResourceModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionTypeModel;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseKeywordResourceService;
import com.xiaodou.ms.service.course.CourseKeywordService;
import com.xiaodou.ms.util.CsvUtil;
import com.xiaodou.ms.util.FileUtils;
import com.xiaodou.ms.vo.NeedProcessCsv;
import com.xiaodou.ms.web.request.exam.ExamQuestionCreateRequest;
import com.xiaodou.summer.dao.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zyp on 15/9/1.
 */
@Service("questionInputService")
public class QuestionInputService {

  // 来源service
  @Resource
  QuestionBankQuestionResourceService questionBankQuestionResourceService;

  // 试题类型service
  @Resource
  QuestionBankQuestionTypeService questionBankQuestionTypeService;

  // 关键词service
  @Resource
  CourseKeywordService courseKeywordService;

  // 关键词关联service
  @Resource
  CourseKeywordResourceService courseKeywordResourceService;

  // 章节service
  @Resource
  CourseChapterService courseChapterService;

  // 题库service
  @Resource
  QuestionBankQuestionService questionBankQuestionService;

  /**
   * 初始化
   * @return
   */
  public List<NeedProcessCsv> initCsvList(){
    Hashtable<Object, Object> keyValues = FileUtils.CSV.getPropertyFile();
    List<NeedProcessCsv> csvList = new ArrayList<>();
    for (Object key:keyValues.keySet()){
      String csvKey = (String)key;
      String[] keySplite = csvKey.split("_");
      String csvValue = (String)keyValues.get(key);
      NeedProcessCsv needProcessCsv = new NeedProcessCsv();
      needProcessCsv.setCourseId(Long.parseLong(keySplite[1]));
      needProcessCsv.setFile(csvValue);
      needProcessCsv.setType(Integer.parseInt(keySplite[2]));
      csvList.add(needProcessCsv);
    }
    return csvList;
  }

  /**
   * 批量预先处理
   */
  public void preProcess(){
    List<NeedProcessCsv> processList = this.initCsvList();
    for (NeedProcessCsv processCsv:processList){
      System.out.println(processCsv.getFile());
      this.preProcessType(processCsv);
      this.preProcessResource(processCsv);
      this.preProcessKeyword(processCsv);
      this.preProcessZhenti(processCsv);
    }
  }

  /**
   * 批量处理
   */
  public void process(){
    List<NeedProcessCsv> processList = this.initCsvList();
    for (NeedProcessCsv processCsv:processList){
      this.processQuestion(processCsv);
    }
  }

  public void processKeywordRelation(){
    List<NeedProcessCsv> processList = this.initCsvList();
    for (NeedProcessCsv processCsv:processList){
      this.processQuestionKeywordResource(processCsv);
    }
  }

  // 1.试题类型预处理
  public void preProcessType(NeedProcessCsv needProcessCsv){

    Boolean zhenti = false;
    if (needProcessCsv.getType().equals(NeedProcessCsv.zhenti)) {
      zhenti = true;
    }

    List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
    Map<String,Integer> typeMap = new HashMap<>();
    for (QuestionBankQuestionTypeModel questionType:typeList){
      typeMap.put(questionType.getTypeName(),questionType.getId());
    }
    List<String[]> allRow = CsvUtil.getAllData(needProcessCsv.getFile());
    List<String[]> result = new ArrayList<>();
    result.add(this.copyRow(allRow.get(0)));
    for (int i=1;i<allRow.size();i++){
      String[] row = allRow.get(i);
      String[] newRow = this.copyRow(row);
      if (zhenti) {
        String typeName = row[5];
        Integer typeId = typeMap.get(StringUtils.trim(typeName));
        if (typeId!=null){
          newRow[17] = typeId.toString();
        } else {
          newRow[17] = "";
        }
      } else {
        newRow[17] = "1";
      }
      result.add(newRow);
    }
    CsvUtil.writeData(result,needProcessCsv.getFile());
  }

  // 2.来源预处理
  public void preProcessResource(NeedProcessCsv needProcessCsv){

    Boolean zhenti = false;
    if (needProcessCsv.getType().equals(NeedProcessCsv.zhenti)){
      zhenti = true;
    }

    List<QuestionBankQuestionResourceModel> resourceList =
      questionBankQuestionResourceService.resourceList();
    Map<String,Integer> resourceMap = new HashMap<>();
    for (QuestionBankQuestionResourceModel resource:resourceList){
      resourceMap.put(resource.getName(),resource.getId());
    }
    List<String[]> allRow = CsvUtil.getAllData(needProcessCsv.getFile());
    List<String[]> result = new ArrayList<>();
    result.add(this.copyRow(allRow.get(0)));
    for (int i=1;i<allRow.size();i++){
      String[] row = allRow.get(i);
      String[] newRow = this.copyRow(row);
      String typeName = row[4];
      if (StringUtils.isNotBlank(typeName)) {
        Integer typeId = resourceMap.get(StringUtils.trim(typeName));
        if (typeId!=null) {
          newRow[18] = typeId.toString();
        } else {
          newRow[18] = "0";
        }
      } else {
        newRow[18] = "0";
      }
      result.add(newRow);
    }
    CsvUtil.writeData(result,needProcessCsv.getFile());
  }

  // 4.关键词预处理
  public void preProcessKeyword(NeedProcessCsv needProcessCsv){

    Boolean zhenti = false;
    if (needProcessCsv.getType().equals(NeedProcessCsv.zhenti)){
      zhenti = true;
    }

    Long chapterId = 0L;
    Page<CourseKeywordModel> keywordList =
      courseKeywordService.cascadeQueryKeywordByChapter(needProcessCsv.getCourseId(), chapterId);
    Map<String,CourseKeywordModel> keywordMap = new HashMap<>();
    for (CourseKeywordModel keyword:keywordList.getResult()){
      keywordMap.put(keyword.getDetail(),keyword);
    }
    List<String[]> allRow = CsvUtil.getAllData(needProcessCsv.getFile());
    List<String[]> result = new ArrayList<>();
    result.add(this.copyRow(allRow.get(0)));
    for (int i=1;i<allRow.size();i++){
      String[] row = allRow.get(i);
      String[] newRow = this.copyRow(row);
      String typeName = row[3];
      CourseKeywordModel keyword = keywordMap.get(StringUtils.trim(typeName));
      if (keyword!=null) {
        newRow[19] = keyword.getId().toString();
        newRow[20] = keyword.getChapterId().toString();
      } else {
        newRow[19] = "";
        newRow[20] = "";
      }
      result.add(newRow);
    }
    CsvUtil.writeData(result,needProcessCsv.getFile());
  }

  // 5.预处理真题
  public void preProcessZhenti(NeedProcessCsv needProcessCsv){
    Boolean zhenti = false;
    if (needProcessCsv.getType().equals(NeedProcessCsv.zhenti)){
      zhenti = true;
    }
    List<String[]> allRow = CsvUtil.getAllData(needProcessCsv.getFile());
    List<String[]> result = new ArrayList<>();
    result.add(this.copyRow(allRow.get(0)));
    for (int i=1;i<allRow.size();i++) {
      String[] row = allRow.get(i);
      String[] newRow = this.copyRow(row);
      if (zhenti){
        newRow[21] = "1";
      } else {
        newRow[21] = "0";
      }
      result.add(newRow);
    }
    CsvUtil.writeData(result,needProcessCsv.getFile());
  }

  // 5.试题处理
  public void processQuestion(NeedProcessCsv needProcessCsv){

    // 17 试题类型
    // 18 试题来源
    // 19 关键词ID
    // 20 章节ID
    // 21 是否真题

    Boolean zhenti = false;
    if (needProcessCsv.getType().equals(NeedProcessCsv.zhenti)){
      zhenti = true;
    }
    List<String[]> allRow = CsvUtil.getAllData(needProcessCsv.getFile());
    List<String[]> result = new ArrayList<>();
    result.add(this.copyRow(allRow.get(0)));
    for (int i=1;i<allRow.size();i++){
      try {
        String[] row = allRow.get(i);
        String[] newRow = this.copyRow(row);
        if (zhenti) {
          if (StringUtils.isBlank(StringUtils.trim(row[6]))|| StringUtils.trim(row[6]).equals("0")) {
            result.add(newRow);
            continue;
          }
        } else {
          if (StringUtils.isBlank(StringUtils.trim(row[5]))||StringUtils.trim(row[5]).equals("0")){
            result.add(newRow);
            continue;
          }
        }
        ExamQuestionCreateRequest createRequest = new ExamQuestionCreateRequest();
        createRequest.setChapterId(Long.parseLong(StringUtils.trim(row[20])));  //章节ID
        createRequest.setCourseId(needProcessCsv.getCourseId()); //课程Id
        createRequest.setCheckBoxSelection("");   //多选选项
        createRequest.setCognitionLevel(1);  //认知层次
        createRequest.setDiffcultLevel(1); //难易程度
        if (zhenti) {
          createRequest.setManalyze(StringUtils.trim(row[12]));   //解析
        } else {
          createRequest.setManalyze(StringUtils.trim(row[11]));
        }
        if (zhenti) {
          createRequest.setMdesc(StringUtils.trim(row[6]));      //题干
        } else {
          createRequest.setMdesc(StringUtils.trim(row[5]));      //题干
        }
        createRequest.setOptionType(1);   //1 文字 2 图片
        createRequest.setQuesImgUrl("");  //图片地址
        createRequest.setQuestionType(Integer.parseInt(StringUtils.trim(row[17])));
        // 如果是单项选择题
        if (createRequest.getQuestionType().equals(1)){
          if (zhenti) {
            String answer = StringUtils.trim(row[11]);
            Integer[] answers = new Integer[4];
            if (answer.equals("A")||answer.equals("a")||answer.equals("Ａ")){
              answers[0] = 1;
            } else {
              answers[0] = 0;
            }
            if (answer.equals("B")||answer.equals("b")||answer.equals("Ｂ")){
              answers[1] = 1;
            } else {
              answers[1] = 0;
            }
            if (answer.equals("C")||answer.equals("c")||answer.equals("Ｃ")){
              answers[2] = 1;
            } else {
              answers[2] = 0;
            }
            if (answer.equals("D")||answer.equals("d")||answer.equals("Ｄ")){
              answers[3] = 1;
            } else {
              answers[3] = 0;
            }
            String radioSelection = row[7]+"|"+answers[0]+";"+row[8]+"|"+answers[1]+";"+row[9]+"|"+answers[2]+";"+row[10]+"|"+answers[3]+";";
            createRequest.setRadioSelection(radioSelection);  //单选选项
          } else {
            String answer = StringUtils.trim(row[10]);
            Integer[] answers = new Integer[4];
            if (answer.equals("A")||answer.equals("a")||answer.equals("Ａ")){
              answers[0] = 1;
            } else {
              answers[0] = 0;
            }
            if (answer.equals("B")||answer.equals("b")||answer.equals("Ｂ")){
              answers[1] = 1;
            } else {
              answers[1] = 0;
            }
            if (answer.equals("C")||answer.equals("c")||answer.equals("Ｃ")){
              answers[2] = 1;
            } else {
              answers[2] = 0;
            }
            if (answer.equals("D")||answer.equals("d")||answer.equals("Ｄ")){
              answers[3] = 1;
            } else {
              answers[3] = 0;
            }
            String radioSelection = row[6]+"|"+answers[0]+";"+row[7]+"|"+answers[1]+";"+row[8]+"|"+answers[2]+";"+row[9]+"|"+answers[3]+";";
            createRequest.setRadioSelection(radioSelection);  //单选选项
          }
        }
        createRequest.setZhenti(Integer.parseInt(StringUtils.trim(row[21])));   //是否真题
        createRequest.setResourceId(Integer.parseInt(StringUtils.trim(row[18])));  //来源Id
        QuestionBankQuestionModel questionModel =
          questionBankQuestionService.addQuestion(createRequest);
        newRow[22] = questionModel.getId().toString();
        result.add(newRow);
        //System.out.println(questionModel.getId());
      } catch (Exception e){
        result.add(this.copyRow(allRow.get(i)));
        System.out.println(needProcessCsv.getFile()+":"+i);
      }
    }
    CsvUtil.writeData(result,needProcessCsv.getFile());
  }

  // 6.试题关键词处理
  public void processQuestionKeywordResource(NeedProcessCsv needProcessCsv){
    List<String[]> allRow = CsvUtil.getAllData(needProcessCsv.getFile());
    for (int i=1;i<allRow.size();i++){
      String[] row = allRow.get(i);
      if (!(StringUtils.isBlank(row[22])||StringUtils.isBlank(row[19]))){
        try {
          Long resourceId = Long.parseLong(row[22]);
          Long keywordId = Long.parseLong(row[19]);
          if (resourceId!=null&&resourceId!=0&&keywordId!=null&&keywordId!=0) {
            courseKeywordResourceService.addKeywordResource(resourceId, ResourceType.QUESTION.getTypeId(), keywordId);
            courseKeywordResourceService.updateResourceKeyPoint(resourceId, ResourceType.QUESTION.getTypeId());
          } else {
            System.out.println("error");
          }
        } catch (Exception e){
          System.out.print(row[22]+":"+row[19]);
        }
      }
    }
  }

  /**
   * 行copy
   * @param row
   * @return
   */
  private String[] copyRow(String[] row){
    String[] newRow = new String[25];
    for (int i=0;i<row.length;i++){
      newRow[i] = row[i];
    }
    return newRow;
  }
}
