package kz.shop.book.repository.good;

import kz.shop.book.entities.good.CategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, String> {

    List<CategoryGroup> findByCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
            String code, String nameRu, String nameKz, String nameEn
    );
}
