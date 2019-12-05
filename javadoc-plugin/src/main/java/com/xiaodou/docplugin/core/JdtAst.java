package com.xiaodou.docplugin.core;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.charset.Charset;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Java源文件解析器
 * @author bin.song
 *
 */
public class JdtAst {

	/**
	 * 
	 */
	private ASTParser astParser = ASTParser.newParser(AST.JLS3);
	
	/**
	 * 获取java文件
	 * @param javaFilePath
	 * java源文件地址
	 * @return
	 * 编译单元
	 */
	public CompilationUnit getCompilationUnit(String javaFilePath){
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					new FileInputStream(javaFilePath));
			byte[] input = new byte[bufferedInputStream.available()];
			bufferedInputStream.read(input);
			bufferedInputStream.close();
			this.astParser.setSource(new String(input, Charset.forName("UTF-8")).toCharArray());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getCompilationUnit Exception, the msg is + " + e.getMessage());
		}
		CompilationUnit result = (CompilationUnit) (this.astParser
				.createAST(null)); // 很慢

		return result;
	}
}
