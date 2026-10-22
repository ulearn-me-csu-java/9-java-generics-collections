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
    private final List<E> elements = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public SavedList(File file) {
        this.file = file;
        if (file.exists()) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
                elements.addAll((List<E>) input.readObject());
            } catch (IOException | ClassNotFoundException e) {
                throw new IllegalStateException("Не удалось загрузить список", e);
            }
        }
    }

    @Override
    public E get(int index) {
        return elements.get(index);
    }

    @Override
    public E set(int index, E element) {
        E previous = elements.set(index, element);
        save();
        return previous;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(int index, E element) {
        elements.add(index, element);
        modCount++;
        save();
    }

    @Override
    public E remove(int index) {
        E removed = elements.remove(index);
        modCount++;
        save();
        return removed;
    }

    private void save() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(elements);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить список", e);
        }
    }
}
