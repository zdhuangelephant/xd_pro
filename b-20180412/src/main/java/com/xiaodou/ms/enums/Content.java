package com.xiaodou.ms.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Content {
	FIRST(0, "这是一封来自知识盛宴的邀请函，不同的知识加餐静待您的尊享"), SECOND(1, "庄子有鲲御空而行，这里，知识就是你的隐形翅膀"), third(2, "我们给你需要的，而你选择想要的"), fourth(
			3, "几根柳藤，一池春水，用简单的方式给你倾心的内容"), fifth(4, "知识在，你在，便是不负好时光"), sixth(5, "只要坚持学习，一切都在意料之中！"), seventh(6,
					"多样的知识帮你玩转各种试题套路"), eighth(7,
							"知识破万卷，下笔才更准"), ninth(8, "既然选择了学习，便只顾风雨兼程"), tenth(9, "知识的价值不在于占有，而在于使用");

	private Integer index;
	private String content;

	private Content(Integer index, String content) {
		this.index = index;
		this.content = content;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getIndex() {
		return index;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	private final static Map<Integer, Content> _allContent = Maps.newHashMap();

	private static void init() {
		for (Content degree : Content.values()) {
			if (null == degree)
				continue;
			_allContent.put(degree.getIndex(), degree);
		}
	}

	static {
		init();
	}

	public static Content getByCode(Integer code) {
		if (null != code && _allContent.containsKey(code))
			return _allContent.get(code);
		return FIRST;
	}
}