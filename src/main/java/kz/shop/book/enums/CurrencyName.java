package kz.shop.book.enums;

import kz.shop.book.dto.userInfo.CurrencyDTO;
import kz.shop.book.dto.userInfo.LanguageDTO;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum CurrencyName {
    // СНГ
    RUB("RUB", "Рубль", "Рубль", "Ruble"), // Россия
    KZT("KZT", "Тенге", "Тенге", "Tenge"), // Казахстан
    BYN("BYN", "Белорусский рубль", "Беларусь рублі", "Belarusian Ruble"), // Беларусь
    UAH("UAH", "Гривна", "Гривня", "Hryvnia"), // Украина
    AMD("AMD", "Драм", "Դրամ", "Dram"), // Армения
    AZN("AZN", "Манат", "Манат", "Manat"), // Азербайджан
    GEL("GEL", "Лари", "ლარი", "Lari"), // Грузия
    MDL("MDL", "Лей", "Лей", "Leu"), // Молдова
    TJS("TJS", "Сомони", "Сомонӣ", "Somoni"), // Таджикистан
    KGS("KGS", "Сом", "Сом", "Som"), // Кыргызстан
    UZS("UZS", "Сум", "Сўм", "Sum"), // Узбекистан
    TMT("TMT", "Новый Манат", "Täze Manat", "New Manat"), // Туркменистан

    // Китай
    CNY("CNY", "Юань", "Юань", "Yuan"),

    // Европа (основные валюты)
    EUR("EUR", "Евро", "Еуро", "Euro"), // Евросоюз
    GBP("GBP", "Фунт стерлингов", "Фунт стерлинг", "Pound Sterling"), // Великобритания
    CHF("CHF", "Швейцарский франк", "Швейцар франк", "Swiss Franc"), // Швейцария
    PLN("PLN", "Злотый", "Злотый", "Zloty"), // Польша
    HUF("HUF", "Форинт", "Форинт", "Forint"), // Венгрия
    CZK("CZK", "Чешская крона", "Чех крона", "Czech Koruna"), // Чехия
    RON("RON", "Румынский лей", "Румын лей", "Romanian Leu"), // Румыния
    SEK("SEK", "Шведская крона", "Швед крона", "Swedish Krona"), // Швеция
    NOK("NOK", "Норвежская крона", "Норвег крона", "Norwegian Krone"), // Норвегия
    DKK("DKK", "Датская крона", "Дат крона", "Danish Krone"), // Дания
    TRY("TRY", "Турецкая лира", "Түрік лирасы", "Turkish Lira"), // Турция

    // США
    USD("USD", "Доллар США", "АҚШ доллары", "US Dollar");

    private final String code; // Код валюты (ISO)
    private final String nameRu; // Название на русском
    private final String nameKz; // Название на казахском
    private final String nameEn; // Название на английском

    CurrencyName(String code, String nameRu, String nameKz, String nameEn) {
        this.code = code;
        this.nameRu = nameRu;
        this.nameKz = nameKz;
        this.nameEn = nameEn;
    }

    /**
     * ✅ Получить локализованное название валюты
     */
    public String getLocalizedName(String locale) {
        return switch (locale.toLowerCase()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn; // По умолчанию English
        };
    }

    /**
     * ✅ Получить список всех валют с локализованными названиями
     */
    public static List<String> getAllNamesByLocale(String locale) {
        return Arrays.stream(CurrencyName.values())
                .map(currency -> currency.getLocalizedName(locale))
                .collect(Collectors.toList());
    }

    /**
     * ✅ Получить объект валюты по коду (например, "USD")
     */

    public static CurrencyName getByCode(String code) {
        return Arrays.stream(CurrencyName.values())
                .filter(currency -> currency.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Валюта с кодом '" + code + "' не найдена"));
    }

    /**
     * ✅ Получить список всех валют в виде объектов (с кодами)
     */
    public static List<CurrencyDTO> getAllCurrencyByLocale(LanguageName locale) {
        return Arrays.stream(CurrencyName.values())
                .map(lang -> CurrencyDTO.builder().name(lang.getLocalizedName(locale.getCode()))
                        .code(lang.getCode()).build())
                .collect(Collectors.toList());
    }


}
