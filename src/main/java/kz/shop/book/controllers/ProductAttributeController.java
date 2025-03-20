package kz.shop.book.controllers;

import kz.shop.book.dto.product.AttributeDTO;
import kz.shop.book.dto.product.ProductAttributesDTO;
import kz.shop.book.dto.product.ProductDTO;
import kz.shop.book.services.product.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-attributes")
@RequiredArgsConstructor
public class ProductAttributeController {
    private final ProductAttributeService productAttributeService;
    /**
     * 🔹 Получение информации о продукте с атрибутами
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productAttributeService.getProductById(productId));
    }
    /**
     * 🔹 Получение атрибутов продукта (связанных/несвязанных)
     */
    @GetMapping("/attributes")
    public ResponseEntity<List<AttributeDTO>> getAttributeDTOList(
            @RequestParam Long productId,
            @RequestParam Boolean isBounded) {
        return ResponseEntity.ok(productAttributeService.getAttributeDTOList(productId, isBounded));
    }

    /**
     * 🔹 Получение всех атрибутов продукта
     */
    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductAttributesDTO>> getProductAttributesByProductId(
            @PathVariable Long productId,
            @RequestParam(required = false) Long attributeId) {
        return ResponseEntity.ok(productAttributeService.getProductAttributesByProductId(productId, attributeId));
    }

    /**
     * 🔹 Привязка атрибута к продукту
     */
    @PostMapping("/bind")
    public ResponseEntity<List<ProductAttributesDTO>> bindAttributeToProduct(
            @RequestParam Long productId,
            @RequestParam Long attributeId) {
        return ResponseEntity.ok(productAttributeService.productAttributesBoundBuild(productId, attributeId));
    }

    /**
     * 🔹 Удаление привязки атрибута от продукта
     */
    @DeleteMapping("/unbind")
    public ResponseEntity<List<ProductAttributesDTO>> unbindAttributeFromProduct(
            @RequestParam Long productId,
            @RequestParam Long attributeId) {
        return ResponseEntity.ok(productAttributeService.productAttributesUnBound(productId, attributeId));
    }
}
