package com.xiaodou.oms.statemachine.engine.model;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;

import com.xiaodou.summer.view.velocity.TemplateUtil;

/**
 * <p>
 * Template模板类
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月16日
 */
public class Template {

  private String name;
  private String file;
  private String pojo;
  public String getPojo() {
    return pojo;
  }

  public void setPojo(String pojo) {
    this.pojo = pojo;
  }

  private org.apache.velocity.Template template;

  public org.apache.velocity.Template getTemplate() {
    return template;
  }

  public void setTemplate(org.apache.velocity.Template template) {
    this.template = template;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public void initTemplate(TemplateUtil templateUtil) {
    template = templateUtil.getTemplate(file);
  }

  public void initDocument() throws TemplateInitException {
    template.initDocument();
  }

  public void merge(Context context, Writer writer) throws ResourceNotFoundException,
      ParseErrorException, MethodInvocationException, IOException {
    template.merge(context, writer);
  }

  public void process() throws ResourceNotFoundException, ParseErrorException, IOException {
    template.process();
  }
}
