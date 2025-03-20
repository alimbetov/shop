package kz.shop.book.mappers;


import kz.shop.book.dto.product.AttributeValueDTO;
import kz.shop.book.entities.product.AttributeValue;
import kz.shop.book.entities.product.Attribute;
import kz.shop.book.repository.product.AttributeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttributeValueMapper {

    private final AttributeRepository attributeRepository;

    public AttributeValueDTO toDto(AttributeValue attributeValue) {
        return AttributeValueDTO.builder()
                .id(attributeValue.getId())
                .attributeId(attributeValue.getAttribute().getId())
                .value(attributeValue.getValue())
                .isPublic(attributeValue.getIsPublic())
                .build();
    }

    public AttributeValue toEntity(AttributeValueDTO dto) {
        Attribute attribute = attributeRepository.findById(dto.getAttributeId())
                .orElseThrow(() -> new RuntimeException("Атрибут не найден"));

        return AttributeValue.builder()
                .id(dto.getId())
                .attribute(attribute)
                .value(dto.getValue())
                .isPublic(dto.getIsPublic())
                .build();
    }
}
