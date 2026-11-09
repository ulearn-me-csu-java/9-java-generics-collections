package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private ArrayList<E> list;

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
        E previous = list.set(index, element);
        save();
        return previous;
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
        if (file == null || !file.exists() || file.length() == 0) {
            this.list = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.list = (ArrayList<E>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            this.list = new ArrayList<>();
        }
    }

    private void save() {
        if (file == null) {
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this.list);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save list to file: " + file.getPath(), e);
        }
    }
}