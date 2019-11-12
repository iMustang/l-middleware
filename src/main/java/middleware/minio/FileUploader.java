package middleware.minio;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.NoResponseException;
import io.minio.errors.RegionConflictException;

/**
 * FileUploader
 * description:Minio文件上传
 */
public class FileUploader {
	private static String MINIO_URL = "http://192.168.121.130:9000";
	private static String ACCESS_KEY = "root";
	private static String SECRET_KEY = "rootroot";

	public static void main(String[] args) throws InvalidPortException, InvalidEndpointException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, RegionConflictException, InvalidArgumentException {
		MinioClient minioClient = new MinioClient(MINIO_URL, ACCESS_KEY, SECRET_KEY);

		String bucketName = "tmp";

		boolean exists = minioClient.bucketExists(bucketName);
		if (!exists) {
			minioClient.makeBucket(bucketName);
		}

		String objectName = "/1/a.txt";
		minioClient.putObject(bucketName, objectName, "d:\\a.txt");
	}
}
