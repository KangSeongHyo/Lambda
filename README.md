# Lambda

## 정의
자바8부터 추가된 함수형 언어 표현식

## 특징

* 메서드를 하나의 식으로 표현(Anonymous function)
* 기존의 자바보다 간결한 표현 가능
* 메서드 이름, 매개변수 자료형, 반환형을 생략하여 표현

### 기본 표현법

```
public int test(int x, int y){      
      return x+y;               =>  (x, y) -> x+y
}                                                                                      
```
***Note:*** return이 있을경우 {}는 생략 불가능 하다.

### @FunctionalInterface

* 함수형 메소드를 사용하기 위해서는 인터페이스가 필요하다.
* Interface 위에 위의 어노테이션을 붙이면 컴파일러가 함수형 언어를 표현을 확인해준다.
* 하나의 인터페이스에 하나의 메소드만 정의할 수 있다.

#### 사용법

```
@FunctionalInterface
Interface Func{
    int test(int x, int y);
}

public class Lambda1{
  public static void main(String[] args){
      Func rambda = (x,y)->x+y;
      System.out.println(rambda.test(1, 2));
  }
}
```

###  java.util.function 패키지
- 이 패키지에는 Lambda 식을 위한 함수형 Interface 있다.
- `Supplier`, `Consumer`, `Function`, `Predicate`  (매개변수 1개 이하)
- `BiConsumer`, `BiFunction`, `BiPredicate` (매개변수 2개)

| Interface 이름 | 매개변수 | 반환형 |
| :-------- | -------- | :-------- |
| Supplier | <center>-</center>  | Object |
| Consumer | Object1 | void |
| Function | Object1, Object2 | Object2 |
| Predicate | Object1 | boolean |
| BiConsumer | Object1, Object2 | void |
| BiFunction | Object1, Object2, Object | Object3 |
| BiPredicate | Object1, Object2 | boolean |


#### 사용법
```
public class Rambda2 {

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
```
### Map, List, Set

- forEach를 위한 lambda 메소드 추가

#### 사용법

```
 public class Lambda3 {

    public static void main(String[] args) {

        List<String> list = new LinkedList<>();

        list.add("1");
        list.add("2");        
	list.add("3");


        for(String str : list) System.out.println(str);
        // 일반 list forEach문


        list.forEach(i->System.out.println(i));
        // List Lambda forEach문 [Consumer]

        Map<Integer, String> map = new HashMap<>();

        map.put(1, "1번");
        map.put(2, "2번");
        map.put(3, "3번");


        map.forEach((key,value)-> System.out.println("key : "+key+" value : "+value));
        // Map Lambda forEach[BiConsumer]

        Set<String> set = new HashSet<>();

        set.add("가");
        set.add("나");
        set.add("다");

        set.forEach(i->System.out.println(i));

        // Set Lambda forEach문 [Consumer]

    }

    /****출력****
     * 1
     * 2
     * 3
     * 1
     * 2
     * 3
     * key : 1 value : 1번
     * key : 2 value : 2번
     * key : 3 value : 3번
     * 가
     * 다
     * 나
     ***********/
 }

```
#### 링크
- [Stream](https://github.com/KangSeongHyo/Lambda/blob/master/Stream.md)

#### 참고
- Java의 정석 3rd Edition
