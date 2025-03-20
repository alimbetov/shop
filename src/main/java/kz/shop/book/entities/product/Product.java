package kz.shop.book.entities.product;


import jakarta.persistence.*;
import kz.shop.book.entities.good.Category;
import kz.shop.book.entities.good.SubCategory;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nameRu; // Название товара на русском

    @Column(nullable = false)
    private String nameKz; // Название товара на казахском

    @Column(nullable = false)
    private String nameEn; // Название товара на английском
    public String getLocalizedName(String locale) {
        return switch (locale.toLowerCase()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn;
        };
    }

    @Column(nullable = false)
    private Boolean isPublic = true; // ✅ Явно указываем значение по умолчанию

    @ManyToOne
    @JoinColumn(name = "category_code", referencedColumnName = "code", nullable = false)
    private Category category; // ✅ Категория товара

    @ManyToOne
    @JoinColumn(name = "sub_category_code", referencedColumnName = "code", nullable = true)
    private SubCategory subCategory; // ✅ Подкатегория товара (опционально)

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductAttribute> productAttributes = new ArrayList<>(); // ✅ Связь с характеристиками
}

