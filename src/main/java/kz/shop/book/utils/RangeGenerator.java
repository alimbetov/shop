package kz.shop.book.utils;

import java.util.ArrayList;
import java.util.List;

public class RangeGenerator {

    public static List<String> generateRanges(String input, int maxRanges) {
        // Проверяем, что maxRanges в адекватных пределах
        if (maxRanges < 1 || maxRanges > 1000) {
            return new ArrayList<>(); // Неверное значение -> пустой список
        }

        // Разбираем строку "1:300:10"
        String[] parts = input.split(":");
        if (parts.length != 3) {
            return new ArrayList<>(); // Неверный формат -> пустой список
        }

        int start, end, step;

        try {
            start = Integer.parseInt(parts[0]);
            end = Integer.parseInt(parts[1]);
            step = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return new ArrayList<>(); // Если не число -> пустой список
        }

        // Проверка корректности границ
        if (start >= end || step <= 0) {
            return new ArrayList<>(); // Ошибки в значениях -> пустой список
        }

        // Если шаг больше всего диапазона, корректируем его
        if (step > (end - start)) {
            step = (end - start) / maxRanges;
            if (step < 1) step = 1; // Минимальный шаг 1
        }

        // Вычисляем количество диапазонов
        int rangeCount = (end - start) / step + 1;

        // Если диапазонов больше maxRanges, автоматически пересчитываем шаг
        if (rangeCount > maxRanges) {
            step = (int) Math.ceil((double) (end - start) / maxRanges);
        }

        // Если диапазонов слишком много, возвращаем пустой список
        if (rangeCount > 1000) {
            return new ArrayList<>();
        }

        List<String> ranges = new ArrayList<>();
        int count = 0; // Счётчик диапазонов

        for (int i = start; i <= end; i += step) {
            int rangeEnd = Math.min(i + step - 1, end);

            // Округляем границы диапазона для удобочитаемости
            int roundedStart = roundToNiceNumber(i);
            int roundedEnd = roundToNiceNumber(rangeEnd);

            // Коррекция, чтобы диапазон оставался непрерывным
            if (!ranges.isEmpty()) {
                int prevEnd = Integer.parseInt(ranges.get(ranges.size() - 1).split("-")[1]);
                roundedStart = prevEnd + 1;
            }

            ranges.add(roundedStart + "-" + roundedEnd);
            count++;

            // Ограничение по maxRanges
            if (count >= maxRanges) {
                break;
            }
        }

        return ranges;
    }

    // Метод округления к "красивому" числу (например, до десятков или сотен)
    private static int roundToNiceNumber(int num) {
        int magnitude = (int) Math.pow(10, Math.floor(Math.log10(num))); // Определяем порядок числа
        return (int) (Math.round((double) num / magnitude) * magnitude); // Округляем до удобного значения
    }


       /* // Пример 1: Корректные данные с maxRanges = 30
        System.out.println(generateRanges("1:300:10", 30));

        // Пример 2: Уменьшение количества диапазонов (maxRanges = 20)
        System.out.println(generateRanges("1:5000:50", 20));

        // Пример 3: Увеличение количества диапазонов (maxRanges = 50)
        System.out.println(generateRanges("1:1000:10", 50));

        // Пример 4: Неверные входные данные (отрицательный шаг)
        System.out.println(generateRanges("10:300:-5", 30));

        // Пример 5: Слишком большое количество диапазонов (ограничиваем)
        System.out.println(generateRanges("1:1000000:1", 30));

        // Пример 6: Неверные данные (не числа)
        System.out.println(generateRanges("abc:def:ghi", 30));*/

}
