package kz.shop.book.repository.product;


import kz.shop.book.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 🔹 Найти все товары по подкатегории (исправлено)
    Page<Product> findBySubCategory_Code(String subCategoryCode, Pageable pageable);

    // 🔹 Найти товары по категории И подкатегории (универсальный метод)
    Page<Product> findByCategory_CodeAndSubCategory_Code(String categoryCode, String subCategoryCode, Pageable pageable);

    // 🔹 Универсальный поиск по названию (RU, KZ, EN)
    Page<Product> findByNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
            String nameRu, String nameKz, String nameEn, Pageable pageable
    );

    // 🔹 Найти товары по категории (если subCategoryCode не передан, ищем только по категории)
    Page<Product> findByCategory_Code(String categoryCode, Pageable pageable);
}


