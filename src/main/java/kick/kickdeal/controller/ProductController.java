package kick.kickdeal.controller;

import kick.kickdeal.dto.ProductDTO;
import kick.kickdeal.dto.ProductUploadDTO;
import kick.kickdeal.entity.Product;
import kick.kickdeal.service.MinioService;
import kick.kickdeal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final MinioService minioService;

    @PostMapping("/save")
    public ProductDTO createProduct(@RequestBody ProductUploadDTO productUploadDTO) {
        minioService.uploadFile(productUploadDTO);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.update(id, productDTO);
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
