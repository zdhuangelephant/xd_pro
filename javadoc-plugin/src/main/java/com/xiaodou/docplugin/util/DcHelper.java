package com.xiaodou.docplugin.util;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 直连API javadoc辅助类
 *
 * @author junpeng.chen
 */
public class DcHelper {
    public static boolean isRestApi(List interfaceList) {
        boolean isApi = false;
        if (interfaceList != null && interfaceList.size() > 0) {
            for (Object inter : interfaceList) {
                if (inter.toString().equals("IService")) {
                    isApi = true;
                    break;
                }
            }
        }
        return isApi;
    }

    public static String getMethodKey(Method method) {
        String methodKey = "(L" + method.getReturnType().getSimpleName() + ")";
        methodKey += method.getName() + "(";
        for (Class<?> parameterType : method.getParameterTypes()) {
            methodKey += "L" + parameterType.getSimpleName() + ",";
        }
        methodKey += ")";
        return methodKey;
    }

    public static String getMethodKey(MethodDeclaration methodDeclaration) {
        String methodKey = "(L" + methodDeclaration.getReturnType2().toString() + ")";
        methodKey += methodDeclaration.getName() + "(";
        for (Object object : methodDeclaration.parameters()) {
            if (object instanceof SingleVariableDeclaration) {
                SingleVariableDeclaration para = (SingleVariableDeclaration) object;
                methodKey += "L" + para.getType().toString() + ",";
            }
        }
        methodKey += ")";
        return methodKey;
    }
}
