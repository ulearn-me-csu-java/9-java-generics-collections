
package com.example.task02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Objects;

public class SavedList<E extends java.io.Serializable> extends AbstractList<E> {

    private final File file;
    private final ArrayList<E> elements;

    public SavedList(File file) {
        this.file = Objects.requireNonNull(file);
        this.elements = load();
    }

    @SuppressWarnings("unchecked")
    private ArrayList<E> load() {
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(file))) {

            return (ArrayList<E>) input.readObject();

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private void save() {
        try (ObjectOutputStream output =
                     new ObjectOutputStream(new FileOutputStream(file))) {

            output.writeObject(elements);

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
        E oldElement = elements.set(index, element);
        save();
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        elements.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        E removedElement = elements.remove(index);
        save();
        return removedElement;
    }
}
