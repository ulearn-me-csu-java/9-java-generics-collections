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

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private ArrayList<E> list = new ArrayList<>();

    public SavedList(File file) {
        this.file = file;
        if (file.exists()) {
            load();
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
        save();
    }

    @Override
    public E remove(int index) {
        E removed = list.remove(index);
        save();
        return removed;
    }

    @SuppressWarnings("unchecked")
    private void load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            list = (ArrayList<E>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Cannot load list from " + file, e);
        }
    }

    private void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException("Cannot save list to " + file, e);
        }
    }
}
