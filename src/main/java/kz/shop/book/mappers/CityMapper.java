package kz.shop.book.mappers;


import kz.shop.book.dto.location.CityDto;
import kz.shop.book.entities.location.City;
import kz.shop.book.entities.location.Country;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityDto toDto(City city) {
        return CityDto.builder()
                .cityCode(city.getCityCode())
                .isPublic(city.getIsPublic())
                .nameRu(city.getNameRu())
                .nameKz(city.getNameKz())
                .nameEn(city.getNameEn())
                .countryCode(city.getCountry().getCountryCode())
                .build();
    }

    public City toEntity(CityDto dto, Country country) {
        return City.builder()
                .cityCode(dto.getCityCode())
                .isPublic(dto.getIsPublic())
                .nameRu(dto.getNameRu())
                .nameKz(dto.getNameKz())
                .nameEn(dto.getNameEn())
                .country(country) // ✅ Привязываем к стране
                .build();
    }
}
