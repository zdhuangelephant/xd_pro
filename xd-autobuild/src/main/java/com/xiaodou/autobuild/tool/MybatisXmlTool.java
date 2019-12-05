package com.xiaodou.autobuild.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.contant.AutobuildContants;
import com.xiaodou.common.util.StringUtils;


public class MybatisXmlTool {
  // private final static String ENV_PATH = "conf/custom/notenv";
  // private final static String FILE_NAME = "mybatis_temp.vm";
  private String output = "";
  private String tableName;
  private Class<?> entityClass;
  private MybatisMapping majorColumn = null;
  private LinkedList<MybatisMapping> columnList = new LinkedList<>();
  // private Map<String, String> mergeObj = Maps.newHashMap();
  private String mergeObj = AutobuildContants.TMP;
  private Integer wait = 0;
  private Integer building = 1;
  private Integer builded = 2;
  private AtomicInteger buildStatus = new AtomicInteger(wait);

  private MybatisXmlTool(Class<?> entityClass, String tableName, String output) {
    this.entityClass = entityClass;
    Xml annotation = entityClass.getAnnotation(Xml.class);
    if (annotation != null) {
      if (StringUtils.isNotBlank(annotation.tableName())) this.tableName = annotation.tableName();
      if (StringUtils.isNotBlank(annotation.outputDir())) this.output = annotation.outputDir();
    }
    if (null != tableName) this.tableName = tableName;
    if (null != output) this.output = output;
    if (StringUtils.isBlank(this.tableName)) throw new RuntimeException("目标表名不能为空");
    init();
  }

  public static final MybatisXmlTool getInstance(Class<?> entityClass) {
    return new MybatisXmlTool(entityClass, null, null);
  }

  public static final MybatisXmlTool getInstance(Class<?> entityClass, String tableName) {
    return new MybatisXmlTool(entityClass, tableName, null);
  }

  public static final MybatisXmlTool getInstance(Class<?> entityClass, String tableName,
      String output) {
    return new MybatisXmlTool(entityClass, tableName, output);
  }

  private void init() {
    if (null == entityClass) return;
    for (Field f : entityClass.getDeclaredFields()) {
      f.setAccessible(true);
      Column column = f.getAnnotation(Column.class);
      if (null != column && !column.persistent()) continue;
      MybatisMapping mapping = new MybatisMapping();
      String name = f.getName();
      String _name = StringUtils.EMPTY;
      for (int i = 0; i < name.length(); i++) {
        if (Character.isUpperCase(name.charAt(i)))
          _name += "_" + Character.toLowerCase(name.charAt(i));
        else
          _name += name.charAt(i);
      }
      if (null != column) mapping.setMajor(column.isMajor());
      if (null != column) mapping.setAutoIncrement(column.autoIncrement());
      if (null != column) mapping.setBetweenScope(column.betweenScope());
      if (null != column) mapping.setListValue(column.listValue());
      if (null != column) mapping.setLikeValue(column.likeValue());
      if (null != column) mapping.setSortBy(column.sortBy());
      if (null != column) mapping.setCanUpdate(column.canUpdate());
      mapping.setProperty(name);
      mapping.setColumn(_name);
      if (null != column && StringUtils.isNotBlank(column.name()))
        mapping.setColumn(column.name());
      mapping.setJavaType(f.getType().getSimpleName());
      mapping.setJdbcType(getType(f.getType()));
      if (mapping.isMajor) majorColumn = mapping;
      columnList.add(mapping);
    }
  }

