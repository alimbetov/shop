package kz.shop.book.entities.good;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @Column(nullable = false, unique = true)
    private String code; // ✅ Уникальный код категории (например, "ELECTRONICS", "CLOTHING")

    @Column(nullable = false)
    private Boolean isPublic = true; // ✅ Значение по умолчанию

    @Column(nullable = false)
    private String nameRu;

    @Column(nullable = false)
    private String nameKz;

    @Column(nullable = false)
    private String nameEn;

    @ManyToOne
    @JoinColumn(name = "group_code", referencedColumnName = "code", nullable = false)
    private CategoryGroup group; // ✅ Связь с группой категорий по `code`

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SubCategory> subCategories = new ArrayList<>(); // ✅ Категория содержит список подкатегорий

    public String getLocalizedName(String locale) {
        return switch (locale.toLowerCase()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn;
        };
    }
}
