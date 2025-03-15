package kick.kickdeal.service;

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
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final MinioService minioService;

    public ProductResponseDTO save(ProductRequestDTO productRequestDTO, MultipartFile image) {
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();

        User seller = userRepository.findById(nickname);

        String imageUrl = minioService.uploadFile(image);

        Product product = Product.builder()
                    .name(productRequestDTO.name())
                    .description(productRequestDTO.description())
                    .price(productRequestDTO.price())
                    .user(seller)
                    .category(productRequestDTO.category())
                    .imageUrl(imageUrl)
                    .build();

        product = productRepository.save(product);

        return new ProductResponseDTO(product);

    }

    public Product update(Long id, ProductRequestDTO productRequestDTO) {

        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id: " + id));

        if (!product.getUser().getId().equals(nickname)) {
            throw new IllegalArgumentException("본인만 상품정보를 수정할 수 있습니다. id: " + id);
        }

        product.update(
                productRequestDTO.name(),
                productRequestDTO.description(),
                productRequestDTO.price(),
                productRequestDTO.category()
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

