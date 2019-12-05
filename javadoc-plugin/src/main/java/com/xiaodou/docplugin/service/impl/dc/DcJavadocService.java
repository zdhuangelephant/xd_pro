package com.xiaodou.docplugin.service.impl.dc;

import com.xiaodou.docplugin.entity.ApiClassJavadocEntity;
import com.xiaodou.docplugin.log.LogHelper;
import com.xiaodou.docplugin.service.IClassJavadocService;
import com.xiaodou.docplugin.service.IJavadocService;
import com.xiaodou.docplugin.util.DcHttpHelper;
import com.xiaodou.docplugin.util.ExceptionHelper;
import com.xiaodou.docplugin.util.FileHelper;
import com.xiaodou.docplugin.util.HttpHelper;
import com.xiaodou.docplugin.util.JarUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成直连JavaDoc
 *
 * @author junpeng.chen
 */
public class DcJavadocService implements IJavadocService {

    public static String basePath = "/";

    public static String projectName = "test";

    public static final String filePattern = ".java";

    public void execute(String basePath, String projectName) {
        try {
            this.basePath = basePath;
            this.projectName = projectName;
            List<File> controllerFiles = FileHelper.getFiles(basePath, filePattern);

            List<ApiClassJavadocEntity> classJavadocEntities = new ArrayList<ApiClassJavadocEntity>();
            for (File controllerFile : controllerFiles) {
                IClassJavadocService classJavadocService = new DcApiClassJavadocService();
                ApiClassJavadocEntity classJavadocEntity = classJavadocService.getClassJavadoc(controllerFile, "");
                if (classJavadocEntity.isController() && classJavadocEntity.isApi()) {
                    classJavadocEntities.add(classJavadocEntity);
                }
            }
            for (ApiClassJavadocEntity classJavadoc : classJavadocEntities) {
                classJavadoc.printSelf();
            }
            DcHttpHelper.setPath(projectName);
            HttpHelper.getIndex(classJavadocEntities);
            DcHttpHelper.getIndex(classJavadocEntities);

            String libPath = DcJavadocService.basePath + "/target/" + DcJavadocService.projectName + "/WEB-INF/lib";
            List<File> libJars = FileHelper.getFiles(libPath, ".jar");
            String entityLib;
            for (File lib : libJars) {
                if (lib.getName().contains("entity")) {
                    entityLib = lib.getAbsolutePath();
                    System.out.println("Unzip from " + entityLib + " to " + DcHttpHelper.apiDocPath + '/');
                    JarUtils.unzip(entityLib, DcHttpHelper.apiDocPath + "/");
                }
            }
        } catch (Exception e) {
            System.err.println("StackInfo=" + ExceptionHelper.getStackTrace(e));
            LogHelper.Log("[Error] : JavadocService-->execute-->ExpMsg=" + e.getMessage() + "-->StackInfo=" + ExceptionHelper.getStackTrace(e));
        }

    }
}
