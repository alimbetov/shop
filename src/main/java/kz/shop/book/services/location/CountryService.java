package kz.shop.book.services.location;

import kz.shop.book.dto.location.CountryDto;
import kz.shop.book.entities.location.City;
import kz.shop.book.entities.location.Country;
import kz.shop.book.mappers.CountryMapper;
import kz.shop.book.repository.location.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    // ✅ Получить все страны (только публичные)
    public List<CountryDto> getAllCountries() {
        return countryRepository.findByIsPublicTrue()
                .stream()
                .map(countryMapper::toDto)
                .collect(Collectors.toList());
    }

    // ✅ Получить страну по коду
    public CountryDto getCountryByCode(String countryCode) {
        return countryRepository.findById(countryCode)
                .map(countryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Страна не найдена: " + countryCode));
    }

    // ✅ Создать новую страну
    public CountryDto createCountry(CountryDto dto) {
        if (countryRepository.existsById(dto.getCountryCode())) {
            throw new RuntimeException("Страна с таким кодом уже существует: " + dto.getCountryCode());
        }

        Country country = countryMapper.toEntity(dto);
        if (country.getCities()==null){
             List<City> list = new ArrayList<>();
            country.setCities(list);
        }

        return countryMapper.toDto(countryRepository.save(country));
    }

    // ✅ Обновить страну
    public CountryDto updateCountry(String countryCode, CountryDto dto) {
        Country country = countryRepository.findById(countryCode)
                .orElseThrow(() -> new RuntimeException("Страна не найдена: " + countryCode));

        country.setNameRu(dto.getNameRu());
        country.setNameKz(dto.getNameKz());
        country.setNameEn(dto.getNameEn());
        country.setIsPublic(dto.getIsPublic());
        if (country.getCities()==null){
            List<City> list = new ArrayList<>();
            country.setCities(list);
        }
        return countryMapper.toDto(countryRepository.save(country));
    }

    // ✅ Удалить страну (если в ней нет городов)
    public void deleteCountry(String countryCode) {
        Country country = countryRepository.findById(countryCode)
                .orElseThrow(() -> new RuntimeException("Страна не найдена: " + countryCode));

        if (!country.getCities().isEmpty()) {
            throw new RuntimeException("Нельзя удалить страну, в которой есть города");
        }

        countryRepository.delete(country);
    }

    public List<CountryDto> searchCountries(String query) {
        return countryRepository
                .findByCountryCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
                        query, query, query, query)
                .stream()
                .map(countryMapper::toDto)
                .collect(Collectors.toList());
    }
}
