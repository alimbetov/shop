package kz.shop.book.entities.userinfo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phone_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String phoneNumber; // ✅ Сам номер

    @Column(nullable = false)
    private Boolean isPublic = true; // ✅ Явно указываем значение по умолчанию

    @Column(nullable = false)
    private String type; // ✅ Тип (например, "Мобильный", "Рабочий", "Домашний")

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;
}
