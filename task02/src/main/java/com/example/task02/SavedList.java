package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {
    private final File file;
    private final ArrayList<E> list = new ArrayList<>();

    public SavedList(File file) {
        this.file = file;
        load();
    }

    private void load() {
        if (!file.exists() || !file.isFile() || file.length() == 0) {
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                list.addAll((List<E>) obj);
            }
        } catch (EOFException e) {
            // пустой/битый файл — считаем, что элементов нет
        } catch (IOException | ClassNotFoundException e) {
            // повреждённый файл — тоже трактуем как пустой список
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E item = list.set(index, element);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(list);
        } catch (IOException e) {
            //
        }
        return item;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(list);
        } catch (IOException e) {
            //
        }
    }

    @Override
    public E remove(int index) {
        E element = list.remove(index);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(list);
        } catch (IOException e) {
            //
        }
        return element;
    }
}
