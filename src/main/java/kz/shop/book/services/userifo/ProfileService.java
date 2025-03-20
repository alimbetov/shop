package kz.shop.book.services.userifo;

import kz.shop.book.dto.location.CityLoacalDto;
import kz.shop.book.dto.userInfo.CurrencyDTO;
import kz.shop.book.dto.userInfo.LanguageDTO;
import kz.shop.book.dto.userInfo.ProfileDTO;
import kz.shop.book.entities.userinfo.Profile;
import kz.shop.book.enums.CurrencyName;
import kz.shop.book.enums.LanguageName;
import kz.shop.book.repository.userinfo.ProfileRepository;
import kz.shop.book.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j // ✅ Добавлено логирование
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final PhoneContactService phoneContactService;
    private final AddressService addressService;

    private final UserService userService;

public List<CityLoacalDto> getAllCityLoacalDto(String token){
    log.info("🔍 Поиск профиля по token: {}", token);
    Profile profile = userService.getProfileByToken(token);
    if (profile == null) {
        throw new RuntimeException("Профиль не найден для токена: " + token);
    }
    return addressService.getPublicCities(profile.getPreferredLanguage());
}
    public ProfileDTO getProfileDtoForOwner(String token) {
        log.info("🔍 Поиск профиля по token: {}", token);
        Profile profile = userService.getProfileByToken(token);
        if (profile != null) {
            return convertToDTO(profile);
        }
        throw new RuntimeException("Профиль не найден для токена: " + token);
    }
    public List<LanguageDTO> getLanguageDtoByOwner(String token) {
        log.info("🔍 Поиск профиля по token: {}", token);
        Profile profile = userService.getProfileByToken(token);
        if (profile == null) {
            throw new RuntimeException("Профиль не найден для токена: " + token);
        }
       return LanguageName.getAllLanguagesByLocale(profile.getPreferredLanguage());
    }
    public List<CurrencyDTO> getAllCurrencyDtoByOwner(String token) {
        Profile profile = userService.getProfileByToken(token);
        if (profile == null) {
            throw new RuntimeException("Профиль не найден для токена: " + token);
        }
        return CurrencyName.getAllCurrencyByLocale(profile.getPreferredLanguage());
    }

    @Transactional
    public ProfileDTO updateProfileByOwner(String token, ProfileDTO profileDTO) {
        log.info("📝 Обновление профиля: по токену {}", token);
        Profile profile = userService.getProfileByToken(token);
        if (profile == null) {
            throw new RuntimeException("Профиль не найден для токена: " + token);
        }
        profile.setProfileName(profileDTO.getProfileName());
        profile.setIsPublic(profileDTO.getIsPublic());
        updateEntity(profileDTO, profile);
        return convertToDTO(profileRepository.save(profile));
    }

    private void updateEntity(ProfileDTO profileDTO, Profile profile) {
        profile.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl() != null ? profileDTO.getProfilePhotoUrl() : "");
        profile.setWebsite(profileDTO.getWebsite() != null ? profileDTO.getWebsite() : "");
        profile.setInstagram(profileDTO.getInstagram() != null ? profileDTO.getInstagram() : "");
        profile.setEmail(profileDTO.getEmail() != null ? profileDTO.getEmail() : "");
        profile.setTelegram(profileDTO.getTelegram() != null ? profileDTO.getTelegram() : "");
        profile.setWebsiteIsPublic(profileDTO.getWebsiteIsPublic());
        profile.setInstagramIsPublic(profileDTO.getInstagramIsPublic());
        profile.setEmailIsPublic(profileDTO.getEmailIsPublic());
        profile.setTelegramIsPublic(profileDTO.getTelegramIsPublic());

        if (profileDTO.getPreferredLanguage() != null) {
            profile.setPreferredLanguage(LanguageName.getByCode(profileDTO.getPreferredLanguage().getCode()));
        }

        if (profileDTO.getPreferredCurrency() != null) {
            profile.setPreferredCurrency(CurrencyName.getByCode(profileDTO.getPreferredCurrency().getCode()));
        }

    }



    private ProfileDTO convertToDTO(Profile profile) {
       var currensyList = CurrencyName.getAllCurrencyByLocale(profile.getPreferredLanguage());
       var preferredLanguageDtoList = LanguageName.getAllLanguagesByLocale(profile.getPreferredLanguage());


        var currentCurrencyDTO = currensyList.stream()
                .filter(e -> e.getCode().equals(profile.getPreferredCurrency().getCode()))
                .findFirst()
                .orElse(CurrencyDTO.builder().code("USD").name("US Dollar").build());

        var preferredLanguageDTO = preferredLanguageDtoList.stream()
                .filter(e -> e.getCode().equals(profile.getPreferredLanguage().getCode()))
                .findFirst()
                .orElse(LanguageDTO.builder().code("en").name("English").build());


        return ProfileDTO.builder()
                .id(profile.getId())
                .profileName(profile.getProfileName())
                .isPublic(profile.getIsPublic())
                .profilePhotoUrl(profile.getProfilePhotoUrl())
                .website(profile.getWebsite())
                .websiteIsPublic(profile.getWebsiteIsPublic())
                .instagram(profile.getInstagram())
                .instagramIsPublic(profile.getInstagramIsPublic())
                .email(profile.getEmail())
                .emailIsPublic(profile.getEmailIsPublic())
                .telegram(profile.getTelegram())
                .telegramIsPublic(profile.getTelegramIsPublic())
                .preferredLanguage(preferredLanguageDTO)
                .preferredCurrency(currentCurrencyDTO)
                .userId(profile.getUser().getId())
                .phoneContacts(phoneContactService.getContactsByProfile(profile.getId()))
                .addresses(addressService.getAddressDtoListByProfile(profile.getId()))
                .build();
    }

}
