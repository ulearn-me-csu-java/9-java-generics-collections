package com.example.task03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
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
        Map<String, Set<String>> groups = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim().toLowerCase();

                if (word.length() < 3 || !word.matches("[а-яё]+")) {
                    continue;
                }

                char[] letters = word.toCharArray();
                Arrays.sort(letters);
                String key = new String(letters);

                groups.computeIfAbsent(key, k -> new TreeSet<>()).add(word);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        List<Set<String>> result = new ArrayList<>();
        for (Set<String> group : groups.values()) {
            if (group.size() >= 2) {
                result.add(group);
            }
        }

        result.sort((a, b) -> a.iterator().next().compareTo(b.iterator().next()));
        return result;
    }
}