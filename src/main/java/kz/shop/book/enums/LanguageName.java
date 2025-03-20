package kz.shop.book.enums;

import java.util.Arrays;

import kz.shop.book.dto.userInfo.LanguageDTO;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum LanguageName {
    RU("ru", "Русский", "Орыс", "Russian"),
    KZ("kz", "Казахский", "Қазақ", "Kazakh"),
    EN("en", "Английский", "Ағылшын", "English");

    private final String code; // Код языка (ISO)
    private final String nameRu; // Название на русском
    private final String nameKz; // Название на казахском
    private final String nameEn; // Название на английском

    LanguageName(String code, String nameRu, String nameKz, String nameEn) {
        this.code = code;
        this.nameRu = nameRu;
        this.nameKz = nameKz;
        this.nameEn = nameEn;
    }

    // ✅ Метод для получения имени языка на нужном языке
    public String getLocalizedName(String locale) {
        return switch (locale.toLowerCase()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn; // По умолчанию English
        };
    }
    // ✅ Метод для получения имени языка на нужном языке
    public String getLocalizedName(LanguageName locale) {
        return switch (locale.code.toLowerCase()) {
            case "ru" -> nameRu;
            case "kz" -> nameKz;
            case "en" -> nameEn;
            default -> nameEn; // По умолчанию English
        };
    }

    // ✅ Метод для получения списка всех языков в нужной локали
    public static List<String> getAllLanguagesByLocale(String locale) {
        return Arrays.stream(LanguageName.values())
                .map(lang -> lang.getLocalizedName(locale))
                .collect(Collectors.toList());
    }
        public static List<LanguageDTO> getAllLanguagesByLocale(LanguageName locale) {
        return Arrays.stream(LanguageName.values())
                .map(lang -> LanguageDTO.builder().name(lang.getLocalizedName(locale.code))
                        .code(lang.getCode()).build())
                .collect(Collectors.toList());
    }
    public static LanguageName getByCode(String code) {
        return Arrays.stream(LanguageName.values())
                .filter(lang -> lang.code.equalsIgnoreCase(code)) // Сравниваем коды без учёта регистра
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Язык с кодом '" + code + "' не найден"));
    }
}

