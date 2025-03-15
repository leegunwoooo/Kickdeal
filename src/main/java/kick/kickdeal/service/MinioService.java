package kick.kickdeal.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import kick.kickdeal.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class MinioService {
    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadFile(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("KickDeal")
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket("KickDeal")
                            .object(fileName)
                            .method(Method.GET)
                            .build()
            );
        } catch(MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new BaseException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "MINIO_ERROR",
                    "파일 업로드 중 오류 발생",
                    e
            );
        }
    }
}
