package com.example.task03;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

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
        Map<String, Set<String>> groups = new HashMap<>();
        try (Scanner scanner = new Scanner(inputStream, charset)) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().toLowerCase(Locale.ROOT);
                if (!word.matches("[а-яё]{3,}")) {
                    continue;
                }

                char[] letters = word.toCharArray();
                Arrays.sort(letters);
                String key = new String(letters);
                Set<String> group = groups.get(key);
                if (group == null) {
                    group = new TreeSet<>();
                    groups.put(key, group);
                }
                group.add(word);
            }
        }

        List<Set<String>> result = new ArrayList<>();
        for (Set<String> group : groups.values()) {
            if (group.size() >= 2) {
                result.add(group);
            }
        }
        result.sort((firstGroup, secondGroup) -> {
            String firstWord = firstGroup.iterator().next();
            String secondWord = secondGroup.iterator().next();
            return firstWord.compareTo(secondWord);
        });
        return result;
    }
}
