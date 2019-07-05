package middleware.apachecommons.lang3;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @Title: DateUtilsDemo
 */
public class DateUtilsDemo {
	public static void main(String[] args) throws ParseException {
		Date now = new Date();
		Date date = DateUtils.addDays(now, 1);
		String format = DateFormatUtils.format(date, "MM-dd HH:mm:ss");
		System.out.println(format);

		Date date1 = DateUtils.parseDate("12-02-23", "yy-MM-dd");
		System.out.println(date1);

	}
}
