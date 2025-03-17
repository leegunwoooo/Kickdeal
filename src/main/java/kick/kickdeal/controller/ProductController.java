package kick.kickdeal.controller;

import io.minio.errors.MinioException;
import kick.kickdeal.dto.ProductRequestDTO;
import kick.kickdeal.dto.ProductResponseDTO;
import kick.kickdeal.entity.Product;
import kick.kickdeal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ProductResponseDTO createProduct(
            @RequestPart("productRequestDTO") ProductRequestDTO productRequestDTO,
            @RequestPart("image") MultipartFile image) {

        return productService.save(productRequestDTO, image);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @ModelAttribute ProductRequestDTO productRequestDTO) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        return productService.update(id, productRequestDTO);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }
}
