# Stream

## 정의

-	A sequence of elements supporting sequential and parallel aggregate operations
- 자료구조, 배열등의 요소를 추상화하여 lambda식을 사용하여 데이터를 다룰 수 있게 만든 Api

## 특징

-	기존의 Collection으로 데이터 처리하던 것을 Stream 함수로 대체한다.
-	파이프라인 방식의 **선언형** 프로그래밍을 할 수 있게 만들면서 코드의 명시성, **간결함** 을 높임
- 요소들을 읽어서 사용하기 때문에 내부 데이터를 변환하지 않는다.
- Stream으로 변환 -> 중개연산 -> 최종연산 3단계의 연산구조를 가진다.
- 재사용이 불가능하다.

### Collection과 Stream의 차이

- Collection은 연산식을 만날때마다 데이터를 처리한다.
- Stream은 최종연산 단계에서만 데이터를 처리한다.

## 연산

### Stream 변환

- ```.stream()``` :  Map, List, Set, 배열 등 자료구조의 요소를 추상화시킨다.(데이터흐름)
- ```.parallelStream()``` : 멀티쓰레드를 통해 연산을 병렬처리 한다.

#### 속도비교
```
public class Stream1 {

	public static void main(String[] args) {
		List<String> list = Arrays.asList(  "1","2","3","4",
				"5","6","7","8");
		long stream_start = System.currentTimeMillis();
		list.stream().forEach(System.out::print);
		long Stream_end = System.currentTimeMillis();

		System.out.println( "\n Stream 실행 시간 : "
		 											+ (Stream_end-stream_start)+"ms");

		long parallelstream_start = System.currentTimeMillis();
		list.parallelStream().forEach(System.out::print);
		long parallelstream_end = System.currentTimeMillis();

		System.out.println( "\n ParallelStream 실행 시간 : "
												+ (parallelstream_end-parallelstream_start)+"ms");

	}
}
/****출력****
 * 12345678
 *  Stream 실행 시간 : 57ms
 * 65784132
 * 	ParallelStream 실행 시간 : 5ms
 ***********/
```
***Note:*** 기존처럼 Thread 객체를 생성할 필요없이 선언만으로 간결한 병렬처리 가능

### 중개연산

- 연산결과가 Stream인 메서드를 의미한다.

| Method 이름| 반환형    |   형태    |    설명   	|
| :--------  | -------- | :-------- | :--------  |
| filter     | boolean  | Predicate | 조건식을 통해 true만 출력 |
| sorted     |<center>-</center>| Comparator| 정렬      	|
| limit      |<center>-</center>| long      | 반복횟수 제한|
| distinct 	 |<center>-</center>|<center>-</center>| 중복제거		|
| skip 			 |<center>-</center>| long | n까지 생략 |
| peek			 |<center>-</center>| Consumer | 전체 요소 반복|

#### 사용법
```
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
```
***Note:*** ``.peek()`` 는 최종연산이 없으면 실행되지 않는다.

### 최종연산

- 중개연산 단계를 거쳐 데이터를 처리하여 반환한다.
- 마지막에 한번만 사용가능하다.

| Method 이름| 반환형    |   형태    |    설명   	   |
| :--------  | -------- | :-------- | :--------     |
| forEach    |	void 		| Consumer  |	전체요소를 반복|
| count			 |  long		|<center>-</center>| 요소갯수 |
| max/min		 | Optional | Comparator| 최댓값/최소값|
| findFirst/Any	 | Optional |<center>-</center>| 첫번째요소/랜덤요소하나|
| all/any/none Match   | boolean  |<center>-</center>|전부/하나이상/없을 경우|
| reduce | < T > |BinaryOperator /<br> T identity, BinaryOperator| 원하지 않는 요소를 제거하면서 반복|
| collect | Collection | Collector/ Supplier, BiConsumer, BiConsumer| 결과를 Collection 형태로 반환 |

***Note:*** ``.reduce`` 의 identity는 초기 연산 값이다.

#### 사용법
```
public class Stream3 {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(5,8,6,7,4);

		boolean all = list.stream().allMatch(i->i==5);
		boolean any = list.stream().anyMatch(i->i==5);
		boolean none = list.stream().noneMatch(i->i==2);
		System.out.println(all + " " + any + " " +none);

		int res = list.stream().reduce((x,y)->x>y?x:y).get();
		System.out.println(res);
		int res2 = list.stream().reduce(10,(x,y)->x>y?x:y).intValue();
		System.out.println(res2);

		List<Integer> list2 = IntStream.range(0, 11)
									   .filter(i->i%2==0)
									   .mapToObj(i->i)
									   .collect(Collectors.toList());

		list2.stream().forEach(System.out::print);

	}
	/****출력****
	 * false true true
	 * 8
	 * 10
	 * 0246810
	 ***********/
}
```
#### 링크
- [Stream2](https://github.com/KangSeongHyo/Lambda/blob/master/Stream2.md)

#### 참고
- Java의 정석 3rd Edition
