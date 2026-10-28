package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {
    private final File file;
    private final List<E> list;

    @SuppressWarnings("unchecked")
    public SavedList(File file) {
        this.file = file;

        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                this.list = (List<E>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Данные не загрузились");
            }
        } else {
            this.list = new ArrayList<>();
        }
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException("Данные не сохранились");
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
