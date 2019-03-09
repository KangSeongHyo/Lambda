# JAVA8 in Action - part1

## Chapter 1 - JAVA8 이란

### 1.1.1 JAVA8 특징
- JAVA8에서 **간단한 병렬처리** 를 제공한다.
    > 기존에는 CPU 코어를 멀티로 활용하려면 Thread 객체 이용했지만 관리가 어렵다는 단점이 있다.

- 함수형 프로그래밍을 위한 **스트림 API** 가 추가 되었다.
- 메서드를 다른 메서드의 인수로 넘겨주는 기능이 추가되었다.(동작의 파라미터화)

    > 메서드를 다른 메서드로 전달할 방법이 없어서 객체를 만들어서 넘기는 복잡한 방법이 있었다.

- 인터페이스의 **디폴트 메서드** 를 이용해서 함수를 구현 할 수 있다.


### 1.1.2 스트림 처리
- 스트림이란 한번에 한개씩 만들어지는 **연속적인 데이터틀** 이다.
- 기존에 한번에 한항목을 처리하던 것을 스트림으로 만들어서 처리 할 수 있다.
- 스트림 파이프라인을 활용하면 여러 cpu 코어에 쉽게 할당(병렬) 할 수 있다.


### 1.2.1 메서드와 람다
- 메서드를 값으로 전달하는 **메서드 레퍼런스** 기능이 추가 되었다.
- 메서드 레퍼런스 연산자는 ``::`` 으로 객체 안에 포함된 메서드를 참조하라는 표현이다.
- 인터페이스를 활용하여 함수도 값으로 취급 할 수 있다.
  ```
  public void name(){
       List<Name> list = Arrays.asList(new Name("seong","kang")
       ,new Name("jin","kim"),new Name("young","choi"));

       Collections.sort(list,Name::compareTo);

   }

   static class Name{
       String first_name;
       String last_name;
       public Name(String first_name, String last_name) {
           this.first_name = first_name;
           this.last_name = last_name;
       }

       public int compareTo(Name o) {
           return last_name.compareTo(o.last_name);
       }
   }

  ```

### 1.3.1 스트림과 컬렉션
- 컬렉션은 반복과정을 직접 처리하는 **외부반복** , 스트림은 라이브러리 내부에서 처리되는 **내부반복** 방식이다.
- 멀티코어 활용의 어려움을 스트림 API로 해결했다.

  => 멀티스레딩 환겨에서 각각의 스레드느 동시에 공유된 데이터에 접근, 갱신할 수 있기 때문에 스레드를 잘 제어하지 못하면 데이터 상에 오류가 발생할 수 있다.

- 스트림 API는 데이터들을 **필터링**, **추출**, **그룹화** 하는 것이 가능하다.
- 컬력션은 데이터를 저장,접근에 중점을 두고 스트림은 데이터에 어떤 계산을 할 것인지에 중점을 둔다.
- 스트림 병렬처리는 라이브러리에서 큰 스트림을 병렬로 처리할 수 있게 작은 스트림으로 분할시킨다.

  ***NOTE*** : 여러 CPU로 데이터를 나눠서 처리하는 것을 포킹이라 한다.

### 1.4 디폴트 메서드
- JAVA8은 구현하지 않아도 되는 메서드를 인터페이스가 포함할 수 있는 기능을 제공한다.
- 디폴트 메서드를 포함한 여러 인터페이스를 활용하면 다중상속과 비슷한 기능을 사용할 수 있다.

## Chapter 2 - 동작파라미터화

### 2.1.1 요구사항에 대응하기
- 소비자의 요구는 항상 바뀔 수 있기 때문에 이러한 변화에 대응하기에 동작파라미터화가 필요하다.
- **동작파라미터화** 는 어떻게 실행할건지 결정하지 않은 코드블록을 의미한다.
- 위의 코드블록을 메서드의 인수로 전달되기 때문에 받은 메서드에서 코드블록은 파라미터화 된다.

