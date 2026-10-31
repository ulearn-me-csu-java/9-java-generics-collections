package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {
    private File file;
    private List<E> list;

    public SavedList(File file) throws Exception {
        this.file = file;
        this.list = new ArrayList<>();

        if (file.exists()){
            try (ObjectInputStream ooj = new ObjectInputStream(new FileInputStream(file))){
                this.list = (List<E>) ooj.readObject();
            }
        }
    }

    private void saveToFile() throws Exception{
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.file))){
            oos.writeObject(this.list);
        }
    }

    @Override
    public E get(int index) {
        return this.list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E oldElement = this.list.set(index,element);
        try{
            saveToFile();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        return oldElement;
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public void add(int index, E element) {
        this.list.add(index, element);
        try{
            saveToFile();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public E remove(int index) {
        E removedElement = this.list.remove(index);
        try{
            saveToFile();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        return removedElement;
    }
}
