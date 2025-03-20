package kz.shop.book.repository.userinfo;
import kz.shop.book.entities.userinfo.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUserId(Long userId); // 🔥 Поиск профиля по user_id

}