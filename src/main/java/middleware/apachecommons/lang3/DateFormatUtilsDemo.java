package middleware.apachecommons.lang3;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @Title: DateFormatUtilsDemo
 */
public class DateFormatUtilsDemo {
	public static void main(String[] args) {
		long l = System.currentTimeMillis();
		String format = DateFormatUtils.format(l, "yy-MM-dd HH:mm:ss");
		System.out.println(format);
	}
}
