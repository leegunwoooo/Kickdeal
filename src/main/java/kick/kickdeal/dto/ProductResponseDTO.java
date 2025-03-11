package kick.kickdeal.dto;

import kick.kickdeal.entity.Category;
import kick.kickdeal.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProductResponseDTO {

    private String name;

    private String description;

    private int price;

    private LocalDate date;

    private String userNickname;

    private Category category;

    private String imageUrl;

    public ProductResponseDTO(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.date = product.getDate();
        this.userNickname = product.getUser().getId(); // 닉네임 가져오기
        this.category = product.getCategory();
        this.imageUrl = product.getImageUrl();
    }
}
