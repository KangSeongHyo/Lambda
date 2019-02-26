# JAVA8 in Action - part1

## Chapter 1 - JAVA8 이란

### 1.1.1 JAVA8 특징
- JAVA8에서 간단한 병렬처리를 제공한다.
    > 기존에는 CPU 코어를 멀티로 활용하려면 Thread 객체 이용했지만 관리가 어렵다는 단점이 있다.

- 함수형 프로그래밍을 위한 스트림 API가 추가 되었다.
- 메서드를 다른 메서드의 인수로 넘겨주는 기능이 추가되었다.(동작의 파라미터화)

    > 메서드를 다른 메서드로 전달할 방법이 없어서 객체를 만들어서 넘기는 복잡한 방법이 있었다.

- 인터페이스의 디폴트 메서드를 이용해서 함수를 구현 할 수 있다.

### 1.1.2 스트림 처리
- 스트림이란 한번에 한개씩 만들어지는 연속적인 데이터틀이다.
- 기존에 한번에 한항목을 처리하던 것을 스트림으로 만들어서 처리 할 수 있다.
- 스트림 파이프라인을 활용하면 여러 cpu 코어에 쉽게 할당(병렬) 할 수 있다.


### 1.2.1 메서드와 람다
- 메서드를 값으로 전달하는 메서드 레퍼런스 기능이 추가 되었다.
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
- 컬렉션은 반복과정을 직접 처리하는 외부반복, 스트림은 라이브러리 내부에서 처리되는 내부 반복방식이다.
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
- 동작파라미터화는 어떻게 실행할건지 결정하지 않은 코드블록을 의미한다.
- 위의 코드블록을 메서드의 인수로 전달되기 때문에 받은 메서드에서 코드블록은 파라미터화 된다.

### 2.1.2 농부의 변덕
- 농부가 처음에 사과를 원했다가 과일종류를 원하고 또 무게를 원하기도 하고 둘다 원하기까지 한다.
- 이런 코드를 짤때 중요한것은 DRY(Don't Repeat Yourself) 해야 한다.

### 2.2.1 동작파라미터화
- 속성에 기초하여 boolean 값을 반환하는 Predicate를 구현하고 인수로 이를 파라미터로 넘긴다.
- 이를 활용하면 한개의 파라미터로 여러 동작이 가능해 유연한 코드를 만들 수 있다.

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

#### 출처
- Java8 in Action
