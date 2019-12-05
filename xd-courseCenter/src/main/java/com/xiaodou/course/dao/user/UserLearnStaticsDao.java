package com.xiaodou.course.dao.user;

import org.springframework.stereotype.Repository;
import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.user.UserLearnStaticsModel;

/**
 * Created by zyp on 15/8/23.
 */
@Repository("userLearnStaticsDao")
public class UserLearnStaticsDao extends BaseProcessDao<UserLearnStaticsModel> {

  /**
   * 查找
   * @param productId
   * @param userId
   * @return
   */
  public UserLearnStaticsModel findEntityByUserAndProduct(Integer productId,Integer userId){
    UserLearnStaticsModel cond = new UserLearnStaticsModel();
    cond.setUserId(userId);
    cond.setProductId(productId);
    return (UserLearnStaticsModel)this.getSqlSession().selectOne(this.getEntityClass().getSimpleName() + ".findEntityByProductAndUser", cond);
  }

}
