package kick.kickdeal.dto;

import kick.kickdeal.entity.Category;

public record ProductRequestDTO(
        String name,
        String description,
        int price,
        Category category) {
}
