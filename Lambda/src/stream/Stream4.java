package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Stream4 {
	public static void main(String[] args) {
		
		IntStream.range(1, 10).map(i-> i%2==0?i:0).forEach(System.out::print);
		System.out.println();
		
		List<String> list = Arrays.asList("사과","메론","바나나","키위");
		long num = list.stream().mapToInt(str->str.length()).filter(i->i==2).count();
		System.out.println(num);
		
		list.stream().flatMap(s->{
			Stream<String> str = Stream.of("작은",s+" ");
			return str;
		}).forEach(System.out::print);
		System.out.println();
		
		Stream<String> str = Stream.of("피카츄 ","라이츄 ","파이리 ","꼬부기 ");
		Stream<List<String>> strlist = Stream.of(Arrays.asList("피카츄","라이츄","파이리","꼬부기"));
		str.forEach(System.out::print);
		System.out.println();
		strlist.forEach(System.out::print);
		
	}
	/****출력****
	 * 020406080
	 * 3
	 * 작은사과 작은메론 작은바나나 작은키위 
	 * 피카츄 라이츄 파이리 꼬부기 
	 * [피카츄, 라이츄, 파이리, 꼬부기]
	 ***********/
}
