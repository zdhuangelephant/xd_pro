package com.xiaodou.common.city.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.city.constant.Constant;
import com.xiaodou.common.city.dao.CityDao;
import com.xiaodou.common.city.model.City;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * 城市信息缓存类
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-13
 */
@Service("cityCache")
public class CityCache {

	@Resource
	CityDao cityDao;

	/**
	 * 缓存id-CityInfo/CityInfo列表
	 */
	public List<City> initCityCodeInfoList() {
		List<City> storeCityList = Lists.newArrayList();
		IQueryParam queryParam = new QueryParam();
		queryParam.addOutputs(CommUtil.getAllField(City.class));
		Page<City> cityPage = cityDao.findEntityListByCond(queryParam, null);
		if (null == cityPage || null == cityPage.getResult() || cityPage.getResult().isEmpty()) {
			return storeCityList;
		}
		List<City> cityList = cityPage.getResult();
		/*Map<String, Object> cond = Maps.newHashMap();
		List<City> cityList = cityDao.findEntityListByCond(cond);
		List<City> storeCityList = Lists.newArrayList();*/
		try {
			if (cityList.isEmpty() || cityList.size() == 0) {
				// TODO 报警
			} else {
				Map<Integer, List<City>> parentCityMap = Maps.newHashMap();
				for (City city : cityList) {
					if (city.getLocLevel() < 3) {
						List<City> subCity = parentCityMap.get(city.getId());
						if (null != subCity && subCity.size() > 0)
							city.setSubCity(subCity);
						else
							parentCityMap.put(city.getId(), city.getSubCity());
					}
					if (city.getParentId() == 0) {
						storeCityList.add(city);
					} else {
						List<City> subCity = parentCityMap.get(city.getParentId());
						if (null != subCity)
							subCity.add(city);
						else
							parentCityMap.put(city.getParentId(), new ArrayList<City>());
					}
					JedisUtil.addStringToJedis(Constant.CACHE_CITY + Constant.SEPRATOR + city.getId(), city.toString(),
							48 * 60 * 60);
				}
				JedisUtil.addStringToJedis(Constant.CACHE_CITY, FastJsonUtil.toJson(storeCityList), 48 * 60 * 60);
			}
		} catch (Exception e) {
			LoggerUtil.error("Err : initCityCodeInfoList failed.", e);
		}
		return storeCityList;
	}

	/**
	 * 缓存id-CityInfo
	 * 
	 * @param id
	 */
	public City initCityCodeInfo(Integer id) {
		City city = new City();
		city.setId(id);
		city = cityDao.findEntityById(city);
		try {
			if (city == null) {
				// TODO 报警
			} else {
				JedisUtil.addStringToJedis(Constant.CACHE_CITY + Constant.SEPRATOR + city.getId(), city.toString(),
						48 * 60 * 60);
			}
		} catch (Exception e) {
			LoggerUtil.error("Err : initCityCodeInfo failed.", e);
		}
		return city;
	}

	/**
	 * 根据CityCode获取City实例
	 * 
	 * @param cityCode
	 *            城市码
	 * @return City
	 */
	public City getCityInfoById(Integer id) {
		String sCity = JedisUtil.getStringFromJedis(Constant.CACHE_CITY + Constant.SEPRATOR + id);
		if (StringUtils.isJsonNotBlank(sCity)) {
			return FastJsonUtil.fromJson(sCity, City.class);
		}
		return initCityCodeInfo(id);
	}

	/**
	 * 获取CityInfo列表
	 * 
	 * @return List<City>
	 */
	public List<City> getCityList() {
		String sCityLst = JedisUtil.getStringFromJedis(Constant.CACHE_CITY);
		if (StringUtils.isJsonNotBlank(sCityLst)) {
			return FastJsonUtil.fromJsons(sCityLst, new TypeReference<List<City>>() {
			});
		}
		return initCityCodeInfoList();
	}

}
