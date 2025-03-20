package kz.shop.book.services.product;

import kz.shop.book.dto.product.AttributeDTO;
import kz.shop.book.dto.product.AttributeValueDTO;
import kz.shop.book.entities.product.Attribute;
import kz.shop.book.entities.product.AttributeValue;
import kz.shop.book.enums.AttributeType;
import kz.shop.book.exception.LimitExceededException;
import kz.shop.book.mappers.AttributeMapper;

import kz.shop.book.mappers.AttributeValueMapper;
import kz.shop.book.repository.product.AttributeRepository;
import kz.shop.book.repository.product.AttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static kz.shop.book.utils.RangeGenerator.generateRanges;

@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final AttributeMapper attributeMapper;
    private final AttributeValueMapper attributeValueMapper;

    public List<AttributeDTO> getAllAttributes() {
        return attributeRepository.findAll()
                .stream()
                .map(attributeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AttributeDTO> searchAttributes(String searchText) {
        return attributeRepository
                .findByCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
                        searchText, searchText, searchText, searchText
                )
                .stream()
                .map(attributeMapper::toDto)
                .collect(Collectors.toList());
    }

    public AttributeDTO getAttributeById(Long id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Атрибут не найден"));
        return attributeMapper.toDto(attribute);
    }

    public AttributeDTO createAttribute(AttributeDTO dto) {
        Attribute attribute = attributeMapper.toEntity(dto);
        return attributeMapper.toDto(attributeRepository.save(attribute));
    }

    public AttributeDTO updateAttribute(Long id, AttributeDTO dto) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Атрибут не найден"));
        attribute.setCode(dto.getCode());
        attribute.setNameRu(dto.getNameRu());
        attribute.setNameKz(dto.getNameKz());
        attribute.setNameEn(dto.getNameEn());
        attribute.setIsPublic(dto.getIsPublic());
        attribute.setType(dto.getType());
        return attributeMapper.toDto(attributeRepository.save(attribute));
    }

    public void deleteAttribute(Long id) {
        attributeRepository.deleteById(id);
    }

    public List<AttributeDTO> findByType(AttributeType type) {
        return attributeRepository.findByType(type).stream()
                .map(attributeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AttributeValueDTO> getAllAttributeValues() {
        return attributeValueRepository.findAll()
                .stream()
                .map(attributeValueMapper::toDto)
                .collect(Collectors.toList());
    }

    public AttributeValueDTO getAttributeValueById(Long id) {
        AttributeValue attributeValue = attributeValueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Значение атрибута не найдено"));
        return attributeValueMapper.toDto(attributeValue);
    }

    public AttributeValueDTO createAttributeValue(AttributeValueDTO dto) {
        if (limitation(dto.getAttributeId())) {
            throw new LimitExceededException("Значение атрибута превышает лимит");
        }
        if (generator(dto)){
            return dto;
        }
        return saveAttributeValueDTO(dto);
    }

    private AttributeValueDTO saveAttributeValueDTO(AttributeValueDTO dto) {
        AttributeValue attributeValue = attributeValueMapper.toEntity(dto);
        return attributeValueMapper.toDto(attributeValueRepository.save(attributeValue));
    }

    private void saveAllAttributeValue(List<AttributeValue> dtoList) {
        attributeValueRepository.saveAll(dtoList);
    }

    public AttributeValueDTO updateAttributeValue(Long id, AttributeValueDTO dto) {
        AttributeValue existing = attributeValueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Значение атрибута не найдено"));

        existing.setValue(dto.getValue());
        existing.setIsPublic(dto.getIsPublic());

        return attributeValueMapper.toDto(attributeValueRepository.save(existing));
    }

    public void deleteAttributeValue(Long id) {
        attributeValueRepository.deleteById(id);
    }

    public List<AttributeValueDTO> findByAttributeId(Long attributeId) {
        return attributeValueRepository.findByAttributeId(attributeId)
                .stream()
                .map(attributeValueMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AttributeValueDTO> findByValue(String value) {
        return attributeValueRepository.findByValueContainingIgnoreCase(value)
                .stream()
                .map(attributeValueMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AttributeValueDTO> findPublicValues() {
        return attributeValueRepository.findByIsPublicTrue()
                .stream()
                .map(attributeValueMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean limitation(Long attributeId) {
        var attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new RuntimeException("Атрибут не найден")); // Улучшение

        var values = attributeValueRepository.findByAttributeId(attributeId);
        int count = (values == null) ? 0 : values.size();

        return switch (attribute.getType()) {
            case ENUM -> count > 30;
            case MULTISELECT -> count > 10;
            case STRING, NUMBER, BOOLEAN -> count >= 1;
            default -> false;
        };
    }


    public Boolean generator(AttributeValueDTO dto) {
        var attribute = attributeRepository.findById(dto.getAttributeId()).orElse(null);
        if (attribute == null) {
            return false; // Исправлено: Ошибка -> false
        }

        var values = attributeValueRepository.findByAttributeId(dto.getAttributeId());
        int count = (values == null) ? 0 : values.size();

        // Генерируем диапазоны только если значений еще нет
        if (count == 0) {
            int limit;
            switch (attribute.getType()) {
                case ENUM -> limit = 30;
                case MULTISELECT -> limit = 10;
                default -> { return false; } // Только ENUM и MULTISELECT поддерживают генерацию
            }

            // Генерируем диапазоны
            var list = generateRanges(dto.getValue(), limit);
            if (list.isEmpty()) {
                return false; // Если диапазоны не сгенерировались, выходим
            }

            // Маппим и сохраняем значения
            var saveList = list.stream()
                    .map(value -> AttributeValue.builder()
                            .attribute(attribute)
                            .isPublic(dto.getIsPublic())
                            .value(value)
                            .build())
                    .toList();

            saveAllAttributeValue(saveList);
            return true;
        }
        return false;
    }

}
