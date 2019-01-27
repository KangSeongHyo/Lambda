package basic;


@FunctionalInterface
interface Func{
	int test(int x, int y);
}

public class Lambda1 {
	public static void main(String[] args) {
		Func rambda = (x,y)->x+y;
		System.out.println(rambda.test(1, 2));
	}
}
/****Ãâ·Â****
 * 3
 ***********/
