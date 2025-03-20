package kz.shop.book.mappers;


import kz.shop.book.dto.product.AttributeDTO;
import kz.shop.book.entities.product.Attribute;
import org.springframework.stereotype.Component;

@Component
public class AttributeMapper {
    public AttributeDTO toDto(Attribute attribute) {
        return AttributeDTO.builder()
                .id(attribute.getId())
                .code(attribute.getCode())
                .nameRu(attribute.getNameRu())
                .nameKz(attribute.getNameKz())
                .nameEn(attribute.getNameEn())
                .isPublic(attribute.getIsPublic())
                .type(attribute.getType())
                .build();
    }

    public Attribute toEntity(AttributeDTO dto) {
        return Attribute.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .nameRu(dto.getNameRu())
                .nameKz(dto.getNameKz())
                .nameEn(dto.getNameEn())
                .isPublic(dto.getIsPublic())
                .type(dto.getType())
                .build();
    }
}
