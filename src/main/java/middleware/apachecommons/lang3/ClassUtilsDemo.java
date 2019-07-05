package middleware.apachecommons.lang3;

import org.apache.commons.lang3.ClassUtils;

/**
 * @Title: ClassUtilsDemo
 */
public class ClassUtilsDemo {
	public static void main(String[] args) {
		ClassUtils.getAllSuperclasses(ClassUtilsDemo.class);
		ClassUtils.getName(ClassUtilsDemo.class);
	}
}
