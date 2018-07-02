package com.system.bhouse.bhouse;

import com.system.bhouse.bhouse.SingleList.SingleList;

import org.junit.Test;

/**
 * Created by Administrator on 2018-07-02.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse
 */

public class TestUnit {
    @Test
    public void UnitMain(){
        // 添加

        SingleList<String> stringSingleList = new SingleList<>();
        stringSingleList.add(new SingleList.Node("111",null));
        stringSingleList.add(new SingleList.Node("222",null));
        stringSingleList.add(new SingleList.Node("555",null));
        stringSingleList.add(new SingleList.Node("333",null));
        stringSingleList.add(new SingleList.Node("555",null));
        stringSingleList.add(new SingleList.Node("444",null));
        stringSingleList.add(new SingleList.Node("555",null));
        //尾部插入

        //删除是index 2 索引
//        stringSingleList.remove(2);

//        for (int i=0;i<stringSingleList.size;i++)
//        {
//            System.out.println(stringSingleList.get(i).toString());
//        }
//
//        Stack<SingleList.Node> objects = new Stack<SingleList.Node>();
//
//        for (int i=0;i<stringSingleList.size;i++)
//        {
//            objects.push(stringSingleList.get(i));
//        }
//
//        while (objects.peek()!=null)
//        {
//            System.out.println(objects.pop().toString());
//        }

        stringSingleList.deleteDuplication();

    }
 }
