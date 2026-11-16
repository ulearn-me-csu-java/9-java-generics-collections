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
        Set<String> words = new TreeSet<>();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            String line;
            while ((line = in.readLine()) != null) {
                String word = line.toLowerCase(Locale.ROOT);

                if (word.length() < 3 || !isRussianWord(word)) {
                    continue;
                }

                words.add(word);
            }
        } catch (IOException e) {
            //
        }

        Map<String, Set<String>> groups = new TreeMap<>();

        for (String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            groups.computeIfAbsent(key, k -> new TreeSet<>()).add(word);
        }

        List<Set<String>> result = new ArrayList<>();

        for (Set<String> group : groups.values()) {
            if (group.size() >= 2) {
                result.add(group);
            }
        }

        result.sort(Comparator.comparing(group -> group.iterator().next()));

        return result;
    }

    private static boolean isRussianWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (c < 'а' || c > 'я') {
                if (c != 'ё') {
                    return false;
                }
            }
        }
        return true;
    }
}
