package com.xiaodou.docplugin.test;

import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.xiaodou.docplugin.core.JdtAst;

public class JstAstTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String javaFilePath = "D:\\OrderMisController.java";
		JdtAst jdt = new JdtAst();

		CompilationUnit result = jdt.getCompilationUnit(javaFilePath);

		List commentList = result.getCommentList();// 获取注释信息,包含 doc注释和单行注释

		PackageDeclaration package1 = result.getPackage();// 获取所在包信息

		// 如:"package readjavafile;"

		List importList = result.imports();// 获取导入的包
		for (Object tImport : importList) {
			if(tImport.getClass().equals(ImportDeclaration.class)){
				ImportDeclaration realImport = (ImportDeclaration)tImport;
				System.out.println(realImport.getName().toString());
				//String _import = tImport.toString().replace("import ", "").replace(";", "");
				//imports.add(_import);
			}
		}
		
		TypeDeclaration type = (TypeDeclaration) result.types().get(0);// 获取文件中的第一个类声明(包含注释)
		
		Javadoc javadoc = type.getJavadoc();
		System.out.println(javadoc.tags().get(0).toString());
				
		List<Object> bodyDeclarations = type.modifiers();
		for (Object bodyDeclaration : bodyDeclarations) {
			System.out.println(bodyDeclaration.toString());
		}

		FieldDeclaration[] fieldList = type.getFields();// 获取类的成员变量
		System.out.println("field type = " + fieldList[0].getType().toString());
		System.out.println("field = " + fieldList[0].toString());
		String[] strArray = fieldList[0].toString().split(" ");
		
		MethodDeclaration[] methodList = type.getMethods();// 获取方法的注释以及方法体
		
		Type method_type = methodList[0].getReturnType2();// 获取返回值类型 如 void
		Type method_type1 = methodList[1].getReturnType2();
		Type method_type2 = methodList[2].getReturnType2();

		SimpleName method_name = methodList[0].getName();// 获取方法名 main

		Javadoc o1 = methodList[0].getJavadoc();// 获取方法的注释

		List o4 = methodList[0].thrownExceptions();// 异常

		List o5 = methodList[0].modifiers();// 访问类型如:[public, static]

		List o6 = methodList[0].parameters();// 获取参数:[String[] args]
		
		List o7 = methodList[0].typeParameters();

		Block method_block = methodList[0].getBody();// 获取方法的内容如:"{System.out.println("Hello");}"

		List statements = method_block.statements();// 获取方法内容的所有行

		ExpressionStatement sta = (ExpressionStatement) statements.get(0);// 获取第一行的内容

	}

}
