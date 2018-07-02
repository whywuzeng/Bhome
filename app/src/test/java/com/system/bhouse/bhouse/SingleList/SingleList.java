package com.system.bhouse.bhouse.SingleList;

/**
 * Created by Administrator on 2018-07-02.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.SingleList
 */

public class SingleList<E> {

    //链表长度
    public int size;

    public Node first;

    public Node last;

    public static class Node<E> {
        E data;
        Node next;

        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public void add(int index, Node data) {
        if (index == 1) {
            Node pre = first;
            first = data;
            first.next = pre; //这里也有可能为空
        }
        else if (index == size) {
            lastAdd(data);

        }
        else {
            Node node = findNode(index - 1);
            Node pre = node.next;
            node.next = data;
            data.next = pre;
        }
        size++;
    }

    public void add(Node data) {
        //从尾部插入
        lastAdd(data);
    }


    public Node findNode(int index) {
        Node l = first;
        for (int i = 0; i < index; i++) {
            l = l.next;
        }
        return l;
    }

    public Node get(int index)
    {
        return findNode(index);
    }

    //尾部插入
    public void lastAdd(Node data) {
        Node temp = last;
        last = data;
        if (temp == null) {
            first = data;
//                Node node = findNode(size - 1);
//                node.next=last;
        }
        else {

            temp.next = last;
        }

        size++;
    }

    //删除
    public void remove(int index) {
        if (index == 1) {
            Node l= first;
            first= l.next;
        }
        else if (index == size-1) {
            Node node = findNode(index - 1);
            node.next=null;
            last=node;
        }
        else {
            Node node = findNode(index);
            Node pre = findNode(index - 1);
            pre.next = node.next;
            node = null;
        }
        size--;
    }

    public void deleteDuplication(){
        if (first==null)return;
        Node<E> pNode=first;
        while (pNode!=null)
        {
            E mdata= pNode.data;
            Node<E> tempNode= pNode.next;
            Node<E> next=pNode.next;

            boolean needDelete=false;
            while (tempNode!=null)
            {
                if (tempNode.data!=mdata)
                {
                    next =next.next;
                    tempNode=next;
                }else
                {
                    tempNode=tempNode.next;
                }
            }

            pNode= pNode.next;
        }
    }
}
