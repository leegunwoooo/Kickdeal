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

    private int price;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "seller")
    private User user;


    @Builder
    public Product(String name, String description, int price, User user) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.date = LocalDate.now();

        this.user = user;
    }


    public void update(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
