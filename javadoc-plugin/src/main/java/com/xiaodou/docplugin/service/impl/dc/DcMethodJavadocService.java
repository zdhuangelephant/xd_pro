package com.xiaodou.docplugin.service.impl.dc;

import com.xiaodou.docplugin.entity.*;
import com.xiaodou.docplugin.service.IMethodJavadocService;
import com.xiaodou.docplugin.service.IParamJavadocService;
import com.xiaodou.docplugin.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class DcMethodJavadocService implements IMethodJavadocService {

    private List<String> imports = new ArrayList<String>();

    public List<ApiMethodJavadocEntity> getMethodJavadoc(
            TypeDeclaration type, CompilationUnit cUnit) {
        List<ApiMethodJavadocEntity> methodJavadocEntities = new ArrayList<ApiMethodJavadocEntity>();
        String fileName = cUnit.getPackage().getName() + "." + type.getName().toString();
        Class<?> serviceClass = null;
        try {
            serviceClass = DcClassLoader.INSTANCE.loadClass(fileName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Method> methodMap = new HashMap<String, Method>();
        for (Method method : serviceClass.getMethods()) {
            if (!isHttpApiMethod(method))
                continue;
            methodMap.put(DcHelper.getMethodKey(method), method);
        }
        //获取import的package列表
        imports = ClassHelper.getImports(cUnit);
        //获取方法集合
        MethodDeclaration[] methodDeclarationArray = type.getMethods();
        for (MethodDeclaration method : methodDeclarationArray) {
            //判断方法是否为HttpAPI方法：@ServiceMethod，Public
            if (!isHttpApiMethod(method)) {
                continue;
            }
            System.out.println(method.getName().toString());

            //定义实体
            ApiMethodJavadocEntity methodJavadocEntity = new ApiMethodJavadocEntity();
            //获取方法名称
            methodJavadocEntity.setMethodName(method.getName().toString());
            //获取RequestMapping value
            methodJavadocEntity.setRequestMapping(getRequestMapping(method, methodMap.get(DcHelper.getMethodKey(method))));
            //获取HttpAPI调用方式
            methodJavadocEntity.setHttpMethod("POST");
            //获取方法描述
            List<Object> descriptions = getDescription(method);
            methodJavadocEntity.setDescription(getDescription(descriptions));
            //获取返回值类型
            HashMap<SubFieldEntity, Object> retMap = getReturnMap(method.getReturnType2(), descriptions, methodMap.get(DcHelper.getMethodKey(method)));
            methodJavadocEntity.setResponseDic(retMap);
            //获取入参类型列表
            HashMap<SubFieldEntity, Object> paramMap = getParamMap(method, descriptions, methodMap.get(DcHelper.getMethodKey(method)));
            methodJavadocEntity.setRequestDic(paramMap);
            if (paramMap != null) {
                Collection<Object> values = paramMap.values();
                for (Object value : values) {
                    if (value instanceof ApiClassJavadocEntity) {
                        methodJavadocEntity.setHttpMethod("POST");
                        break;
                    }
                }
            }
            methodJavadocEntities.add(methodJavadocEntity);
        }

        return methodJavadocEntities;
    }

    private HashMap<SubFieldEntity, Object> getReturnMap(Type type, List<Object> descriptions, Method method) {
        HashMap<SubFieldEntity, Object> returnMap = new HashMap<SubFieldEntity, Object>();
        //Type returnType = method.getReturnType2();
        SubFieldEntity fieldEntity = new SubFieldEntity();
        fieldEntity.setName("");
        fieldEntity.setDescription(MethodHelper.getMethodReturnTypeDesc(type, descriptions));
        fieldEntity.setType(type.toString());
        String realRetType = TypeHelper.getType(type);
        IParamJavadocService paramJavadocService = new DcParamJavadocService();
        Object realReturnType = paramJavadocService.getFieldJavadoc(realRetType, imports);
        returnMap.put(fieldEntity, realReturnType);

        if (realRetType.equals(realReturnType)) {
            Class<?> returnClass = method.getReturnType();
            if (isDcLibEntity(returnClass)) {
                DcLibEntity dcLibEntity = new DcLibEntity();
                dcLibEntity.setPackageName(returnClass.getPackage().getName());
                dcLibEntity.setSimpleTypeName(returnClass.getSimpleName());
                System.out.println(dcLibEntity.getUrl());
                returnMap.put(fieldEntity, dcLibEntity);
            }
        }

        return returnMap;
    }

    private HashMap<SubFieldEntity, Object> getParamMap(MethodDeclaration method, List<Object> descriptions, Method methodDefine) {
        HashMap<SubFieldEntity, Object> paramMap = new HashMap<SubFieldEntity, Object>();
        HashMap<String, ParamEntity> paramDic =
                MethodHelper.getMethodParamTypeDesc(method.parameters(), descriptions);
        for (String variable : paramDic.keySet()) {
            ParamEntity paramEntity = paramDic.get(variable);
            SubFieldEntity fieldEntity = new SubFieldEntity();
            fieldEntity.setName(paramEntity.getName());
            fieldEntity.setDescription(paramEntity.getDesc());
            fieldEntity.setType(paramEntity.getType().toString());
            String realRetType = TypeHelper.getType(paramEntity.getType());
            IParamJavadocService paramJavadocService = new DcParamJavadocService();
            Object realReturnType = paramJavadocService.getFieldJavadoc(realRetType, imports);
            paramMap.put(fieldEntity, realReturnType);

            if (realRetType.equals(realReturnType)) {
                Class<?> parameterClass = methodDefine.getReturnType();
                for (Class<?> parameter : methodDefine.getParameterTypes()) {
                    if (parameter.getSimpleName().equals(realReturnType)) {
                        parameterClass = parameter;
                        break;
                    }
                }
                if (isDcLibEntity(parameterClass)) {
                    DcLibEntity dcLibEntity = new DcLibEntity();
                    dcLibEntity.setPackageName(parameterClass.getPackage().getName());
                    dcLibEntity.setSimpleTypeName(parameterClass.getSimpleName());
                    System.out.println(dcLibEntity.getUrl());
                    paramMap.put(fieldEntity, dcLibEntity);
                }
            }
        }
        return paramMap;
    }

    private boolean isDcLibEntity(Class<?> parameterClass) {
        return parameterClass != null && !parameterClass.isPrimitive()
                && !(parameterClass == String.class) && !(parameterClass == Date.class);
    }

    private List<Object> getAnnotations(MethodDeclaration method) {
        List<Object> annotations = new ArrayList<Object>();
        List<Object> bodyDeclarations = method.modifiers();
        if (bodyDeclarations != null) {
            for (Object bodyDeclaration : bodyDeclarations) {
                annotations.add(bodyDeclaration);
            }
        }
        return annotations;
    }

    private String getRequestMapping(MethodDeclaration method, Method methodDef) {
        String requestMapping = "";
        SingleVariableDeclaration requestParam = (SingleVariableDeclaration) method.parameters().get(0);
        for (String aImport : imports) {
            if (aImport.endsWith(requestParam.getType().toString())) {
                requestMapping = "rest/" + aImport.toLowerCase().replace(".", "/");
                break;
            }
        }
        if (requestMapping.isEmpty()) {
            for (Class<?> parameterType : methodDef.getParameterTypes()) {
                requestMapping = "rest/" + parameterType.getName().replace(".", "/");
                break;
            }
        }
        return requestMapping;
    }

    private String getHttpMethod(MethodDeclaration method, List<Object> annotations) {
        //样例：method=RequestMethod.POST
        String httpMethod = "GET";
        for (Object annotation : annotations) {

            //遍历所有注解
            if (AnnotationHelper.isRequestMapping(annotation.toString())) {

                //判断是否为SingleMemberAnnotation
                if (annotation.getClass().equals(SingleMemberAnnotation.class)) {
                    SingleMemberAnnotation realAnnotation = (SingleMemberAnnotation) annotation;
                    String tmpHttpMethod = realAnnotation.getValue().toString().trim();
                    if (tmpHttpMethod.startsWith("RequestMethod.")) {
                        httpMethod = tmpHttpMethod.replace("RequestMethod.", "").toUpperCase();
                    }
                } else if (annotation.getClass().equals(NormalAnnotation.class)) {
                    //如果是NormalAnnotation，则需要遍历values
                    NormalAnnotation realAnnotation = (NormalAnnotation) annotation;
                    for (Object anno : realAnnotation.values()) {
                        String realAnno = anno.toString().trim();
                        if (realAnno.startsWith("method=RequestMethod.")) {
                            httpMethod = realAnno.replace("method=RequestMethod.", "").toUpperCase();
                            break;
                        }
                    }
                }

            }
        }
        return httpMethod;
    }

    private List<Object> getDescription(MethodDeclaration method) {
        List<Object> descriptions = new ArrayList<Object>();
        Javadoc javadoc = method.getJavadoc();
        if (javadoc != null) {
            descriptions = javadoc.tags();
        }
        return descriptions;
    }

    /**
     * 获取描述
     *
     * @return
     */
    private String getDescription(List<Object> descriptions) {
        String desc = "";
        if (descriptions != null) {
            for (Object description : descriptions) {
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
     * 判断是否为httpmethod
     *
     * @param method
     * @return
     */
    private boolean isHttpApiMethod(MethodDeclaration method) {
        List parameters = method.parameters();
        if (parameters == null || parameters.size() != 1)
            return false;

        boolean isHttpApiMethod;
        boolean isPublic = false;
        boolean isServiceMethod = false;
        List bodyDeclarations = method.modifiers();
        if (bodyDeclarations != null) {
            for (Object bodyDeclaration : bodyDeclarations) {
                if (bodyDeclaration.getClass().equals(Modifier.class)) {
                    Modifier body = (Modifier) bodyDeclaration;
                    if (body.toString().equals("public")) {
                        isPublic = true;
                    }
                }
                if (bodyDeclaration.getClass().equals(NormalAnnotation.class)) {
                    NormalAnnotation body = (NormalAnnotation) bodyDeclaration;
                    if (body.getTypeName().toString().equals("ServiceMethod")) {
                        isServiceMethod = true;
                    }
                }
                if (bodyDeclaration.getClass().equals(SingleMemberAnnotation.class)) {
                    SingleMemberAnnotation body = (SingleMemberAnnotation) bodyDeclaration;
                    if (body.getTypeName().toString().equals("ServiceMethod")) {
                        isServiceMethod = true;
                    }
                }
                if (bodyDeclaration.getClass().equals(MarkerAnnotation.class)) {
                    MarkerAnnotation body = (MarkerAnnotation) bodyDeclaration;
                    if (body.getTypeName().toString().equals("ServiceMethod")) {
                        isServiceMethod = true;
                    }
                }
            }
        }
        isHttpApiMethod = isPublic && isServiceMethod;
        return isHttpApiMethod;
    }

    /**
     * 判断是否为httpmethod
     *
     * @param method
     * @return
     */
    private boolean isHttpApiMethod(Method method) {
        boolean isHttpApiMethod = false;
        for (java.lang.annotation.Annotation annotation : method.getDeclaredAnnotations()) {
            System.out.println(annotation.toString());
            System.out.println(annotation.annotationType().getName());
            if (annotation.annotationType().getName().equals("com.elong.hotel.dc.restapi.server.ServiceMethod")) {
                isHttpApiMethod = true;
                break;
            }
        }
        return isHttpApiMethod && java.lang.reflect.Modifier.isPublic(method.getModifiers());
    }
}
