  package com.xiaodou.st.dashboard.web.controller.score;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.score.ImportScoreDTO;
import com.xiaodou.st.dashboard.domain.score.LearnRecordDO;
import com.xiaodou.st.dashboard.domain.score.LearnRecordDTO;
import com.xiaodou.st.dashboard.domain.score.ScoreDO;
import com.xiaodou.st.dashboard.domain.score.ScoreDO.ScoreNode;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.grade.ClassService;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.st.dashboard.service.product.ProductService;
import com.xiaodou.st.dashboard.service.score.ScoreService;
import com.xiaodou.st.dashboard.service.unit.UnitService;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.st.dashboard.web.controller.BaseController;
import com.xiaodou.summer.dao.pagination.Page;

@Controller("scoreController")
@RequestMapping("/score")
public class ScoreController extends BaseController {

  @Resource
  ScoreService scoreService;
  @Resource
  ClassService classService;
  @Resource
  ProductService productService;
  @Resource
  UnitService unitService;
  @Resource
  ManageStaticInfoService manageStaticInfoService;

  @RequestMapping("/score_list")
  public ModelAndView score(ScoreDO scoreDO) {
    ModelAndView mv = new ModelAndView("/score/scoreList");
    // select下拉框数据
    mv.addObject("listExamDate", productService.listExamDate());
    mv.addObject("listCategory", productService.listCategory());
    if (null != scoreDO && null != scoreDO.getCatId()) {
      mv.addObject("listProduct", productService.listProduct(scoreDO.getCatId()));
    }
    mv.addObject("listPilotUnit", unitService.listPilotUnit());
    mv.addObject("listClass", classService.listClass());

    Page<ScoreDO> pg = new Page<ScoreDO>();
    Integer pageNo = scoreDO.getPageNo();
    if (pageNo == null) {
      pageNo = 1;
      scoreDO.setPageNo(pageNo);
    }
    pg.setPageNo(pageNo);
    Integer pageSize = scoreDO.getPageSize();
    if (null == pageSize) pageSize = 10;
    pg.setPageSize(pageSize);
    mv.addObject("adminUser", super.getAdminUser());
    Page<ScoreDO> page = scoreService.listScore(scoreDO, pg);
    if (null == page) return mv;
    mv.addObject("totalPage", page.getTotalPage());
    mv.addObject("totalCount", page.getTotalCount());
    mv.addObject("pageNo", page.getPageNo());
    mv.addObject("pageSize", pageSize);
    mv.addObject("listScore", page.getResult());
    mv.addObject("listSize",page.getResult().size() );
    mv.addObject("scoreDO", scoreDO);
    return mv;
  }

  @RequestMapping("/export_score_list")
  public void exportScoreList(ScoreDO scoreDO, HttpServletResponse response) {
    Page<ScoreDO> page = scoreService.listScore(scoreDO, null);
    if (null != page && null != page.getResult() && !page.getResult().isEmpty()){
      String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
      scoreService.exportScoreList(page.getResult(), response,"score-list"+dateFileName);
    }
  }

  @RequestMapping("/export_transfer_score_list")
  public void exportTransferScoreList(ScoreDO scoreDO, HttpServletResponse response) {
    Page<ScoreDO> page = scoreService.listScoreJoinProduct(scoreDO, null);
    if (null != page && null != page.getResult() && !page.getResult().isEmpty()){
      String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
      scoreService.exportTransferScoreList(page.getResult(), response,"transfer-score-list"+dateFileName);
    }
  }
  
  
  @RequestMapping("/learn_record_list")
  public ModelAndView listLearnRecord(LearnRecordDTO learnRecordDTO) {
    ModelAndView mv = new ModelAndView("/score/learnRecordList");
    mv.addObject("adminUser", super.getAdminUser());
    mv.addObject("learnRecordDTO", learnRecordDTO);
    mv.addObject("scoreDO",
        scoreService.listScoreByCond(learnRecordDTO.getProductId(), learnRecordDTO.getStudentId()));
    List<LearnRecordDO> listLearnRecord = scoreService.listLearnRecord(learnRecordDTO);
    for (LearnRecordDO learnRecordDO : listLearnRecord) {
      learnRecordDO.setLearnTime(learnRecordDO.getLearnTime() % 60 != 0 ? learnRecordDO
          .getLearnTime() / 60 + 1 : learnRecordDO.getLearnTime() / 60);
    }
    mv.addObject("listLearnRecord", listLearnRecord);
    return mv;
  }

