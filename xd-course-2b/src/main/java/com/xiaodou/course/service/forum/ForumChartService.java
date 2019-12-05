package com.xiaodou.course.service.forum;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.course.constant.SlideConstant;
import com.xiaodou.course.model.product.ModuleSlideModel;
import com.xiaodou.course.service.product.ModuleSlideService;
import com.xiaodou.course.vo.product.ModuleSlide;

/**
 * 话题推荐service
 * 
 * @author bing.cheng
 * 
 */
@Service("forumRecommendService")
public class ForumChartService {

  @Resource
  ModuleSlideService moduleSlideService;

  public List<ModuleSlide>  getChart() {
    // 幻灯片列表
    List<ModuleSlideModel> sildes =
        moduleSlideService.moduleSlideList(SlideConstant.FIND_SILDE);
    List<ModuleSlide> slideList = new ArrayList<>();
    for (ModuleSlideModel msm : sildes) {
      ModuleSlide ms = new ModuleSlide();
      ms.setDescription(msm.getDescription());
      ms.setImageUrl(msm.getImageUrl());
      ms.setLinkUrl(msm.getLinkUrl());
      ms.setModuleId(msm.getModuleId());
      slideList.add(ms);
    }
    return slideList;
  }

}
