package kz.shop.book.dto.userInfo;



import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {
    private UUID id;
    private String profileName;
    private Boolean isPublic = true;
    private String profilePhotoUrl; // ✅ Фото профиля
    private String website;
    private Boolean websiteIsPublic;
    private String instagram;
    private Boolean instagramIsPublic;
    private String email;
    private Boolean emailIsPublic;
    private String telegram;
    private Boolean telegramIsPublic;
    private Boolean trial;
    private LanguageDTO preferredLanguage;
    private CurrencyDTO preferredCurrency;
    private Long userId; // 🔥 Вместо User, храним user_id
    private List<PhoneContactDTO> phoneContacts;
    private List<AddressDTO> addresses;
}
