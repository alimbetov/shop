package kz.shop.book.entities.location;

import jakarta.persistence.*;
import kz.shop.book.enums.LanguageName;
import lombok.*;

@Entity
@Table(name = "cities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id
    @Column(nullable = false, unique = true)
    private String cityCode; // Код города (например, "ALMATY", "ASTANA")
    @Column(nullable = false)
    private Boolean isPublic = true; // ✅ Явно указываем значение по умолчанию
    @Column(nullable = false)
    private String nameRu; // Название на русском

    @Column(nullable = false)
    private String nameKz; // Название на казахском

    @Column(nullable = false)
    private String nameEn; // Название на английском

    @ManyToOne
    @JoinColumn(name = "country_code", nullable = false) // ✅ Привязываем к стране
    private Country country;

    // ✅ Метод для получения имени города по локали
    public String getLocalizedName(LanguageName lang) {
        return switch (lang.getCode()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn;
        };
    }
}
