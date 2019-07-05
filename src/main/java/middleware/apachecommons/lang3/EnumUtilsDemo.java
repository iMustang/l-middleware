package middleware.apachecommons.lang3;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;

/**
 * @Title: EnumUtilsDemo
 */
public class EnumUtilsDemo {
	public static void main(String[] args) {
		List<Season> enumList = EnumUtils.getEnumList(Season.class);
		System.out.println(enumList);
		Season spring = EnumUtils.getEnum(Season.class, "SPRING");
		System.out.println(spring.name());
	}

	enum Season {
		SPRING, SUMMER, AUTORM, WINTER;
	}
}
