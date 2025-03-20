package kz.shop.book.entities.product;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_attributes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // ✅ К какому товару относится

    @Column(nullable = false)
    private Boolean isPublic = true; // ✅ Явно указываем значение по умолчанию

    @ManyToOne
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute; // ✅ Какой атрибут (например, "COLOR")

    @Column(nullable = false)
    private String value; // ✅ Значение атрибута (например, "Черный", "Nike", "42")
}
