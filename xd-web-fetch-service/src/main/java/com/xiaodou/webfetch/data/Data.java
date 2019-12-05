package com.xiaodou.webfetch.data;

import org.jsoup.nodes.Element;
import org.springframework.util.Assert;

import com.xiaodou.webfetch.unique.IUnique;
import com.xiaodou.webfetch.unique.Target;

/**
 * @name @see com.xiaodou.webfetch.data.Data.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月13日
 * @description 可达数据
 * @version 1.0
 */
public class Data implements IUnique {

	// public Data(Target target, Elements elements) {
	// Assert.notNull(target);
	// Assert.notNull(elements);
	// this.target = target;
	// this.data = elements;
	// }

	public Data(Target target, Element node) {
		Assert.notNull(target);
		Assert.notNull(node);
		this.target = target;
		this.data = node;
	}

	private Element data;

	public boolean hasData() {
		return null != data;
	}

	public Element getData() {
		return data;
	}

	private Target target;

	@Override
	public String unique() {
		return target.unique();
	}

}
