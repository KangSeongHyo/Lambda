# JAVA8 in Action - part2

## Chapter 4 - Steam 소개

### 4.1 스트림이란
- 자바 API에 새로 추가된 기능, **선언형** 으로 컬렉션 데이터를 처리 할 수 있다.
- 특징 : 선언형, 조립, 병렬화

### 4.2 스트림시작하기
- 스트림이란 데이터 처리 연산을 지원하도록 소스에서 추출된 **연속된 요소**
  * 연속된 요소 : 컬렉션의 주제는 **데이터** 이고 스트림의 주제는 **계산** 이다.
    > 컬렉션에서는 ArrayList, LinkedList 둘 중 어떤 것을 쓸건지에 대한 시간&공간의 복잡성과 관련된 요소 저장 및 접근 연산이 주이다. 스트림은 filter, map처럼 계산식이 있다.

  * 소스 : 리스트의 요소와 같은 순서를 유지
  * 파이프라이닝 : 스트림 연산끼리 연결해 커다란 파이프라인을 만들 수 있게 스트림 자신을 반환
  * 내부반복 : 스트림 내부에서 반복이 이루어 진다.

- Stream의 데이터 처리연산
 * ``filter`` : 람다를 인수로 받아 특정요소 제거
 * ``map`` : 람다를 이용해서 한 요소를 다른 요소로 변환하거나 정보를 추출
 * ``limit`` : 스트림 크기를 축소한다.
 * ``collet`` : 스트림을 다른 형식으로 변환

### 4.3 스트림과 컬렉션
- 둘다 순차적으로 값을 저장하는 자료구조 인테페이스를 제공
- 가장 큰 차이는 데이터를 언제 계산하는지에 대한 차이점이 있다.
  * 컬렉션의 모든 요소는 추가하기 전에 계산되어야 한다.(DVD)
    > 추가 삭제시 모든 요소를 메모리에 저장해야한다.

  * 스트림은 요청할때만 요소를 계산한다. (스트리밍)

- 스트림은 한 번만 탐색할 수 있다. (=Iterator)
- 컬렉션은 **외부반복** (반복자가 데이터를 가져와서 처리)이고 스트림은 **내부반복** 이다.
- 외부반복은 병렬성을 스스로 관리 해야한다.

### 4.4 스트림 연산
- 서로 파이프라인을 형성할 수 있는 스트림 연산을 중간연산, 닫는 연산을 최종연산이라 한다.
- 중간연산
  * 다른 스트림을 반환 할 수 있게 때문에 원하는 질의로 스트림을 만들 수 있다.
  * 하나씩 순차적으로 데이터가 연산들 모두 통하고 다음 데이터를 처리하는 방식이다.
- 최종연산
  * 중간 파이프라인에서 넘어온 것으로 부터 결과를 도출한다.

## Chapter 5 - 스트림 활용

### 5.1.0 필터링과 슬라이싱
- 데이터를 어떻게 처리할 것인지를 스트림 API가 알아서 처리한다.
- 필터링의 종류
  * 프리디케이트 : boolean형을 인수로 받아 처리
  * ``distinct`` : 중복을 제거
  * ``limit`` , ``skip`` : 축소, 건너뛰기

### 5.2.0 매핑
- ``map`` 과 ``flatMap`` 을 통해 특정데이터를 **선택** 하는 작업을 할 수 있다.

### 5.2.1 스트림의 각 요소에 함수 적용하기
- ``map`` 은 새로운 버전을 만든다 라는 변환에 가까운 매핑이다.
- 출력으로 스트림형태 Stream 형식을 가진다.
- ``map`` 은 다른 ``map`` 과 연결이 가능하다.

### 5.2.2 스트림 평면화
- "hello" , "world" 을 중복없이 쓰인 알파벳을 구하는 예제

  ```
  words.stream()
       .map(word->word.split(" "))
       .distinct()
       .collect(toList())
  ```

- 위의 코드의 문제는 Stream<String>이 아닌 Stream<String[]> 을 반환한다.
- ``flatMap`` 과 ``Arrays.stream()`` 으로 처리할 수 있다. ``Arrays.stream()`` 는 문자열을 스트림으로 반환한다.

  ```
  words.stream()
       .map(word->word.split(" "))
       .flatMap(Arrays::stream) // 하나의 스트림으로 평면화
       .distinct()
       .collect(toList())
  ```

