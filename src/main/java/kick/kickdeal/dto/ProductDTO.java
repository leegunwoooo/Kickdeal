package kick.kickdeal.dto;

import kick.kickdeal.entity.Category;
import kick.kickdeal.entity.Product;
import kick.kickdeal.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private String name;

    private String description;

    //url 정규식을 통한 악성 url 검사
    @Pattern(
            regexp = "^(https?://).+\\.(png|jpe?g|gif|bmp|webp|svg)(\\?.*)?$",
            message = "유효한 이미지 URL 형식이 아닙니다."
    )
    private String image;

    private int price;

    private LocalDate date;

    private String userNickname;

    private Category category;


    public ProductDTO(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.image = product.getImage();
        this.price = product.getPrice();
        this.date = product.getDate();
        this.userNickname = product.getUser().getId();
        this.category = product.getCategory();
    }
}
