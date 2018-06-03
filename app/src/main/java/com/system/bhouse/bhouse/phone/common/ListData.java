package com.system.bhouse.bhouse.phone.common;

import java.io.Serializable;
import java.util.ArrayList;

public class ListData<T> implements Serializable {
    public static final String HAS_MORE = "hasmore";
    private static final long serialVersionUID = 6315534220215573857L;
    private boolean append = true;
    private ArrayList<T> list = new ArrayList();

    public void add(int i, T t) {
        this.list.add(i, t);
    }

    public void add(T t) {
        this.list.add(t);
    }

    public void add(ArrayList<T> arrayList) {
        this.list.addAll(arrayList);
    }

    public void clear() {
        this.list.clear();
        this.append = true;
    }

    public T get(int i) {
        return this.list.get(i);
    }

    public ArrayList<T> getArrayList() {
        return this.list;
    }

    public ArrayList<T> getListData() {
        return this.list;
    }

    public void insert(int i, T t) {
        this.list.add(i, t);
    }

    public boolean isAppend() {
        return this.append;
    }

    public void remove(int i) {
        this.list.remove(i);
    }

    public void setAppend(boolean z) {
        this.append = z;
    }

    public int size() {
        return this.list.size();
    }
}
