package kz.shop.book.controllers;


import kz.shop.book.dto.product.AttributeDTO;
import kz.shop.book.dto.product.AttributeValueDTO;
import kz.shop.book.enums.AttributeType;
import kz.shop.book.services.product.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/attributes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Разрешает запросы с любого фронтенда
public class AttributeController {

    private final AttributeService attributeService;

    // 🔹 Получить все возможные типы атрибутов
    @GetMapping("/types")
    public ResponseEntity<List<AttributeType>> getAttributeTypes() {
        return ResponseEntity.ok(Arrays.asList(AttributeType.values()));
    }

    // 🔹 Получить все атрибуты
    @GetMapping
    public ResponseEntity<List<AttributeDTO>> getAllAttributes() {
        return ResponseEntity.ok(attributeService.getAllAttributes());
    }

    // 🔹 Поиск атрибутов по тексту (code, nameRu, nameKz, nameEn)
    @GetMapping("/search")
    public ResponseEntity<List<AttributeDTO>> searchAttributes(@RequestParam String query) {
        return ResponseEntity.ok(attributeService.searchAttributes(query));
    }

    // 🔹 Получить атрибут по ID
    @GetMapping("/{id}")
    public ResponseEntity<AttributeDTO> getAttributeById(@PathVariable Long id) {
        return ResponseEntity.ok(attributeService.getAttributeById(id));
    }

    // 🔹 Создать новый атрибут
    @PostMapping
    public ResponseEntity<AttributeDTO> createAttribute(@RequestBody AttributeDTO dto) {
        return ResponseEntity.ok(attributeService.createAttribute(dto));
    }

    // 🔹 Обновить существующий атрибут
    @PutMapping("/{id}")
    public ResponseEntity<AttributeDTO> updateAttribute(@PathVariable Long id, @RequestBody AttributeDTO dto) {
        return ResponseEntity.ok(attributeService.updateAttribute(id, dto));
    }

    // 🔹 Удалить атрибут по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable Long id) {
        attributeService.deleteAttribute(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Найти атрибуты по типу (STRING, NUMBER, BOOLEAN, ENUM, MULTISELECT)
    @GetMapping("/type/{type}")
    public ResponseEntity<List<AttributeDTO>> findByType(@PathVariable AttributeType type) {
        return ResponseEntity.ok(attributeService.findByType(type));
    }





    // 🔹 Получить все значения атрибута
    @GetMapping("/values")
    public ResponseEntity<List<AttributeValueDTO>> getAllAttributeValues() {
        return ResponseEntity.ok(attributeService.getAllAttributeValues());
    }

    // 🔹 Получить значение атрибута по ID
    @GetMapping("/values/{id}")
    public ResponseEntity<AttributeValueDTO> getAttributeValueById(@PathVariable Long id) {
        return ResponseEntity.ok(attributeService.getAttributeValueById(id));
    }

    // 🔹 Создать значение атрибута
    @PostMapping("/values")
    public ResponseEntity<AttributeValueDTO> createAttributeValue(@RequestBody AttributeValueDTO dto) {
        return ResponseEntity.ok(attributeService.createAttributeValue(dto));
    }

    // 🔹 Обновить значение атрибута
    @PutMapping("/values/{id}")
    public ResponseEntity<AttributeValueDTO> updateAttributeValue(@PathVariable Long id, @RequestBody AttributeValueDTO dto) {
        return ResponseEntity.ok(attributeService.updateAttributeValue(id, dto));
    }

    // 🔹 Удалить значение атрибута
    @DeleteMapping("/values/{id}")
    public ResponseEntity<Void> deleteAttributeValue(@PathVariable Long id) {
        attributeService.deleteAttributeValue(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Найти все значения атрибута по `attributeId`
    @GetMapping("/values/attribute/{attributeId}")
    public ResponseEntity<List<AttributeValueDTO>> findByAttributeId(@PathVariable Long attributeId) {
        return ResponseEntity.ok(attributeService.findByAttributeId(attributeId));
    }

    // 🔹 Поиск значений по подстроке (например, "черный")
    @GetMapping("/values/search")
    public ResponseEntity<List<AttributeValueDTO>> findByValue(@RequestParam String query) {
        return ResponseEntity.ok(attributeService.findByValue(query));
    }

    // 🔹 Получить только публичные значения атрибутов
    @GetMapping("/values/public")
    public ResponseEntity<List<AttributeValueDTO>> findPublicValues() {
        return ResponseEntity.ok(attributeService.findPublicValues());
    }
}
