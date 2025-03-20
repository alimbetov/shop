package kz.shop.book.entities.product;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attribute_values")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isPublic = true; // ✅ Явно указываем значение по умолчанию

    @ManyToOne
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute; // ✅ К какому атрибуту относится (например, "COLOR")

    @Column(nullable = false)
    private String value; // ✅ Значение (например, "Черный", "Nike", "42")
}
