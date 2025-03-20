package kz.shop.book.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kz.shop.book.enums.LanguageName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDto {

    @NotBlank(message = "Код города обязателен")
    @Size(min = 2, max = 10, message = "Код города должен содержать от 2 до 10 символов")
    @Pattern(regexp = "^[A-Z0-9_-]+$", message = "Код города должен содержать только заглавные буквы, цифры, '_', '-'")
    private String cityCode;

    @NotNull(message = "Поле isPublic обязательно")
    private Boolean isPublic;

    @NotBlank(message = "Название на русском обязательно")
    @Size(min = 2, max = 100, message = "Название на русском должно содержать от 2 до 100 символов")
    private String nameRu;

    @NotBlank(message = "Название на казахском обязательно")
    @Size(min = 2, max = 100, message = "Название на казахском должно содержать от 2 до 100 символов")
    private String nameKz;

    @NotBlank(message = "Название на английском обязательно")
    @Size(min = 2, max = 100, message = "Название на английском должно содержать от 2 до 100 символов")
    private String nameEn;

    @NotBlank(message = "Код страны обязателен")
    @Size(min = 2, max = 3, message = "Код страны должен содержать от 2 до 3 символов (ISO 3166-1 alpha-2)")
    @Pattern(regexp = "^[A-Z]+$", message = "Код страны должен содержать только заглавные буквы (например, 'KZ', 'RU', 'US')")
    private String countryCode;

    public String getLocalizedName(LanguageName lang) {
        return switch (lang.getCode()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn;
        };
    }

}
