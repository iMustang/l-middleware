package middleware.apachecommons.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Title: FileUtilsDemo
 */
public class FileUtilsDemo {
	public static void main(String[] args) throws IOException {
		File file = FileUtils.getFile("F:\\iRepo\\apachecommons\\src\\main\\resource\\test.txt");
		FileInputStream fileInputStream = FileUtils.openInputStream(file);
		String string = IOUtils.toString(fileInputStream, Charset.forName("UTF-8"));
		System.out.println(string);

		String string1 = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
		System.out.println(string1);
	}
}
