package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private final List<E> list = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public SavedList(File file) {
        this.file = file;
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                list.addAll((List<E>) in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Не удалось загрузить список из файла", e);
            }
        }
    }

    private void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(new ArrayList<>(list));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E old = list.set(index, element);
        save();
        return old;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        modCount++;
        save();
    }

    @Override
    public E remove(int index) {
        E removed = list.remove(index);
        modCount++;
        save();
        return removed;
    }
}
