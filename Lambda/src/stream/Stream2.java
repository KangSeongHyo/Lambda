package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Stream2 {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("가","나","나","다");
		
		IntStream.range(0, 10).limit(5).forEach(System.out::print);
		System.out.println();
		
		list.stream().distinct().forEach(System.out::print);
		System.out.println();
		
		list.stream().skip(2).forEach(System.out::print);
		System.out.println();
		
		list.stream().peek(str->System.out.print("peek : "+str+" "))
					 .filter(str->str.equals("가"))
					 .forEach(str->System.out.print("forEach : "+str+" "));
	}
}
/****출력****
 * 01234
 * 가나다
 * 나다
 * peek : 가 forEach : 가 peek : 나 peek : 나 peek : 다 
 ***********/