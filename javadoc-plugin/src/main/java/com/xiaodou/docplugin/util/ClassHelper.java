package com.xiaodou.docplugin.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

/**
 * 
 * @author bin.song
 *
 */
public class ClassHelper {

	/**
	 * 
	 * @param cUnit
	 * @return
	 */
	public static List<String> getImports(CompilationUnit cUnit){
		List<String> imports = new ArrayList<String>();
		if(cUnit != null){
			List tImports = cUnit.imports();
			for (Object tImport : tImports) {
				if(tImport.getClass().equals(ImportDeclaration.class)){
					ImportDeclaration realImport = (ImportDeclaration)tImport;
					imports.add(realImport.getName().toString());
					//String _import = tImport.toString().replace("import ", "").replace(";", "");
					//imports.add(_import);
				}
			}
		}
		return imports;
	}
}
