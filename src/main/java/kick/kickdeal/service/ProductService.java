package kick.kickdeal.service;


import kick.kickdeal.dto.ProductDTO;
import kick.kickdeal.entity.Product;
import kick.kickdeal.entity.User;
import kick.kickdeal.repository.ProductRepository;
import kick.kickdeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Product save(ProductDTO productDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // 로그인된 사용자의 username (아이디) 가져오기

        User seller = userRepository.findById(username);

        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .user(seller)
                .build();

        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, ProductDTO productDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id: " + id));

        if(!product.getUser().getId().equals(username)) {
            throw new IllegalArgumentException("본인만 상품정보를 수정할 수 있습니다. id: " + id);
        }

        product.update(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice());

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id: " + id));

        if(!product.getUser().getId().equals(username)) {
            throw new IllegalArgumentException("본인만 상품정보를 수정할 수 있습니다. id: " + id);
        }

        productRepository.deleteById(id);
    }
}

