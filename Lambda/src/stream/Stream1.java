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
		
		System.out.println( "\n Stream ���� �ð� : " + (Stream_end-stream_start)+"ms");
		long parallelstream_start = System.currentTimeMillis(); 
		list.parallelStream().forEach(System.out::print);	
		long parallelstream_end = System.currentTimeMillis(); 
		
		System.out.println( "\n ParallelStream ���� �ð� : " + (parallelstream_end-parallelstream_start)+"ms");
		
	}
}
/****���****
 * 12345678
 *  Stream ���� �ð� : 57ms
 * 65784132
 * 	ParallelStream ���� �ð� : 5ms
 ***********/