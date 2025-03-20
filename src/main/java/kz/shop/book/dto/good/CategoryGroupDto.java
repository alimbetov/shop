package kz.shop.book.dto.good;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryGroupDto {

    @NotBlank(message = "Код группы обязателен")
    @Size(min = 2, max = 50, message = "Код группы должен содержать от 2 до 50 символов")
    private String code;

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

    private List<String> categoryCodes;
}