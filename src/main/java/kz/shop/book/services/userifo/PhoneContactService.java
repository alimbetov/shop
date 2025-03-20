package kz.shop.book.services.userifo;


import kz.shop.book.dto.userInfo.PhoneContactDTO;
import kz.shop.book.entities.userinfo.PhoneContact;
import kz.shop.book.entities.userinfo.Profile;
import kz.shop.book.repository.userinfo.PhoneContactRepository;
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
public class PhoneContactService {
    private final PhoneContactRepository phoneContactRepository;

    private final UserService userService;
    /**
     * 🔍 Получает список номеров по `profileId`
     */
    public List<PhoneContactDTO> getContactsByProfile(UUID profileId) {
        return phoneContactRepository.findByProfileId(profileId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PhoneContactDTO> getContactsByTokenProfile(String token) {
        Profile profile = userService.getProfileByToken(token);
        if (profile == null) {
            throw new RuntimeException("Профиль не найден для токена: " + token);
        }
        return getContactsByProfile(profile.getId());
    }

    @Transactional
    public PhoneContactDTO addPhoneToProfile(String token,  PhoneContactDTO phoneDTO) {
        log.info("📞 Добавление номера в профиль: {}", phoneDTO.getPhoneNumber());
        Profile profile = userService.getProfileByToken(token);
        if (profile == null) {
            throw new RuntimeException("Профиль не найден для токена: " + token);
        };
        boolean exists = phoneContactRepository.existsByPhoneNumberAndProfile(phoneDTO.getPhoneNumber(), profile);
        if (exists) {
            log.warn("⚠️ Номер {} уже существует в профиле ID {}", phoneDTO.getPhoneNumber(), profile.getId());
            throw new RuntimeException("Этот номер уже добавлен");
        }
        PhoneContact phoneContact = convertToEntity(phoneDTO);
        phoneContact.setProfile(profile);
        return convertToDTO(phoneContactRepository.save(phoneContact));
    }

    @Transactional
    public PhoneContactDTO updatePhone(Long phoneId, PhoneContactDTO phoneDTO) {
        PhoneContact phoneContact = phoneContactRepository.findById(phoneId)
                .orElseThrow(() -> new RuntimeException("Телефон не найден"));

        phoneContact.setPhoneNumber(phoneDTO.getPhoneNumber());
        phoneContact.setIsPublic(phoneDTO.getIsPublic());
        phoneContact.setType(phoneDTO.getType());

        return convertToDTO(phoneContactRepository.save(phoneContact));
    }

    @Transactional
    public void deletePhone(Long phoneId) {
        phoneContactRepository.deleteById(phoneId);
    }

    /**
     * 🔄 Конвертация `PhoneContact` → `PhoneContactDTO`
     */
    private PhoneContactDTO convertToDTO(PhoneContact phone) {
        return PhoneContactDTO.builder()
                .id(phone.getId())
                .phoneNumber(phone.getPhoneNumber())
                .isPublic(phone.getIsPublic())
                .type(phone.getType())
                .build();
    }

    /**
     * 🔄 Конвертация `PhoneContactDTO` → `PhoneContact`
     */
    private PhoneContact convertToEntity(PhoneContactDTO dto) {
        PhoneContact phone = new PhoneContact();
        phone.setPhoneNumber(dto.getPhoneNumber());
        phone.setIsPublic(dto.getIsPublic());
        phone.setType(dto.getType());
        return phone;
    }
}
