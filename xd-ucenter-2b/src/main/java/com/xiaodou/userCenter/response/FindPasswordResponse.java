package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

/**
 * 
 * 找回密码response
 *
 * @author		weirong.li 
 * @version		1.0  
 * @since		JDK1.7
 */
public class FindPasswordResponse extends BaseResultInfo{
  public FindPasswordResponse(ResultType type) {
    super(type);
  }
  public FindPasswordResponse(UcenterResType type){
	  super(type);
  }

}
