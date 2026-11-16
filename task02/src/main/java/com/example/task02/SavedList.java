package com.example.task02;

import java.io.EOFException;
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
    private final List<E> elements;

    public SavedList(File file) {
        this.file = file;
        this.elements = new ArrayList<>();

        if (file.isFile() && file.length() > 0) {
            load();
        }
    }

    private void load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof List) {
                List<E> loaded = (List<E>) obj;
                elements.addAll(loaded);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Не удалось загрузить список", e);
        }
    }

    private void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(new ArrayList<>(elements));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    @Override
    public E get(int index) {
        return elements.get(index);
    }
    @Override
    public int size() {
        return elements.size();
    }
    @Override
    public E set(int index, E element) {
        E old = elements.set(index, element);
        save();
        return old;
    }
    @Override
    public void add(int index, E element) {
        elements.add(index, element);
        save();
    }
    @Override
    public E remove(int index) {
        E old = elements.remove(index);
        save();
        return old;
    }
}