package com.example.task03;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Task03Main {

    private static final String DICTIONARY = "/singular.txt";

    private static final Charset DICTIONARY_CHARSET = Charset.forName("windows-1251");

    public static void main(String[] args) throws IOException {

        try (InputStream dictionary = Task03Main.class.getResourceAsStream(DICTIONARY)) {
            List<Set<String>> anagrams = findAnagrams(dictionary, DICTIONARY_CHARSET);
            for (Set<String> anagram : anagrams) {
                System.out.println(anagram);
            }
        }

    }


    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset) {
        Map<String, Set<String>> groups = new TreeMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, charset))) {

            String word;

            while ((word = reader.readLine()) != null) {
                word = word.toLowerCase();

                // Исключаем слова короче 3 символов
                if (word.length() < 3) {
                    continue;
                }

                // Оставляем только русские буквы
                if (!word.matches("[а-яё]+")) {
                    continue;
                }

                // Создаём ключ: буквы слова в отсортированном порядке
                String key = word.chars()
                        .sorted()
                        .mapToObj(c -> String.valueOf((char) c))
                        .collect(Collectors.joining());

                // Добавляем слово в соответствующую группу
                groups.computeIfAbsent(key, k -> new TreeSet<>()).add(word);
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения словаря", e);
        }

        // Оставляем группы минимум с двумя словами
        List<Set<String>> result = new ArrayList<>();

        for (Set<String> group : groups.values()) {
            if (group.size() >= 2) {
                result.add(group);
            }
        }

        // Сортируем группы по первому слову
        result.sort((a, b) ->
                a.iterator().next().compareTo(b.iterator().next()));

        return result;
    }
}
