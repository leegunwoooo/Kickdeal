package kick.kickdeal.dto;

import kick.kickdeal.entity.Category;
import kick.kickdeal.entity.Product;

import java.time.LocalDate;

public record ProductResponseDTO(
        String name,
        String description,
        int price,
        LocalDate date,
        String userNickname,
        Category category,
        String imageUrl) {

    public ProductResponseDTO(Product product) {
        this(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getDate(),
                product.getUser().getId(),
                product.getCategory(),
                product.getImageUrl()
        );
    }
}
