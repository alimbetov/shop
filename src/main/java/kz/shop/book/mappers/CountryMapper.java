package kz.shop.book.mappers;



import kz.shop.book.dto.location.CountryDto;
import kz.shop.book.entities.location.Country;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CountryMapper {

    public CountryDto toDto(Country country) {
        return CountryDto.builder()
                .countryCode(country.getCountryCode())
                .isPublic(country.getIsPublic())
                .nameRu(country.getNameRu())
                .nameKz(country.getNameKz())
                .nameEn(country.getNameEn())
                .cities(country.getCities().stream()
                        .map(city -> city.getCityCode()) // ✅ Сохраняем только коды городов
                        .collect(Collectors.toList()))
                .build();
    }

    public Country toEntity(CountryDto dto) {
        return Country.builder()
                .countryCode(dto.getCountryCode())
                .isPublic(dto.getIsPublic())
                .nameRu(dto.getNameRu())
                .nameKz(dto.getNameKz())
                .nameEn(dto.getNameEn())
                .build(); // ✅ Города добавляются отдельно
    }
}

