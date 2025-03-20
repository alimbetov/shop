package kz.shop.book.mappers;



import kz.shop.book.dto.good.CategoryGroupDto;
import kz.shop.book.entities.good.CategoryGroup;
import kz.shop.book.entities.good.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryGroupMapper {

    public CategoryGroupDto toDto(CategoryGroup entity) {
        return CategoryGroupDto.builder()
                .code(entity.getCode())
                .isPublic(entity.getIsPublic())
                .nameRu(entity.getNameRu())
                .nameKz(entity.getNameKz())
                .nameEn(entity.getNameEn())
                .categoryCodes(entity.getCategories() != null ?
                        entity.getCategories().stream().map(Category::getCode).collect(Collectors.toList()) :
                        null)
                .build();
    }

    public CategoryGroup toEntity(CategoryGroupDto dto) {
        return CategoryGroup.builder()
                .code(dto.getCode())
                .isPublic(dto.getIsPublic())
                .nameRu(dto.getNameRu())
                .nameKz(dto.getNameKz())
                .nameEn(dto.getNameEn())
                .build();
    }
}