- ``flatMap`` 는 스트림의 각 값을 다른 스트림으로 만들고 모든 스트림을 **하나의 스트림으로 연결** 하는 기능이다.
- 아래 코드는 (1,2,3) (3,4)를 각 2개의 쌍으로 만드는 예제이다.

  ```
  List<Integer> list = Arrays.asList(1,2,3);
  List<Integer> list2 = Arrays.asList(1,2);

  List<Stream<int[]>> res= list.stream()
    .map(i-> list2.stream()
                  .map(j->new int[] {i,j}))
    .collect(Collectors.toList());
    // map일 경우

  List<int[]> res2= list.stream()
      .flatMap(i-> list2.stream()
                        .map(j->new int[] {i,j}))
      .collect(Collectors.toList());
    // flatMap일 경우
  ```

### 5.3.0 검색과 매칭
- 매칭에는 ``allMatch`` , ``anyMatch`` , ``noneMatch`` , ``findFirst`` , ``findAny`` 가 있다. (boolean 반환)
- 쇼트서킷이란 원하는 결과를 얻었을때 전체스트림을 처리하지 않아도 반환할 수 있는 것을 말한다.
- Optional은 값의 존재,부재 여부를 표현하는 컨테이너 클래스이다.
- 병렬스트림에서 ``findFirst`` 는 찾기 어렵기 때문에 순서가 상관없다면 ``findAny`` 를 사용한다.

### 5.4.0 리듀싱
- 결과가 나올때까지 스트림 모든 요소를 **반복적** 으로 처리해야 하는 것을 리듀싱 연산이라한다.
- ``reduce`` 는 두개의 인수 초기값과 Operator를 가진다.
- 초기값이 없는 경우는 Optional객체를 반환한다.
- ``reduce`` 의 장점
  * 내부 구현으로 병렬화 처리를 할 수 있다.
- ``sorted`` 같은 메서드는 과거 이력을 버퍼에 담아야하므로 모든 요소를 버퍼에 추가해야된다.(stateful operation)

### 5.5 실전 연습
- 학생 추출해보는 리스트 예제
  ```
   //2011년에 일어난 모든 트랜잭션 값을 찾아 오름차순으로 출력
   transactions.stream()
               .filter(trn-> trn.getYear()==2011)
               .sorted((x,y)->Integer.compare(x.getValue(),y.getValue()))
               .forEach(str-> System.out.println(str.toString()));
               Comparator.comparing(Transaction::getValue);

   // 근무하는 도시를 중복없이 출력
   transactions.stream()
               .map(str->str.getTrander().city)
               .distinct()
               .forEach(str-> System.out.println(str));

   // 케임브릿지 근무자 찾아 이름순 정렬
   transactions.stream()
               .filter(str->str.getTrander().city.equals("camb"))
               .sorted((a,b)->(a.getTrander().name.compareTo(b.getTrander().name)))
               .forEach(str-> System.out.println(str));

   // 밀라노에 거래자 있는지?
   transactions.stream()
               .anyMatch(tran->tran.getTrander().getCity().equals("mian"));

   // 전체 트랜잭션 최솟값
   transactions.stream()
               .mapToInt(Transaction::getValue)
               .min()
               .getAsInt();

   transactions.stream()
               .mapToInt(Transaction::getValue)
               .reduce(Integer::min)
               .getAsInt();

   transactions.stream()
               .min(Comparator.comparing(Transaction::getValue))
               .get()
               .getValue();

  ```

### 5.6.1 기본형 스트림
- ``map`` 으로 스트림을 생성할 경우 ``sum`` , ``min`` 등은 직접 호출할 수 없다.
- 그래서 기본형 스트림을 제공한다. ``IntStream`` , ``mapToDouble`` 등
- 기본형 스트림을 다시 스트림으로 바꾸려면 ``boxed`` 를 사용하면 된다.

### 5.6.2 숫자범위
- ``range``, ``rangeClosed`` 지원하고 각각 n제외, n포함이다.

