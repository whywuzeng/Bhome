package com.system.bhouse.bhouse;

import com.system.bhouse.bhouse.Command.Light;

import org.junit.Test;

import java.util.HashSet;

/**
 * Created by Administrator on 2018-06-28.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse
 */

public class MianShiTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Light light = new Light();
        Light light1 = new Light();

//        if (light==light1)
//        {
//
//        }
//
//        if (light.equals(light1))
//        {
//
//        }

        /**
         * hashCode 作为对象 在比较取值的 -- 唯一值
         */

//        People jack = new People("Jack", 12);
//        System.out.println(jack.hashCode());
//
//        HashMap<People,Integer> map=new HashMap<>();
//        map.put(jack,1);
//
//        Integer integer = map.get(new People("Jack", 12));

        /**
         * String 不可变
         */
        HashSet<StringBuilder> stringBuilders = new HashSet<>();
        StringBuilder stringBuilder = new StringBuilder("aa");
        StringBuilder stringBuilder1 = new StringBuilder("bb");

        stringBuilders.add(stringBuilder);
        stringBuilders.add(stringBuilder1);

        StringBuilder sb3=stringBuilder;

        sb3.append("cc");

//        HashSet 键值唯一性

        System.out.println(stringBuilders);

    }


    class People{
        private String name;
        private int age;

        public People(String name,int age) {
            this.name = name;
            this.age = age;
        }

        public void setAge(int age){
            this.age = age;
        }

        @Override
        public int hashCode() {
            return name.hashCode()*37+age;
        }

        @Override
        public boolean equals(Object obj) {
            // TODO Auto-generated method stub
            return this.name.equals(((People)obj).name) && this.age== ((People)obj).age;
        }
    }
}
