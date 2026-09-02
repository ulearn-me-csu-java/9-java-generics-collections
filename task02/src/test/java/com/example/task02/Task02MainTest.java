package com.example.task02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class Task02MainTest {

    @TempDir
    File folder;

    private File file(String name) {
        return new File(folder, name);
    }

    @Test
    public void test1() throws Exception {

        File file = file("list1.dat");

        // create list
        SavedList<String> list = new SavedList<>(file);
        list.add("One");
        list.add("Two");
        list.add("Three");
        for (int i = 0; i < 100; i++) {
            list.add(1, "Hello " + i);
        }
        list.remove("Two");

        // check contents
        Assertions.assertTrue(list.contains("Hello 50"));
        Assertions.assertEquals(102, list.size());
        Assertions.assertEquals("One", list.get(0));
        Assertions.assertEquals("Hello 99", list.get(1));
        Assertions.assertEquals("Hello 0", list.get(100));

        // create another list
        File file2 = file("list1a.dat");
        SavedList<String> list2 = new SavedList<>(file2);
        list2.add("One");
        list2.add("Two");
        list2.add("Three");

        // retain
        list.retainAll(list2);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    public void test2() throws Exception {

        File file = file("list2.dat");

        // create list
        SavedList<String> list = new SavedList<>(file);
        list.add("One");
        list.add("Two");
        list.add("Three");
        for (int i = 0; i < 100; i++) {
            list.add(1, "Hello " + i);
        }
        list.remove("Two");
        list = null;

        // load list
        SavedList<String> loadedList = new SavedList<>(file);

        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(loadedList.contains("Hello 50"));
        Assertions.assertEquals(102, loadedList.size());
        Assertions.assertEquals("One", loadedList.get(0));
        Assertions.assertEquals("Hello 99", loadedList.get(1));
        Assertions.assertEquals("Hello 0", loadedList.get(100));
    }

    @Test
    public void test3() throws Exception {
        File file = file("list3.dat");

        // create list
        SavedList<String> list = new SavedList<>(file);
        list.add("One");
        list.add("Two");
        list.add("Three");
        for (int i = 0; i < 100; i++) {
            list.add(1, "Hello " + i);
        }
        list.remove("Two");

        // load list and remove elements
        SavedList<String> loadedList = new SavedList<>(file);
        loadedList.removeIf(next -> next.contains("8"));
        Assertions.assertEquals(83, loadedList.size());


        // reload
        list = new SavedList<>(file);
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertEquals(83, list.size());

        // remove file
        file.delete();

        // reload
        list = new SavedList<>(file);
        Assertions.assertTrue(list.isEmpty());
        Assertions.assertEquals(0, list.size());
    }


    @Test
    public void test4() throws Exception {
        File file = file("list4.dat");

        // create list
        SavedList<Integer> list = new SavedList<>(file);
        list.add(1);
        list.add(2);
        list.add(3);
        list.set(0, 999);
        list = null;

        // load list
        SavedList<Integer> loadedList = new SavedList<Integer>(file);

        Assertions.assertTrue(file.exists());
        Assertions.assertEquals(Integer.valueOf(999), loadedList.get(0));
        Assertions.assertEquals(Integer.valueOf(2), loadedList.get(1));
        Assertions.assertEquals(Integer.valueOf(3), loadedList.get(2));
        Assertions.assertEquals(3, loadedList.size());
    }

    @Test
    public void test5() throws Exception {
        File file = file("list5.dat");

        // no file - no elements
        SavedList<String> list = new SavedList<>(file);
        Assertions.assertFalse(file.exists());
        Assertions.assertTrue(list.isEmpty());

        // every single change must be written to the file at once
        list.add("One");
        Assertions.assertTrue(file.exists());
        Assertions.assertEquals(Collections.singletonList("One"), new SavedList<>(file));

        list.add(0, "Zero");
        Assertions.assertEquals(Arrays.asList("Zero", "One"), new SavedList<>(file));

        list.set(1, "Two");
        Assertions.assertEquals(Arrays.asList("Zero", "Two"), new SavedList<>(file));

        list.remove("Zero");
        Assertions.assertEquals(Collections.singletonList("Two"), new SavedList<>(file));

        list.clear();
        Assertions.assertEquals(Collections.emptyList(), new SavedList<>(file));
    }
}
