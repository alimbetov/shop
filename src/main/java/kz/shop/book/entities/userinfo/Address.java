package kz.shop.book.entities.userinfo;

import jakarta.persistence.*;
import kz.shop.book.entities.location.City;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Boolean isPublic = true; // ✅ Явно указываем значение по умолчанию

    @Column(nullable = false)
    private String streetAddress; // ✅ Улица и номер дома

    @Column(nullable = false)
    private String postalCode; // ✅ Почтовый индекс

    @Column
    private Double latitude; // ✅ Широта (например, 43.2567)

    @Column
    private Double longitude; // ✅ Долгота (например, 76.9286)

    @ManyToOne
    @JoinColumn(name = "city_code", nullable = false)
    private City city; // ✅ Связь с городом

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;
}
