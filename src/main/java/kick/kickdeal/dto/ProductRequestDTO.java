package kick.kickdeal.dto;

import kick.kickdeal.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ProductRequestDTO {

    private String name;

    private String description;

    private int price;

    private String userNickname;

    private Category category;

    private MultipartFile image;
}