### 2.1.2 농부의 변덕
- 농부가 처음에 사과를 원했다가 과일종류를 원하고 또 무게를 원하기도 하고 둘다 원하기까지 한다.
- 이런 코드를 짤때 중요한것은 DRY(Don't Repeat Yourself) 해야 한다.

### 2.2.1 동작파라미터화
- 속성에 기초하여 boolean 값을 반환하는 Predicate를 구현하고 인수로 이를 파라미터로 넘긴다.
- 이를 활용하면 한개의 파라미터로 여러 동작이 가능해 **유연한 코드** 를 만들 수 있다.

  ```
      public static void main(String[] args) {

          List<Student> list = Arrays.asList(new Student(25, "김철수", "남"),
                             new Student(22, "최영희", "여"),
                             new Student(23, "임지현", "여"));

          StdntPredicate manPredicate = new ManPredicate();
          StdntPredicate agePredicate = new AgePredicate();

          System.out.println(find(list, manPredicate));
          System.out.println(find(list, agePredicate));

        }

       public static Student find(List<Student> list, StdntPredicate sp) {

            for(Student stu : list) {
              if(sp.isSame(stu)) {
                return stu;
              }
            }

          return null;
       }

      interface StdntPredicate{
          boolean isSame(Student stu);
      }

      class ManPredicate implements StdntPredicate{
          @Override
          public boolean isSame(Temp.Student stu) {
            if(stu.gender.equals("남")) return true;
            return false;
           }
      }

      class AgePredicate implements StdntPredicate{
        @Override
        public boolean isSame(Temp.Student stu) {
          if(stu.age == 23) return true;
          return false;
        }
     }

     /*** 실행결과 ***/
     [age=25, name=김철수, gender=남]
     [age=23, name=임지현, gender=여]

  ```

### 2.3.1 복잡한 과정 간소화
- 위의 코드는 새로운 동작을 만들기 위해서는 각각의 클래스를 만들어서 인스턴스화하는 번거로움이 있다.
- 이를 해결하는 클래스 선언과 인스턴스화를 동시에 할 수 있는 익명클래스는 장황한 단점이 있다.
- 람다 표현식을 사용하면 위의 코드를 간결하게 나타낼 수 있다.
  ```
    System.out.println(find(list, (Student stu)->stu.gender.equals("남")));
    System.out.println(find(list, (Student stu)->stu.age == 23));

    /*** 실행결과 ***/
    [age=25, name=김철수, gender=남]
    [age=23, name=임지현, gender=여]

  ```

## Chapter 3 - 람다 표현식

### 3.1.1 람다란?
- 익명클래스처럼 이름없는 함수이면서 메서드를 인수로 전달할 수 있다.
- 람다표현식은 메서드로 전달할 수 있는 **익명함수를 단순화** 한 것이라 할 수 있다.
- 람다의 특징
  * 익명, 간결
  * 함수 : 람다는 메서드처럼 특정클래스에 종속 되지 않는다.(예외리스트 가능)
  * 전달 : 람다표현식을 인수로 전달하거나 변수로 저장할 수 있다.
- 람다의 구성
  * 파라미터, 화살표(->), 바디

### 3.2.1 함수형 인터페이스
- 위의 Predicate가 함수형 인터페이스라 할 수 있다.
- 함수형 인터페이스는 **하나의 추상메서드** 를 지정하는 인터페이스이다. (ex. Runnable, Comparator)
- 디폴트 메서드를 많이 가지고 있어도 추상메서드가 하나라면 함수형 인터페이스이다.

### 3.2.2 함수 디스크립터
- 람다 표현식의 시그니처를 서술하는 메서드를 **함수디스크립터** 라고 부른다.
  * 시그니처는 파라미터, 반환값을 통칭한다.

### 3.3.1 람다 활용
- 설정 - 처리 - 정리로 처리과정이 이처럼 두과정의 형태를 갖는 형식이 **실행 어라운드 패턴** 이다.
- 함수형 인터페이스를 선언하고 원하는 메서드에 파라미터로 구현을 하면 간결한 코드를 작성할 수 있다.

### 3.4.1 함수형 인터페이스 사용
- java.util.function 패키지에 여러가지 새로운 함수형 인터페이스를 추가했다.
  * Predicate : T 객체를 받아 boolean으로 처리한다.
  * Consumer  : T 객체를 받아 void로 처리한다.
  * Function  : T 객체를 받아 R로 처리한다.
- 기본형을 참조형으로 변환하는 것을 박싱, 반대의 경우는 언박싱이라고 한다.
- 박싱값은 기본형을 감싸는 Wrapper이게 되고 힙에 저장된다.

  > 박싱한 값은 메모리를 더 소비하고 기본형을 가져올때도 메모리를 탐색하는 과정이 필요하다.

- JAVA8에서는 이런 **오토박싱** 문제를 해결하기 위한 기본형 인터페이스를 제공한다.
- 예외는 명시적으로 try-catch를 사용할 수 있다.

### 3.5.1 형식 검사
- 람다표현식 자체는  어떤 함수형 인터페이스를 구현하느지 정보가 포함되어 있지 않다.
- context를 이용해서 람다의 type을 추론할 수 있다.
- 람다의 표현형식을 target type이라고 부른다.

### 3.5.2 같은 람다 , 다른 함수형 인터페이스
- 같은 람다 표현식이더라도 호환되는 추상메서드를 가진 다른 함수형 인터페이스로 사용될 수 있다.

### 3.5.3 Type 추론
- 컴파일러는 함수 디스크립터를 통해서 람다의 시그니처를 추론할 수 있다.
- 파라미터 형식을 배제하거나 하지 않거나는 개발자 스스로에게 달려있다.

### 3.5.4 지역변수 사용
- 람다 표현식 안에서도 지역변수를 사용 할 수 있다. 이를 **람다 캡처링** 이라고 한다.
- 지역 변수의 조건은 **한 번만 할당** 할 수 있어야 한다. (final)
- 인스턴스 변수는 힙에 저장되고 지역변수는 스택에 위치한다
- 람다를 실행하는 스레드에서는 지역변수에 직접 접근하는 것이 아닌 **복사본** 을 제공받는다.
- 람다는 변수가 아닌 값에 국한되어 동작을 수행한다.
- **클로저** 란 함수의 비지역 변수를 자유롭게 참조할 수 있는 함수의 인스턴스를 가르킨다.
- 클로저는 클로저 외부에 정의된 변수값에 접근하고 값을 바꿀 수 있다.

### 3.6.1 메서드 레퍼런스
- 메서드 레버런스를 이용하면 기존 메서드 정의를 재활용해서 람다처럼 전달할 수 있다. (System.out::print)
- 메서드 레퍼런스는 특정 메서드만을 호출하는 **람다의 축약형** 이라고 할 수 있다.
- 명시적으로 메서드명을 참조해서 **가독성** 을 높일 수 있다.
- 객체 자체를 레퍼런스 할수도 있다. (student::getName)
- 생성자 레퍼런스는 new 키워드를 이용한다.(Student::new)

### 3.8.1 Comparator의 메서드
- Comparator.``comparing``(Student::getAge) 이용해 비교값을 추출할 수 있다.
- Comparator.comparing().``reversed``로 역정렬 할 수 있다.
- Comparator.``thenComparing``으로 두번째비교 연산자를 만들 수 있다.
  ```
    list.stream().sort(Comparator.comparing(Student::getAge))
        .thenComparing(Student::getName)
    나이순으로 정렬하되 나이가 같으면 이름순으로 정렬
  ```

### 3.8.2 Predicate의 메서드
- ``negate``(반전), ``and``(&&), ``or``(||) 세가지 메서드를 제공한다.
  ```
    Predicate<Student> thirdAndMiddle =
        third.and(s->s.getSchool.equals("Middle"));
    3학년이면서 중학교 다니는 학생    
  ```

### 3.8.3 Function의 메서드
- ``andThen``, ``compose`` 두가지 메서드를 제공한다.
- andThen는 먼저 실행된 람다식의 결과를 가지고 람다식을 수행한다.(g(f(x)))
- compose는 나중에 정의된 람다식의 결과를 먼저 정의된 람다식의 인수로 사용한다.(f(g(x)))

#### 링크
- [Part2](/Part2)

#### 출처
- Java8 in Action
