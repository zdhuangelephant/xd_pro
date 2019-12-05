package com.xiaodou.oms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpMethod;
import org.junit.Test;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;

/**
 * 批量aos导入配置
 * <p/>
 * Date: 2014/11/12
 * Time: 18:18
 *
 * @author Tian.Dong
 */
public class AosPostKVTest {
  @Test
  public void t() {

  }

  //@Test
  public void test() throws IOException {
    String cookie = "CookieGuid=2cee4570-447e-4527-8dcd-d0a2199e57fc; member=23441099%40qq.com; H5SessionId=7f75b558-e33c-49dd-b277-df6b35d2b006; H5CookieId=aaf52e25-ab9f-48ec-bb54-970ad9e35a49; SessionGuid=e3184679-2bc8-40a0-ae9a-d0827cc3c053; com.eLong.CommonService.OrderFromCookieInfo=Pkid=50&Parentid=50000&Coefficient=0&Status=1&Priority=8000&Makecomefrom=0&Savecookies=0&Cookiesdays=0&Isusefparam=0&Orderfromtype=1&ExpiresTime=0001%2f01%2f01+00%3a00%3a00; PHPSESSID=6orj9cns5mcbb91mcrlaip0005; topMenuId=ConfManage; Esid=69b14a78-301a-447e-9dc8-83b464c7db81; TLTSID=3D207A67484A53FF24ADE0928B830D52; s_cc=true; s_sq=%5B%5BB%5D%5D; TLTHID=464EBDC04EF251865B1702878C62DEE3; utoken=BQQADAABAAgAAAAAAAAAAAAAAAEAAAABAAAACUVMT05HLkNPTQAAAAhkYW4uemhhbwAAAAEAAAABAAAACUVMT05HLkNPTQAAAAhkYW4uemhhbwAAAAIAAAACAAAACUVMT05HLkNPTQAAAAZrcmJ0Z3QAAAAJRUxPTkcuQ09NABIAAAAgxFmsKZfiiaPIwMZbTRi1YbHDPMTJ2Rh5Yzd4L2ieerpUo5pVVKOaVVSkJvVUpOvVAADBAAAAAAAAAAAAAAAAAVJhggFOMIIBSqADAgEFoQsbCUVMT05HLkNPTaIeMBygAwIBAqEVMBMbBmtyYnRndBsJRUxPTkcuQ09No4IBFDCCARCgAwIBEqEDAgEBooIBAgSB%2FxlBduFT4um668AJqq76DW75iLrPdPxY9psAnbmrU0I4kzHskyAUUR19PLF5HHsCAy6D0LxuTwrrt2SaFurOoRVJXhYNKIgAGp45535zoh0YKPsv5x1SQNSlnwWsIptAIRj8ySDjjNe52ffTcugU03L7MdhBLjn%2BAJ01EMeh%2B9NCEzq2%2BzJR67zeXxBmR%2FZ85NVBtU9SsugHjGJdc5B53PcuJWMPnGJ4WSAmFTBzg1C0jyX4c%2F0Eh514jiscNTLATnE3ASflsQnoYCUwPDRmDwadU6subA4EQxL%2FBlV%2Fixd%2FNmShfH4qRBJ5fsFBw1zhnbnrPk2%2BZFrqcJQrOjPHKwAAAAA%3D; userKey=%2F%F2%C7%B2OrF%9E%ABLRv%83T%95%B8%26%D6%1E%9F%14%C6%D4%7BWE%F6%CB%96%DF%89%82%9D%CE%25%AC%99u%CF%3C; nodeId=27660";
    //String nodeId = "27660";//oms job
    String nodeId = "27588";//oms api
    String fileName = "cache.properties";

    uploadKV(cookie,nodeId,fileName);
  }

