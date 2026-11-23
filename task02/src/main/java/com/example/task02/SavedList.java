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

public class SavedList<E extends Serializable> extends AbstractList<E>
{

    private final File file;
    private final List<E> items;

    public SavedList(File file)
    {
        this.file = file;
        this.items = load();
    }

    @Override
    public E get(int index)
    {
        return items.get(index);
    }

    @Override
    public E set(int index, E element)
    {
        E oldElement = items.set(index, element);
        save();

        return oldElement;
    }

    @Override
    public int size()
    {
        return items.size();
    }

    @Override
    public void add(int index, E element)
    {
        items.add(index, element);
        modCount++;
        save();
    }

    @Override
    public E remove(int index)
    {
        E removedElement = items.remove(index);
        modCount++;
        save();

        return removedElement;
    }

    @SuppressWarnings("unchecked")
    private List<E> load()
    {
        if (!file.exists())
        {
            return new ArrayList<>();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file)))
        {
            return (List<E>) inputStream.readObject();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Не удалось прочитать список из файла " + file, e);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Не удалось прочитать список из файла " + file, e);
        }
    }

    private void save()
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file)))
        {
            outputStream.writeObject(items);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Не удалось сохранить список в файл " + file, e);
        }
    }
}
