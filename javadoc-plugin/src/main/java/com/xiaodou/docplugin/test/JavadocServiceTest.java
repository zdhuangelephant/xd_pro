package com.xiaodou.docplugin.test;

import com.xiaodou.docplugin.service.IJavadocService;
import com.xiaodou.docplugin.service.impl.JavadocService;

public class JavadocServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String basePath = "D:/code/Java/java-standard-project/crm-psg/Trunk";
		String projectName = "crm-psg";
		IJavadocService javadocService = new JavadocService();
		javadocService.execute(basePath, projectName);
	}

}
