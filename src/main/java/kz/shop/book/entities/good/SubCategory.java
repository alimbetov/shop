package kz.shop.book.entities.good;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sub_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategory {

    @Id
    @Column(nullable = false, unique = true)
    private String code; // ✅ Уникальный 0 код (например, "SMARTPHONES", "LAPTOPS")
    @Column(nullable = false)
    private Boolean isPublic = true; // ✅ Явно указываем значение по умолчанию
    @Column(nullable = false)
    private String nameRu;

    @Column(nullable = false)
    private String nameKz;

    @Column(nullable = false)
    private String nameEn;

    @ManyToOne
    @JoinColumn(name = "category_code", referencedColumnName = "code", nullable = false)
    private Category category; // ✅ Теперь связь по `code`

    public String getLocalizedName(String locale) {
        return switch (locale.toLowerCase()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn;
        };
    }
}