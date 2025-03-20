package kz.shop.book.entities.product;

import jakarta.persistence.*;
import kz.shop.book.enums.AttributeType;
import lombok.*;

@Entity
@Table(name = "attributes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // ✅ Код атрибута (например, "COLOR", "SIZE", "MODEL")

    @Column(nullable = false)
    private String nameRu; // ✅ Название атрибута на русском

    @Column(nullable = false)
    private String nameKz; // ✅ Название атрибута на казахском

    @Column(nullable = false)
    private String nameEn; // ✅ Название атрибута на английском
    @Column(nullable = false)
    private Boolean isPublic = true; // ✅ Явно указываем значение по умолчанию
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttributeType type; // ✅ Тип атрибута (STRING, NUMBER, BOOLEAN, ENUM, MULTISELECT)

    public String getLocalizedName(String locale) {
        return switch (locale.toLowerCase()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn;
        };
    }
}