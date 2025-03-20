package kz.shop.book.repository.product;

import kz.shop.book.entities.product.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    List<ProductAttribute> findByProductId(Long productId);

    boolean existsByProductIdAndAttributeIdAndValue(Long productId, Long attributeId, String value);

    boolean existsByProductIdAndAttributeId(Long productId, Long attributeId);
    long deleteAllByProductIdAndAttributeId(Long productId, Long attributeId);


    @Query("SELECT pa.attribute.id FROM ProductAttribute pa WHERE pa.product.id = :productId")
    List<Long> findAttributeIdsByProductId(@Param("productId") Long productId);

    @Query("SELECT pa.value FROM ProductAttribute pa WHERE pa.product.id = :productId AND pa.attribute.id = :attributeId")
    List<String> findValuesByProductIdAndAttributeId(@Param("productId") Long productId, @Param("attributeId") Long attributeId);

    @Query("SELECT pa.id FROM ProductAttribute pa WHERE pa.product.id = :productId AND pa.attribute.id = :attributeId")
    List<Long> findIdsProductIdAndAttributeId(@Param("productId") Long productId, @Param("attributeId") Long attributeId);

}
