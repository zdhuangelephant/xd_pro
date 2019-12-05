package com.xiaodou.control.util.compartor;

import java.util.Comparator;

import com.xiaodou.control.model.server.base.BaseServerModel;

public class ServerCompartor implements Comparator<BaseServerModel> {

	@Override
	public int compare(BaseServerModel arg0, BaseServerModel arg1) {
		// TODO Auto-generated method stub
	    String o1=arg0.getServerName();
	    String o2=arg1.getServerName();
		return o2.compareTo(o1);
	}
}