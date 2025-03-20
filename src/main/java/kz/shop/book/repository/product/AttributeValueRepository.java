package kz.shop.book.repository.product;

import kz.shop.book.entities.product.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {

    List<AttributeValue> findByAttributeId(Long attributeId);

    List<AttributeValue> findByValueContainingIgnoreCase(String value);

    List<AttributeValue> findByIsPublicTrue();
}