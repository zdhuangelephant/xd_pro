package com.xiaodou.docplugin.test;

import com.xiaodou.docplugin.util.FileHelper;

public class FileHelpTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String basePath = 
				"D:/code/Java/java-standard-project/elongorder/giftcardms/Trunk/src/main/java/com/elong/giftmis/web";
		
		FileHelper.getFiles(basePath,"Controller.java");
	}

}
