package com.example.task03;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
        String text = "";
        try {
            text = new String(inputStream.readAllBytes(), charset);
        } catch (IOException e) {
            return new ArrayList<>();
        }

        String[] words = text.split("\\s+");

        Set<String> uniqueWords = new HashSet<>();
        for (String word : words) {
            String lowerWord = word.toLowerCase();
            if (lowerWord.length() >= 3 && lowerWord.matches("[а-яё]+")) {
                uniqueWords.add(lowerWord);
            }
        }

        Map<String, Set<String>> map = new HashMap<>();
        for (String word : uniqueWords) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            map.computeIfAbsent(key, k -> new TreeSet<>()).add(word);
        }

        List<Set<String>> result = new ArrayList<>();
        for (Set<String> group : map.values()) {
            if (group.size() >= 2) {
                result.add(group);
            }
        }

        for (Set<String> set : result) {
            List<String> sortedList = new ArrayList<>(set);
            Collections.sort(sortedList);
            set.clear();
            set.addAll(sortedList);
        }

        result.sort((set1, set2) -> {
            String firstWord1 = set1.iterator().next();
            String firstWord2 = set2.iterator().next();
            return firstWord1.compareTo(firstWord2);
        });
        return result;
    }
}
