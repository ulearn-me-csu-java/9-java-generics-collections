package com.example.task02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private final ArrayList<E> list = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public SavedList(File file) {
        this.file = file;
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<E> loaded = (List<E>) ois.readObject();
                list.addAll(loaded);
            } catch (Exception e) {
                // Ignore load error
            }
        }
    }

    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E prev = list.set(index, element);
        save();
        return prev;
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

    @Override
    public void clear() {
        list.clear();
        modCount++;
        save();
    }
}
