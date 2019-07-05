package middleware.apachecommons.lang3;

import org.apache.commons.lang3.BooleanUtils;

/**
 * @Title: BooleanUtilsDemo
 */
public class BooleanUtilsDemo {
	public static void main(String[] args) {
		boolean[] a = {false, true};
		BooleanUtils.and(a);

		BooleanUtils.compare(false, true);
		BooleanUtils.isFalse(false);
		BooleanUtils.negate(false);

		BooleanUtils.toBoolean(1);
		BooleanUtils.toInteger(false);

		BooleanUtils.toStringOnOff(true);
		BooleanUtils.toStringTrueFalse(false);
		BooleanUtils.toStringYesNo(false);

		BooleanUtils.xor(a);
	}


}
