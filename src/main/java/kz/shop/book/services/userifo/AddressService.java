package kz.shop.book.services.userifo;

import kz.shop.book.dto.location.CityLoacalDto;
import kz.shop.book.dto.userInfo.AddressCoordinatesDTO;
import kz.shop.book.dto.userInfo.AddressDTO;
import kz.shop.book.entities.userinfo.Address;
import kz.shop.book.entities.userinfo.Profile;
import kz.shop.book.enums.LanguageName;
import kz.shop.book.repository.location.CityRepository;
import kz.shop.book.repository.userinfo.AddressRepository;
import kz.shop.book.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j // ✅ Добавлено логирование
public class AddressService {
    private final AddressRepository addressRepository;

    private final UserService userService;

    private final CityRepository cityRepository;

    public List<AddressDTO> getAddressDtoListByProfile(UUID profileId) {
        return addressRepository.findByProfileId(profileId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<AddressDTO> getAddressDtoListByToken(String token) {
        Profile profile = userService.getProfileByToken(token);
        if (profile == null) {
            throw new RuntimeException("Профиль не найден для токена: " + token);
        };
        return getAddressDtoListByProfile(profile.getId());
    }

    public AddressDTO addAddressToProfileByToken(String token, AddressDTO addressDTO) {
        Profile profile = userService.getProfileByToken(token);
        if (profile == null) {
            throw new RuntimeException("Профиль не найден для токена: " + token);
        }
        return addAddressToProfile(profile, addressDTO);
    }

    @Transactional
    public AddressDTO addAddressToProfile(Profile profile, AddressDTO addressDTO) {
        log.info("🏠 Добавление адреса в профиль: {}", profile.getId());

        if (addressDTO.getCityCode() == null || addressDTO.getCityCode().isBlank()) {
            log.warn("⚠️ Почтовый индекс не указан!");
            throw new IllegalArgumentException("Почтовый индекс обязателен");
        }
        var city =  cityRepository.findById(addressDTO.getCityCode());
        if (!city.isPresent()) {
            log.warn("⚠️ код города не правильно указан!");
            throw new IllegalArgumentException(" код города не правильно указан!");
        }
        Address address = convertToEntity(addressDTO);
        address.setProfile(profile);
        address.setCity(city.get());
        return convertToDTO(addressRepository.save(address));
    }


    public AddressDTO updateAddressByToken(String token, AddressDTO addressDTO) {
        Profile profile = userService.getProfileByToken(token);
        if (profile == null) {
            throw new RuntimeException("Профиль не найден для токена: " + token);
        };
        return updateAddress(profile, addressDTO);
    }
    @Transactional
    public AddressDTO updateAddress(Profile profile, AddressDTO addressDTO) {
        var city = cityRepository.findById(addressDTO.getCityCode()).orElse(null);
        Address address = addressRepository.findById(addressDTO.getId())


                .orElseThrow(() -> new RuntimeException("Адрес не найден"));
        if (address.getProfile().getId().equals(profile.getId())) {
            address.setStreetAddress(addressDTO.getStreetAddress());
            address.setPostalCode(addressDTO.getPostalCode());
            address.setLatitude(addressDTO.getLatitude());
            address.setLongitude(addressDTO.getLongitude());
            address.setIsPublic(addressDTO.getIsPublic());
        }
        if (city!=null){
        if (!address.getCity().getCityCode().equals(addressDTO.getCityCode())) {
            if (city==null) {
                log.warn("⚠️ код города не правильно указан!");
                throw new IllegalArgumentException(" код города не правильно указан!");
            } else {
                address.setCity(city);
            }
        }}
        return convertToDTO(addressRepository.save(address));
    }

    /**
     * 🗑 Удаляет адрес по ID
     */
    @Transactional
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    /**
     * 🔄 Конвертация `Address` → `AddressDTO`
     */
    private AddressDTO convertToDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .streetAddress(address.getStreetAddress())
                .postalCode(address.getPostalCode())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .isPublic(address.getIsPublic())
                .cityCode(address.getCity().getCityCode())
                .build();
    }

    /**
     * 🔄 Конвертация `AddressDTO` → `Address`
     */
    private Address convertToEntity(AddressDTO dto) {
        Address address = new Address();
        address.setStreetAddress(dto.getStreetAddress());
        address.setPostalCode(dto.getPostalCode());
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());
        address.setIsPublic(dto.getIsPublic());
        return address;
    }

    /**
     * 🗺 Обновляет координаты (широту и долготу) для адреса по ID
     */
    @Transactional
    public void updateCoordinates(Long addressId, Double latitude, Double longitude) {
        int updatedRows = addressRepository.updateCoordinatesById(addressId, latitude, longitude);
        if (updatedRows == 0) {
            throw new RuntimeException("Адрес с ID " + addressId + " не найден.");
        }
    }
    @Transactional
    public void clearCoordinates(Long addressId) {
        int updatedRows = addressRepository.clearCoordinatesById(addressId);
        if (updatedRows == 0) {
            throw new RuntimeException("Адрес с ID " + addressId + " не найден.");
        }
    }

    public AddressCoordinatesDTO getAddressCoordinatesDTOByProfile(Long adressId) {
        var address = addressRepository.findById(adressId).orElse(null);
        if (address!=null) {
         return    AddressCoordinatesDTO.builder()
                    .latitude(address.getLatitude())
                    .longitude(address.getLongitude())
                    .title(address.getStreetAddress().concat(" ".concat(address.getPostalCode())) )
                    .build();
        }
        return AddressCoordinatesDTO.builder().build();
    }


    public List<CityLoacalDto> getPublicCities(LanguageName preferredLanguage) {
        return cityRepository.findByIsPublicTrue().stream()
                .map(item -> CityLoacalDto.builder()
                        .cityCode(item.getCityCode())
                        .name(item.getLocalizedName(preferredLanguage))
                        .build()
                )
                .collect(Collectors.toList()); // Убрана лишняя скобка
    }

}