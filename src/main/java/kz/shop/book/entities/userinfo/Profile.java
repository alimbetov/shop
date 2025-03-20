package kz.shop.book.entities.userinfo;

import jakarta.persistence.*;
import kz.shop.book.entities.User;
import kz.shop.book.enums.CurrencyName;
import kz.shop.book.enums.LanguageName;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id; // ✅ Генерируемый UUID
    @Column(nullable = false, unique = true)
    private String profileName;
    @Column(nullable = true)
    private String profilePhotoUrl; // ✅ Фото профиля

    private String website;
    private Boolean isPublic = true;
    private Boolean websiteIsPublic = true;
    @Column(nullable = true, unique = false)
    private String instagram;

    private Boolean instagramIsPublic = true;


    private String email;
    private Boolean emailIsPublic = true;


    private String telegram;
    private Boolean telegramIsPublic = true;


    private Boolean trial = true;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private LanguageName preferredLanguage = LanguageName.EN; // ✅ Значение по умолчанию

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CurrencyName preferredCurrency = CurrencyName.USD; // ✅ Значение по умолчанию
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    // ✅ Связь с телефонами (Один профиль - много телефонов)
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PhoneContact> phoneContacts = new HashSet<>();



    // ✅ Связь с адресами (Один профиль - много адресов)
    @Builder.Default
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Address> addresses = new HashSet<>();
}
