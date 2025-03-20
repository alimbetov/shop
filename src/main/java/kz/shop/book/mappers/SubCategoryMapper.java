package kz.shop.book.mappers;


import kz.shop.book.dto.good.SubCategoryDto;
import kz.shop.book.entities.good.SubCategory;
import kz.shop.book.entities.good.Category;
import org.springframework.stereotype.Component;

@Component
public class SubCategoryMapper {

    public SubCategoryDto toDto(SubCategory entity) {
        return SubCategoryDto.builder()
                .code(entity.getCode())
                .isPublic(entity.getIsPublic())
                .nameRu(entity.getNameRu())
                .nameKz(entity.getNameKz())
                .nameEn(entity.getNameEn())
                .categoryCode(entity.getCategory().getCode())
                .build();
    }

    public SubCategory toEntity(SubCategoryDto dto, Category category) {
        return SubCategory.builder()
                .code(dto.getCode())
                .isPublic(dto.getIsPublic())
                .nameRu(dto.getNameRu())
                .nameKz(dto.getNameKz())
                .nameEn(dto.getNameEn())
                .category(category)
                .build();
    }
}