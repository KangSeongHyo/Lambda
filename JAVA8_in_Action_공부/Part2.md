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
- ``map`` 과 ``flatMap`` 을 통해 특정데이터를 선택하는 작업을 할 수 있다.

### 5.2.1 스트림의 각 요소에 함수 적용하기
- ``map`` 은 '새로운 버전'을 만든다라는 변환에 가까운 매핑이다.
- 출력으로 스트림형태 Stream<Object> 형식을 가진다.
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
- ``flatMap`` 는 스트림의 각 값을 다른 스트림으로 만들고 모든 스트림을 하나의 스트림으로 연결하는 기능이다.
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
- 결과가 나올때까지 스트림 모든 요소를 반복적으로 처리해야 하는 것을 리듀싱 연산이라한다.
- ``reduce`` 는 두개의 인수 초기값과 Operator를 가진다.
- 초기값이 없는 경우는 Optional객체를 반환한다.
- ``reduce`` 의 장점
  * 내부 구현으로 병렬화 처리를 할 수 있다.
- ``sorted`` 같은 메서드는 과거 이력을 버퍼에 담아야하므로 모든 요소를 버퍼에 추가해야된다.(stateful operation)

### 5.5 실전 연습
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
- ``iterator`` 는 요청할 떄 마다 값을 생성할 수 있고 언바운드 스트림이라고 부른다.
- ``generate`` 는 값을 연속적으로 계산하지 않는다. 불변상태를 유지하고 싶을때 사용한다.(상태저장이 없다)
- 무한스트림의 요소는 무한적으로 계산이 반복되기 때문에 정렬이나 리듀스 할 수 없다.
- 피보나치 수열
  ```
  Stream.iterate(new int[]{0,1},arr->new int[]{arr[1],arr[0]+arr[1]})
             .limit(10)
             .map(arr->arr[0]) // 각요소 중 첫번째 요소
             .forEach(System.out::println);
  ```

#### 링크
- [part1](/part1)

#### 출처
- Java8 in Action
