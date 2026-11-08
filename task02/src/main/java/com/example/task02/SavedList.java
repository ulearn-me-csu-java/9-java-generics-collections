package com.example.task02;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractList;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private final List<E> list;


    public SavedList(File file) {
        this.file = file;
        this.list = new ArrayList<>();

        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<E> loadedList = (List<E>) ois.readObject();
                this.list.addAll(loadedList);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Не удалось загрузить список из файла", e);
            }
        }
    }


    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this.list);
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось сохранить список в файл", e);
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E oldElement = list.set(index, element);
        save();
        return oldElement;
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
        E removedElement = list.remove(index);
        save();
        return removedElement;
    }
}