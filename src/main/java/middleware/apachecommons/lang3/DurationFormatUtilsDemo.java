package middleware.apachecommons.lang3;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.util.Date;

/**
 * DurationFormatUtilsDemo
 * description:
 */
public class DurationFormatUtilsDemo {
	public static void main(String[] args) {
		Date date = new Date();
		Date after = DateUtils.addDays(date, 35);
		String difference = DurationFormatUtils.formatPeriod(date.getTime(), after.getTime(), "d");
		System.out.println(difference);
	}
}
