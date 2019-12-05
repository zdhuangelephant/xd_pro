package com.xiaodou.ms.web.controller.util;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.summer.vo.out.ResultInfo;

import lombok.Cleanup;
//import net.sourceforge.tess4j.ITesseract;
//import net.sourceforge.tess4j.Tesseract;

@Controller("vCRController")
@RequestMapping("/vcr")
public class VCRController extends BaseController {
  private static ScheduledExecutorService newScheduledThreadPool =
      Executors.newScheduledThreadPool(2);;

  @RequestMapping("/getData")
  @ResponseBody
  public static String getData(String vcode) {
    ResultInfo info = new ResultInfo();
    try {
      info.setServerIp(InetAddress.getLocalHost().getHostAddress());
      String baseName = UUID.randomUUID().toString() + ".png";
      final File file = new File(baseName);
      @Cleanup
      FileOutputStream fos = new FileOutputStream(file);
      
//      generateImage(vcode, fos);
      // 验证码识别
//      ITesseract instance = new Tesseract();
//      instance.setDatapath("F:\\xdworks\\xd-ms\\data");
//      instance.setLanguage("eng");
//      String ocrResult = instance.doOCR(file);
//      System.out.println(ocrResult);
//
//      newScheduledThreadPool.schedule(new Runnable() {
//        @Override
//        public void run() {
//          if (null != file && file.exists()) {
//            file.delete();
//          }
//        }
//      }, 10, TimeUnit.SECONDS);
//
//      info.setRetcode("0");
//      info.setRetdesc(ocrResult == null ? StringUtils.EMPTY : ocrResult.replaceAll("\r|\n", ""));
      return info.toString0();
    } catch (Exception e) {}

    info.setRetcode("-1");
    info.setRetdesc(StringUtils.EMPTY);
    return info.toString0();
  }




/**
 * @Description: 将base64编码字符串转换为图片
 * @Author: 
 * @CreateTime: 
 * @param imgStr base64编码字符串
 * @param path 图片路径-具体到文件
 * @return
*/
  /*public static boolean generateImage(String imgStr, OutputStream out) {
    if (imgStr == null) // 图像数据为空
        return false;
    BASE64Decoder decoder = new BASE64Decoder();
    try {
        // Base64解码
        byte[] b = decoder.decodeBuffer(imgStr);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        out.write(b);
        out.flush();
        out.close();
       return true;
    } catch (Exception e) {
       return false;
    }
}*/

}
