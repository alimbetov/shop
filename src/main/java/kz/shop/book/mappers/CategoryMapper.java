package kz.shop.book.mappers;


import kz.shop.book.dto.good.CategoryDto;
import kz.shop.book.entities.good.Category;
import kz.shop.book.entities.good.CategoryGroup;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryDto.builder()
                .code(category.getCode())
                .isPublic(category.getIsPublic())
                .nameRu(category.getNameRu())
                .nameKz(category.getNameKz())
                .nameEn(category.getNameEn())
                .groupCode(category.getGroup().getCode()) // ✅ Вытаскиваем `groupCode`
                .build();
    }

    public Category toEntity(CategoryDto dto, CategoryGroup group) {
        if (dto == null || group == null) {
            throw new IllegalArgumentException("CategoryDto и CategoryGroup не могут быть null");
        }

        return Category.builder()
                .code(dto.getCode())
                .isPublic(dto.getIsPublic())
                .nameRu(dto.getNameRu())
                .nameKz(dto.getNameKz())
                .nameEn(dto.getNameEn())
                .group(group) // ✅ Устанавливаем связь с группой
                .build();
    }
}
