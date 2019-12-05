package com.xiaodou.docplugin.service.impl.dc;

import com.xiaodou.docplugin.core.JdtAst;
import com.xiaodou.docplugin.entity.ApiClassJavadocEntity;
import com.xiaodou.docplugin.entity.ApiMethodJavadocEntity;
import com.xiaodou.docplugin.log.LogHelper;
import com.xiaodou.docplugin.service.IClassJavadocService;
import com.xiaodou.docplugin.service.IMethodJavadocService;
import com.xiaodou.docplugin.service.impl.MethodJavadocService;
import com.xiaodou.docplugin.util.AnnotationHelper;
import com.xiaodou.docplugin.util.ClassHelper;
import com.xiaodou.docplugin.util.DcHelper;

import org.eclipse.jdt.core.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author junpeng.chen
 */
public class DcApiClassJavadocService implements IClassJavadocService {

    private ApiClassJavadocEntity classJavadocEntity = new ApiClassJavadocEntity();
    private CompilationUnit cUnit;
    private List<Object> annotations = new ArrayList<Object>();
    private List<Object> desciptions = new ArrayList<Object>();
    private List<String> imports = new ArrayList<String>();

    /**
     * 获取class javadoc
     */
    public ApiClassJavadocEntity getClassJavadoc(File javaFile, String fullName) {
        JdtAst jdtAst = new JdtAst();
        cUnit = jdtAst.getCompilationUnit(javaFile.getAbsolutePath());
        if (cUnit == null) {
            LogHelper.Log("[Error] : getClassJavadoc-->File=" + javaFile.getAbsolutePath());
            return classJavadocEntity;
        }
        TypeDeclaration type = null;
        //获取class类型
        try {
            type = (TypeDeclaration) cUnit.types().get(0);
        } catch (Throwable ignored) {
            return classJavadocEntity;
        }
        //获取导入的packages
        imports = ClassHelper.getImports(cUnit);
        //设置class名称
        classJavadocEntity.setApiName(getName(type));
        //判断是否实现了IService接口
        if (!DcHelper.isRestApi(type.superInterfaceTypes())) {
            return classJavadocEntity;
        }
        //设置类为Controller
        classJavadocEntity.setController(true);
        //获取RequestMapping内的value
        classJavadocEntity.setRequestMapping("");
        //获取类描述与作者
        getDescription(type);
        classJavadocEntity.setAuthor(getAuthor());
        classJavadocEntity.setDescription(getDescription());
        //获取字段详情
        /*IFieldJavadocService fieldJavadocService = new FieldJavadocService();
		HashMap<SubFieldEntity, Object> fieldMap = fieldJavadocService.getFieldJavadoc(type, imports);
		classJavadocEntity.setFieldList(fieldMap);*/
        //获取方法详情
        IMethodJavadocService methodJavadocService = new DcMethodJavadocService();
        List<ApiMethodJavadocEntity> methodJavadocEntities =
                methodJavadocService.getMethodJavadoc(type, cUnit);
        classJavadocEntity.setMethodList(methodJavadocEntities);
        if (methodJavadocEntities == null || methodJavadocEntities.size() <= 0) {
            classJavadocEntity.setApi(false);
        }

        return classJavadocEntity;
    }

    /**
     * @param type
     * @return
     */
    private String getName(TypeDeclaration type) {
        String name = "";
        if (cUnit.types().isEmpty()) {
            return name;
        }
        name = type.getName().getFullyQualifiedName();
        return name;
    }

    /**
     * @param type
     * @return
     */
    private List<String> getAnnotations(TypeDeclaration type) {
        List<String> annotations = new ArrayList<String>();
        List<Object> bodyDeclarations = type.modifiers();
        if (bodyDeclarations != null) {
            for (Object bodyDeclaration : bodyDeclarations) {
                annotations.add(bodyDeclaration.toString());
                this.annotations.add(bodyDeclaration);
            }
        }
        return annotations;
    }

    /**
     * @param type
     * @return
     */
    private String getRequestMapping(TypeDeclaration type) {
        String requestMapping = "";
        for (Object annotation : annotations) {
            //遍历所有注解
            if (AnnotationHelper.isRequestMapping(annotation.toString())) {
                //判断是否为SingleMemberAnnotation
                if (annotation.getClass().equals(SingleMemberAnnotation.class)) {
                    SingleMemberAnnotation realAnnotation = (SingleMemberAnnotation) annotation;
                    requestMapping = realAnnotation.getValue().toString().trim().replace("\"", "");
                } else if (annotation.getClass().equals(NormalAnnotation.class)) {
                    //如果是NormalAnnotation，则需要遍历values
                    NormalAnnotation realAnnotation = (NormalAnnotation) annotation;
                    for (Object anno : realAnnotation.values()) {
                        String realAnno = anno.toString().trim();
                        if (realAnno.startsWith("value=\"")) {
                            requestMapping = realAnno.replace("value=", "").replace("\"", "");
                            break;
                        }
                    }
                }
            }
        }
        return requestMapping;
    }

    /**
     * @param type
     * @return
     */
    private void getDescription(TypeDeclaration type) {
        Javadoc javadoc = type.getJavadoc();
        if (javadoc != null) {
            desciptions = javadoc.tags();
        }
        //desciptions = type.getJavadoc().tags();
    }

    /**
     * 获取描述
     *
     * @return
     */
    private String getDescription() {
        String desc = "";
        if (desciptions != null) {
            for (Object description : desciptions) {
                if (description.getClass().equals(TagElement.class)) {
                    TagElement realDesc = (TagElement) description;
                    if (realDesc.fragments().size() > 0) {
                        desc = realDesc.fragments().get(0).toString();
                        break;
                    }
                }
            }
        }
        return desc;
    }

    /**
     * 获取作者
     *
     * @return
     */
    private String getAuthor() {
        String author = "";
        if (desciptions != null) {
            for (Object description : desciptions) {
                if (description.getClass().equals(TagElement.class)) {
                    if (description.toString().contains("@author")) {
                        TagElement realDesc = (TagElement) description;
                        if (realDesc.fragments().size() > 0) {
                            author = realDesc.fragments().get(0).toString();
                            break;
                        }
                    }
                }
            }
        }
        return author;
    }

}
