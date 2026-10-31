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

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset){
        Set<String> allwords = new HashSet<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))){
            String line;
            while ((line = br.readLine()) != null){
                String[] words = line.split("\\s+");

                for(String word : words){
                    if (word.length() < 3){
                        continue;
                    }
                    if (!word.matches("[а-яА-ЯёЁ]+")){
                        continue;
                    }
                    allwords.add(word.toLowerCase());
                }
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }

        Map<String, Set<String>> anagramMap = new HashMap<>();
        for (String word : allwords){
            char[] arr = word.toCharArray();
            Arrays.sort(arr);
            String key = new String(arr);

            if (!anagramMap.containsKey(key)){
                anagramMap.put(key, new TreeSet<>());
            }
            anagramMap.get(key).add(word);
        }

        List<Set<String>> result = new ArrayList<>();
        for(Set<String> set : anagramMap.values()){
            if (set.size() >= 2){
                result.add(set);
            }
        }

        result.sort((set1, set2) -> {
            // Достаем первое слово из первого сета
            String firstWord1 = set1.iterator().next();
            // Достаем первое слово из второго сета
            String firstWord2 = set2.iterator().next();

            // Сравниваем эти слова. compareTo сортирует по алфавиту.
            return firstWord1.compareTo(firstWord2);
        });

        return result;
    }
}
