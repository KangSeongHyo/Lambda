# Stream

### map
- 중개연산 가장 많이 사용되는 메서드
- 종류로는 `.map()`, `.flatMap()` ,  `.mapToObj()`, `.mapToDouble()` `.mapToLong()`
- 로직에 따라 원하는 요소들을 하나의 Stream으로 만들어준다.
- flatMap은 여러 Stream을 하나의 Stream으로 만들어준다. (반환 값이 Stream)

### Stream 인터페이스
- Stream으로 변환되는 요소들을 서포트하는 인터페이스
- AutoBoxing, UnBoxing 문제가 있어 int, long 등은 따로 있는 IntStream, LongStream 등 인터페이스를 활용하면 된다.

***Note:*** AutoBoxing, UnBoxing 은 primitive형 변수와 reference형 변수 간에 형태에 맞춰 자동으로 변환 되는 것을 을 의미한다.

#### 사용법

```
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

```
## Etc

### 무한스트림
- 로직을 무한스트림으로 만들고 싶은 경우는 `.iterate()` 와 `.generate()`를 이용하면 된다.
- `.iterate()` 는 seed 값을 통해서 누적으로 연산을 진행한다.
- `.generate()` 는 단순히 내용을 반복한다. 매개변수가 없고 반환형만을 가지는 형태이다.

#### 사용법
```
public class Etc1 {
	public static void main(String[] args) {

	 IntStream intStream = IntStream.iterate(0, i->i+2).filter(i->!(i==0));
	 intStream.limit(3).forEach(i->System.out.print(i+" "));
	 System.out.println();

	IntStream intStream2 = IntStream.generate(()->3).filter(i->!(i==0));
	 intStream2.limit(3).forEach(i->System.out.print(i+" "));

	}

	/****출력****
	 * 2 4 6
	 * 3 3 3
	 ***********/
}
```

#### 링크
- [JAVA8 in Action_Part1](/JAVA8_in_Action_공부/Part1.md)

#### 참고
- Java의 정석 3rd Edition