  private enum SqlType {
    nameSpace() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        return xmlTool.entityClass.getSimpleName();
      }
    },
    baseResultMap() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        StringBuilder builder = new StringBuilder(200);
        for (MybatisMapping mapping : xmlTool.columnList) {
          if (mapping.isMajor)
            builder.append(String.format("<id column=\"%s\" property=\"%s\" jdbcType=\"%s\" />\n",
                mapping.column, mapping.property, mapping.jdbcType));
          else
            builder.append(String.format(
                "<result column=\"%s\" property=\"%s\" jdbcType=\"%s\" />\n", mapping.column,
                mapping.property, mapping.jdbcType));
        }
        return builder.toString();
      }
    },
    tableName() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        return xmlTool.tableName;
      }
    },
    majorKeyQuery() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        return String.format("%s = #\\{%s,jdbcType=%s\\}\n", xmlTool.majorColumn.column,
            xmlTool.majorColumn.property, xmlTool.majorColumn.jdbcType);
      }
    },
    autoIncrement() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        StringBuilder builder = new StringBuilder(200);
        for (MybatisMapping mapping : xmlTool.columnList) {
          if (mapping.isMajor && mapping.autoIncrement)
            builder.append(String.format("useGeneratedKeys=\"true\" keyProperty=\"%s\"",
                mapping.property));
        }
        return builder.toString();
      }

    },
    fullPath() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        return xmlTool.entityClass.getName();
      }
    },
    baseColumn() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        StringBuilder builder = new StringBuilder(200);
        for (MybatisMapping mapping : xmlTool.columnList) {
          builder.append(mapping.getColumn()).append(", ");
        }
        return builder.substring(0, builder.length() - 2).toString();
      }
    },
    insertColumn() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        StringBuilder builder = new StringBuilder(200);
        for (MybatisMapping mapping : xmlTool.columnList) {
          builder.append(String.format("<if test=\"%s != null\">%s,</if>\n", mapping.property,
              mapping.column));
        }
        return builder.toString();
      }
    },
    insertValue() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        StringBuilder builder = new StringBuilder(200);
        for (MybatisMapping mapping : xmlTool.columnList) {
          builder.append(String.format("<if test=\"%s != null\">#\\{%s,jdbcType=%s\\},</if>\n",
              mapping.property, mapping.property, mapping.jdbcType));
        }
        return builder.toString();
      }
    },
    updateValue() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        StringBuilder builder = new StringBuilder(200);
        for (MybatisMapping mapping : xmlTool.columnList) {
          if (mapping.isMajor || !mapping.canUpdate) continue;
          builder
              .append(String
                  .format(
                      "<if test=\"value != null and value.%s != null\">%s = #\\{value.%s,jdbcType=%s\\},</if>\n",
                      mapping.property, mapping.column, mapping.property, mapping.jdbcType));
        }
        return builder.toString();
      }
    },
    dynamicOutput() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        StringBuilder builder = new StringBuilder(200);
        for (MybatisMapping mapping : xmlTool.columnList) {
          builder.append(String.format(
              "<if test=\"output != null and output.%s != null\">%s,</if>\n", mapping.property,
              mapping.column));
        }
        return builder.toString();
      }
    },
    dynamicWhere() {
      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        StringBuilder builder = new StringBuilder(200);
        for (MybatisMapping mapping : xmlTool.columnList) {
          if (mapping.betweenScope) {
            builder
                .append(String
                    .format(
                        "<if test=\"input != null and input.%sLower != null\">and %s &gt; #\\{input.%sLower\\}</if>\n",
                        mapping.property, mapping.column, mapping.property));
            builder
                .append(String
                    .format(
                        "<if test=\"input != null and input.%sUpper != null\">and %s &lt; #\\{input.%sUpper\\}</if>\n",
                        mapping.property, mapping.column, mapping.property));

          } else {
            builder.append(String.format(
                "<if test=\"input != null and input.%s != null\">and %s=#\\{input.%s\\}</if>\n",
                mapping.property, mapping.column, mapping.property));
          }
          if (mapping.listValue) {
            builder
                .append(String
                    .format(
                        "<if test=\"input != null and input.%sList != null\">and %s in \n"
                            + "<foreach collection=\"input.%sList\" open=\"(\" close=\")\" separator=\",\" item=\"item\">#\\{item\\}</foreach>\n"
                            + "</if>\n", mapping.property, mapping.column, mapping.property));
          }
          if (mapping.likeValue) {
            builder
                .append(String
                    .format(
                        "<if test=\"input != null and input.%sLike != null\">and %s like concat('%%',#\\{input.%sLike\\},'%%')</if>\n",
                        mapping.property, mapping.column, mapping.property));
          }
        }
        return builder.toString();
      }
    },
    dynamicSort() {

      @Override
      public String getSql(MybatisXmlTool xmlTool) {
        StringBuilder builder = new StringBuilder(200);
        for (MybatisMapping mapping : xmlTool.columnList) {
          if (mapping.sortBy) {
            builder
                .append(String
                    .format(
                        "<if test=\"sort != null and sort.%s != null and ( sort.%s == 'ASC' or sort.%s == 'DESC' )\">%s \\$\\{sort.%s\\},</if>\n",
                        mapping.property, mapping.property, mapping.property, mapping.column,
                        mapping.property));
          }
        }
        return builder.toString();
      }

    };
    SqlType() {}

    public abstract String getSql(MybatisXmlTool xmlTool);
  }

  private void buildMybatisXml() {
    if (buildStatus.compareAndSet(wait, building)) {
      for (SqlType sqlType : SqlType.values()) {
        // mergeObj.put(sqlType.toString(), sqlType.getSql(this));
        mergeObj = mergeObj.replaceAll("##" + sqlType.toString(), sqlType.getSql(this));
      }
      buildStatus.set(builded);
    }
  }

  /*private String parse2Xml() {
    Template template = TemplateUtil.getTemplate(ENV_PATH, FILE_NAME);
    StringWriter writer = new StringWriter();
    VelocityContext context = new VelocityContext();
    context.put("mergeObj", mergeObj);
    template.merge(context, writer);
    String res = writer.toString();
    return res;
  }*/

  public void buildXml() {
    buildMybatisXml();
    File xmlFile = null;
    if (StringUtils.isNotBlank(output))
      xmlFile = new File(new File(output), entityClass.getSimpleName() + ".xml");
    else
      xmlFile = new File(entityClass.getSimpleName() + ".xml");
    try (BufferedWriter writer =
        new BufferedWriter(new OutputStreamWriter(new FileOutputStream(xmlFile)))) {
      // writer.write(parse2Xml());
      writer.write(mergeObj);
      writer.flush();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createTable() {

  }

  public static String getType(Class<?> fieldType) {
    if (fieldType.equals(Integer.class)) return "INTEGER";
    if (fieldType.equals(String.class)) return "VARCHAR";
    if (fieldType.equals(Double.class)) return "DOUBLE";
    if (fieldType.equals(Long.class)) return "BIGINT";
    if (fieldType.equals(Short.class)) return "TINYINT";
    if (fieldType.equals(Boolean.class)) return "VARCHAR";
    if (fieldType.equals(Character.class)) return "VARCHAR";
    if (fieldType.equals(Float.class)) return "DOUBLE";
    if (fieldType.equals(Timestamp.class)) return "TIMESTAMP";
    return "VARCHAR";
  }

  @Data
  public static class MybatisMapping {
    private boolean isMajor = false;
    private boolean autoIncrement = false;
    private boolean betweenScope = false;
    private boolean listValue = false;
    private boolean likeValue = false;
    private boolean sortBy = true;
    private boolean canUpdate = true;
    private String column;
    private String property;
    private String javaType;
    private String jdbcType;
  }

}
