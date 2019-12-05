package com.xiaodou.control.util.compartor;

import java.util.Comparator;

import com.xiaodou.control.vo.FtpFileVo;

public class FileCompartor implements Comparator<FtpFileVo> {

	@Override
	public int compare(FtpFileVo arg0, FtpFileVo arg1) {
		// TODO Auto-generated method stub
	    String n1[]=arg0.getName().split("_");
	    String n2[]=arg1.getName().split("_");
		return Integer.parseInt(n2[0])
				- Integer.parseInt(n1[0]);
	}
}