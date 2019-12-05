package com.xiaodou.autobuild.contant;

public interface AutobuildContants {
  public static String TMP =
      "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
          + "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >"
          + "<mapper namespace=\"##nameSpace\">"
          + "    <resultMap id=\"BaseResultMap\" type=\"##fullPath\">"
          + "        ##baseResultMap"
          + "    </resultMap>"
          + "    <sql id=\"base_column\">##baseColumn</sql>"
          + "    <select id=\"findEntityById\" resultMap=\"BaseResultMap\""
          + "        parameterType=\"##fullPath\">"
          + "        select"
          + "        <include refid=\"base_column\" />"
          + "        from ##tableName"
          + "        where ##majorKeyQuery"
          + "    </select>"
          + "    <select id=\"findEntityListByCond\" resultMap=\"BaseResultMap\""
          + "        parameterType=\"java.util.Map\">"
          + "        select"
          + "        <include refid=\"dynamic_output\" />"
          + "        from ##tableName"
          + "        <include refid=\"dynamic_where\" />"
          + "        <include refid=\"dynamic_sort\" />"
          + "        <include refid=\"dynamic_limit\" />"
          + "    </select>"
          + "    <insert id=\"addEntity\" parameterType=\"##fullPath\" ##autoIncrement >"
          + "        insert into ##tableName"
          + "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">"
          + "            ##insertColumn"
          + "        </trim>"
          + "        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">"
          + "            ##insertValue"
          + "        </trim>"
          + "    </insert>"
          + "    <update id=\"updateEntityById\" parameterType=\"##fullPath\">"
          + "        update ##tableName"
          + "        <set>"
          + "            ##updateValue"
          + "        </set>"
          + "        where ##majorKeyQuery"
          + "    </update>"
          + "    <update id=\"updateEntity\" parameterType=\"##fullPath\">"
          + "        update ##tableName"
          + "        <set>"
          + "            ##updateValue"
          + "        </set>"
          + "        <include refid=\"dynamic_where\" />"
          + "    </update>"
          + "    <update id=\"deleteEntityById\" parameterType=\"##fullPath\">"
          + "        delete from ##tableName"
          + "        where ##majorKeyQuery"
          + "    </update>"
          + "    <delete id=\"deleteEntity\" parameterType=\"java.util.Map\">"
          + "        delete from ##tableName"
          + "        <include refid=\"dynamic_where\" />"
          + "    </delete>"
          + "    <sql id=\"dynamic_output\">"
          + "        <trim suffixOverrides=\",\">"
          + "            ##dynamicOutput"
          + "        </trim>"
          + "    </sql>"
          + "    <sql id=\"dynamic_where\">"
          + "        <where>"
          + "            ##dynamicWhere"
          + "        </where>"
          + "    </sql>"
          + "    <sql id=\"dynamic_limit\">"
          + "        <if test=\"limit != null and limit.limitStart != null and limit.limitCount != null\">"
          + "            limit \\${limit.limitStart}, \\${limit.limitCount}" + "        </if>"
          + "    </sql>" + "    <sql id=\"dynamic_sort\">" + "        <trim suffixOverrides=\",\">"
          + "            <if test=\"sort !=null\">" + "                order by"
          + "            </if>" + "            ##dynamicSort" + "        </trim>" + "    </sql>"
          + "</mapper>";
}
