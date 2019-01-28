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
        // 일반 list forEach문

        list.forEach(i->System.out.println(i));
        // List Lambda forEach문 [Consumer]


        Map<Integer, String> map = new HashMap<>();
        map.put(1, "1번");
        map.put(2, "2번");
        map.put(3, "3번");

        map.forEach((key,value)-> System.out.println("key : "+key+" value : "+value));
        // Map Lambda forEach[BiConsumer]
 
        Set<String> set = new HashSet<>();
        set.add("가");
        set.add("나");
        set.add("다");
 
        set.forEach(i->System.out.println(i));
        // Set Lambda forEach문 [Consumer]
    }

    /****출력****
     * 1
     * 2
     * 3
     * 1
     * 2
     * 3
     * key : 1 value : 1번
     * key : 2 value : 2번
     * key : 3 value : 3번
     * 가
     * 다
     * 나
     ***********/
 }
