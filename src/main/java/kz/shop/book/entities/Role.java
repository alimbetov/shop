package kz.shop.book.entities;

import jakarta.persistence.*;
import kz.shop.book.enums.RoleName;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements GrantedAuthority { // ✅ Реализуем `GrantedAuthority`
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName name;

    @Override
    public String getAuthority() { // ✅ Spring Security требует этот метод
        return name.name();
    }

    public RoleName getName() { // ✅ Добавляем `getName()`
        return name;
    }


}
