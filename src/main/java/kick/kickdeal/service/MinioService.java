package kick.kickdeal.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.errors.*; // Minio 관련 예외
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
    private static final String DEFAULT_BUCKET = "KickDeal";

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadFile(MultipartFile file) throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        InputStream inputStream = file.getInputStream();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();


        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(DEFAULT_BUCKET)
                        .object(fileName)
                        .stream(inputStream, file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        // Presigned URL 생성 (파일 다운로드 가능)
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(DEFAULT_BUCKET)
                        .object(fileName)
                        .method(Method.GET) // HTTP GET 요청용 Presigned URL
                        .build()
        );
    }

}
