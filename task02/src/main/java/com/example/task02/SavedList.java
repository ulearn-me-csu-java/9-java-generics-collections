package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {
    private final File file;
    private List<E> list = new ArrayList<>();

    public SavedList(File file) {
        this.file = file;
        // Если файл существует и не пустой, загружаем данные из него
        if (file.exists() && file.length() > 0) {
            loadFile();
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E previous = list.set(index, element);
        saveFile(); // Сохраняем после изменения
        return previous;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        saveFile();
    }

    @Override
    public E remove(int index) {
        E removed = list.remove(index);
        saveFile();
        return removed;
    }

    // Вспомогательный метод для чтения списка из файла
    @SuppressWarnings("unchecked")
    private void loadFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.list = (ArrayList<E>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Вспомогательный метод для сохранения списка в файл
    private void saveFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this.list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}