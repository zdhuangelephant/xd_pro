package com.xiaodou.st.dashboard.web.controller.finalExam;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.xiaodou.st.dashboard.domain.finalExam.DashboardFinalExamDO;
import com.xiaodou.st.dashboard.domain.finalExam.RawDataFinalExamDO;
import com.xiaodou.st.dashboard.service.finalExam.FinalExamService;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("finalExamController")
@RequestMapping("/final_exam")
public class FinalExamController extends BaseController {

    @Resource
    FinalExamService finalExamService;
    
    @RequestMapping()
    public ModelAndView dashboard(Long studentId,Long productId) throws ParseException {
      ModelAndView mv = new ModelAndView("/dashboard");
      List<RawDataFinalExamDO> listChapterFinalExam = finalExamService.listRdFinalExam(studentId,productId,0);
      List<RawDataFinalExamDO> listTestFinalExam = finalExamService.listRdFinalExam(studentId,productId,0);
      List<DashboardFinalExamDO> listDbFinalExamDO = finalExamService.listDbFinalExam(studentId, productId);
      mv.addObject("chapterFinalExam", listChapterFinalExam.get(0));
      mv.addObject("testFinalExam", listTestFinalExam.get(0));
      mv.addObject("listChapterFinalExam", listChapterFinalExam.remove(0));
      mv.addObject("listTestFinalExam", listTestFinalExam.remove(0));
      Map<Short,DashboardFinalExamDO> map = Maps.newHashMap();
      for(DashboardFinalExamDO rd : listDbFinalExamDO){
          map.put(rd.getType(), rd);
        }
      mv.addObject("map", map);
      return mv;
    }
}
