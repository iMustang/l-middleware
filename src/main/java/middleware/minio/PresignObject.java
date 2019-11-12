package middleware.minio;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.NoResponseException;

/**
 * PresignObject
 * description:
 */
public class PresignObject {
	private static String MINIO_URL = "http://192.168.121.130:9000";
	private static String ACCESS_KEY = "root";
	private static String SECRET_KEY = "rootroot";

	public static void main(String[] args) throws InvalidPortException, InvalidEndpointException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidExpiresRangeException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
		MinioClient minioClient = new MinioClient(MINIO_URL, ACCESS_KEY, SECRET_KEY);

		String bucketName = "tmp";
		String objectName = "/1/a.txt";
		String url = minioClient.presignedGetObject(bucketName, objectName, 60 * 60);
		System.out.println("url:" + url);
	}
}
