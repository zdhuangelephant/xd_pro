package com.xiaodou.resources.request.forum;

import java.util.List;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name AnyCountRequest CopyRright (c) 2016 by zhouhuan
 * 
 * @author zhouhuan
 * @date 2016年9月1日
 * @description 通用数量请求接口
 * @version 1.0
 */
public class AnyCountRequest extends BaseRequest {

    @NotEmpty
	private String ids;
	
	public String getIds() {
		return ids;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}
}
