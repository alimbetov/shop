package kz.shop.book.services.location;

import kz.shop.book.dto.location.CityDto;
import kz.shop.book.entities.location.City;
import kz.shop.book.entities.location.Country;
import kz.shop.book.mappers.CityMapper;
import kz.shop.book.repository.location.CityRepository;
import kz.shop.book.repository.location.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CityMapper cityMapper;

    public CityService(CityRepository cityRepository, CountryRepository countryRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.cityMapper = cityMapper;
    }

    // ✅ Получить список всех городов (только публичные)
    public List<CityDto> getAllCities() {
        return cityRepository.findByIsPublicTrue()
                .stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    // ✅ Получить список городов по стране
    public List<CityDto> getCitiesByCountry(String countryCode) {
        return cityRepository.findByCountry_CountryCode(countryCode)
                .stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    // ✅ Получить город по коду
    public CityDto getCityByCode(String cityCode) {
        return cityRepository.findById(cityCode)
                .map(cityMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Город не найден: " + cityCode));
    }

    // ✅ Создать новый город
    public CityDto createCity(CityDto dto) {
        if (cityRepository.existsById(dto.getCityCode())) {
            throw new RuntimeException("Город с таким кодом уже существует: " + dto.getCityCode());
        }

        Country country = countryRepository.findById(dto.getCountryCode())
                .orElseThrow(() -> new RuntimeException("Страна не найдена: " + dto.getCountryCode()));

        City city = cityMapper.toEntity(dto, country);
        return cityMapper.toDto(cityRepository.save(city));
    }

    // ✅ Обновить город
    public CityDto updateCity(String cityCode, CityDto dto) {
        City city = cityRepository.findById(cityCode)
                .orElseThrow(() -> new RuntimeException("Город не найден: " + cityCode));

        city.setNameRu(dto.getNameRu());
        city.setNameKz(dto.getNameKz());
        city.setNameEn(dto.getNameEn());
        city.setIsPublic(dto.getIsPublic());

        return cityMapper.toDto(cityRepository.save(city));
    }

    // ✅ Удалить город
    public void deleteCity(String cityCode) {
        City city = cityRepository.findById(cityCode)
                .orElseThrow(() -> new RuntimeException("Город не найден: " + cityCode));

        cityRepository.delete(city);
    }
    public List<CityDto> searchCities(String query) {
        return cityRepository
                .findByCityCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
                        query, query, query, query)
                .stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }
}
