package kick.kickdeal.presentation.dto;

import kick.kickdeal.domain.entity.Category;
import kick.kickdeal.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {

    private String name;

    private String description;

    private int price;

    private LocalDate date;

    private String userNickname;

    private Category category;

    private String imageUrl;

    private MultipartFile image;

    public ProductDTO(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.date = product.getDate();
        this.userNickname = product.getUser().getId();
        this.category = product.getCategory();
        this.imageUrl = product.getImageUrl();
    }
}
