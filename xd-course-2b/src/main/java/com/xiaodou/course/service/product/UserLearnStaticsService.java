package com.xiaodou.course.service.product;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.user.UserLearnStaticsModel;
import com.xiaodou.course.model.user.UserLearnTaskModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;

/**
 * Created by zyp on 15/8/23.
 */
@Service("userLearnStaticsService")
public class UserLearnStaticsService {

  @Resource
  ProductServiceFacade productServiceFacade;
  
  @Resource
  ProductItemService productItemService;

  @Resource
  UserLearnTaskService userLearnTaskService;

  /**
   * 学习统计
   * 
   * @param learnStatics
   * @return
   */
  public UserLearnStaticsModel addEntity(UserLearnStaticsModel learnStatics) {
    return productServiceFacade.insertUserLearnStatics(learnStatics);
  }

  /**
   * 编辑
   * 
   * @param learnStatics
   * @return
   */
  public Boolean editEntity(UserLearnStaticsModel learnStatics) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", learnStatics.getId());
    productServiceFacade.updateUserLearnStatics(cond, learnStatics);
    return true;
  }

  /**
   * 查找
   * 
   * @param productId
   * @param userId
   * @return
   */
  public UserLearnStaticsModel findStatics(Long productId, Long userId) {
    UserLearnStaticsModel learnStatics =
        productServiceFacade.queryUserLearnStaticsByUserAndProduct(productId, userId);
    return learnStatics;
  }

  /**
   * 更新学习进度
   * 
   * @param userId
   * @param item
   * @return
   */
  public Boolean updateLearnStatics(Long userId, Integer moduleId, ProductItemModel item) {

    Integer needAddRatio = 0;
    Boolean needAddStatics = false;

    // 任务完成记录
    UserLearnTaskModel learnTask = new UserLearnTaskModel();
    learnTask.setItemId(item.getId());
    learnTask.setBeginTime(new Timestamp(System.currentTimeMillis()));
    learnTask.setCompleteTime(new Timestamp(System.currentTimeMillis()));
    learnTask.setModuleId(moduleId);
    learnTask.setProductId(item.getProductId());
    learnTask.setTaskRatio(item.getTaskRatio());
    learnTask.setUserId(userId);
    List<UserLearnTaskModel> completeTask =
        userLearnTaskService.findCompleteTask(learnTask.getProductId(), learnTask.getItemId(),
            learnTask.getUserId());
    if (completeTask == null || completeTask.size() <= 0) {
      userLearnTaskService.addCompleteTask(learnTask);
      needAddRatio = learnTask.getTaskRatio();
    }

    UserLearnStaticsModel learnStatics =
        productServiceFacade.queryUserLearnStaticsByUserAndProduct(item.getProductId(), userId);
    if (learnStatics == null) {
      needAddStatics = true;
      learnStatics = new UserLearnStaticsModel();
      learnStatics.setProductId(item.getProductId());
      learnStatics.setUserId(userId);
      learnStatics.setRatio(0);
    }
    learnStatics.setRatio(learnStatics.getRatio() + needAddRatio);
    learnStatics.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    learnStatics.setCurrentItem(item.getId().intValue());
    learnStatics.setCurrentItemName(item.getChapterId());
    if (item.getParentId() != 0) {
      ProductItemModel parentChapter = productItemService.findItemById(item.getParentId());
      learnStatics.setChapterId(item.getParentId());
      learnStatics.setChapterName(parentChapter.getChapterId());
    } else {
      learnStatics.setChapterId(item.getId());
      learnStatics.setChapterName(item.getChapterId());
    }

    if (needAddStatics) {
      this.addEntity(learnStatics);
    } else {
      this.editEntity(learnStatics);
    }
    return true;
  }

}
