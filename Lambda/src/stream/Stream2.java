package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Stream2 {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("��","��","��","��");
		
		IntStream.range(0, 10).limit(5).forEach(System.out::print);
		System.out.println();
		
		list.stream().distinct().forEach(System.out::print);
		System.out.println();
		
		list.stream().skip(2).forEach(System.out::print);
		System.out.println();
		
		list.stream().peek(str->System.out.print("peek : "+str+" "))
					 .filter(str->str.equals("��"))
					 .forEach(str->System.out.print("forEach : "+str+" "));
	}
}
/****���****
 * 01234
 * ������
 * ����
 * peek : �� forEach : �� peek : �� peek : �� peek : �� 
 ***********/