package kz.shop.book.controllers;


import kz.shop.book.dto.location.CityLoacalDto;
import kz.shop.book.dto.userInfo.*;
import kz.shop.book.services.userifo.ProfileService;
import kz.shop.book.services.userifo.PhoneContactService;
import kz.shop.book.services.userifo.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j // ✅ Логирование
public class UserProfileController {

    private final ProfileService profileService;
    private final PhoneContactService phoneContactService;
    private final AddressService addressService;


    /**
     * 🌐 Получить список городов  по токену
     */
    @GetMapping("/cities")
    public ResponseEntity<List<CityLoacalDto>> getAllCityLocalDto(@RequestHeader(name = "Authorization") String authHeader) {
        String token = extractToken(authHeader);
        return ResponseEntity.ok(profileService.getAllCityLoacalDto(token));
    }

    /**
     * 🔍 Получить профиль по токену
     */
    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(@RequestHeader(name = "Authorization") String authHeader) {
        String token = extractToken(authHeader);
        return ResponseEntity.ok(profileService.getProfileDtoForOwner(token));
    }

    /**
     * 🔄 Обновить профиль
     */
    @PutMapping
    public ResponseEntity<ProfileDTO> updateProfile(
            @RequestHeader(name = "Authorization") String authHeader,
            @RequestBody ProfileDTO profileDTO) {
        String token = extractToken(authHeader);
        return ResponseEntity.ok(profileService.updateProfileByOwner(token, profileDTO));
    }



    /**
     * 🌐 Получить список языков
     */
    @GetMapping("/languages")
    public ResponseEntity<List<LanguageDTO>> getLanguages(@RequestHeader(name = "Authorization") String authHeader) {
        String token = extractToken(authHeader);
        return ResponseEntity.ok(profileService.getLanguageDtoByOwner(token));
    }

    /**
     * 💰 Получить список валют
     */
    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyDTO>> getCurrencies(@RequestHeader(name = "Authorization") String authHeader) {
        String token = extractToken(authHeader);
        return ResponseEntity.ok(profileService.getAllCurrencyDtoByOwner(token));
    }

    /**
     * 📞 Получить список телефонов
     */
    @GetMapping("/phones")
    public ResponseEntity<List<PhoneContactDTO>> getPhoneContacts(@RequestHeader(name = "Authorization") String authHeader) {
        String token = extractToken(authHeader);
        return ResponseEntity.ok(phoneContactService.getContactsByTokenProfile(token));
    }

    /**
     * ➕ Добавить телефон
     */
    @PostMapping("/phones")
    public ResponseEntity<PhoneContactDTO> addPhone(
            @RequestHeader(name = "Authorization") String authHeader,
            @RequestBody PhoneContactDTO phoneDTO) {
        String token = extractToken(authHeader);
        return ResponseEntity.ok(phoneContactService.addPhoneToProfile(token, phoneDTO));
    }

    /**
     * ✏ Обновить телефон
     */
    @PutMapping("/phones/{phoneId}")
    public ResponseEntity<PhoneContactDTO> updatePhone(
            @PathVariable Long phoneId,
            @RequestBody PhoneContactDTO phoneDTO) {
        return ResponseEntity.ok(phoneContactService.updatePhone(phoneId, phoneDTO));
    }

    /**
     * 🗑 Удалить телефон
     */
    @DeleteMapping("/phones/{phoneId}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long phoneId) {
        phoneContactService.deletePhone(phoneId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 🏠 Получить список адресов
     */
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses(@RequestHeader(name = "Authorization") String authHeader) {
        String token = extractToken(authHeader);
        return ResponseEntity.ok(addressService.getAddressDtoListByToken(token));
    }

    /**
     * ➕ Добавить адрес
     */
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> addAddress(
            @RequestHeader(name = "Authorization") String authHeader,
            @RequestBody AddressDTO addressDTO) {
        String token = extractToken(authHeader);
        return ResponseEntity.ok(addressService.addAddressToProfileByToken(token, addressDTO));
    }

    /**
     * ✏ Обновить адрес
     */
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @RequestHeader(name = "Authorization") String authHeader,
            @PathVariable Long addressId,
            @RequestBody AddressDTO addressDTO) {
        String token = extractToken(authHeader);
        addressDTO.setId(addressId);
        return ResponseEntity.ok(addressService.updateAddressByToken(token, addressDTO));
    }

    /**
     * 🗑 Удалить адрес
     */
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 🎯 Вспомогательный метод: Извлекает токен из заголовка Authorization
     */
    private String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Токен отсутствует или некорректен");
        }
        return authHeader.substring(7); // Убираем "Bearer "
    }
}
