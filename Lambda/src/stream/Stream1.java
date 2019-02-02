package stream;

import java.util.Arrays;
import java.util.List;

public class Stream1 {

	public static void main(String[] args) {
		List<String> list = Arrays.asList(  "1","2","3","4",
				"5","6","7","8");
		long stream_start = System.currentTimeMillis(); 
		list.stream().forEach(System.out::print);	
		long Stream_end = System.currentTimeMillis(); 
		
		System.out.println( "\n Stream 실행 시간 : " + (Stream_end-stream_start)+"ms");
		long parallelstream_start = System.currentTimeMillis(); 
		list.parallelStream().forEach(System.out::print);	
		long parallelstream_end = System.currentTimeMillis(); 
		
		System.out.println( "\n ParallelStream 실행 시간 : " + (parallelstream_end-parallelstream_start)+"ms");
		
	}
}
/****출력****
 * 12345678
 *  Stream 실행 시간 : 57ms
 * 65784132
 * 	ParallelStream 실행 시간 : 5ms
 ***********/