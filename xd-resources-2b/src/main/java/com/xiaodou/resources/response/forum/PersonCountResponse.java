package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name ColumnistListResponse 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhouhuan
 * @date 2016年9月1日
 * @description 
 * @version 1.0
 */
public class PersonCountResponse extends BaseResponse {
    public PersonCountResponse(){}
    public PersonCountResponse(ResultType type) {
      super(type);
    }

    public PersonCountResponse(ForumResType type) {
      super(type);
    }
 
    public Integer getPariseCount() {
	return pariseCount;
	}
	
	public void setPariseCount(Integer pariseCount) {
		this.pariseCount = pariseCount;
	}
	
	public Integer getColumnistCount() {
		return columnistCount;
	}
	
	public void setColumnistCount(Integer columnistCount) {
		this.columnistCount = columnistCount;
	}
	
	public Integer getResourcesCount() {
		return resourcesCount;
	}
	
	public void setResourcesCount(Integer resourcesCount) {
		this.resourcesCount = resourcesCount;
	}

  private Integer pariseCount=0;
  private Integer columnistCount=0;
  private Integer resourcesCount=0;
}
