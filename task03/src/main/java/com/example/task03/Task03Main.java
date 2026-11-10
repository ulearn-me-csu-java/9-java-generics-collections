package com.example.task03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
        Map<String, TreeSet<String>> groups = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim().toLowerCase();
                if (!isValid(word)) {
                    continue;
                }
                String key = sortLetters(word);
                if (!groups.containsKey(key)) {
                    groups.put(key, new TreeSet<>());
                }
                groups.get(key).add(word);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, Set<String>> sorted = new TreeMap<>();
        for (TreeSet<String> group : groups.values()) {
            if (group.size() >= 2) {
                sorted.put(group.first(), group);
            }
        }
        return new ArrayList<>(sorted.values());
    }

    private static boolean isValid(String word) {
        return word.length() >= 3 && word.matches("[а-яё]+");
    }

    private static String sortLetters(String word) {
        char[] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}
