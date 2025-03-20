package kz.shop.book.enums;

import java.util.Arrays;

public enum RoleName {
    ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR, ROLE_INSPECTOR;

    // ✅ Метод для поиска роли по названию строки
    public static RoleName fromString(String name) {
        return Arrays.stream(RoleName.values())
                .filter(role -> role.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неизвестная роль: " + name));
    }
}
