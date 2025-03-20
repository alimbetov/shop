package kz.shop.book.dto.product;

import kz.shop.book.enums.AttributeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeDTO {
    private Long id;

    @NotBlank(message = "Код атрибута обязателен")
    private String code;

    @NotBlank(message = "Название на русском обязательно")
    @Size(min = 2, max = 100, message = "Название на русском должно содержать от 2 до 100 символов")
    private String nameRu;

    @NotBlank(message = "Название на казахском обязательно")
    @Size(min = 2, max = 100, message = "Название на казахском должно содержать от 2 до 100 символов")
    private String nameKz;

    @NotBlank(message = "Название на английском обязательно")
    @Size(min = 2, max = 100, message = "Название на английском должно содержать от 2 до 100 символов")
    private String nameEn;

    private Boolean isPublic;
    private AttributeType type;

}