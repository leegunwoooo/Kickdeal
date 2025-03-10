package kick.kickdeal.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import kick.kickdeal.dto.ProductUploadDTO;
import org.springframework.stereotype.Service;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.errors.*; // Minio 관련 예외
import io.minio.http.Method;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class MinioService {

    private final MinioClient minioClient;
    private static final String DEFAULT_BUCKET = "KickDeal";

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadFile(ProductUploadDTO productUploadDTO) throws IOException, MinioException {
        InputStream inputStream = productUploadDTO.getImage().getInputStream();
        String fileName = UUID.randomUUID() + "_" + productUploadDTO.getImage().getOriginalFilename();


        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(DEFAULT_BUCKET)
                        .object(fileName)
                        .stream(inputStream, productUploadDTO.getImage().getSize(), -1)
                        .contentType(productUploadDTO.getImage().getContentType())
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
