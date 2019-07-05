package middleware.apachecommons.io;

import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.util.Arrays;

/**
 * @Title: SuffixFileFilterDemo
 */
public class SuffixFileFilterDemo {
	public static void main(String[] args) {
		File dict = new File(".");
		String absolutePath = dict.getAbsolutePath();
		System.out.println(absolutePath);
		String[] list = dict.list();
		System.out.println(Arrays.toString(list));
		String[] ios = dict.list(new SuffixFileFilter(".xml"));
		System.out.println(Arrays.toString(ios));
	}
}
