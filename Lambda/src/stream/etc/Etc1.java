package stream.etc;

import java.util.stream.IntStream;

public class Etc1 {
	public static void main(String[] args) {
		
	 IntStream intStream = IntStream.iterate(0, i->i+2).filter(i->!(i==0));
	 intStream.limit(3).forEach(i->System.out.print(i+" "));
	 System.out.println();
	 
	IntStream intStream2 = IntStream.generate(()->3).filter(i->!(i==0));
	 intStream2.limit(3).forEach(i->System.out.print(i+" "));
	 
	}	
	
	/****Ãâ·Â****
	 * 2 4 6 
	 * 3 3 3 
	 ***********/
}
