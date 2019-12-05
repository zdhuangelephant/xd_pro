package com.xiaodou.common.util.reflect;

import java.lang.reflect.Field;
import java.util.Map;

import sun.misc.Unsafe;

import com.google.common.collect.Maps;

/**
 * @Location xd-util/com.xiaodou.common.util.reflect.ReflectionField.java.java
 * @Description 反射工具类
 * @see Field#get(Object) 针对一组对象做批量处理时,{@link #getFieldVal(Object)}
 *      方法效率是前者的十倍,基本等同于直接调用变量
 * @Author <a href="mailto:zhaodan@corp.51xiaodou.com">zhadan</a>
 * @Date 下午1:55:56
 */
@SuppressWarnings("restriction")
public class ReflectionField<T> {

	private long offset = 0;

  private static final sun.misc.Unsafe unsafe;

	private static final Map<Field, Long> offsetMap = Maps.newConcurrentMap();

	static {
		sun.misc.Unsafe value = null;
		try {
			Class<?> clazz = Class.forName("sun.misc.Unsafe");
			Field field = clazz.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			value = (Unsafe) field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error to get theUnsafe", e);
		}
		unsafe = value;
	}

	public static final sun.misc.Unsafe getUnsafe() {
		return unsafe;
	}

	public ReflectionField(Class<T> clazz, Field field)
			throws NoSuchFieldException, SecurityException {
		clazz.getDeclaredField(field.getName());
		this.offset = getOffset0(field);
	}

	public ReflectionField(Class<T> clazz, String fieldName)
			throws NoSuchFieldException, SecurityException {
		Field field = clazz.getDeclaredField(fieldName);
		this.offset = getOffset0(field);
	}

	/**
	 * 针对一组对象做批量处理时比较高效的反射方法,通过预先获取offset值,反射效率为 {@link Field#get(Object)}方法的10倍
	 * 
	 * @param srcObj
	 * @return
	 */
	public final Object getFieldVal(T srcObj) {
		return unsafe.getObject(srcObj, this.offset);
	}

	/**
	 * 针对一组对象做批量处理时比较高效的反射方法,通过预先获取offset值,反射效率为
	 * {@link Field#set(Object, Object)}方法的10倍
	 * 
	 * @param srcObj
	 * @return
	 */
	public final void putField(T srcObj, Object dstObj) {
		unsafe.putObject(srcObj, this.offset, dstObj);
	}

	private static final Long getOffset0(Field field) {
		return unsafe.objectFieldOffset(field);
	}

	/**
	 * 比较低效的反射方法,反射效率为{@link Field#get(Object)}方法的1.5倍
	 * 
	 * @param srcObj
	 * @return
	 */
	public static Object getField(Field argument, Object vo) {
		Long offset = offsetMap.get(argument);
		if (null != offset)
			return unsafe.getObject(vo, offset);
		offset = getOffset0(argument);
		offsetMap.put(argument, offset);
		return unsafe.getObject(vo, offset);
	}

}
