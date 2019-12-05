package com.xiaodou.docplugin.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.xiaodou.docplugin.entity.ApiClassJavadocEntity;
import com.xiaodou.docplugin.log.LogHelper;
import com.xiaodou.docplugin.service.IClassJavadocService;
import com.xiaodou.docplugin.service.IJavadocService;
import com.xiaodou.docplugin.util.ExceptionHelper;
import com.xiaodou.docplugin.util.FileHelper;
import com.xiaodou.docplugin.util.HttpHelper;

/**
 * 
 * @author bin.song
 * 
 */
public class JavadocService implements IJavadocService {

  public static String basePath = "/";

  public static String projectName = "test";

  public static final String controllerPattern = "Controller.java";

  /**
	 * 
	 */
  public void execute(String basePath, String projectName) {
    // TODO Auto-generated method stub
    try {
      this.basePath = basePath;
      this.projectName = projectName;
      // 获取@Controller注释的Java文件
      List<File> controllerFiles = FileHelper.getFiles(basePath, controllerPattern);

      List<ApiClassJavadocEntity> classJavadocEntities = new ArrayList<ApiClassJavadocEntity>();
      for (File controllerFile : controllerFiles) {
        IClassJavadocService classJavadocService = new ControllerClassJavadocService();
        ApiClassJavadocEntity classJavadocEntity =
            classJavadocService.getClassJavadoc(controllerFile, "");
        if (classJavadocEntity.isController() && classJavadocEntity.isApi()) {
          classJavadocEntities.add(classJavadocEntity);
        }
      }
      for (ApiClassJavadocEntity classJavadoc : classJavadocEntities) {
        classJavadoc.printSelf();
      }
      HttpHelper.setPath(projectName);
      HttpHelper.getIndex(classJavadocEntities);
    } catch (Exception e) {
      // TODO: handle exception
      System.err.println("StackInfo=" + ExceptionHelper.getStackTrace(e));
      LogHelper.Log("[Error] : JavadocService-->execute-->ExpMsg=" + e.getMessage()
          + "-->StackInfo=" + ExceptionHelper.getStackTrace(e));
    }

  }
}
