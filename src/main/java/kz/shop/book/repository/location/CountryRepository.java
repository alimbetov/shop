package kz.shop.book.repository.location;


import kz.shop.book.entities.location.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {
    List<Country> findByIsPublicTrue(); // ✅ Найти только публичные страны
    List<Country> findByCountryCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
            String countryCode, String nameRu, String nameKz, String nameEn
    );
}
