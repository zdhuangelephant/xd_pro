package com.xiaodou.common.test.util.warp;

import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

/**
 * Date: 2014/10/9
 * Time: 11:18
 *
 * @author Tian.Dong
 */
public class WarpTest {
  String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
      "\n" +
      "<beans xmlns=\"http://www.springframework.org/schema/beans\"\n" +
      "\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:context=\"http://www.springframework.org/schema/context\"\n" +
      "\txmlns:mvc=\"http://www.springframework.org/schema/mvc\" xmlns:aop=\"http://www.springframework.org/schema/aop\"\n" +
      "\txmlns:tx=\"http://www.springframework.org/schema/tx\" xmlns:oscache=\"http://www.springmodules.org/schema/oscache\"\n" +
      "\txsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\n" +
      "\t\t   http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd\n" +
      "           http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd\n" +
      "           http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd\n" +
      "           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd\n" +
      "           http://www.springmodules.org/schema/oscache http://www.springmodules.org/schema/cache/springmodules-oscache.xsd\">\n" +
      "\n" +
      "\t<context:property-placeholder\n" +
      "\t\tlocation=\"classpath:conf/custom/env/config.properties\"\n" +
      "\t\tignore-unresolvable=\"true\" />\n" +
      "    <context:property-placeholder\n" +
      "            location=\"classpath:conf/custom/env/rabbitmq.properties\"\n" +
      "            ignore-unresolvable=\"true\" />\n" +
      "\t<!-- spring 线程池配置 start -->\n" +
      "\t<!-- insurance线程池 -->\n" +
      "\t<bean id=\"insuranceTaskExecutor\"\n" +
      "\t\tclass=\"org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor\">\n" +
      "\t\t<property name=\"corePoolSize\" value=\"${insurance.taskexecutor.corePoolSize}\" />\n" +
      "\t\t<property name=\"maxPoolSize\" value=\"${insurance.taskexecutor.maxPoolSize}\" />\n" +
      "\t\t<property name=\"keepAliveSeconds\" value=\"${insurance.taskexecutor.keepAliveSeconds}\" />\n" +
      "\t\t<property name=\"queueCapacity\" value=\"${insurance.taskexecutor.queueCapacity}\" />\n" +
      "\t</bean>\n" +
      "\t<!-- spring 线程池配置 end -->\n" +
      "\t<!--批处理关单线程池 -->\n" +
      "\t<bean id=\"closeOrderTaskExecutor\"\n" +
      "\t\tclass=\"org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor\">\n" +
      "\t\t<property name=\"corePoolSize\" value=\"${closeOrderTaskExecutor.corePoolSize}\" />\n" +
      "\t\t<property name=\"maxPoolSize\" value=\"${closeOrderTaskExecutor.maxPoolSize}\" />\n" +
      "\t\t<property name=\"keepAliveSeconds\" value=\"${closeOrderTaskExecutor.keepAliveSeconds}\" />\n" +
      "\t\t<property name=\"queueCapacity\" value=\"${closeOrderTaskExecutor.queueCapacity}\" />\n" +
      "\t</bean>\n" +
      "\t<!--短信发送线程池 -->\n" +
      "\t<bean id=\"smsTaskExecutor\"\n" +
      "\t\tclass=\"org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor\">\n" +
      "\t\t<property name=\"corePoolSize\" value=\"${smsTaskExecutor.corePoolSize}\" />\n" +
      "\t\t<property name=\"maxPoolSize\" value=\"${smsTaskExecutor.maxPoolSize}\" />\n" +
      "\t\t<property name=\"keepAliveSeconds\" value=\"${smsTaskExecutor.keepAliveSeconds}\" />\n" +
      "\t\t<property name=\"queueCapacity\" value=\"${smsTaskExecutor.queueCapacity}\" />\n" +
      "\t</bean>\n" +
      "\n" +
      "\t<!--乘客信息审核线程池 -->\n" +
      "\t<bean id=\"verifyPassengerTaskExecutor\"\n" +
      "\t\tclass=\"org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor\">\n" +
      "\t\t<property name=\"corePoolSize\" value=\"${verifyPassenger.taskExecutor.corePoolSize}\" />\n" +
      "\t\t<property name=\"maxPoolSize\" value=\"${verifyPassenger.taskExecutor.maxPoolSize}\" />\n" +
      "\t\t<property name=\"keepAliveSeconds\" value=\"${verifyPassenger.taskExecutor.keepAliveSeconds}\" />\n" +
      "\t\t<property name=\"queueCapacity\" value=\"${verifyPassenger.taskExecutor.queueCapacity}\" />\n" +
      "\t</bean>\n" +
      "\n" +
      "    <!--rabbit mq 连接池 -->\n" +
      "    <bean id=\"rabbitPool\" class=\"com.elong.train.service.rabbit.pool.RabbitPool\">\n" +
      "        <property name=\"hostName\" value=\"${rabbit.hostName}\" />\n" +
      "        <property name=\"hostPort\" value=\"${rabbit.hostPort}\" />\n" +
      "        <property name=\"username\" value=\"${rabbit.username}\" />\n" +
      "        <property name=\"password\" value=\"${rabbit.password}\" />\n" +
      "    </bean>\n" +
      "    \n" +
      "    <!-- Elong抓取数据线程池 -->\n" +
      "\t<bean id=\"elongSearchTaskExecutor\"\n" +
      "\t\tclass=\"org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor\">\n" +
      "\t\t<property name=\"corePoolSize\" value=\"${elongSearch.taskExecutor.corePoolSize}\" />\n" +
      "\t\t<property name=\"maxPoolSize\" value=\"${elongSearch.taskExecutor.maxPoolSize}\" />\n" +
      "\t\t<property name=\"keepAliveSeconds\" value=\"${elongSearch.taskExecutor.keepAliveSeconds}\" />\n" +
      "\t\t<property name=\"queueCapacity\" value=\"${elongSearch.taskExecutor.queueCapacity}\" />\n" +
      "\t</bean>\n" +
      "</beans>";