  public void uploadKV(String cookie, String nodeId, String fileName) throws IOException {
    File file = new File("F:\\workProject\\ElongOrder\\elong-oms\\Trunk\\src\\main\\resources\\conf\\custom\\env\\" + fileName);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = null;
    while ((line = br.readLine()) != null) {
      if (line.length() == 0 || line.startsWith("#")) {
        continue;
      }
      String key = line.split("=")[0];
      String value = line.substring(key.length() + 1);
      System.out.println(key + ":" + value);
      HttpUtil httpUtil = HttpUtil.getInstance();
      //String str = "key=%s&value=%s&nodeId=33383&description=";
      //String str = "key=%s&value=%s&nodeId=27661&description=";
      String str = "key=%s&value=%s&nodeId=%s&description=";
      str = String.format(str, key, value, nodeId);
      HttpMethod postMethod = HttpMethodUtil.getPostMethod("http://aos.corp.elong.com/apus_web/ConfItem/Create", "application/x-www-form-urlencoded", "utf-8", str);
      postMethod.setRequestHeader("Cookie", cookie);
      httpUtil.setMethod(postMethod);
      HttpResult httpResult = httpUtil.getHttpResult();
      System.out.println(httpResult.getContent());
    }
  }

  //@Test
  public void makeDoubleDollar() throws IOException {
    File file = new File("F:\\workProject\\ElongOrder\\elong-oms\\Trunk\\src\\main\\resources\\conf\\custom\\env\\oms_pay_mapping.properties");
    BufferedReader br = new BufferedReader(new FileReader(file));
    //File toFile = new File("E:\\config2.properties");
    // PrintWriter pw = new PrintWriter(new FileWriter(toFile));
    String line = null;
    while ((line = br.readLine()) != null) {
      if (line.contains("=")) {
        String key = line.split("=")[0];
        //pw.println(key + "=$$" + key);
        System.out.println(key + "=$$" + key);
      }
    }
  }

