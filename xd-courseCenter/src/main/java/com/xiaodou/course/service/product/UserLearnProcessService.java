package com.xiaodou.course.service.product;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.course.common.enums.ResourceType;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.user.UserLearnProcessModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.web.request.product.LearnProcessRequest;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zyp on 15/8/9.
 */
@Service("userLearnProcessService")
public class UserLearnProcessService {

  @Resource
  ProductServiceFacade productServiceFacade;
  
  @Resource
  UserLearnTaskService userLearnTaskService;

  @Resource
  UserLearnStaticsService userLearnStaticsService;

  @Resource
  ProductItemService productItemService;

  /**
   * 学习进度记录
   * 
   * @param learnProcess
   * @return
   */
  public UserLearnProcessModel recordLearnProcess(UserLearnProcessModel learnProcess) {
    return productServiceFacade.insertUserLearnProcess(learnProcess);
  }

  /**
   * 学习进度记录
   * 
   * @param processRequest
   * @return
   */
  public BaseResponse recordLearnProcess(LearnProcessRequest processRequest) {
    try {
      ProductItemModel item = productItemService.findItemById(processRequest.getItemId());

      if (item==null){
        BaseResponse result = new BaseResponse(ResultType.SYSFAIL);
        result.setRetdesc("本课件不存在");
        return result;
      }

      Integer userId = Integer.parseInt(processRequest.getUid());
      Integer moduleId = Integer.parseInt(processRequest.getModule());

      if (item.getResourceType().equals(ResourceType.CHAPTER.getTypeId())) {
        if (item.getRelationItem() != null && item.getRelationItem() != 0) {
          ProductItemModel relationItem = productItemService.findItemById(item.getRelationItem());
          item.setTaskRatio(relationItem.getTaskRatio());
        }
      }

      // 1.记录学习记录
      UserLearnProcessModel learnProcess = new UserLearnProcessModel();
      learnProcess.setModuleId(moduleId);
      learnProcess.setRecordTime(new Timestamp(System.currentTimeMillis()));
      learnProcess.setItemId(item.getId());
      learnProcess.setProductId(item.getProductId());
      learnProcess.setChapterId(item.getParentId());
      learnProcess.setUserId(userId);
      this.recordLearnProcess(learnProcess);
      // 2.任务完成情况判断
      // 暂时无
      // 3.学习进度统计
      userLearnStaticsService.updateLearnStatics(userId, moduleId, item);
      return new BaseResponse(ResultType.SUCCESS);
    } catch (Exception e) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
  }

}
