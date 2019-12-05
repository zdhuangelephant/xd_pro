package com.xiaodou.common.info.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * @name @see com.xiaodou.common.util.MailSender.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月28日
 * @description 邮件工具类
 * @version 1.0
 */
public class MailSender {
  private static MailSender mailSender = new MailSender();

  private static String server;
  private static String port;
  private static String address;
  private static String username;
  private static String password;
  private static boolean validate = true;
  private static Session session = null;

  static {
    init();
  }

  private static void init() {
    InputStream input = null;
    try {
      input =
          Thread.currentThread().getContextClassLoader()
              .getResourceAsStream("conf/custom/env/email-info.properties");
      if (null != input) {
        init(input);
      }
    } catch (Exception e) {
      LoggerUtil.error("initialize error", e);
    } finally {
      if (null != input) {
        try {
          input.close();
        } catch (IOException e) {}
      }
    }
  }

  private MailSender() {

  }

  public static MailSender getInstance() {
    return mailSender;
  }

  private static void init(InputStream input) throws Exception {
    if (session != null) return;
    Properties prop = new Properties();
    prop.load(input);
    if (prop.containsKey("mail.smtp.host")) {
      server = prop.getProperty("mail.smtp.host");
    }
    if (prop.containsKey("mail.smtp.port")) {
      port = prop.getProperty("mail.smtp.port");
    }
    if (prop.containsKey("mail.address")) {
      address = prop.getProperty("mail.address");
    }
    if (prop.containsKey("mail.username")) {
      username = prop.getProperty("mail.username");
    }
    if (prop.containsKey("mail.password")) {
      password = prop.getProperty("mail.password");
    }
    // 是否进行验证
    prop.put("mail.smtp.auth", "true");
    session = Session.getInstance(getProperties(), new Authentic());

  }

  private static Properties getProperties() {
    Properties rs = new Properties();
    rs.setProperty("mail.smtp.host", server);
    rs.setProperty("mail.smtp.port", port);
    rs.setProperty("mail.smtp.socketFactory.port", port);
    // 开启ssl验证
    rs.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    rs.setProperty("mail.smtp.socketFactory.fallback", "false");
    rs.setProperty("mail.smtp.auth", validate ? "true" : "false");
    return rs;
  }

  static class Authentic extends Authenticator {
    public Authentic() {}

    public PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(username, password);
    }
  }

  public boolean send(String toAddress, boolean toSender, String subject, Object content,
      Map<String, File> files) {
    try {
      if (session == null) {
        LoggerUtil.error("Session not init!", new RuntimeException());
        return false;
      }
      Message rs = new MimeMessage(session);
      Address from = new InternetAddress(address);
      rs.setFrom(from);

      if (!toSender && StringUtils.isNotBlank(toAddress)) {
        String[] toAddress2 = toAddress.split("\\;");
        Address[] add = new Address[toAddress2.length];
        for (int i = 0; i < toAddress2.length; i++)
          add[i] = new InternetAddress(toAddress2[i]);
        rs.setRecipients(RecipientType.TO, add); // 接收地址
      } else if (toSender && StringUtils.isBlank(toAddress)) {
        rs.setRecipient(RecipientType.TO, new InternetAddress(address)); // 接收地址
      } else if (toSender && StringUtils.isNotBlank(toAddress)) {
        String[] toAddress2 = toAddress.split("\\;");
        Address[] add = new Address[1 + toAddress2.length];
        add[0] = new InternetAddress(address);
        for (int i = 0; i < toAddress2.length; i++)
          add[1 + i] = new InternetAddress(toAddress2[i]);
        rs.setRecipients(RecipientType.TO, add); // 接收地址
      } else { // !toSender && toAddress.equals("")
        LoggerUtil.error("User mailaddress is empty!", new RuntimeException());
        return false;
      }
      String subjectNew = MimeUtility.encodeText(subject, "UTF-8", "B");
      rs.setSubject(subjectNew);

      Multipart mpp = new MimeMultipart("top");
      MimeBodyPart contentBody = new MimeBodyPart();
      Multipart mp = new MimeMultipart("middle");

      MimeBodyPart html = new MimeBodyPart();
      html.setContent(content, "text/html; charset=UTF-8"); // 邮件HTML内容
      mp.addBodyPart(html);

      if (files != null && files.size() > 0) { // 邮件附件
        for (String name : files.keySet()) {
          MimeBodyPart mbp = new MimeBodyPart();
          FileDataSource fds = new FileDataSource(files.get(name));
          mbp.setDataHandler(new DataHandler(fds));
          mbp.setContentID(name);
          mbp.setFileName(name);
          mp.addBodyPart(mbp);
        }
      }
      contentBody.setContent(mp);
      mpp.addBodyPart(contentBody);
      rs.setContent(mpp);
      rs.setSentDate(new Date());
      Transport.send(rs);
      return true;
    } catch (Exception e) {
      LoggerUtil.error("MailSenderServlet have error :", e);
      e.printStackTrace();
      return false;
    }
  }

}
