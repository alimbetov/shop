package kz.shop.book.mappers;


import kz.shop.book.dto.product.ProductDTO;
import kz.shop.book.entities.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .nameRu(product.getNameRu())
                .nameKz(product.getNameKz())
                .nameEn(product.getNameEn())
                .isPublic(product.getIsPublic())
                .categoryCode(product.getCategory().getCode())
                .subCategoryCode(product.getSubCategory() != null ? product.getSubCategory().getCode() : null)
                .build();
    }

    public Product toEntity(ProductDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .nameRu(dto.getNameRu())
                .nameKz(dto.getNameKz())
                .nameEn(dto.getNameEn())
                .isPublic(dto.getIsPublic())
                .build();
    }
}