### 5.7.1 스트림 만들기
- ``Stream.of`` 를 사용하면 값으로 스트림을 만들 수 있다. ``Stream.empty`` 로 비울 수 있다.
- ``iterator`` , ``generate`` 로 무한스트림을 생성할 수 있다.
- ``iterator`` 는 요청할 떄 마다 값을 생성할 수 있고 **언바운드 스트림** 이라고 부른다.
- ``generate`` 는 값을 연속적으로 계산하지 않는다. **불변상태** 를 유지하고 싶을때 사용한다.(상태저장이 없다)
- 무한스트림의 요소는 무한적으로 계산이 반복되기 때문에 정렬이나 리듀스 할 수 없다.
- 피보나치 수열

  ```
  Stream.iterate(new int[]{0,1},arr->new int[]{arr[1],arr[0]+arr[1]})
             .limit(10)
             .map(arr->arr[0]) // 각요소 중 첫번째 요소
             .forEach(System.out::println);
  ```

## Chapter 6 - 스트림으로 데이터 수집

### 6.1.0 정리
- **중간연산** 은 한 스트림을 다른 스트림으로 변환하는 연산으로 여러 연산을 **연결** 할 수 있다.
- **최종연산** 은 스트림의 요소를 소비해 최종 **결과** 를 도출한다.

### 6.1.1 컬렉터란 무엇인가
- 함수형 프로그래밍에서는 무엇을 원하는지 직접 명시할 수 있어서 방법을 신경 쓸 필요가 없다.
- 함수형 API 장점은 **조합성** 과 **재사용성**
- 데이터들을 가로로 된 스트림이라는 통에 집어넣고 세로로 연산을 수행해 결과를 추출해서 자료구조에 저장해둔다.

### 6.1.2 미리 정의된 컬렉터
- ``Collectors`` 에서 제공하는 메서드 기능
  * 스트림 요소를 하나의 값으로 리듀스하고 요약
  * 요소 그룹화
  * 요소 분할

### 6.2.0 리듀싱과 요약
- **컬렉터** 로 스트림의 항목을 **컬렉션으로 재구성** 할 수 있다.

### 6.2.1 스트림값에서 최댓값 최소값 검색
- Collectors 클래스에는 ``maxBy`` , ``minBy``  개의 메서드가 있다.

### 6.2.2 요약 연산
- ``summingInt``, ``averagingInt`` 는 객체를 int로 매핑하는 함수를 인수로 받아 값을 구하는 메서드 이다.
- 합계, 평균, 최댓값 등 두개 이상의 연산을 한번에 해야할 경우 ``summarizingInt`` 메서드를 지원한다.
- ``summarizingInt`` 메서드는 반환값으로 IntSummaryStatistics 클래스를 값으로 받는다.

### 6.2.3 문자열 연결
- ``joining`` 메서드를 이용하면 각 객체에 toString 메서드를 호출해서 하나의 문자열로 **연결** 해서 반환한다.
- ``joining`` 메서드는 **StringBuilder** 를 이용해서 문자열을 하나로 만든다.

### 6.2.4 범용 리듀싱 요약연산
- ``reducing``은 초기값, 내부값, Operator 세가지를 인수로 제공받는다. (초기값을 없어도 가능)
- ``reduce`` 메서드를 잘못 사용하면 여러 스레드가 동시 점유로 인해 병렬로 수행할 수 없다.
- **병렬처리** 가 필요한 경우는 ``collet`` 메서드를 쓰는 것이 좋다.
-  ``collet`` 는 결과를 누적하는 컨테이너를 변경, ``reduce`` 는 두값을 하나로 도출하는 불변형 연##
  ***NOTE :*** 제네릭 와일드카드 ? 는 형식의 자유로움을 의미한다.

### 6.3.0 그룹화
- 데이터 집합을 하나이상의 특성으로 분류해 **그룹화** 하는 연산
- ``groupingBy`` 으로 그룹화 할 수 있다.
- 함수를 기준으로 스트림이 그룹화 되기때문에 **분류함수** 라고 부른다.
- 분류기준(>,<) 같이 복잡하는 것은 람다로직으로 구현한다.
  ***NOTE :*** 함수는 독립적으로 존재하는 것을 의미하고 메서드는 클래스에 종속된 것을 의미한다.
  ```
  Map<Dish.Type,List<Dish>> d = menu.stream()
                                    .collect(groupingBy(Dish::getType));
                                    // Dish.Type은 eum형태
  ```

