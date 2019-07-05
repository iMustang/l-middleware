package middleware.apachecommons.lang3;

import org.apache.commons.lang3.ObjectUtils;

/**
 * @Title: ObjectUtilsDemo
 * @Description:
 * @Author: xMustang
 * @Version: 1.0
 * @create: 2019-06-24 00:14
 */
public class ObjectUtilsDemo {
	public static void main(String[] args) {
		ObjectUtilsDemo a = null;
		ObjectUtilsDemo b = null;
		ObjectUtilsDemo[] c = {a, b};
		ObjectUtils.allNotNull(c);

		Integer clone = ObjectUtils.clone(new Integer(1));

	}
}
