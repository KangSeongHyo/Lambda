package basic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Lambda3 {

    public static void main(String[] args) {

        List<String> list = new LinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        for(String str : list) System.out.println(str);
        // �Ϲ� list forEach��

        list.forEach(i->System.out.println(i));
        // List Lambda forEach�� [Consumer]


        Map<Integer, String> map = new HashMap<>();
        map.put(1, "1��");
        map.put(2, "2��");
        map.put(3, "3��");

        map.forEach((key,value)-> System.out.println("key : "+key+" value : "+value));
        // Map Lambda forEach[BiConsumer]
 
        Set<String> set = new HashSet<>();
        set.add("��");
        set.add("��");
        set.add("��");
 
        set.forEach(i->System.out.println(i));
        // Set Lambda forEach�� [Consumer]
    }

    /****���****
     * 1
     * 2
     * 3
     * 1
     * 2
     * 3
     * key : 1 value : 1��
     * key : 2 value : 2��
     * key : 3 value : 3��
     * ��
     * ��
     * ��
     ***********/
 }
