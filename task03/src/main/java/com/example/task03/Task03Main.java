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
        String line;

        Map<String, SortedSet<String>> set = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = bufferedReader.readLine()) != null) {
                String word = line.trim().toLowerCase();

                if (word.length() > 2 && word.matches("^[а-яё]+$")) {
                    char[] letters = word.toCharArray();
                    Arrays.sort(letters);
                    String sortedKey = new String(letters);

                    set.computeIfAbsent(sortedKey, k -> new TreeSet<>()).add(word);
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }

        List<Set<String>> result = new ArrayList<>();

        for (SortedSet<String> group : set.values()) {
            if (group.size() >= 2) {
                result.add(group);
            }
        }

        result.sort(Comparator.comparing(group -> group.iterator().next()));

        return result;
    }
}
