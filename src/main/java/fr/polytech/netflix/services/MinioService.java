package fr.polytech.netflix.services;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class MinioService {

    private final Environment env;
    private final MinioClient minioClient;

    public MinioService(Environment env) {
        this.env = env;
        this.minioClient =
                MinioClient.builder()
                        .endpoint(Objects.requireNonNull(env.getProperty("s3.url")))
                        .credentials(Objects.requireNonNull(env.getProperty("s3.accessKey")), Objects.requireNonNull(env.getProperty("s3.secretKey")))
                        .build();
    }

    public String getUploadUrl(String fileName, String bucket) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs
                            .builder()
                            .method(Method.PUT)
                            .object(fileName)
                            .bucket(bucket)
                            .expiry(60 * 60 * 24)
                            .build()
            );
        } catch (IOException | ErrorResponseException | InsufficientDataException | InternalException |
                 InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException | XmlParserException |
                 ServerException e) {
            throw new RuntimeException(e);
        }
    }
    public String getDownloadUrl(String name, String bucket) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs
                            .builder()
                            .method(Method.GET)
                            .object(name)
                            .bucket(bucket)
                            .expiry(60 * 60 * 24)
                            .build()
            );
        } catch (IOException | ErrorResponseException | InsufficientDataException | InternalException |
                 InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException | XmlParserException |
                 ServerException e) {
            throw new RuntimeException(e);
        }
    }
}
