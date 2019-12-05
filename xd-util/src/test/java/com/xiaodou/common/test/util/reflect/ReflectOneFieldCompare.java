package com.xiaodou.common.test.util.reflect;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.xiaodou.common.util.reflect.ReflectionField;

public class ReflectOneFieldCompare {
	private static final int count = 10000000;

	/**
	 * @param args
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static void main(String[] args) throws NoSuchFieldException,
			SecurityException {
		long duration = testGetCommon();
		System.out.println("int common get for  " + count
				+ " times, duration: " + duration);
		duration = testGetUnsafe();
		System.out.println("int unsafe get for  " + count
				+ " times, duration: " + duration);
		duration = testGetAge();
		System.out.println("int getage method for " + count
				+ " times, duration: " + duration);
		duration = testSetCommon();
		System.out.println("int common set for  " + count
				+ " times, duration: " + duration);
		duration = testSetUnsafe();
		System.out.println("int unsafe set for  " + count
				+ " times, duration: " + duration);
		duration = testSetAge();
		System.out.println("int setage method for " + count
				+ " times, duration: " + duration);
	}

	private static long testGetAge() {
		long start = System.currentTimeMillis();
		int temp = count;
		while (temp-- > 0) {
			try {
				new TestBean().getAge();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return System.currentTimeMillis() - start;
	}

	private static long testSetAge() {
		long start = System.currentTimeMillis();
		int temp = count;
		while (temp-- > 0) {
			try {
				new TestBean().setAge(123);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return System.currentTimeMillis() - start;
	}

	private static long testGetUnsafe() throws NoSuchFieldException,
			SecurityException {
		long start = System.currentTimeMillis();
		int temp = count;
		ReflectionField<TestBean> reflect = new ReflectionField<TestBean>(
				TestBean.class, "age");
		while (temp-- > 0) {
			 reflect.getFieldVal(new TestBean());
		}
		return System.currentTimeMillis() - start;
	}

	private static long testSetUnsafe() throws NoSuchFieldException,
			SecurityException {
		long start = System.currentTimeMillis();
		int temp = count;
		ReflectionField<TestBean> reflect = new ReflectionField<TestBean>(
				TestBean.class, "age");
		while (temp-- > 0) {
			reflect.putField(new TestBean(), 123);
		}
		return System.currentTimeMillis() - start;
	}

	private static long testGetCommon() throws SecurityException,
			NoSuchFieldException {
		long start = System.currentTimeMillis();
		int temp = count;
		Field declaredField = TestBean.class.getDeclaredField("age");
		declaredField.setAccessible(true);
		while (temp-- > 0) {
			try {
				declaredField.get(new TestBean());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return System.currentTimeMillis() - start;
	}

	private static long testSetCommon() throws SecurityException,
			NoSuchFieldException {
		long start = System.currentTimeMillis();
		int temp = count;
		Field declaredField = TestBean.class.getDeclaredField("age");
		declaredField.setAccessible(true);
		while (temp-- > 0) {
			try {
				declaredField.set(new TestBean(), 123);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return System.currentTimeMillis() - start;
	}
	
	static interface ITestBean extends Serializable{
	}

	/**
	 * @author haitao.yao Dec 14, 2010
	 */
	static class TestBean implements Serializable {
		/**
			 * 
			 */
		private static final long serialVersionUID = -5994966479456252766L;

		private String name;
		private int age;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the age
		 */
		public int getAge() {
			return age;
		}

		/**
		 * @param age
		 *            the age to set
		 */
		public void setAge(int age) {
			this.age = age;
		}
	}
}