  //手动注册
  //@Test
  public void registration() throws UnsupportedEncodingException {
    String pathStr = "F:\\workProject\\ElongOrder\\ElongOrder\\train\\Branches\\train-1.46.1\\target\\classes\\conf\\custom\\env";
    System.out.println(pathStr);
    File path = new File(pathStr);
    for (File file : path.listFiles()) {
      if (file.getName().endsWith(".vm")) {
        String fileName = file.getName();

        System.out.print(fileName.substring(0, fileName.length() - 9));
        System.out.print(" ");
        System.out.println(fileName);
        String str = "metaId=49674&confs=.%2Fconf%2Fcustom%2Fenv%2F" + fileName;
//System.out.println(str);
//        HttpMethod postMethod = HttpMethodUtil.getPostMethod("http://aos.corp.elong.com/apus_web/confManager/Registration", "application/x-www-form-urlencoded", "utf-8", str);
//        postMethod.setRequestHeader("Cookie", "CookieGuid=e5345cda-8b93-45aa-acc2-b9644e5b3b0c; member=23441099%40qq.com; TLTSID=7841DBE24BA551ADB5CF669626DEDBD8; TLTHID=E3B4C17C4B29232609F821B49F407418; utoken=BQQADAABAAgAAAABAAAAAAAAAAEAAAABAAAACUVMT05HLkNPTQAAAAxqaWFuanVuLndhbmcAAAABAAAAAQAAAAlFTE9ORy5DT00AAAAMamlhbmp1bi53YW5nAAAAAgAAAAIAAAAJRUxPTkcuQ09NAAAABmtyYnRndAAAAAlFTE9ORy5DT00AEgAAACCGSyyp4bEf1OugMNc6AfSXvIh%2Fygc44t%2FxulyRbOHBWFR4QiFUeEIhVHjOwVR5k6AAAMEAAAAAAAAAAAAAAAABV2GCAVMwggFPoAMCAQWhCxsJRUxPTkcuQ09Noh4wHKADAgECoRUwExsGa3JidGd0GwlFTE9ORy5DT02jggEZMIIBFaADAgESoQMCAQGiggEHBIIBA3HQgD2%2FFsp%2FvIK9dC4BApwL5vVUVU7Gq5KXoe95JBo6yW2sHgwvySy10bEJSYS7pDu2ltW%2B7WZhjnZxUpgeHJbChryrPgkcD0L%2BGVxTyTQ%2BvdlV3VWba5QTenKpnnERy6u5CIyKsKUy5I9lAJDh5jksBq1RE2FiPSN4O%2FEAZqBMfsVwXWPRwdK%2ByOZ0a6LIxhjQ8w9zYX0t0ms%2BYdO2T1syUrBBDLY6CB3KgbvDzN%2FjujsqbM9byh9a1Vv9YEEpjdcRycBBWyFeoFwBp8%2BqBCce6WmsSTED774rj99WaKdDX39A27LbaiDovaJiLeW%2BGNlwRj%2Bap7JD5WIJ22A1l%2Fovo1AAAAAA; userKey=%88%1Dx%24%0CDX%DB%C7%CB%FF%A2n%12Q%16%0B%C4%C4%D1%BC%E9%83%DA%7B%89%8Be%AEi%BC%8C%89%9E%A1%94%EF%17b0; PHPSESSID=s3jv1o3a8k91aotms8f3mvav06; nodeId=37151; topMenuId=ConfManage");
//        HttpUtil httpUtil = HttpUtil.getInstance();
//        httpUtil.setMethod(postMethod);
//        HttpResult httpResult = httpUtil.getHttpResult();
//        System.out.println(httpResult.getContent());

      }
    }
//    while ((line = br.readLine()) != null) {
//      System.out.println(key + ":" + value);
//      HttpUtil httpUtil = HttpUtil.getInstance();
//      String str = "key=%s&value=%s&nodeId=37151&description=";
//      str = String.format(str, key, value);
//      HttpMethod postMethod = HttpMethodUtil.getPostMethod("http://aos.corp.elong.com/apus_web/ConfItem/Create", "application/x-www-form-urlencoded", "utf-8", str);
//      postMethod.setRequestHeader("Cookie", "CookieGuid=e5345cda-8b93-45aa-acc2-b9644e5b3b0c; member=23441099%40qq.com; TLTSID=7841DBE24BA551ADB5CF669626DEDBD8; TLTHID=E3B4C17C4B29232609F821B49F407418; utoken=BQQADAABAAgAAAABAAAAAAAAAAEAAAABAAAACUVMT05HLkNPTQAAAAxqaWFuanVuLndhbmcAAAABAAAAAQAAAAlFTE9ORy5DT00AAAAMamlhbmp1bi53YW5nAAAAAgAAAAIAAAAJRUxPTkcuQ09NAAAABmtyYnRndAAAAAlFTE9ORy5DT00AEgAAACCGSyyp4bEf1OugMNc6AfSXvIh%2Fygc44t%2FxulyRbOHBWFR4QiFUeEIhVHjOwVR5k6AAAMEAAAAAAAAAAAAAAAABV2GCAVMwggFPoAMCAQWhCxsJRUxPTkcuQ09Noh4wHKADAgECoRUwExsGa3JidGd0GwlFTE9ORy5DT02jggEZMIIBFaADAgESoQMCAQGiggEHBIIBA3HQgD2%2FFsp%2FvIK9dC4BApwL5vVUVU7Gq5KXoe95JBo6yW2sHgwvySy10bEJSYS7pDu2ltW%2B7WZhjnZxUpgeHJbChryrPgkcD0L%2BGVxTyTQ%2BvdlV3VWba5QTenKpnnERy6u5CIyKsKUy5I9lAJDh5jksBq1RE2FiPSN4O%2FEAZqBMfsVwXWPRwdK%2ByOZ0a6LIxhjQ8w9zYX0t0ms%2BYdO2T1syUrBBDLY6CB3KgbvDzN%2FjujsqbM9byh9a1Vv9YEEpjdcRycBBWyFeoFwBp8%2BqBCce6WmsSTED774rj99WaKdDX39A27LbaiDovaJiLeW%2BGNlwRj%2Bap7JD5WIJ22A1l%2Fovo1AAAAAA; userKey=%88%1Dx%24%0CDX%DB%C7%CB%FF%A2n%12Q%16%0B%C4%C4%D1%BC%E9%83%DA%7B%89%8Be%AEi%BC%8C%89%9E%A1%94%EF%17b0; PHPSESSID=s3jv1o3a8k91aotms8f3mvav06; nodeId=37151; topMenuId=ConfManage");
//      httpUtil.setMethod(postMethod);
//      HttpResult httpResult = httpUtil.getHttpResult();
//      System.out.println(httpResult.getContent());
//    }
  }
}
