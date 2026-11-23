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
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Task03Main
{

    private static final String DICTIONARY = "/singular.txt";
    private static final Charset DICTIONARY_CHARSET = Charset.forName("windows-1251");

    public static void main(String[] args) throws IOException
    {
        try (InputStream dictionary = Task03Main.class.getResourceAsStream(DICTIONARY))
        {
            List<Set<String>> anagrams = findAnagrams(dictionary, DICTIONARY_CHARSET);
            for (Set<String> anagram : anagrams)
            {
                System.out.println(anagram);
            }
        }
    }

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset)
    {
        Map<String, SortedSet<String>> groups = new HashMap<>();

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset));
            String line;

            while ((line = reader.readLine()) != null)
            {
                String word = line.toLowerCase();

                if (!isValidWord(word))
                {
                    continue;
                }

                String key = sortLetters(word);
                SortedSet<String> group = groups.get(key);

                if (group == null)
                {
                    group = new TreeSet<>();
                    groups.put(key, group);
                }

                group.add(word);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Не удалось прочитать слова из потока", e);
        }

        Map<String, Set<String>> setsByFirstWord = new TreeMap<>();

        for (SortedSet<String> group : groups.values())
        {
            if (group.size() >= 2)
            {
                setsByFirstWord.put(group.first(), group);
            }
        }

        return new ArrayList<>(setsByFirstWord.values());
    }

    private static boolean isValidWord(String word)
    {
        if (word.length() < 3)
        {
            return false;
        }

        for (int i = 0; i < word.length(); i++)
        {
            char letter = word.charAt(i);

            if ((letter < 'а' || letter > 'я') && letter != 'ё')
            {
                return false;
            }
        }

        return true;
    }

    private static String sortLetters(String word)
    {
        char[] letters = word.toCharArray();
        Arrays.sort(letters);

        return new String(letters);
    }
}
