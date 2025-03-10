package kick.kickdeal.dto;

import kick.kickdeal.entity.Category;
import kick.kickdeal.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private String name;

    private String description;

    private int price;

    private LocalDate date;

    private String userNickname;

    private Category category;

    private String imageUrl;

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
