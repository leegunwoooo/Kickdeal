package kick.kickdeal.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(length = 2083)
    private String imageUrl;

    private int price;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "seller")
    private User user;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Product(String name, String description,String imageUrl, int price, User user, Category category) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.date = LocalDate.now();
        this.user = user;
        this.category = category;
    }


    public void update(String name, String description, int price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
