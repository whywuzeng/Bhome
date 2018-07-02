package com.system.bhouse.bhouse.MyArrayList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Administrator on 2018-06-29.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.MyArrayList
 */

public class MyArrayList<E>  {

    private  static final int default_size=10;

    transient Object[] objects;

    private int size;

    public static final Object[] empty_data={};


    private MyArrayList(int size){
        objects=new Object[size];
    }

    private MyArrayList(Collection<? extends E> c){
        objects=c.toArray();
        if ((size=objects.length)!=0)
        {
            if (!(objects instanceof Object[]))
            {
               this.objects = Arrays.copyOf(this.objects, size, Object[].class);
            }
        }else {
            objects=empty_data;
        }
    }

    public Iterator<E> iterator(){
        return new Itr();
    }

    private int size(){
        return 0;
    }

    private E get(int i){
        return null;
    }

    private void remove(int i)
    {

    }

    private  class Itr implements Iterator<E> {
        //最近调用next或者pre 的索引
        int lastRet=-1;

        //随后调用下一个元素的索引。
         int cursor=0;



        @Override
        public boolean hasNext() {
            return cursor!=size();
        }

        @Override
        public E next() {
            try {
                int i = cursor;
                E t = get(i);
                lastRet = i;
                cursor++;
                return t;

            } catch (IndexOutOfBoundsException e) {

            }
            return null;
        }

        public void remove(){
            if (lastRet<0)
            {

            }

            MyArrayList.this.remove(lastRet);
            if (lastRet<cursor)
            {
                cursor--;
            }
            lastRet=-1;

        }
    }




}
