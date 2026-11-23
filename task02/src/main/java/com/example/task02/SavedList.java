package com.example.task02;

import java.io.*;
import java.util.ArrayList;
import java.util.AbstractList;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private final ArrayList<E> list;

    public SavedList(File file) {
        this.file = file;
        this.list = new ArrayList<>();

        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                ArrayList<E> loaded = (ArrayList<E>) in.readObject();
                list.addAll(loaded);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(list);
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
