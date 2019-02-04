package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Stream4 {
	public static void main(String[] args) {
		
		IntStream.range(1, 10).map(i-> i%2==0?i:0).forEach(System.out::print);
		System.out.println();
		
		List<String> list = Arrays.asList("���","�޷�","�ٳ���","Ű��");
		long num = list.stream().mapToInt(str->str.length()).filter(i->i==2).count();
		System.out.println(num);
		
		list.stream().flatMap(s->{
			Stream<String> str = Stream.of("����",s+" ");
			return str;
		}).forEach(System.out::print);
		System.out.println();
		
		Stream<String> str = Stream.of("��ī�� ","������ ","���̸� ","���α� ");
		Stream<List<String>> strlist = Stream.of(Arrays.asList("��ī��","������","���̸�","���α�"));
		str.forEach(System.out::print);
		System.out.println();
		strlist.forEach(System.out::print);
		
	}
	/****���****
	 * 020406080
	 * 3
	 * ������� �����޷� �����ٳ��� ����Ű�� 
	 * ��ī�� ������ ���̸� ���α� 
	 * [��ī��, ������, ���̸�, ���α�]
	 ***********/
}
