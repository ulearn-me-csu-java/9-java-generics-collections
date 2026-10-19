package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {
    private static final long serialVersionUID = 7L;
    private final File file;
    private final List<E> list = new ArrayList<>();

    public SavedList(File file) {
        this.file = file;
        load();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E oldValue = list.set(index, element);
        save();
        return oldValue;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        E removed = list.remove(index);
        save();
        return removed;
    }

    private void load() {
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream objectIS = new ObjectInputStream(new FileInputStream(file))) {
            List<E> loadedList = (List<E>) objectIS.readObject();
            list.clear();
            list.addAll(loadedList);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Ошибка при загрузке списка");
        }
    }

    private void save() {
        try (ObjectOutputStream objectOS = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOS.writeObject(new ArrayList<>(list));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файла");
        }
    }
}
