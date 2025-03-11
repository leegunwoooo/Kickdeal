package kick.kickdeal.service;

import io.minio.errors.MinioException;
import kick.kickdeal.dto.ProductResponseDTO;
import kick.kickdeal.dto.ProductRequestDTO;
import kick.kickdeal.entity.Product;
import kick.kickdeal.entity.User;
import kick.kickdeal.repository.ProductRepository;
import kick.kickdeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final MinioService minioService;

    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        // 현재 로그인한 사용자 닉네임을 가져옵니다.
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();

        // 사용자의 정보 가져오기
        User seller = userRepository.findById(nickname);

        // Image파일 가져와서 minio에 올리고 ImageUrl 반환
        String imageUrl = minioService.uploadFile(productRequestDTO.getImage());

        // Product 객체 생성
        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .user(seller)
                .category(productRequestDTO.getCategory())
                .imageUrl(imageUrl)
                .build();

        // 상품 저장
        product = productRepository.save(product);

        // 저장된 상품을 DTO로 변환하여 반환
        return new ProductResponseDTO(product);
    }

    @Transactional
    public Product update(Long id, ProductRequestDTO productRequestDTO) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {

        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id: " + id));

        if (!product.getUser().getId().equals(nickname)) {
            throw new IllegalArgumentException("본인만 상품정보를 수정할 수 있습니다. id: " + id);
        }

        product.update(
                productRequestDTO.getName(),
                productRequestDTO.getDescription(),
                productRequestDTO.getPrice(),
                productRequestDTO.getCategory()
        );

        return product;
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id: " + id));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void delete(Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id: " + id));

       if(!product.getUser().getId().equals(username)) {
            throw new IllegalArgumentException("본인만 상품정보를 수정할 수 있습니다. id: " + id);
       }

        productRepository.deleteById(id);
    }
}

