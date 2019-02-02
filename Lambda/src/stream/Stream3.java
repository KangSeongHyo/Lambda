package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Stream3 {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(5,8,6,7,4);
		
		boolean all = list.stream().allMatch(i->i==5);
		boolean any = list.stream().anyMatch(i->i==5);
		boolean none = list.stream().noneMatch(i->i==2);
		System.out.println(all + " " + any + " " +none);
		
		int res = list.stream().reduce((x,y)->x>y?x:y).get();
		System.out.println(res);
		int res2 = list.stream().reduce(10,(x,y)->x>y?x:y).intValue();
		System.out.println(res2);
		
		List<Integer> list2 = IntStream.range(0, 11)
									   .filter(i->i%2==0)
									   .mapToObj(i->i)
									   .collect(Collectors.toList());
		
		list2.stream().forEach(System.out::print);
		
	}
	/****Ãâ·Â****
	 * false true true
	 * 8
	 * 10
	 * 0246810
	 ***********/
}
