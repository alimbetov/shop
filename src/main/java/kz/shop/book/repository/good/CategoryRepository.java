package kz.shop.book.repository.good;
import kz.shop.book.entities.good.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, String> {

        List<Category> findByCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
                String code, String nameRu, String nameKz, String nameEn
        );

        List<Category> findAllByGroupCode(String groupCode);
}
