package middleware.apachecommons.lang3;

import org.apache.commons.lang3.StringUtils;

/**
 * @Title: StringUtilsDemo
 */
public class StringUtilsDemo {
    public static void main(String[] args) {
        String str = StringUtils.trim("dd  ");
        System.out.println("'" + str + "'");

        String value = null;
        processStr(value);

    }

    static boolean processStr(String input) {
	    boolean a = StringUtils.equals(input, "a");
	    boolean blank = StringUtils.isBlank(input);
	    if ("a".equals(input)) {
            return true;
        }
        return false;
    }
}
