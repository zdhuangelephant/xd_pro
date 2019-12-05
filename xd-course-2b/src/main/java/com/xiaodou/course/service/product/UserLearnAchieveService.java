package com.xiaodou.course.service.product;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.course.model.user.UserLearnAchieveModel;
import com.xiaodou.course.service.AbstractService;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.web.request.user.UserLearnAchieveRequest;

@Service("userLearnAchieveService")
public class UserLearnAchieveService extends AbstractService {
  @Resource
  ProductServiceFacade productServiceFacade;

  @Resource
  UserLearnTaskService userLearnTaskService;

  @Resource
  UserLearnStaticsService userLearnStaticsService;

  @Resource
  ProductItemService productItemService;

  public void saveLearnAchieve(UserLearnAchieveRequest pojo) {
    try {
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("userId", pojo.getUid());
      cond.put("moduleId", pojo.getModule());
      cond.put("productId", pojo.getCourseId());
      List<UserLearnAchieveModel> learnAchieveList =
          productServiceFacade.queryUserLearnAchieve(cond,
              CommUtil.getAllField(UserLearnAchieveModel.class));
      UserLearnAchieveModel model = new UserLearnAchieveModel();
      model.setUserId(Long.valueOf(pojo.getUid()));
      model.setModuleId(Integer.valueOf(pojo.getModule()));
      model.setProductId(Long.valueOf(pojo.getCourseId()));
      model.setChapterId(Long.valueOf(pojo.getChapterId()));
      model.setItemId(Long.valueOf(pojo.getItemId()));
      if (null != learnAchieveList && learnAchieveList.size() == 1) {
        // update
        productServiceFacade.updateUserLearnAchieve(cond, model);
      } else if (null != learnAchieveList && learnAchieveList.size() > 1) {
        // delete->add
        if (productServiceFacade.deleteUserLearnAchieve(cond)) {
          model.setCreateTime(new Timestamp(System.currentTimeMillis()));
          productServiceFacade.insertUserLearnAchieve(model);
        }
      } else {
        // add
        model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        productServiceFacade.insertUserLearnAchieve(model);
      }
    } catch (Exception e) {
      LoggerUtil.error("记录用户最近学习课程至第几章第几节时出错。", e);
    }
  }
}