### 6.3.1 다수준 그룹화
- ``groupingBy`` 는 분류함수와 컬렉터를 인수로 받는다. (분류함수가 먼저 실행)
- ``groupingBy`` 는 스트림의 첫번째요소를 찾은 이후에 그룹화 맵에 추가한다.
- ``collectingAndThen`` 으로 컬렉터가 반환한 결과를 다른 형식으로 활용할 수 있다.
- 리듀싱 작업을 할때는 ``groupingBy`` 에 두번째 인수로 전달한 컬렉터를 사용한다.(``summingInt``)
- ``mapping`` 은 변환함수(표시)와 결과 객체를 누적하는 컬렉터를 인수로 받는다.
  ```
  Map<Dish.Type, Dish> map =
				menu.stream()
					.collect(
						groupingBy(Dish::getType,
						collectingAndThen(
						  minBy(Comparator.comparingInt(Dish::getCalories)),
						  Optional::get)));
		// collectingAndThen는 인수로 Comparator, 결과 두개를 받는다.
    // Type으로 분류-> 각각의 서브스트림 -> collectingAndThen
  ```

### 6.4.0 분할
- 분할은 분할 함수라 불리는 프레디케이트를 분류 함수로 사용하는 특수한 그룹화 기능이다.

### 6.4.1 분할의 장점
- 참 거짓 두가지 요소의 스트림 리스트를 모두 유지하는 것이 분할의 장점이다.
- ``partitioningBy`` 는 프리디케이트 & 컬렉터를 인수로 받는다.
  ```
  Map<Boolean,List<Dish>> map
    = menu.stream()
        .collet(partitioningBy(Dish::isVegetarian),groupingBy(Dish::getType));
  ```

### 6.5.0 Collector 인터페이스
- Collector 인터페이스는 리듀싱 연산을 어떻게 구현할지 제공하는 메서드 집합으로 구성
- Collector<T,A,R> 에서 T는 수집될 항목,  A는 누적자(중간결과 누적), R은 연산결과 객체형태
- 누적과정에서 사용되는 객체가 수집과정의 최종결과로 사용

### 6.5.1 Collector 인터페이스 메서드 살펴보기
- ``collect`` 메서드에서 실행하는 함수를 반환하는 메서드
  * ``supplier`` 는 빈누적자 인스턴스를 만드는 파라미터가 없는 함수
  * ``accumulator`` 는 리듀싱 연상을 수행하는 함수를 반환
  * ``finisher`` 는 누적자 객체를 최종 결과로 변환하면서 누적과정을 끝낼때 호출할 함수를 반환
  * ``combiner``는 서로 다른 서브 스트림을 병렬로 처리할 때 누적자가 판단하여 병합한다
- Charaterisitic 메서드는 컬렉터의 연산을 정의하는 Charaterisitic 형식의 불변 집합을 반환한다.

## Chapter 7 - 병렬데이터 처리와 성능

### 7.1.0 병렬스트림
- 외부반복을 **내부반복** 으로 바꾸면 네이티브 자바 라이브러리가 스트림 요소의 처리를 제어 할 수 있다.
  >> 개발자가 컬렉션 데이터 처리 속도를 높이려고 따로 고민할 필요가 없다.

- ``parallelStream`` 을 호출하면 병렬스트림이 생성된다. 이는 여러 청크로 분할한 스트림이다.
- 병렬 스트림을 이용하면 멀티코어 **프로세서들** 이 각각의 청크를 처리하도록 할당할 수 있다.

### 7.1.1 순차스트림을 병렬 스트림으로 변환하기
- ``parallel`` 과 ``sequential`` 중에 가장 **마지막** 에 선언된 것이 실행된다.
- 순차 스트림에 ``parallel`` 을 호출하면 스트림 자체에는 아무 변화가 일어나지 않는다.
- 그 이유는 내부적으로 연산이 병렬로 수행해야 함을 의미하는 **boolean 플래그** 가 설정된다.
- 병렬 스트림은 내부적으로 **ForkJoinPool** 을 사용한다. 기본적으로 프로세서 수에 상응하는 스레드를 갖는다.

