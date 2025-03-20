package kz.shop.book.dto.product;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributesDTO {
    private Long id;
    private Long productId;
    private String productInfo;
    private Long attributeId;
    private String attributeInfo;
    private String value;
    private Boolean isPublic;
}
