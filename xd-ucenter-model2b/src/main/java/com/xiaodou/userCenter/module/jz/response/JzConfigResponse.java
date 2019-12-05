package com.xiaodou.userCenter.module.jz.response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.response.ConfigResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

/**
 * @name @see com.xiaodou.userCenter.moudle.jz.response.JzConfigResponse.java
 * @CopyRright (c) 2015 by <a
 *             href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月21日
 * @description 教师资格获取配置文件接口返回值
 * @version 1.0
 * @waste
 */
public class JzConfigResponse extends
		ConfigResponse<JzConfigResponse.ConfigData> {

	public JzConfigResponse(ResultType type) {
		super(type);
	}

	public JzConfigResponse(UcenterResType type) {
		super(type);
	}

	public static class ConfigData {
		/** city 城市列表 */
		private List<City> city = Lists.newArrayList();
		/** examDate 考期列表 */
		private List<ExamDate> examDate = Lists.newArrayList();
		/** advertisement 广告 */
		private List<Advertisement> advertisement = Lists.newArrayList();
		/** thirdlogin 三方登录 */
		private List<String> thirdlogin = Lists.newArrayList();
		/** shareplateform 分享平台 */
		private List<String> shareplateform = Lists.newArrayList();
		/** blankNotice 系统公告和活动 */
		private BlankNotice blankNotice;

		public BlankNotice getBlankNotice() {
			return blankNotice;
		}

		public void setBlankNotice(BlankNoticeResponse blankNoticeResponse) {
			BlankNotice blankNotice = new BlankNotice();
			this.blankNotice = blankNotice.setBlankNotice(blankNoticeResponse);
		}

		public List<String> getThirdlogin() {
			return thirdlogin;
		}

		public void setThirdlogin(List<String> thirdlogin) {
			this.thirdlogin = thirdlogin;
		}

		public List<String> getShareplateform() {
			return shareplateform;
		}

		public void setShareplateform(List<String> shareplateform) {
			this.shareplateform = shareplateform;
		}

		public List<City> getCity() {
			return city;
		}

		public void setCity(List<City> city) {
			this.city = city;
		}

		public List<ExamDate> getExamDate() {
			return examDate;
		}

		public void setExamDate(List<ExamDate> examDate) {
			this.examDate = examDate;
		}

		public List<Advertisement> getAdvertisement() {
			return advertisement;
		}

		public void setAdvertisement(List<Advertisement> advertisement) {
			this.advertisement = advertisement;
		}

	}

	/**
	 * @name @see com.xiaodou.userCenter.moudle.jz.response.ExamDate.java
	 * @CopyRright (c) 2015 by <a
	 *             href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * 
	 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * @date 2015年7月21日
	 * @description 考期
	 * @version 1.0
	 * @tags 如果你想尝试优化这段代码,请你将浪费的时间记录在下面,让以后尝试优化的人作为一个参考.
	 * @waste
	 */
	public static class ExamDate {
		/** name 考期 */
		private String name;
		/** order 排序 */
		private String order;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOrder() {
			return order;
		}

		public void setOrder(String order) {
			this.order = order;
		}
	}

	/**
	 * @name TypeCode
	 * @CopyRright (c) 2015 by <a
	 *             href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * 
	 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * @date 2015年7月21日
	 * @description 报考类型码
	 * @version 1.0
	 * @tags 如果你想尝试优化这段代码,请你将浪费的时间记录在下面,让以后尝试优化的人作为一个参考.
	 * @waste
	 */
	public static class TypeCode {
		/** code 报考类型码值 */
		private String code;
		/** name 报考类型名称 */
		private String name;
		/** order 排列顺序 */
		private String order;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOrder() {
			return order;
		}

		public void setOrder(String order) {
			this.order = order;
		}

	}

	/**
	 * @name City
	 * @CopyRright (c) 2015 by <a
	 *             href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * 
	 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * @date 2015年7月21日
	 * @description 城市码
	 * @version 1.0
	 * @tags 如果你想尝试优化这段代码,请你将浪费的时间记录在下面,让以后尝试优化的人作为一个参考.
	 * @waste
	 */
	public static class City {
		/** id 唯一编码 */
		private String id;
		/** name 城市名称 */
		private String name;
		/** subCity 子城市 */
		private List<City> subCity = Lists.newArrayList();

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<City> getSubCity() {
			return subCity;
		}

		public void setSubCity(List<City> subCity) {
			this.subCity = subCity;
		}
	}

	/**
	 * @name @see com.xiaodou.userCenter.module.jz.response.Advertisement.java
	 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
	 * 
	 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * @date 2015年8月31日
	 * @description 广告实体类
	 * @version 1.0
	 */
	public static class Advertisement {

		private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		private static String urlWithTs = "%s?%s";

		public Advertisement(String imageUrl, String dubbingUrl) {
			this.imageUrl = imageUrl;
			this.dubbingUrl = dubbingUrl;
		}

		/** iamgeUrl 图片地址 */
		private String imageUrl;
		/** dubbingUrl 音频地址 */
		private String dubbingUrl;

		public String getImageUrl() {
			return getTimestamp(imageUrl);
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getDubbingUrl() {
			return getTimestamp(dubbingUrl);
		}

		public void setDubbingUrl(String dubbingUrl) {
			this.dubbingUrl = dubbingUrl;
		}

		private String getTimestamp(String url) {
			return String.format(urlWithTs, url, sdf.format(new Date()));
		}
	}
	/**
	 * @name @see com.xiaodou.userCenter.module.jz.response.Advertisement.java
	 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
	 * 
	 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
	 * @description 首页弹出公告实体类
	 * @version 1.0
	 */
	public static class BlankNotice {
		// 展示方式：0 每次都展示，1 只展示一次，2 不展示
		private String type;
		// 展示地址 http://
		private String showUrl;
		// 跳转地址 http:// app://
		private String jumpUrl;

		public BlankNotice() {
			super();
		}
		
		public BlankNotice(String type, String showUrl, String jumpUrl) {
			super();
			this.type = type;
			this.showUrl = showUrl;
			this.jumpUrl = jumpUrl;
		}

		public BlankNotice setBlankNotice(BlankNoticeResponse blankNoticeResponse) {
			return new BlankNotice(blankNoticeResponse.getType().toString(),
					blankNoticeResponse.getShowUrl(),
					blankNoticeResponse.getJumpUrl());
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getShowUrl() {
			return showUrl;
		}

		public void setShowUrl(String showUrl) {
			this.showUrl = showUrl;
		}

		public String getJumpUrl() {
			return jumpUrl;
		}

		public void setJumpUrl(String jumpUrl) {
			this.jumpUrl = jumpUrl;
		}
	}
}
