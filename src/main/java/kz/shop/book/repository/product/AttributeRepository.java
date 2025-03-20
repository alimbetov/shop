package kz.shop.book.repository.product;


import kz.shop.book.entities.product.Attribute;
import kz.shop.book.enums.AttributeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    Optional<Attribute> findByCode(String code);

    List<Attribute> findByType(AttributeType type);

    List<Attribute> findByIsPublicTrue();

    List<Attribute> findByNameRuContainingIgnoreCase(String nameRu);

    List<Attribute> findByNameKzContainingIgnoreCase(String nameKz);

    List<Attribute> findByNameEnContainingIgnoreCase(String nameEn);

    List<Attribute> findByCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
            String code, String nameRu, String nameKz, String nameEn
    );
}
