package com.example.task03;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.*;
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
        Map<String, Set<String>> groups = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, charset))) {

            String word;

            while ((word = reader.readLine()) != null) {
                word = word.toLowerCase();

                if (word.length() < 3 || !word.matches("[а-яё]+")) {
                    continue;
                }

                char[] chars = word.toCharArray();
                Arrays.sort(chars);

                String key = new String(chars);

                groups.computeIfAbsent(key, k -> new TreeSet<>()).add(word);
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return groups.values().stream()
                .filter(group -> group.size() >= 2)
                .sorted(Comparator.comparing(Set::iterator, (a, b) ->
                        a.next().compareTo(b.next())))
                .collect(Collectors.toList());
    }
}
