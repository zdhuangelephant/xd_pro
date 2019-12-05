package com.xiaodou.resources.service.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.resources.model.forum.ModuleSlideModel;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;

/**
 * Created by zhouhuan on 16/9/2.
 */
@Service("moduleSlideService")
public class ModuleSlideService {

  @Resource
  ForumServiceFacade forumServiceFacade;

  public List<ModuleSlideModel> moduleSlideList(String moduleId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("moduleId", moduleId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("description", "");
    output.put("imageUrl", "");
    output.put("linkUrl", "");
    output.put("listOrder", "");
    output.put("createTime", "");
    output.put("moduleId", "");
    List<ModuleSlideModel> list =
    		forumServiceFacade.queryModuleSlideListByCondWithOutPage(cond, output);
    return list;
  }

}