### 7.1.2 스트림 성능 측정
- for 루프는 저수준으로 작동해 기본값의 박싱이 없기 때문에 수행속도가 빠르다.
- iterate 연산은 결과에 따라 다음 함수 입력이 **달라지기** 때문에 청크로 분할하기 어렵다.
- 기본형Stream은 쉽게 청크로 분할할 수 있는 숫자 범위를 생산한다.
- 병렬화를 이용하기 위해서는 몇 단계의 과정이 필요하다.
  * 스트림을 재귀적으로 분할
  * 각 서브스트림을 서로 다른 스레드의 리듀싱 연산으로 할당
  * 결과값을 다시 하나로 합침
- 코어간 데이터 전송시간보다 오래 걸리는 작업만 병렬로 처리하는 것이 바람직하다.

### 7.1.3 병렬 스트림의 올바른 사용법
- 많이 잘못 사용하는 부분은 **공유된 상태** 를 바꾸는 알고리즘을 사용하기 때문에 발생한다.
- 병렬 계산에서는 공유된 가변 상태를 피해야 한다.

### 7.1.4 병렬 스트림 효과적으로 사용하기
- 언제나 병렬 스트림이 순차스트림보다 **빠른 것** 은 아니다.
- 오토박싱 문제에 주의하여야 한다.
- ``limit`` , ``findFirst`` 처럼 요소 **순서에 의존** 하는 연산은 병렬처리에서 성능이 떨어진다.
- 소량의 데이터에서는 병렬 스트림이 도움되지 않는다.
- 최종 연산의 병합과정 비용을 살펴봐야 한다.

