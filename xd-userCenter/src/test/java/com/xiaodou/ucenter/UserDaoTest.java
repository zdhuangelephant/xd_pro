package com.xiaodou.ucenter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.userCenter.dao.UserModelVoDao;
import com.xiaodou.userCenter.model.property.UserModelVoProperty;
import com.xiaodou.userCenter.model.vo.UserModelVo;


/**
 * 
 * userDao 测试类
 *
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class UserDaoTest extends BaseUnitils{

  @SpringBean("userModelDao")
  UserModelVoDao userModelDao;
  
//  /**
//   * 
//   * @Title: addEntity
//   * @Description: 添加文档
//   */
//  @Test
//  public void addEntity() {
//    try {
//      UserModel entity = new UserModel();
////    entity.setId("1");
//      entity.setNickName("小逗");
////    entity.setCreateTime(new Timestamp(0));
//      entity.setPassword("3747474744433");
//      entity.setPortrait("www.xiaodou.com/images/2015");
//      entity.setSalt("23456");
//      entity.setToken("32sdfds3456DFG");
//      entity.setUsedDeviceId("DF3459988wert");
////    entity.setTokenTime(new Timestamp(0));
//      entity.setUserName("18766654354");
////      userModelDao.addEntity(entity);
//    } catch (Exception e) {
//      throw e;
//    }
//  }
//
//  /**
//   * 
//   * @Title: updateEntity
//   * @Description: 修改文档
//   */
//  @Test
//  public void updateEntity() {
//    UserModel entity = new UserModel();
////    entity.setNickName("小逗");
////    entity.setCreateTime(new Timestamp(0));
//    entity.setPassword("3747474744433");
//    entity.setPortrait("www.xiaodou.com/images/2015");
//    entity.setSalt("23456");
//    entity.setToken("sdfds3456DFG");
//    entity.setUsedDeviceId("DF3459988wert");
////    entity.setTokenTime(new Timestamp(0));
//    entity.setUserName("18766654345");
//
//    Map<String, String> condition = new HashMap<String, String>();
//    condition.put("id", "51");
////    Integer isUpt = userModelDao.updateEntity(condition, entity);
////    Assert.assertNotNull(isUpt);
//  }

  /**
   * 
   * @Title: queryList
   * @Description: 查询文档
   * @param 设定文件
   * @return void 返回类型
   * @throws
   */
  @Test
  public void queryList() {
    UserModelVo model = new UserModelVo();
    model.setId(1l);
    //model.setNickName("zhangsan");
    Map<String, Object> inputArgument = this.getQueryCond(null, null, model);
    List<UserModelVo> list = userModelDao.queryList(inputArgument, UserModelVoProperty.getAllInfo());
    System.out.println(FastJsonUtil.toJson(list));
    Assert.assertNotNull(list);
  }
  
  public Map<String, Object> getQueryCond(List<Long> queryIdList, List<String> listSequence,
    Object queryCond) {
  Map<String, Object> input = new HashMap<String, Object>();
  if (queryIdList != null && queryIdList.size() > 0) {
    input.put("listId", queryIdList);
  }

  if (listSequence != null && listSequence.size() > 0) {
    input.put("listSequence", listSequence);
  }

  if (queryCond != null) {
    try {
      Field[] queryParams = queryCond.getClass().getDeclaredFields();
      for (Field param : queryParams) {
        param.setAccessible(true);
        input.put(param.getName(), param.get(queryCond));
      }
    } catch (Exception e) {
    }
  }

  return input;
}
}
