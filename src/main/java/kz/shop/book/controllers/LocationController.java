package kz.shop.book.controllers;


import kz.shop.book.dto.location.CityDto;
import kz.shop.book.dto.location.CountryDto;
import kz.shop.book.services.location.CityService;
import kz.shop.book.services.location.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geo")
@CrossOrigin(origins = "*") // ✅ Разрешаем CORS
public class LocationController {
    private final CountryService countryService;
    private final CityService cityService;

    public LocationController(CountryService countryService, CityService cityService) {
        this.countryService = countryService;
        this.cityService = cityService;
    }

    // ✅ Получить список всех стран
    @GetMapping("/countries")
    public List<CountryDto> getAllCountries() {
        return countryService.getAllCountries();
    }
    @GetMapping("/countries/search")
    public List<CountryDto> searchCountries(@RequestParam String query) {
        return countryService.searchCountries(query);
    }
    // ✅ Получить информацию о конкретной стране
    @GetMapping("/countries/{countryCode}")
    public CountryDto getCountry(@PathVariable String countryCode) {
        return countryService.getCountryByCode(countryCode);
    }

    // ✅ Создать новую страну
    @PostMapping("/countries")
    public CountryDto createCountry( @RequestBody CountryDto dto) {
        return countryService.createCountry(dto);
    }

    // ✅ Обновить информацию о стране
    @PutMapping("/countries/{countryCode}")
    public CountryDto updateCountry(@PathVariable String countryCode,  @RequestBody CountryDto dto) {
        return countryService.updateCountry(countryCode, dto);
    }

    // ✅ Удалить страну (если в ней нет городов)
    @DeleteMapping("/countries/{countryCode}")
    public void deleteCountry(@PathVariable String countryCode) {
        countryService.deleteCountry(countryCode);
    }


    // ✅ Получить список всех публичных городов
    @GetMapping("/cities")
    public List<CityDto> getAllCities() {
        return cityService.getAllCities();
    }

    // ✅ Получить список городов по стране
    @GetMapping("/cities/country/{countryCode}")
    public List<CityDto> getCitiesByCountry(@PathVariable String countryCode) {
        return cityService.getCitiesByCountry(countryCode);
    }


    // ✅ Получить информацию о конкретном городе
    @GetMapping("/cities/{cityCode}")
    public CityDto getCityByCode(@PathVariable String cityCode) {
        return cityService.getCityByCode(cityCode);
    }
    @GetMapping("/cities/search")
    public List<CityDto> searchCities(@RequestParam String query) {
        return cityService.searchCities(query);
    }
    // ✅ Создать новый город
    @PostMapping("/cities")
    public CityDto createCity( @RequestBody CityDto cityDto) {
        return cityService.createCity(cityDto);
    }

    // ✅ Обновить информацию о городе
    @PutMapping("/cities/{cityCode}")
    public CityDto updateCity(@PathVariable String cityCode,  @RequestBody CityDto cityDto) {
        return cityService.updateCity(cityCode, cityDto);
    }

    // ✅ Удалить город
    @DeleteMapping("/cities/{cityCode}")
    public void deleteCity(@PathVariable String cityCode) {
        cityService.deleteCity(cityCode);
    }
}
