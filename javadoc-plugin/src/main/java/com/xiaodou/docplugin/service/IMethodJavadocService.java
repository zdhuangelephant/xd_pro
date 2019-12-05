package com.xiaodou.docplugin.service;

import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.xiaodou.docplugin.entity.ApiMethodJavadocEntity;

/**
 * 
 * @author bin.song
 *
 */
public interface IMethodJavadocService {

	/**
	 * 
	 * @param method
	 * @return
	 */
	List<ApiMethodJavadocEntity> getMethodJavadoc(TypeDeclaration type, CompilationUnit cUnit);
}
