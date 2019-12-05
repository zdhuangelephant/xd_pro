package com.xiaodou.docplugin.service.impl.dc;

import com.xiaodou.docplugin.service.IClassJavadocService;
import com.xiaodou.docplugin.service.IParamJavadocService;
import com.xiaodou.docplugin.service.impl.FieldClassJavadocService;
import com.xiaodou.docplugin.util.FileHelper;

import java.io.File;
import java.util.List;

/**
 * @author junpeng.chen
 */
public class DcParamJavadocService implements IParamJavadocService {

    public Object getFieldJavadoc(String type, List<String> imports) {
        Object ret = null;
        if (type == null || imports == null) {
            return ret;
        }
        String realType = type.toString();
        if (realType.equals("")) {
            return realType;
        }
        for (String _import : imports) {
            if (_import.endsWith(realType)) {
                String javaFilePath = getJavaFilePath(_import);
                //判断指定java文件是否存在
                if (FileHelper.exists(javaFilePath)) {
                    //调用ClassService获取类型详情
                    File javaFile = new File(javaFilePath);
                    IClassJavadocService classJavadocService = new FieldClassJavadocService();
                    ret = classJavadocService.getClassJavadoc(javaFile, _import);
                } else {
                    ret = realType;
                }
            }
        }

        if (ret == null) {
            ret = realType;
        }

        return ret;
    }

    /**
     * @param pckName
     * @return
     */
    private String getJavaFilePath(String pckName) {
        String path = DcJavadocService.basePath + "/src/main/java/";
        String pckPath = pckName.replace(".", "/");
        return path + pckPath + ".java";
    }

}
