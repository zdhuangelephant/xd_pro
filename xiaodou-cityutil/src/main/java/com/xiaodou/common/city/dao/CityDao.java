package com.xiaodou.common.city.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.city.model.City;
import com.xiaodou.summer.dao.jdbc.BaseDao;


/**
 * 城市信息Dao
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-13
 */
@Repository("cityDao")
public class CityDao extends BaseDao<City> {

//	  public List<City> findEntityListByCond(Map<String, Object> cond) {
//	    Page<City> result = findEntityListByCond(cond, null);
//	    if (result != null && result.getResult() != null && result.getResult().size() > 0)
//	      return result.getResult();
//	    return null;
//	  }
	
}