### 7.2.0 포크/조인 프레임워크
- 병렬화할 수 있는 작업을 **재귀적** 으로 작은 작업으로 분할한다.
- 그 후에 서브태스크 각각의 결과를 합쳐서 전체 결과를 만들도록 설계한다.
- 포크/조인 프레임워크는 작업자 스레드에 분산 할당하는 ExecutorService 인터페이스를 구현한다.
- **포크** 는 태스크를 분할하는 것을 의미하고 **조인** 은 결과를 합치는 것을 의미한다.
- 하나의 작업을 여러 개로 분할하여 큐에 담고 스레드는 접근하여 자신의 큐로 옮긴다.
- 이때 스레드가 다른스레드 큐의 작업에도 접근 가능하다.  
- [포크조인에 대한 이해 참고](https://okky.kr/article/345720)


### 7.2.1 RecursiveTask 활용
- 스레드 풀을 이용하려면 **RecursiveTask** 의 서브클래스를 만들어야 한다.
- RecursiveTask를 정의하려면 ``compute`` 를 구현해야한다.
- ``compute`` 메서드는 서브태스크로 더이상 분할할 수 없을때 각각의 서브태스크 결과 만들 로직을 정의한다.
- 둘이상의 ForkJoinPool을 쓰지 않는다. 한번만 인스턴스화 해서 **싱글턴** 으로 저장한다.
- 포크/조인 프레임워크로 병렬합계 수행
  ```
    public class Main{
    	public static void main(String[] args) throws InterruptedException, ExecutionException {
    		int[] arr = IntStream.range(1, 11).toArray();
    		ForkJoinTask<Integer> task = new Cal(arr);
    		int res = new ForkJoinPool().invoke(task);
    		// 일을 Pool에 넣는다. 모든프로세서가 자유롭게 접근한다.
    		System.out.println(res);
    	}
    }


    class Cal extends RecursiveTask<Integer>{
    	private final int[] numbers;
    	private final int start;
    	private final int end;
    	private final int THRESHOLD = 2;

    	private Cal(int[] numbers, int start, int end) {
    		this.numbers = numbers;
    		this.start = start;
    		this.end = end;
    	}
    	public Cal(int[] numbers) {
    		this(numbers,0,numbers.length);
    	}
    	@Override
    	protected Integer compute() {
    		int length = end - start;
    		if(length <= THRESHOLD) {
    		// 지금 이 스레드에서 처리하겠다. THRESHOLD까지
    			return computeSeq();
    		}

    		Cal left = new Cal(numbers,start,start+length/2);
    		left.fork(); // 비동기로 left 객체를 서브태스크로 분할
    		Cal right = new Cal(numbers,start+length/2,end);
    		int rightResult = right.compute();// 오른쪽 재귀
    		int leftResult = left.join();
    		// 위에서 서브태스크로 분할하여 다른스레드의 처리가 올때까지 기다렸다 합침
    		System.out.println("조인함 : "+(leftResult+rightResult)
    						  +" 처리쓰레드 : "+Thread.currentThread().getName());
    		return leftResult+rightResult;
    	}

    	private int computeSeq() {
    		int sum = 0;
    		for(int i = start; i < end; i++) {
    			sum+= numbers[i];
    		}
    		System.out.println("합계함 : "+start+" 끝 : "+(end-1)+" 결과 : "+sum
    				+" 처리쓰레드 : "+Thread.currentThread().getName());
    		return sum;
    	}

    	/********************************************************
    	  출력
        ----

    	  합계함 : 8 끝 : 9 결과 : 19 처리쓰레드 : ForkJoinPool-1-worker-1
    		합계함 : 5 끝 : 6 결과 : 13 처리쓰레드 : ForkJoinPool-1-worker-3
    		합계함 : 0 끝 : 1 결과 : 3 처리쓰레드 : ForkJoinPool-1-worker-4
    		합계함 : 2 끝 : 2 결과 : 3 처리쓰레드 : ForkJoinPool-1-worker-3
    		합계함 : 7 끝 : 7 결과 : 8 처리쓰레드 : ForkJoinPool-1-worker-1
    		조인함 : 27 처리쓰레드 : ForkJoinPool-1-worker-1
    		합계함 : 3 끝 : 4 결과 : 9 처리쓰레드 : ForkJoinPool-1-worker-2
    		조인함 : 40 처리쓰레드 : ForkJoinPool-1-worker-1
    		조인함 : 12 처리쓰레드 : ForkJoinPool-1-worker-2
    		조인함 : 15 처리쓰레드 : ForkJoinPool-1-worker-2
    		조인함 : 55 처리쓰레드 : ForkJoinPool-1-worker-1
    		55

    	 *******************************************************/

    }

  ```

### 7.2.2 포크/조인 프레임워크 제대로 사용하는 방법
- 두 서브태스크가 모두 시작된 다음에 ``join`` 을 호출해야한다.
- 순차코드에서 병렬 계산을 시작할때만 ``invoke`` 를 사용한다.
- 한쪽 작업에는 ``compute``를 사용하는 것이 효과적이다.
  >> 같은 스레드를 재사용하여 불필요한 서브태스크 할당을 피할 수 있음

- 순차처리 보다 무조건 빠르지 않다. 서브태스크 실행시간이 **포킹시간** 보다 길어야한다.

### 7.2.3 작업 훔치기
- 코어 개수와 상관없이 적절한 크기로 분할된 태스크를 포킹하는 것이 바람직하다.
- **작업훔치기** 기법에서는 스레드를 공정하게 분할한다.
- 스레드는 이중 연결리스트를 가지고 있어 이 꼬리를 참조하여 다른 스레드의 작업을 가져온다.

### 7.3.0 Spliterator
- 자바8에서 제공하는 인터페이스
- **Spliterator** 는 분할할 수 있는 반복자라는 의미이다.
- Iterator 처럼 요소 탐색기능을 제공하지만 **병렬작업** 에 특화된 특징이 있다.
  * ``tryAdvance`` 는 요소를 하나씩 소비하면서 요소가 남아있으면 반환한다.
  * ``trySplit`` 는 일부 요소를 분할해서 새로운 Spliterator를 생성한다.
  * ``estimateSize`` 는 탐색해야할 요소수를 반환한다.

### 7.3.1 분할과정
- 스트림을 여러 스트림으로 분할하는 과정은 **재귀적** 으로 일어난다.
- ``trySplit`` 의 결과가 null 일때까지 반복한다.
- ``charaterisitic`` 은 Spliterator 자체 특성 집합을 포함하는 int를 반환한다.
  * ORDERED, DISINCT, SIZED 등 (p.248 참고)

### 7.3.2 커스텀 Spliterator 구현
- 탐색하려는 데이터를 포함하는 스트림을 어떻게 병렬화할 것인지 정의         

#### 링크
- [Part3](/Part3.md)

#### 출처
- Java8 in Action
