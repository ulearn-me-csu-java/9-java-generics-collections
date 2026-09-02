package com.example.task03;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Task03MainTest {

    private static final Charset WINDOWS_1251 = Charset.forName("windows-1251");

    private void check(List<String> words, List<List<String>> result) {
        check(words, result, StandardCharsets.UTF_8);
    }

    private void check(List<String> words, List<List<String>> result, Charset charset) {
        String input = String.join("\n", words);
        Assertions.assertThat("" + Task03Main.findAnagrams(new ByteArrayInputStream(input.getBytes(charset)), charset))
                .as("Input: %s", input)
                .isEqualTo("" + result);
    }

    @Test
    public void testExample() {
        List<String> words = Arrays.asList(
                "трос",
                "накал",
                "рост",
                "чесотка",
                "сорт",
                "отсечка");

        List<List<String>> result = Arrays.asList(
                Arrays.asList("отсечка", "чесотка"),
                Arrays.asList("рост", "сорт", "трос")
        );

        check(words, result);
    }

    @Test
    public void test() {
        List<String> words = Arrays.asList(
                "Корт",
                "Варан",
                "КРОТ",
                "Навар"
        );

        List<List<String>> result = Arrays.asList(
                Arrays.asList("варан", "навар"),
                Arrays.asList("корт", "крот")
        );

        check(words, result);
    }

    @Test
    public void testOrder() {
        List<String> words = Arrays.asList(
                "тело",
                "корт",
                "лето",
                "апельсин",
                "крот",
                "спаниель"
        );

        List<List<String>> result = Arrays.asList(
                Arrays.asList("апельсин", "спаниель"),
                Arrays.asList("корт", "крот"),
                Arrays.asList("лето", "тело")
        );

        check(words, result);
    }

    @Test
    public void testCharset() {
        List<String> words = Arrays.asList(
                "Корт",
                "Варан",
                "КРОТ",
                "Навар"
        );

        List<List<String>> result = Arrays.asList(
                Arrays.asList("варан", "навар"),
                Arrays.asList("корт", "крот")
        );

        check(words, result, WINDOWS_1251);
    }

    @Test
    public void testErrors() {
        List<String> words = Arrays.asList(
                "а",
                "а",
                "б",
                "б",
                "ддд",
                "ддд",
                "ддд",
                "ддд",
                "ва",
                "ав",
                "Адрес",
                "адреС",
                "шляпа-котелок",
                "котелок-шляпа",
                "кот3лок",
                "к3телок",
                "",
                "",
                "qwerty",
                "ytrewq"
        );

        List<List<String>> result = Collections.emptyList();

        check(words, result);
    }

    @Test
    public void testEmpty() {
        List<String> words = Collections.emptyList();
        List<List<String>> result = Collections.emptyList();
        check(words, result);
    }
}
