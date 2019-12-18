package middleware.apachecommons.lang3;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;

/**
 * @Title: SystemUtilsDemo
 */
public class SystemUtilsDemo {
    public static void main(String[] args) {
        boolean isJava11 = SystemUtils.IS_JAVA_11;
        System.out.println(isJava11);

        File userDir = SystemUtils.getUserDir();
        System.out.println(userDir.getPath());

        File javaHome = SystemUtils.getJavaHome();
        System.out.println(javaHome.getPath());

    }
}
