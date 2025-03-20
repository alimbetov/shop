package kz.shop.book.entities.good;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryGroup {

    @Id
    @Column(nullable = false, unique = true)
    private String code; // ✅ Теперь это уникальный ID (например, "PRODUCTS", "SERVICES")
    @Column(nullable = false)
    private Boolean isPublic = true; // ✅ Явно указываем значение по умолчанию
    @Column(nullable = false)
    private String nameRu;

    @Column(nullable = false)
    private String nameKz;

    @Column(nullable = false)
    private String nameEn;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Category> categories = new ArrayList<>(); // ✅ Значение по умолчанию

    public String getLocalizedName(String locale) {
        return switch (locale.toLowerCase()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn;
        };
    }
}