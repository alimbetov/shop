package kz.shop.book.services.product;

import jakarta.annotation.Nullable;
import kz.shop.book.dto.product.AttributeDTO;
import kz.shop.book.dto.product.ProductAttributesDTO;
import kz.shop.book.dto.product.ProductDTO;
import kz.shop.book.entities.product.ProductAttribute;
import kz.shop.book.repository.product.AttributeRepository;
import kz.shop.book.repository.product.AttributeValueRepository;
import kz.shop.book.repository.product.ProductAttributeRepository;
import kz.shop.book.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {

    private final ProductAttributeRepository productAttributeRepository;
    private final ProductRepository productRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final AttributeRepository attributeRepository;

    private final AttributeService attributeService;
    private final ProductService productService;

    public ProductDTO getProductById(Long productId) {
        var productAttributesDTOList = getProductAttributesByProductId(productId, null);
        var productDTO = productService.getProductById(productId);

        if (!productAttributesDTOList.isEmpty()) {
            productDTO.setProductAttributes(productAttributesDTOList);
        }

        return productDTO;
    }

    public List<AttributeDTO> getAttributeDTOList(Long productId, Boolean isBounded) {
        // Используем `Set` для быстрого поиска
        Set<Long> boundedAttributeIds = new HashSet<>(Optional.ofNullable(
                productAttributeRepository.findAttributeIdsByProductId(productId)
        ).orElse(List.of())); // ✅ Защита от `null`
        return attributeService.getAllAttributes().stream()
                .filter(attribute -> isBounded.equals(boundedAttributeIds.contains(attribute.getId())))
                .collect(Collectors.toList());
    }


    public List<ProductAttributesDTO> getProductAttributesByProductId(@NonNull Long productId, @Nullable Long attributeId) {
        List<ProductAttribute> productAttributeList = productAttributeRepository.findByProductId(productId);

        return productAttributeList.stream()
                .filter(f -> attributeId == null || Objects.equals(f.getAttribute().getId(), attributeId)) // ✅ Безопасное сравнение
                .map(pa -> ProductAttributesDTO.builder()
                        .attributeId(pa.getAttribute() != null ? pa.getAttribute().getId() : null) // ✅ Проверка `null`
                        .attributeInfo(pa.getAttribute() != null ?
                                pa.getAttribute().getCode().concat(pa.getAttribute().getType().name()) : "Нет данных")
                        .productId(pa.getProduct() != null ? pa.getProduct().getId() : null) // ✅ Проверка `null`
                        .productInfo(pa.getProduct() != null ? pa.getProduct().getNameRu() : "Неизвестный продукт")
                        .id(pa.getId())
                        .isPublic(pa.getIsPublic())
                        .value(pa.getValue())
                        .build())
                .collect(Collectors.toList());
    }


    public List<ProductAttributesDTO> productAttributesBoundBuild(@NonNull Long productId, @NonNull Long attributeId) {
        var attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new RuntimeException("Атрибут не найден: " + attributeId));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден: " + productId));

        if (productAttributeRepository.existsByProductIdAndAttributeId(productId, attributeId)) {
            return getProductAttributesByProductId(productId, attributeId);
        }

        // Загружаем все существующие значения одним SQL-запросом
        Set<String> existingValues = new HashSet<>(productAttributeRepository.findValuesByProductIdAndAttributeId(productId, attributeId));

        var newProductAttributes = attributeValueRepository.findByAttributeId(attributeId).stream()
                .filter(item -> !existingValues.contains(item.getValue())) // Локальная проверка
                .map(item -> ProductAttribute.builder()
                        .attribute(attribute)
                        .value(item.getValue())
                        .isPublic(item.getIsPublic())
                        .product(product)
                        .build())
                .toList();

        productAttributeRepository.saveAll(newProductAttributes);

        return getProductAttributesByProductId(productId, attributeId);
    }


    public List<ProductAttributesDTO> productAttributesUnBound(@NonNull Long productId, @NonNull Long attributeId) {
       try {
        var remove_data =   productAttributeRepository.findIdsProductIdAndAttributeId(productId, attributeId);
           productAttributeRepository.deleteAllById(remove_data);
       } catch (Exception e){
           System.out.println(e.getMessage());
       }
        return List.of();
    }
}
