package kz.shop.book.dto.product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeValueDTO {
    private Long id;
    private Long attributeId;

    @NotBlank(message = "Значение атрибута обязательно")
    @Size(min = 0, max = 100, message = "Значение атрибута должно содержать от 1 до 100 символов")
    private String value;

    private Boolean isPublic;
}