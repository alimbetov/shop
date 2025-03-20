package kz.shop.book.services.product;


import kz.shop.book.dto.product.ProductDTO;
import kz.shop.book.entities.product.Product;
import kz.shop.book.mappers.ProductMapper;

import kz.shop.book.repository.product.ProductRepository;
import kz.shop.book.repository.good.CategoryRepository;
import kz.shop.book.repository.good.SubCategoryRepository;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    private final ProductMapper productMapper;

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));
        return productMapper.toDto(product);
    }

    public ProductDTO createProduct(ProductDTO dto) {
        // 🔹 Находим категорию, если не найдена — выбрасываем ошибку
        var category = categoryRepository.findById(dto.getCategoryCode())
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));

        // 🔹 Подкатегория может быть `null`, поэтому если нет — ищем
        var subCategory = dto.getSubCategoryCode() != null
                ? subCategoryRepository.findById(dto.getSubCategoryCode()).orElse(null)
                : null;

        // 🔹 Создаём товар
        Product product = productMapper.toEntity(dto);
        product.setCategory(category);
        product.setSubCategory(subCategory); // Подкатегория может быть `null`

        return productMapper.toDto(productRepository.save(product));
    }


    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));


        // 🔹 Находим категорию, если не найдена — выбрасываем ошибку
        var category = categoryRepository.findById(dto.getCategoryCode())
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));

        // 🔹 Подкатегория может быть `null`, поэтому если нет — ищем
        var subCategory = dto.getSubCategoryCode() != null
                ? subCategoryRepository.findById(dto.getSubCategoryCode()).orElse(null)
                : null;

        product.setNameRu(dto.getNameRu());
        product.setNameKz(dto.getNameKz());
        product.setNameEn(dto.getNameEn());
        product.setIsPublic(dto.getIsPublic());
        product.setCategory(category);
        product.setSubCategory(subCategory); // Подкатегория может быть `null`
        return productMapper.toDto(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // ✅ Поиск по всем языкам с пагинацией
    public Page<ProductDTO> searchProductsByName(String query, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return productRepository
                .findByNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(query, query, query, pageable)
                .map(productMapper::toDto);
    }


    public Page<ProductDTO> searchProducts(String categoryCode, String subCategoryCode, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        // 🔹 Проверяем, что строки не пустые и не null
        if (StringUtils.isNotBlank(categoryCode) && StringUtils.isNotBlank(subCategoryCode)) {
            return productRepository.findByCategory_CodeAndSubCategory_Code(categoryCode, subCategoryCode, pageable)
                    .map(productMapper::toDto);
        } else if (StringUtils.isNotBlank(categoryCode)) {
            return productRepository.findByCategory_Code(categoryCode, pageable)
                    .map(productMapper::toDto);
        } else if (StringUtils.isNotBlank(subCategoryCode)) {
            return productRepository.findBySubCategory_Code(subCategoryCode, pageable)
                    .map(productMapper::toDto);
        } else {
            return productRepository.findAll(pageable).map(productMapper::toDto);
        }
    }

}
