package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final List<E> inMemoryData = new ArrayList<E>();
    private final File fileStore;

    public SavedList(File file) {
        this.fileStore = file;
        try {
            deserializeFromFile();
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot load SavedList from " + file, e);
        }
    }



    @Override
    public E get(int index) {
        return inMemoryData.get(index);
    }

    @Override
    public E set(int index, E element) {
        E old = inMemoryData.set(index, element);
        serializeToFile();
        return old;
    }

    @Override
    public int size() {
        return inMemoryData.size();
    }

    @Override
    public void add(int index, E element) {
        inMemoryData.add(index, element);
        serializeToFile();
    }

    @Override
    public E remove(int index) {
        E old = inMemoryData.remove(index);
        serializeToFile();
        return old;
    }


    private void deserializeFromFile() throws IOException {
        if (!fileStore.exists()) {
            return;
        }
        try (var ois = new ObjectInputStream(new FileInputStream(fileStore))) {
            List<E> loaded = (List<E>) ois.readObject();
            inMemoryData.clear();
            inMemoryData.addAll(loaded);
        } catch (EOFException eof) {
            inMemoryData.clear();
        } catch (ClassNotFoundException e) {
            throw new IOException("Cannot deserialize SavedList from " + fileStore, e);
        }
    }


    private void serializeToFile() {
        try (var oos = new ObjectOutputStream(new FileOutputStream(fileStore))) {
            oos.writeObject(inMemoryData);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot save SavedList to " + fileStore, e);
        }
    }
}
