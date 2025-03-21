package kz.shop.book.controllers;


import kz.shop.book.dto.product.ProductDTO;
import kz.shop.book.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    // 🔹 Получить товар по ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // 🔹 Создать товар
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    // 🔹 Обновить товар
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    // 🔹 Удалить товар
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Поиск товаров по названию (по всем языкам) с пагинацией
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProductsByName(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.searchProductsByName(query, page, size));
    }

    // 🔹 Фильтрация товаров по категориям с пагинацией
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDTO>> filterProducts(
            @RequestParam(required = false) String categoryCode,
            @RequestParam(required = false) String subCategoryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.searchProducts(categoryCode, subCategoryCode, page, size));
    }
}
