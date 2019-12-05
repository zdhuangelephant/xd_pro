package com.xiaodou.docplugin.test;

import com.xiaodou.docplugin.service.IJavadocService;
import com.xiaodou.docplugin.service.impl.JavadocService;
import com.xiaodou.docplugin.service.impl.dc.DcJavadocService;
import com.xiaodou.docplugin.util.DcClassLoader;

public class DcJavadocServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String basePath = "D:/work/src/dc/trunk/core";
		String projectName = "dc-core";
		IJavadocService javadocService = new DcJavadocService();
		javadocService.execute(basePath, projectName);
	}

}