  @RequestMapping("/score_detail")
  public ModelAndView scoreDetail(Long scoreId) {
    ModelAndView mv = new ModelAndView("/score/scoreDetail");
    ScoreDO scoreDO = scoreService.getScoreById(scoreId);
    mv.addObject("scoreDO", scoreDO);
    if (StringUtils.isJsonNotBlank(scoreDO.getChapterNodeList())) {
      List<ScoreNode> chapterNodeList =
          FastJsonUtil.fromJsons(scoreDO.getChapterNodeList(),
              new TypeReference<List<ScoreNode>>() {});
      Collections.sort(chapterNodeList, new Comparator<ScoreNode>() {
        @Override
        public int compare(ScoreNode o1, ScoreNode o2) {
          Integer i = Integer.valueOf(o1.getOrder()) - Integer.valueOf(o2.getOrder());
          return i;
        }
      });
      mv.addObject("chapterNodeList", chapterNodeList);
    }
    if (StringUtils.isJsonNotBlank(scoreDO.getFinalExamNodeList())) {
      List<ScoreNode> finalExamNodeList =
          FastJsonUtil.fromJsons(scoreDO.getFinalExamNodeList(),
              new TypeReference<List<ScoreNode>>() {});
      Collections.sort(finalExamNodeList, new Comparator<ScoreNode>() {
        @Override
        public int compare(ScoreNode o1, ScoreNode o2) {
          Integer i = Integer.valueOf(o1.getOrder()) - Integer.valueOf(o2.getOrder());
          return i;
        }
      });
      mv.addObject("finalExamNodeList", finalExamNodeList);
    }
    return mv;
  }

  /*******************
   * 成绩转换
   * 
   * @throws ParseException
   ****************/
  @RequestMapping("/score_transfer")
  public ModelAndView scoreTransfer(ScoreDO scoreDO) throws ParseException {
    ModelAndView mv = new ModelAndView("score/scoreTransferList");
    AdminUser adminUser = super.getAdminUser();
    mv.addObject("adminUser", adminUser);

    StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
    Date productEndApplyTime = new SimpleDateFormat("yyyy-MM-dd").parse(staticInfo.getEndApplyTime());
    if (!DateUtil.isbeforeNow(productEndApplyTime)) {
      mv.setViewName("score/scoreTransferListHide");
      mv.addObject("endApplyTime", staticInfo.getEndApplyTime());
      return mv;
    }
    String visibly = StaticInfoProp.batchBtnVisibly().trim().toLowerCase();
    if (visibly.equals(Constants.SCORE_VISIBLY) && Constants.POILT_UNIT_ROLE.equals(adminUser.getRole())
        && Constants.POILT_UNIT_PARENT_ROLE.equals(adminUser.getChildRole())) {
      mv.addObject("isShow", true);
    }
    // select下拉框数据
    mv.addObject("listExamDate", productService.listExamDate());
    mv.addObject("listCategory", productService.listCategory());
    if (null != scoreDO && null != scoreDO.getCatId()) {
      mv.addObject("listProduct", productService.listProduct(scoreDO.getCatId()));
    }
    mv.addObject("listPilotUnit", unitService.listPilotUnit());
    mv.addObject("listClass", classService.listClass());

    Page<ScoreDO> pg = new Page<ScoreDO>();
    Integer pageNo = scoreDO.getPageNo();
    if (pageNo == null) {
      pageNo = 1;
      scoreDO.setPageNo(pageNo);
    }
    pg.setPageNo(pageNo);
    Integer pageSize = scoreDO.getPageSize();
    if (null == pageSize) pageSize = 10;
    pg.setPageSize(pageSize);
    // Page<ScoreDO> page = scoreService.listScore(scoreDO, pg);
    Page<ScoreDO> page = scoreService.listScoreJoinProduct(scoreDO, pg);
    if (page != null && page.getResult() != null && page.getResult().size() > 0) {
      for (ScoreDO vo : page.getResult()) {
        if (vo == null) {
          continue;
        }
        if (vo.getScore() == null || vo.getCoefficient() == null) {
          vo.setDiscountScore(0D);
        } else if (vo.getScore() != null && vo.getCoefficient() != null) {
          vo.setDiscountScore(Double.valueOf(Constants.DECIMAL_FORMAT.format(vo.getScore()
              * vo.getCoefficient())));
        }
        if (vo.getDailyScore() != null)
          vo.setReportFinalScore(vo.getDiscountScore() + vo.getDailyScore());
        else
          vo.setReportFinalScore(vo.getDiscountScore() + 0D);
      }
    }

    if (null == page) return mv;
    mv.addObject("totalPage", page.getTotalPage());
    mv.addObject("totalCount", page.getTotalCount());
    mv.addObject("pageNo", page.getPageNo());
    mv.addObject("pageSize", pageSize);
    mv.addObject("listScore", page.getResult());
    mv.addObject("scoreDO", scoreDO);
    return mv;
  }

  @RequestMapping("/download_excel")
  public void downloadExcel(HttpServletResponse response) {
  //根据报名状态正常的数据，构造score表数据
    //List<ImportScoreDTO> list = scoreService.listScoreByApply();
    List<ImportScoreDTO> list = scoreService.listImportScore();
    String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
    scoreService.downloadExcel("成绩导入示例", "template" + dateFileName, list, response);
  }

  @RequestMapping("/detection_excel_url")
  @ResponseBody()
  public String detectionExcelUrl(String url) {
    return scoreService.detectionExcelUrl(url);
  }

  @RequestMapping("/create_error_excel")
  public void createErrorExcel(String url, HttpServletResponse response) {
    List<ImportScoreDTO> errorExcelList = scoreService.createErrorExcel(url);
    String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
    scoreService.downloadExcel("检测", "detection" + dateFileName, errorExcelList, response);
  }

  @RequestMapping("/import_score")
  @ResponseBody()
  public String importScore(String url) {
    return scoreService.importScore(url);
  }
}
