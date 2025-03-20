package kz.shop.book.repository.location;

import kz.shop.book.entities.location.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, String> {

    // ✅ Найти все города, которые являются публичными
    List<City> findByIsPublicTrue();

    // ✅ Найти все города по коду страны
    List<City> findByCountry_CountryCode(String countryCode);

    // ✅ Поиск по разным параметрам (код города, название на разных языках)
    List<City> findByCityCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
            String cityCode, String nameRu, String nameKz, String nameEn
    );
}