  @Test
  public void test() {
    Document doc = null;
    try {
      doc = DocumentHelper.parseText(xml);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    /**
     * <property name="corePoolSize" value="${insurance.taskexecutor.corePoolSize}" />
     <property name="maxPoolSize" value="${insurance.taskexecutor.maxPoolSize}" />
     <property name="keepAliveSeconds" value="${insurance.taskexecutor.keepAliveSeconds}" />
     <property name="queueCapacity" value="${insurance.taskexecutor.queueCapacity}" />
     */
    Element root = doc.getRootElement();
    for (Object ele : root.elements()) {
      Element element = (Element) ele;
      if (element.getName().equals("bean")) {
        String id = element.attributeValue("id");
        for (Object obj : element.elements()) {
          Element property = (Element) obj;
          if (property.attributeValue("name").equals("corePoolSize")) {
            //insuranceTaskExecutor.setCorePoolSize(fileUtil.getPropertiesInt(""));
            System.out.println(id + "." + "setCorePoolSize"
                    + "(fileUtil.getPropertiesInt(" + "\"" + property.attributeValue("value").substring(2, property.attributeValue("value").length() - 1)
                    + "\"" + ");"
            );
          }
          if (property.attributeValue("name").equals("maxPoolSize")) {
            //insuranceTaskExecutor.setCorePoolSize(fileUtil.getPropertiesInt(""));
            System.out.println(id + "." + "setMaxPoolSize"
                    + "(fileUtil.getPropertiesInt(" + "\"" + property.attributeValue("value").substring(2, property.attributeValue("value").length() - 1)
                    + "\"" + ");"
            );
          }
          if (property.attributeValue("name").equals("keepAliveSeconds")) {
            //insuranceTaskExecutor.setCorePoolSize(fileUtil.getPropertiesInt(""));
            System.out.println(id + "." + "setKeepAliveSeconds"
                    + "(fileUtil.getPropertiesInt(" + "\"" + property.attributeValue("value").substring(2, property.attributeValue("value").length() - 1)
                    + "\"" + ");"
            );
          }
          if (property.attributeValue("name").equals("queueCapacity")) {
            //insuranceTaskExecutor.setCorePoolSize(fileUtil.getPropertiesInt(""));
            System.out.println(id + "." + "setQueueCapacity"
                    + "(fileUtil.getPropertiesInt(" + "\"" + property.attributeValue("value").substring(2, property.attributeValue("value").length() - 1)
                    + "\"" + ");"
            );
          }
        }
      }
    }
  }

  @Test
  public void test2() {
    Document doc = null;
    try {
      doc = DocumentHelper.parseText(xml);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    /**
     * <property name="corePoolSize" value="${insurance.taskexecutor.corePoolSize}" />
     <property name="maxPoolSize" value="${insurance.taskexecutor.maxPoolSize}" />
     <property name="keepAliveSeconds" value="${insurance.taskexecutor.keepAliveSeconds}" />
     <property name="queueCapacity" value="${insurance.taskexecutor.queueCapacity}" />
     */
    Element root = doc.getRootElement();
    for (Object ele : root.elements()) {
      Element element = (Element) ele;
      if (element.getName().equals("bean")) {
        String id = element.attributeValue("id");
        if (id.equals("rabbitPool")) {
          for (Object obj : element.elements()) {
            Element property = (Element) obj;

            //insuranceTaskExecutor.setCorePoolSize(fileUtil.getPropertiesInt(""));
            System.out.println(id + "." + "set" + property.attributeValue("name")
                    + "(fileUtil.getPropertiesInt(" + "\"" + property.attributeValue("value").substring(2, property.attributeValue("value").length() - 1)
                    + "\"" + "));"
            );

          }
        }

      }
    }
  }

  @Test
  public void test3() {
    String xml = "<bean id=\"mqcrDataSource\" class=\"org.logicalcobwebs.proxool.ProxoolDataSource\">\n" +
        "\t\t<property name=\"driver\">\n" +
        "\t\t\t<value>${jdbc_mqcr.driver-class}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"driverUrl\">\n" +
        "\t\t\t<value>${jdbc_mqcr.driver-url}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"user\">\n" +
        "\t\t\t<value>${jdbc_mqcr.user}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"password\">\n" +
        "\t\t\t<value>${jdbc_mqcr.password}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"alias\">\n" +
        "\t\t\t<value>${jdbc_mqcr.alias}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"prototypeCount\">\n" +
        "\t\t\t<value>${jdbc_mqcr.prototype-count}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"maximumConnectionCount\">\n" +
        "\t\t\t<value>${jdbc_mqcr.maximum-connection-count}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"minimumConnectionCount\">\n" +
        "\t\t\t<value>${jdbc_mqcr.minimum-connection-count}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"maximumActiveTime\">\n" +
        "\t\t\t<value>${jdbc_mqcr.maximum-active-time}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"trace\">\n" +
        "\t\t\t<value>${jdbc_mqcr.trace}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"verbose\">\n" +
        "\t\t\t<value>${jdbc_mqcr.verbose}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"statistics\">\n" +
        "\t\t\t<value>${jdbc_mqcr.statistics}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"houseKeepingTestSql\">\n" +
        "\t\t\t<value>${jdbc_mqcr.house-keeping-test-sql}</value>\n" +
        "\t\t</property>\n" +
        "\t\t<property name=\"simultaneousBuildThrottle\">\n" +
        "\t\t\t<value>${jdbc_mqcr.simultaneous-build-throttle}</value>\n" +
        "\t\t</property>\n" +
        "\t</bean>";

    Document doc = null;
    try {
      doc = DocumentHelper.parseText(xml);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    Element bean = doc.getRootElement();
    System.out.println(bean.attribute("id"));
    for (Object obj : bean.elements()) {
      Element property = (Element) obj;
      Element value = property.element("value");
      System.out.println("dataSource" + "." + "set" + property.attributeValue("name").substring(0, 1).toUpperCase() + property.attributeValue("name").substring(1)
              + "(fileUtil.getPropertiesInt(" + "\"" + value.getText().substring(2, value.getText().length() - 1)
              + "\"" + "));"
      );
    }
  }

//  @Test
  public void test4() {
    String str = "user=${jdbc_ticket.username},password=${jdbc_ticket.password}";
    str = String.format(str,"1","2");
    System.out.println(str);
  }
  
  public static void main(String[] args) throws UnsupportedEncodingException {
//    System.out.print(" 账号 ");
//    System.out.print(RandomUtil.randomString(16).toLowerCase());
//    System.out.print(" 密码 ");
//    System.out.println(RandomUtil.randomString(20));
//    System.out.println(new String(Base64Utils.decode("6LW16IGD")));
  }
}
