package com.example.task02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private final ArrayList<E> items = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public SavedList(File file) {
        this.file = file;

        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                items.addAll((List<E>) in.readObject());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(items);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public E get(int index) {
        return items.get(index);
    }

    @Override
    public E set(int index, E element) {
        E old = items.set(index, element);
        save();
        return old;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public void add(int index, E element) {
        items.add(index, element);
        modCount++;
        save();
    }

    @Override
    public E remove(int index) {
        E old = items.remove(index);
        modCount++;
        save();
        return old;
    }
}