package middleware.apachecommons.io;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @Title: IOUtilsDemo
 */
public class IOUtilsDemo {
    public static void main(String[] args) {
        try (InputStream resourceAsStream = IOUtilsDemo.class.getResourceAsStream("/test.txt")) {
            String string = IOUtils.toString(resourceAsStream, Charset.forName("UTF-8"));
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
