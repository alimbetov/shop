package kz.shop.book.repository.userinfo;


import kz.shop.book.entities.userinfo.PhoneContact;
import kz.shop.book.entities.userinfo.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhoneContactRepository extends JpaRepository<PhoneContact, Long> {
    List<PhoneContact> findByProfileId(UUID profileId); // 🔥 Найти телефоны по ID профиля

    boolean existsByPhoneNumberAndProfile(String phoneNumber, Profile profile);
}
