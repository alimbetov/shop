package kz.shop.book.repository.userinfo;

import kz.shop.book.entities.userinfo.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByProfileId(UUID profileId); // 🔥 Найти адреса по ID профиля

    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.latitude = :latitude, a.longitude = :longitude WHERE a.id = :id")
    int updateCoordinatesById(Long id, Double latitude, Double longitude);


    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.latitude = NULL, a.longitude = NULL WHERE a.id = :id")
    int clearCoordinatesById(Long id);
}