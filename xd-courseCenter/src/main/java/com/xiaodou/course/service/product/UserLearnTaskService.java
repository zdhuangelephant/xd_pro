package com.xiaodou.course.service.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.course.model.user.UserLearnTaskModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/8/23.
 */
@Service("userLearnTaskService")
public class UserLearnTaskService {
  
  @Resource
  ProductServiceFacade productServiceFacade;

  /**
   * 学习任务
   * @param learnTask
   * @return
   */
  public UserLearnTaskModel addCompleteTask(UserLearnTaskModel learnTask){
    return productServiceFacade.insertUserLearnTask(learnTask);
  }

  /**
   * 查找完成任务
   * @param productId
   * @param itemId
   * @param userId
   * @return
   */
  public List<UserLearnTaskModel> findCompleteTask(Integer productId,Integer itemId,Integer userId){
    Map<String,Object> cond = new HashMap<>();
    cond.put("userId",userId);
    cond.put("itemId",itemId);
    cond.put("productId",productId);
    Map<String,Object> output = new HashMap<>();
    CommUtil.getBasicField(output, UserLearnTaskModel.class);
    Page<UserLearnTaskModel> userLearnTaskModelPage =
        productServiceFacade.queryUserLearnTaskListByCondWithOutPage(cond, output);
    return userLearnTaskModelPage.getResult();
  }

}
