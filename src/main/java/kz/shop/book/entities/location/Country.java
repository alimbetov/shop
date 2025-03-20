package kz.shop.book.entities.location;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {
    @Id
    @Column(nullable = false, unique = true)
    private String countryCode; // ✅ Код страны (например, KZ, RU, US)
    @Column(nullable = false)
    private Boolean isPublic = true; // ✅ Явно указываем значение по умолчанию
    @Column(nullable = false)
    private String nameRu; // ✅ Название на русском

    @Column(nullable = false)
    private String nameKz; // ✅ Название на казахском

    @Column(nullable = false)
    private String nameEn; // ✅ Название на английском

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<City> cities = new ArrayList<>(); // ✅ Значение по умолчанию; // ✅ Связь со списком городов

    // ✅ Метод для получения имени страны по локали
    public String getLocalizedName(String locale) {
        return switch (locale.toLowerCase()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn;
        };
    }
}
