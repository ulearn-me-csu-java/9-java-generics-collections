package com.example.task03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, Set<String>> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim().toLowerCase();
                if (word.length() >= 3 && word.matches("[а-яё]+")) {
                    char[] chars = word.toCharArray();
                    Arrays.sort(chars);
                    String key = new String(chars);
                    map.computeIfAbsent(key, k -> new TreeSet<>()).add(word);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Set<String>> result = new ArrayList<>();
        for (Set<String> set : map.values()) {
            if (set.size() >= 2) {
                result.add(set);
            }
        }

        result.sort(Comparator.comparing(s -> s.iterator().next()));
        return result;
    }
}
