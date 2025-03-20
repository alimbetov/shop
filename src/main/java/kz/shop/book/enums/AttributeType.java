package kz.shop.book.enums;


public enum AttributeType {
    STRING,      // ✅ Текст (например, "Черный", "Nike")
    NUMBER,      // ✅ Число (например, 42, 1500)
    BOOLEAN,     // ✅ Да/Нет (например, "Новый", "Гарантия")
    ENUM,        // ✅ Выбор одного значения (например, "M", "L", "XL")
    MULTISELECT  // ✅ Несколько значений (например, "Android, iOS")
}