package basic;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Lambda2 {

	public static void main(String[] args) {
		String str ="hello";
		Supplier<String> sup = ()->str;
		System.out.println(sup.get());
		
		Consumer<Integer> con = (x)->System.out.println(x);
		con.accept(1);
		
		Function<Integer, String> func = (x)->x+" 입니다";
		System.out.println(func.apply(7));
		
		Predicate<String> pred = (x)->x.length() > 2;
		System.out.println(pred.test(str));
		System.out.println(pred.test("w"));
		
		String str2 ="world";
		BiConsumer<String, String> bicon = (x,y)-> System.out.println(x+" "+y);
		bicon.accept(str, str2);
		
		BiFunction<Integer, Integer, String> bifunc = (x,y)-> x+y+" 입니다";
		System.out.println(bifunc.apply(1, 2));
		
		BiPredicate<String, String> biperd = (x,y)-> x.equals(y);
		System.out.println(biperd.test(str, str2));
	}
}
/****출력****
 * hello
 * 1
 * 7 입니다
 * true
 * false
 * hello world
 * 3 입니다
 * false
 ***********/
