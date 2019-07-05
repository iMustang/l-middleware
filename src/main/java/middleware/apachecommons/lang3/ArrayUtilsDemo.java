package middleware.apachecommons.lang3;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * @Title: ArrayUtilsDemo
 */
public class ArrayUtilsDemo {
	public static void main(String[] args) {
		int[] a = {1, 2, 3, 4};
		int[] b = ArrayUtils.add(a, 6);
		System.out.println(ArrayUtils.toString(b));

		int[] c = {1, 2, 3};
		int[] d = {4, 5, 6};
		int[] e = ArrayUtils.addAll(c, d);
		System.out.println(ArrayUtils.toString(e));

		int[] f = {1, 3, 5};
		int[] g = ArrayUtils.clone(f);
		System.out.println(ArrayUtils.toString(g));
		System.out.println(f == g);

		int[] h = {1, 2, 3};
		boolean contains = ArrayUtils.contains(h, 3);
		System.out.println(contains);

		int[] i = {1, 2, 3};
		int length = ArrayUtils.getLength(i);
		System.out.println(length);

		int[] j = {1, 2, 3};
		int index = ArrayUtils.indexOf(j, 2);
		System.out.println(index);

		int[] k = {1, 2, 3};
		int[] l = ArrayUtils.insert(2, k, 5);
		System.out.println(ArrayUtils.toString(l));

		System.out.println(ArrayUtils.isEmpty(k));
		System.out.println(ArrayUtils.isSameLength(k, l));
		System.out.println(ArrayUtils.isSorted(k));
		System.out.println(ArrayUtils.lastIndexOf(k, 3));

		int[] m = null;
		System.out.println(Arrays.toString(m));
		int[] n = ArrayUtils.nullToEmpty(m);
		System.out.println(Arrays.toString(n));

		int[] o = ArrayUtils.remove(l, 2);
		System.out.println(ArrayUtils.toString(o));

		Object[] p = {new Integer(1), new Integer(2)};
		System.out.println(ArrayUtils.toStringArray(p));


	}
}
