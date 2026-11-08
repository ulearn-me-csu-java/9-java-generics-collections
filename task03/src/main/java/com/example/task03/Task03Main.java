package com.example.task03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
        Set<String> uniqueWords = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim().toLowerCase();

                if (word.length() >= 3 && word.matches("[а-яё]+")) {
                    uniqueWords.add(word);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения потока", e);
        }

        Map<String, Set<String>> groups = new HashMap<>();

        for (String word : uniqueWords) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String signature = new String(chars);

            groups.computeIfAbsent(signature, k -> new TreeSet<>()).add(word);
        }

        List<Set<String>> result = new ArrayList<>();

        for (Set<String> group : groups.values()) {
            if (group.size() >= 2) {
                result.add(group);
            }
        }

        result.sort(Comparator.comparing(set -> set.iterator().next()));

        return result;
    }
}