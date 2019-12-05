package com.xiaodou.docplugin.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.ParserConfig;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class ResourseResoler{

	ParserConfig parserConfig = ParserConfig.getGlobalInstance();
	public ResourseResoler() {
		// entityClassesCache = new ArrayList<EntityClass>();
	}

	private boolean isBasicType(Class<?> clazz) {
		if(clazz.getName().startsWith("java.util") || clazz.getName().startsWith("java.lang"))
			return true;
		return parserConfig.isPrimitive(clazz);
	}

	public JSONObject getInitJsonObject(Class<?> clazz, JSONObject jsonObject,
			boolean isGetParent) {
		JSONObject reusltJsonObject = isGetParent ? jsonObject
				: new JSONObject();
		Class<?> parentClass = clazz.getSuperclass();

		if (parentClass != null && !isBasicType(parentClass)) {
			reusltJsonObject = getInitJsonObject(parentClass, reusltJsonObject, true);
		}

		if(isBasicType(clazz)){
			Object defaultValue = "";
			String key = clazz.getSimpleName();
			reusltJsonObject.put(key, defaultValue);

			return reusltJsonObject;
		}
		
		Field fields[] = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()))
				continue;
			if (field.getType().equals(Map.class)
					|| field.getType().equals(Dictionary.class))
				continue;
			
			String key = field.getName();
			if (field.isAnnotationPresent(JSONField.class)
					&& !field.getAnnotation(JSONField.class).name().equals(""))
				key = field.getAnnotation(JSONField.class).name();
						
			Class<?> baseType = field.getType();

			boolean isArray = false;
			// 泛型，目前当做list处理
			java.lang.reflect.Type type = field.getGenericType();
			if (type instanceof ParameterizedType) {
				isArray = true;
				baseType = (Class<?>) ((ParameterizedType) type)
						.getActualTypeArguments()[0];
			}

			// 数组
			if (field.getType().isArray()) {
				isArray = true;
				baseType = field.getType().getComponentType();
			}

			if (isBasicType(baseType) || field.getType().isEnum()) {
				Object defaultValue = null;
				if (defaultValue == null
						|| defaultValue.toString().equals(""))
					defaultValue = getDefaultValue(baseType, key);

				if (isArray) {
					reusltJsonObject.put(key, new Object[] { defaultValue });
				} else {
					reusltJsonObject.put(key, defaultValue);
				}
			} else {
				JSONObject compomentoJsonObject = getInitJsonObject(baseType,
						reusltJsonObject, false);
				if (isArray) {
					reusltJsonObject.put(key,
							new Object[] { compomentoJsonObject });
				} else {
					reusltJsonObject.put(key, compomentoJsonObject);
				}
			}
		}
		return reusltJsonObject;
	}

	private Object getDefaultValue(Class<?> objectClass, String name) {

		Object value = null;
		if (name.equalsIgnoreCase("cardno") || name.equalsIgnoreCase("emmbership")) {
			value = "49";
		} else if (objectClass.isEnum()) {
			value = 0;
		} else if (name.equalsIgnoreCase("pagesize")) {
			value = 15;
		} else if (name.toLowerCase().contains("cityid")) {
			value = "0101";
		} else if (objectClass == boolean.class || objectClass == Boolean.class) {
			value = true;
		} else if (objectClass == char.class || objectClass == Character.class) {
			value = 'a';
		} else if (objectClass == byte.class || objectClass == Byte.class) {
			value = 0;
		} else if (objectClass == short.class || objectClass == Short.class) {
			value = 0;
		} else if (objectClass == int.class || objectClass == Integer.class) {
			value = 0;
		} else if (objectClass == long.class || objectClass == Long.class) {
			value = 0;
		} else if (objectClass == float.class || objectClass == Float.class) {
			value = 0.0;
		} else if (objectClass == double.class || objectClass == Double.class) {
			value = 0.00;
		} else if (objectClass == BigInteger.class) {
			value = 0;
		} else if (objectClass == Number.class) {
			value = 0.00;
		} else if (objectClass == BigDecimal.class) {
			value = 0.00;
		} else if (objectClass == String.class) {
			value = "";
		} else if (objectClass == Date.class || objectClass == Calendar.class
				|| objectClass.getSuperclass() == Calendar.class || objectClass == Timestamp.class) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			if (name.toLowerCase().contains("checkout")
					|| name.toLowerCase().contains("leave")) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				value = formatter.format(calendar.getTime());
			} else if(name.equalsIgnoreCase("EndSaleDate")){
				value="2099-12-31";
			}else if(name.equalsIgnoreCase("StartSaleDate")){
				value="2000-01-01";
			}else {
				value = formatter.format(date);
			}
		} else {
			value = "";
		}
		return value;
	}
}