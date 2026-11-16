package com.example.task03;

import java.io.*;
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
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim().toLowerCase();
                if (word.length() < 3) {
                    continue;
                }
                if (!word.matches("[а-яё]+")) {
                    continue;
                }
                words.add(word);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        Map<String, Set<String>> groups = new HashMap<>();
        for (String word : words) {
            String key = sortLetters(word);
            Set<String> group = groups.get(key);
            if (group == null) {
                group = new TreeSet<>();
                groups.put(key, group);
            }
            group.add(word);
        }

        List<Set<String>> result = new ArrayList<>();
        for (Set<String> group : groups.values()) {
            if (group.size() >= 2) {
                result.add(group);
            }
        }

        Collections.sort(result, new Comparator<Set<String>>() {
            public int compare(Set<String> a, Set<String> b) {
                return a.iterator().next().compareTo(b.iterator().next());
            }
        });

        return result;
    }
    private static String sortLetters(String word) {
        char[] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}
