package kz.shop.book.repository.good;

import kz.shop.book.entities.good.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SubCategoryRepository extends JpaRepository<SubCategory, String> {
        List<SubCategory> findByCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
                String code, String nameRu, String nameKz, String nameEn
        );

        List<SubCategory> findAllByCategoryCode(String code);
}