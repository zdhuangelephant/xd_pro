package com.xiaodou.st.dashboard.web.controller.session;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.product.RawDataProductCategoryDO;
import com.xiaodou.st.dashboard.domain.session.CategorySessionPercentVO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionLearnPercentDTO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionPercentVO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionProductAvgScoreDTO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.product.ProductService;
import com.xiaodou.st.dashboard.service.session.SessionService;
import com.xiaodou.st.dashboard.service.unit.UnitService;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("sessionController")
@RequestMapping("/session")
public class SessionController extends BaseController {

  @Resource
  SessionService sessionService;
  @Resource
  ProductService productService;
  @Resource
  UnitService unitService;

  /**
   * 专业列表
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @return
   */
  @RequestMapping("/category_list")
  public ModelAndView categoryList() {
    ModelAndView mv = new ModelAndView("/session/categoryList");
    AdminUser adminUser = super.getAdminUser();
    mv.addObject("adminUser", adminUser);
    try {
      List<CategorySessionPercentVO> listCategorySessionPercentVO = sessionService.listCategorySessionPercentVO();
      mv.addObject("listCategorySessionPercentVO", listCategorySessionPercentVO);
    } catch (Exception e) {
      LoggerUtil.error("专业列表出错！", e);
    }
    return mv;
  }
  
  /**
   * 专业下面的第三级单位列表
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @return
   */
  @RequestMapping("/category_unit_list")
  public ModelAndView categoryUnitList(Integer catId) {
    ModelAndView mv = new ModelAndView("/session/categoryUnitList");
    if(null == catId) return mv;
    AdminUser adminUser = super.getAdminUser();
    mv.addObject("adminUser", adminUser);
    RawDataProductCategoryDO rdo = productService.getRawDataProductCategory(catId);
    if(null == rdo) return mv;
    mv.addObject("catName", rdo.getName());
    List<CategoryUnitSessionPercentVO> listCategoryUnitSessionPercentVO = sessionService.listCategoryUnitSessionPercentVO(catId);
    mv.addObject("listCategoryUnitSessionPercentVO",listCategoryUnitSessionPercentVO);
    return mv;
  }

  /**
   * 专业下面的第三级单位下的趋势分析
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @return
   * @throws ParseException
   */
  @RequestMapping("/category_unit_tendency_list")
  public ModelAndView categoryUnitLearnList(Integer catId, Long pilotUnitId) throws ParseException {
    ModelAndView mv = new ModelAndView("/session/categoryUnitTendencyList");
    if(null == catId || null == pilotUnitId) return mv;
    AdminUser adminUser = super.getAdminUser();
    RawDataProductCategoryDO rdo = productService.getRawDataProductCategory(catId);
    if(null == rdo) return mv;
    mv.addObject("catName", rdo.getName());
    UnitDO unitDO = unitService.getUnit(pilotUnitId);
    if(null == unitDO) return mv;
    mv.addObject("pilotUnitName", unitDO.getUnitName());
    mv.addObject("catId", catId);
    mv.addObject("pilotUnitId", pilotUnitId);
    mv.addObject("adminUser", adminUser);
    if (Constants.POILT_UNIT_ROLE.equals(adminUser.getRole())) {
    	List<CategoryUnitSessionPercentVO> listCategoryUnitSessionPercentVO = sessionService.listCategoryUnitSessionPercentVO(catId);
      mv.addObject("listCategoryUnitSessionPercentVO",
    		  listCategoryUnitSessionPercentVO);
    }
    List<CategoryUnitSessionLearnPercentDTO> list =
        sessionService.getCategoryUnitSessionLearnPercentDTO(Constants.DEFAULT_DAYS, catId, pilotUnitId);
    if (null != list && list.size() > 0) {
      mv.addObject("learnJsonData", FastJsonUtil.toJson(list));
    }
    List<CategoryUnitSessionProductAvgScoreDTO> list1 =
        sessionService.getCategoryUnitSessionProductAvgScoreDTO(catId, pilotUnitId);
    if (null != list1 && list1.size() > 0) {
      mv.addObject("avgJsonData", FastJsonUtil.toJson(list1));
    }
    return mv;
  }

  @RequestMapping("echarts_line")
  @ResponseBody
  public String echartsLine(Integer days, Integer catId, Long pilotUnitId) throws ParseException {
    List<CategoryUnitSessionLearnPercentDTO> list =
        sessionService.getCategoryUnitSessionLearnPercentDTO(days, catId, pilotUnitId);
    return FastJsonUtil.toJson(list);
  }
}
